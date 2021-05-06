package com.xysss.androidnews.viewmodel.state

import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.callback.databind.StringObservableField

/**
 * Author:bysd-2
 * Time:2021/4/3013:55
 */

class LookInfoViewModel : BaseViewModel() {

    var name = StringObservableField("--")

    var imageUrl =
        StringObservableField("https://upload.jianshu.io/users/upload_avatars/9305757/93322613-ff1a-445c-80f9-57f088f7c1b1.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/300/format/webp")

    var info = StringObservableField()


}