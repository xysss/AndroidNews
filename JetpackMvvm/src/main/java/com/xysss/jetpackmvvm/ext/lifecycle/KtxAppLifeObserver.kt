package com.xysss.jetpackmvvm.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.xysss.jetpackmvvm.callback.livedata.BooleanLiveData

/**
 * Author:bysd-2
 * Time:2021/5/617:15
 */

object KtxAppLifeObserver : LifecycleObserver {

    var isForeground = BooleanLiveData()

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private  fun onForeground() {
        isForeground.value = true
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        isForeground.value = false
    }

}