package com.xysss.androidnews.ui.fragment.search

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.eventViewModel
import com.xysss.androidnews.app.ext.*
import com.xysss.androidnews.app.weight.loadcallback.ErrorCallback
import com.xysss.androidnews.app.weight.recyclerview.SpaceItemDecoration
import com.xysss.androidnews.data.model.bean.CollectBus
import com.xysss.androidnews.databinding.FragmentListBinding
import com.xysss.androidnews.ui.adapter.AriticleAdapter
import com.xysss.androidnews.viewmodel.request.RequestCollectViewModel
import com.xysss.androidnews.viewmodel.request.RequestSearchViewModel
import com.xysss.androidnews.viewmodel.state.SearchViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.navigateAction
import com.xysss.jetpackmvvm.ext.parseState
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:56
 */

class SearchResultFragment : BaseFragment<SearchViewModel, FragmentListBinding>() {

    private var searchKey = ""

    //适配器
    private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf(), true) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //收藏viewmodel
    private val requestCollectViewModel: RequestCollectViewModel by viewModels()

    /** */
    private val requestSearchViewModel: RequestSearchViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let { arguments -> arguments.getString("searchKey")?.let { searchKey = it } }

        toolbar.initClose(searchKey) {
            nav().navigateUp()
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestSearchViewModel.getSearchResultData(searchKey, true)
        }

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                requestSearchViewModel.getSearchResultData(searchKey, false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            requestSearchViewModel.getSearchResultData(searchKey, true)
        }

        articleAdapter.run {
            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    requestCollectViewModel.uncollect(item.id)
                } else {
                    requestCollectViewModel.collect(item.id)
                }
            }
            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable("ariticleData", articleAdapter.data[position])
                })
            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_home_author, R.id.item_project_author -> {
                        nav().navigateAction(
                            R.id.action_searchResultFragment_to_lookInfoFragment,
                            Bundle().apply {
                                putInt("id", articleAdapter.data[position].userId)
                            })
                    }
                }
            }
        }

    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestSearchViewModel.getSearchResultData(searchKey, true)
    }

    override fun createObserver() {
        requestSearchViewModel.seachResultData.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                swipeRefresh.isRefreshing = false
                //请求成功，页码+1
                requestSearchViewModel.pageNo++
                if (it.isRefresh() && it.datas.size == 0) {
                    //如果是第一页，并且没有数据，页面提示空布局
                    loadsir.showEmpty()
                } else if (it.isRefresh()) {
                    //如果是刷新的，有数据
                    loadsir.showSuccess()
                    articleAdapter.setList(it.datas)
                } else {
                    //不是第一页
                    loadsir.showSuccess()
                    articleAdapter.addData(it.datas)
                }
                recyclerView.loadMoreFinish(it.isEmpty(), it.hasMore())
            }, {
                //这里代表请求失败
                swipeRefresh.isRefreshing = false
                if (articleAdapter.data.size == 0) {
                    //如果适配器数据没有值，则显示错误界面，并提示错误信息
                    loadsir.setErrorText(it.errorMsg)
                    loadsir.showCallback(ErrorCallback::class.java)
                } else {
                    recyclerView.loadMoreError(0, it.errorMsg)
                }
            })
        })
        requestCollectViewModel.collectUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                eventViewModel.collectEvent.value = CollectBus(it.id, it.collect)
            } else {
                showMessage(it.errorMsg)
                for (index in articleAdapter.data.indices) {
                    if (articleAdapter.data[index].id == it.id) {
                        articleAdapter.data[index].collect = it.collect
                        articleAdapter.notifyItemChanged(index)
                        break
                    }
                }
            }
        })
        appViewModel.run {
            //监听账户信息是否改变 有值时(登录)将相关的数据设置为已收藏，为空时(退出登录)，将已收藏的数据变为未收藏
            userInfo.observeInFragment(this@SearchResultFragment, Observer {
                if (it != null) {
                    it.collectIds.forEach { id ->
                        for (item in articleAdapter.data) {
                            if (id.toInt() == item.id) {
                                item.collect = true
                                break
                            }
                        }
                    }
                } else {
                    for (item in articleAdapter.data) {
                        item.collect = false
                    }
                }
                articleAdapter.notifyDataSetChanged()
            })

            //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
            eventViewModel.collectEvent.observeInFragment(this@SearchResultFragment, Observer {
                for (index in articleAdapter.data.indices) {
                    if (articleAdapter.data[index].id == it.id) {
                        articleAdapter.data[index].collect = it.collect
                        articleAdapter.notifyItemChanged(index)
                        break
                    }
                }
            })

        }

    }
}