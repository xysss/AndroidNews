package com.xysss.androidnews.viewmodel.state

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.xysss.androidnews.app.util.ColorUtil
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.callback.databind.IntObservableField
import com.xysss.jetpackmvvm.callback.databind.StringObservableField

/**
 * Author:bysd-2
 * Time:2021/4/3013:55
 * 描述　: 专门存放 MeFragment 界面数据的ViewModel
 */

class MeViewModel : BaseViewModel() {

    var name = StringObservableField("请先登录~")

    var integral = IntObservableField(0)

    var info = StringObservableField("id：--　排名：-")

    var imageUrl = StringObservableField(ColorUtil.randomImage())
}