package com.xysss.androidnews.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.bindViewPager2
import com.xysss.androidnews.app.ext.init
import com.xysss.androidnews.app.ext.setUiTheme
import com.xysss.androidnews.app.util.CacheUtil
import com.xysss.androidnews.databinding.FragmentViewpagerBinding
import com.xysss.androidnews.viewmodel.state.TreeViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.navigateAction
import kotlinx.android.synthetic.main.include_viewpager.*

/**
 * Author:bysd-2
 * Time:2021/4/3014:58
 * 描述　: 广场模块父Fragment管理四个子fragment
 */

class TreeArrFragment : BaseFragment<TreeViewModel, FragmentViewpagerBinding>() {

    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")

    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(PlazaFragment())
        fragments.add(AskFragment())
        fragments.add(SystemFragment())
        fragments.add(NavigationFragment())
    }

    override fun layoutId() = R.layout.fragment_viewpager

    override fun initView(savedInstanceState: Bundle?)  {
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { setUiTheme(it, viewpager_linear) }
        include_viewpager_toolbar.run {
            inflateMenu(R.menu.todo_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.todo_add -> {
                        if(CacheUtil.isLogin()){
                            nav().navigateAction(R.id.action_mainfragment_to_addAriticleFragment)
                        }else{
                            nav().navigateAction(R.id.action_to_loginFragment)
                        }
                    }
                }
                true
            }
        }
    }

    override fun lazyLoadData() {
        //初始化viewpager2
        view_pager.init(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mStringList = titleData) {
            if (it != 0) {
                include_viewpager_toolbar.menu.clear()
            } else {
                include_viewpager_toolbar.menu.hasVisibleItems().let { flag ->
                    if (!flag) include_viewpager_toolbar.inflateMenu(R.menu.todo_menu)
                }
            }
        }
    }

    override fun createObserver() {
        appViewModel.appColor.observeInFragment(this, Observer {
            setUiTheme(it, viewpager_linear)
        })
    }

}