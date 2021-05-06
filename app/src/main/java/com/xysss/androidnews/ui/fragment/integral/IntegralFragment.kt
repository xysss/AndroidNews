package com.xysss.androidnews.ui.fragment.integral

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.*
import com.xysss.androidnews.app.weight.recyclerview.SpaceItemDecoration
import com.xysss.androidnews.data.model.bean.BannerResponse
import com.xysss.androidnews.data.model.bean.IntegralResponse
import com.xysss.androidnews.databinding.FragmentIntegralBinding
import com.xysss.androidnews.ui.adapter.IntegralAdapter
import com.xysss.androidnews.viewmodel.request.RequestIntegralViewModel
import com.xysss.androidnews.viewmodel.state.IntegralViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.navigateAction
import com.xysss.jetpackmvvm.ext.view.gone
import com.xysss.jetpackmvvm.ext.view.notNull
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_integral.*
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * Author:bysd-2
 * Time:2021/4/3014:20
 * 描述　:积分排行
 */

class IntegralFragment : BaseFragment<IntegralViewModel, FragmentIntegralBinding>() {
    private var rank: IntegralResponse? = null

    //适配器
    private lateinit var integralAdapter: IntegralAdapter

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //请求的ViewModel /** */
    private val requestIntegralViewModel: RequestIntegralViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_integral

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        rank = arguments?.getParcelable("rank")
        rank.notNull({
            mViewModel.rank.set(rank)
        }, {
            integral_cardview.gone()
        })
        integralAdapter = IntegralAdapter(arrayListOf(), rank?.rank ?: -1)
        toolbar.run {
            inflateMenu(R.menu.integral_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.integral_guize -> {
                        nav().navigateAction(R.id.action_to_webFragment,
                            Bundle().apply {
                                putParcelable(
                                    "bannerdata",
                                    BannerResponse(
                                        title = "积分规则",
                                        url = "https://www.wanandroid.com/blog/show/2653"
                                    )
                                )
                            }
                        )
                    }
                    R.id.integral_history -> {
                        nav().navigateAction(R.id.action_integralFragment_to_integralHistoryFragment)
                    }
                }
                true
            }
            initClose("积分排行") {
                nav().navigateUp()
            }
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestIntegralViewModel.getIntegralData(true)
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), integralAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                requestIntegralViewModel.getIntegralData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            requestIntegralViewModel.getIntegralData(true)
        }
        appViewModel.appColor.value?.let {
            setUiTheme(it,
                integral_merank, integral_mename, integral_mecount
            )
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestIntegralViewModel.getIntegralData(true)
    }

    override fun createObserver() {
        requestIntegralViewModel.integralDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, integralAdapter, loadsir, recyclerView,swipeRefresh)
        })
    }

}