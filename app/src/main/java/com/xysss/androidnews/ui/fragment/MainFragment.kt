package com.xysss.androidnews.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.init
import com.xysss.androidnews.app.ext.initMain
import com.xysss.androidnews.app.ext.interceptLongClick
import com.xysss.androidnews.app.ext.setUiTheme
import com.xysss.androidnews.databinding.FragmentMainBinding
import com.xysss.androidnews.viewmodel.state.MainViewModel
import com.xysss.jetpackmvvm.ext.util.loge
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Author:bysd-2
 * Time:2021/4/3012:19
 * 描述　:项目主页Fragment
 */

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2
        mainViewpager.initMain(this)
        //初始化 bottomBar
        mainBottom.init{
            when (it) {
                R.id.menu_main -> mainViewpager.setCurrentItem(0, false)
                R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                R.id.menu_system -> mainViewpager.setCurrentItem(2, false)
                R.id.menu_public -> mainViewpager.setCurrentItem(3, false)
                R.id.menu_me -> mainViewpager.setCurrentItem(4, false)
            }
        }
        mainBottom.interceptLongClick(R.id.menu_main,R.id.menu_project,R.id.menu_system,R.id.menu_public,R.id.menu_me)
    }

    override fun createObserver() {
        appViewModel.appColor.observeInFragment(this, Observer {
            setUiTheme(it, mainBottom)
        })
    }

    override fun onPause() {
        super.onPause()
        "onPause".loge("hgj")
    }

    override fun onResume() {
        super.onResume()
        "onResume".loge("hgj")
    }

}