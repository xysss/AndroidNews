package com.xysss.androidnews.ui.fragment.publicNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.*
import com.xysss.androidnews.app.weight.loadcallback.ErrorCallback
import com.xysss.androidnews.databinding.FragmentViewpagerBinding
import com.xysss.androidnews.viewmodel.request.RequestPublicNumberViewModel
import com.xysss.jetpackmvvm.ext.parseState
import kotlinx.android.synthetic.main.include_viewpager.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:56
 * 描述　:聚合公众号
 */

class PublicNumberFragment : BaseFragment<RequestPublicNumberViewModel, FragmentViewpagerBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //fragment集合
    private var fragments: ArrayList<Fragment> = arrayListOf()

    //标题集合
    private var mDataList: ArrayList<String> = arrayListOf()

    override fun layoutId() = R.layout.fragment_viewpager

    override fun initView(savedInstanceState: Bundle?)  {
        //状态页配置
        loadsir = loadServiceInit(view_pager) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.getPublicTitleData()
        }
        //初始化viewpager2
        view_pager.init(this,fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager,mDataList)
        appViewModel.appColor.value?.let { setUiTheme(it, viewpager_linear,loadsir) }
    }

    /**
     * 懒加载
     */
    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        //请求标题数据
        mViewModel.getPublicTitleData()
    }

    override fun createObserver() {
        mViewModel.titleData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                mDataList.addAll(it.map { it.name })
                it.forEach { classify ->
                    fragments.add(PublicChildFragment.newInstance(classify.id))
                }
                magic_indicator.navigator.notifyDataSetChanged()
                view_pager.adapter?.notifyDataSetChanged()
                view_pager.offscreenPageLimit = fragments.size
                loadsir.showSuccess()
            }, {
                //请求项目标题失败
                loadsir.showCallback(ErrorCallback::class.java)
                loadsir.setErrorText(it.errorMsg)
            })
        })
        appViewModel.appColor.observeInFragment(this, Observer {
            setUiTheme(it, viewpager_linear,loadsir)
        })
    }
}