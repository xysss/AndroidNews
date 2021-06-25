package com.xysss.androidnews.ui.fragment.demo

import android.os.Bundle
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.initClose
import com.xysss.androidnews.databinding.FragmentDemoBinding
import com.xysss.androidnews.viewmodel.state.DemoViewModel
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.navigateAction
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Author:bysd-2
 * Time:2021/4/3013:41
 * 放一些示例，目前只有 文件下载示例 后面想到什么加什么，作者那个比很懒，佛性添加
 */

class DemoFragment : BaseFragment<DemoViewModel, FragmentDemoBinding>() {

    override fun layoutId() = R.layout.fragment_demo

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()

        toolbar.initClose("示例") {
            nav().navigateUp()
        }
    }


    inner class ProxyClick {
        fun download() {
            //测试一下 普通的下载
            nav().navigateAction(R.id.action_demoFragment_to_downLoadFragment)
        }

        fun downloadLibrary() {
            //测试一下利用三方库下载
            nav().navigateAction(R.id.action_demoFragment_to_downLoadLibraryFragment)
        }

        fun roomTestDemo(){
            //Room数据库的使用
            nav().navigateAction(R.id.action_demoFragment_to_roomDemoFragment)
        }
    }



}