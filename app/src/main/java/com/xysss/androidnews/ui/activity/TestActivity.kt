package com.xysss.androidnews.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseActivity
import com.xysss.androidnews.databinding.ActivityTestBinding
import com.xysss.androidnews.ui.adapter.TestAdapter
import com.xysss.androidnews.viewmodel.request.RequestLoginRegisterViewModel
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.util.logd

/**
 * Author:bysd-2
 * Time:2021/4/3011:16
 */

class TestActivity : BaseActivity<BaseViewModel, ActivityTestBinding>() {

    val viewModel: RequestLoginRegisterViewModel by viewModels()

    val adapter: TestAdapter by lazy { TestAdapter(arrayListOf()) }

    override fun layoutId() = R.layout.activity_test


    override fun initView(savedInstanceState: Bundle?) {

        //强烈注意：使用addLoadingObserve 将非绑定当前activity的viewmodel绑定loading回调 防止出现请求时不显示 loading 弹窗bug
        addLoadingObserve(viewModel)

        adapter.run {
            clickAction = { position, item, state ->
                "海王收到了点击事件，并准备发一个红包".logd()
            }
        }

    }

    override fun createObserver() {


    }
}

