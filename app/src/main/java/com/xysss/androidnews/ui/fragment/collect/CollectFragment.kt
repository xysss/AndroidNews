package com.xysss.androidnews.ui.fragment.collect

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.bindViewPager2
import com.xysss.androidnews.app.ext.init
import com.xysss.androidnews.app.ext.initClose
import com.xysss.androidnews.databinding.FragmentCollectBinding
import com.xysss.androidnews.viewmodel.request.RequestCollectViewModel
import com.xysss.jetpackmvvm.ext.nav
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Author:bysd-2
 * Time:2021/4/3013:35
 * 描述　: 收藏
 */

class CollectFragment: BaseFragment<RequestCollectViewModel, FragmentCollectBinding>() {

    var titleData = arrayListOf("文章","网址")

    private var fragments : ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(CollectAriticleFragment())
        fragments.add(CollectUrlFragment())
    }
    override fun layoutId() = R.layout.fragment_collect

    override fun initView(savedInstanceState: Bundle?)  {
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { collect_viewpager_linear.setBackgroundColor(it) }
        //初始化viewpager2
        collect_view_pager.init(this,fragments)
        //初始化 magic_indicator
        collect_magic_indicator.bindViewPager2(collect_view_pager,mStringList = titleData)
        toolbar.initClose(){
            nav().navigateUp()
        }

    }
}