package com.xysss.androidnews.ui.fragment.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.databinding.FragmentPagerBinding
import com.xysss.androidnews.ui.fragment.collect.CollectFragment
import com.xysss.androidnews.ui.fragment.search.SearchFragment
import com.xysss.androidnews.ui.fragment.share.AriticleFragment
import com.xysss.androidnews.ui.fragment.todo.TodoListFragment
import com.xysss.androidnews.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.fragment_pager.*

/**
 * Author:bysd-2
 * Time:2021/4/3013:41
 * 测试 Viewpager下的懒加载
 */

class PagerFragment : BaseFragment<MainViewModel, FragmentPagerBinding>() {

    override fun layoutId() = R.layout.fragment_pager

    override fun initView(savedInstanceState: Bundle?) {
        pagerViewpager.adapter = object : FragmentStatePagerAdapter(childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        SearchFragment()
                    }
                    1 -> {
                        TodoListFragment()
                    }
                    2 -> {
                        AriticleFragment()
                    }
                    else -> {
                        CollectFragment()
                    }
                }
            }

            override fun getCount(): Int {
                return 5;
            }
        }
        pagerViewpager.offscreenPageLimit = 5
    }

}