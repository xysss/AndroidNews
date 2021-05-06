package com.xysss.androidnews.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.tencent.bugly.beta.Beta
import com.xysss.androidnews.R
import com.xysss.androidnews.app.appViewModel
import com.xysss.androidnews.app.base.BaseActivity
import com.xysss.androidnews.app.util.StatusBarUtil
import com.xysss.androidnews.databinding.ActivityMainBinding
import com.xysss.androidnews.viewmodel.state.MainViewModel
import com.xysss.jetpackmvvm.network.manager.NetState

/**
 * Author:bysd-2
 * Time:2021/4/3011:16
 */

/**
 * 项目主页Activity
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L
    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        //进入首页检查更新
        Beta.checkUpgrade(false, true)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainfragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
        appViewModel.appColor.value?.let {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0) }
    }

    override fun createObserver() {
        appViewModel.appColor.observeInActivity(this, Observer {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0)
        })
    }

    /**
     * 示例，在Activity/Fragment中如果想监听网络变化，可重写onNetworkStateChanged该方法
     */
    override fun onNetworkStateChanged(netState: NetState) {
        super.onNetworkStateChanged(netState)
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我特么终于有网了啊!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我特么怎么断网了!", Toast.LENGTH_SHORT).show()
        }
    }

}
