package com.xysss.jetpackmvvm.ext.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.xysss.jetpackmvvm.ext.util.logd

/**
 * Author:bysd-2
 * Time:2021/5/617:16
 */

class KtxLifeCycleCallBack : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        KtxActivityManger.pushActivity(activity)
        "onActivityCreated : ${activity.localClassName}".logd()
    }
    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.localClassName}".logd()
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.localClassName}".logd()
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.localClassName}".logd()
    }


    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.localClassName}".logd()
        KtxActivityManger.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.localClassName}".logd()
    }



}