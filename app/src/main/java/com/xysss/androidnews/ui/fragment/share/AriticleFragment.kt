package com.xysss.androidnews.ui.fragment.share

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.eventViewModel
import com.xysss.androidnews.app.ext.*
import com.xysss.androidnews.app.weight.recyclerview.SpaceItemDecoration
import com.xysss.androidnews.databinding.FragmentListBinding
import com.xysss.androidnews.ui.adapter.ShareAdapter
import com.xysss.androidnews.viewmodel.request.RequestAriticleViewModel
import com.xysss.androidnews.viewmodel.state.AriticleViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.navigateAction
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:57
 */

class AriticleFragment : BaseFragment<AriticleViewModel, FragmentListBinding>() {

    //适配器
    private val articleAdapter: ShareAdapter by lazy { ShareAdapter(arrayListOf()) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //记得要写泛型，虽然在 by lazy中 提示不用写，但是你不写就会报错
    private val requestViewModel: RequestAriticleViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.run {
            initClose("我分享的文章") {
                nav().navigateUp()
            }
            inflateMenu(R.menu.todo_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.todo_add -> {
                        nav().navigateAction(R.id.action_ariticleFragment_to_addAriticleFragment)
                    }
                }
                true
            }
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestViewModel.getShareData(true)
        }

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                requestViewModel.getShareData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            requestViewModel.getShareData(true)
        }

        articleAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable("ariticleData", articleAdapter.data[position])
                })
            }
            addChildClickViewIds(R.id.item_share_del)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_share_del -> {
                        showMessage("确定删除该文章吗？", positiveButtonText = "删除", positiveAction = {
                            requestViewModel.deleteShareData(
                                articleAdapter.data[position].id,
                                position
                            )
                        }, negativeButtonText = "取消")
                    }
                }
            }
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestViewModel.getShareData(true)
    }

    override fun createObserver() {
        requestViewModel.shareDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadsir, recyclerView,swipeRefresh)
        })
        requestViewModel.delDataState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                //删除成功 如果是删除的最后一个了，那么直接把界面设置为空布局
                if (articleAdapter.data.size == 1) {
                    loadsir.showEmpty()
                }
                articleAdapter.remove(it.data!!)
            } else {
                //删除失败，提示
                showMessage(it.errorMsg)
            }
        })
        eventViewModel.shareArticleEvent.observeInFragment(this, Observer {
            if (articleAdapter.data.size == 0) {
                //界面没有数据时，变为加载中 增强一丢丢体验
                loadsir.showLoading()
            } else {
                //有数据时，swipeRefresh 显示刷新状态
                swipeRefresh.isRefreshing = true
            }
            requestViewModel.getShareData(true)
        })
    }
}