package com.xysss.androidnews.ui.fragment.tree

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.bindViewPager2
import com.xysss.androidnews.app.ext.init
import com.xysss.androidnews.app.ext.initClose
import com.xysss.androidnews.data.model.bean.SystemResponse
import com.xysss.androidnews.databinding.FragmentSystemBinding
import com.xysss.androidnews.ui.fragment.publicNumber.PublicChildFragment.Companion.newInstance
import com.xysss.androidnews.viewmodel.state.TreeViewModel
import com.xysss.jetpackmvvm.ext.nav
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.include_viewpager.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:58
 */

class SystemArrFragment : BaseFragment<TreeViewModel, FragmentSystemBinding>() {

    lateinit var data: SystemResponse

    var index = 0

    private var fragments: ArrayList<Fragment> = arrayListOf()

    override fun layoutId() = R.layout.fragment_system

    override fun initView(savedInstanceState: Bundle?)  {
        arguments?.let {
            data = it.getParcelable("data")!!
            index = it.getInt("index")
        }
        toolbar.initClose(data.name) {
            nav().navigateUp()
        }
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { viewpager_linear.setBackgroundColor(it) }
        //设置栏目标题居左显示
        (magic_indicator.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.LEFT

    }

    override fun lazyLoadData() {
        data.children.forEach {
            fragments.add(SystemChildFragment.newInstance(it.id))
        }
        //初始化viewpager2
        view_pager.init(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, data.children.map { it.name })

        view_pager.offscreenPageLimit = fragments.size

        view_pager.postDelayed({
            view_pager.currentItem = index
        },100)

    }

    override fun createObserver() {

    }


}