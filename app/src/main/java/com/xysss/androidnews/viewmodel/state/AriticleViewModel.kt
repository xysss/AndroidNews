package com.xysss.androidnews.viewmodel.state

import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.callback.databind.StringObservableField

/**
 * Author:bysd-2
 * Time:2021/4/3013:53
 */
class AriticleViewModel : BaseViewModel() {

    //分享文章标题
    var shareTitle = StringObservableField()

    //分享文章网址
    var shareUrl = StringObservableField()

    //分享文章的人
    var shareName = StringObservableField()

}