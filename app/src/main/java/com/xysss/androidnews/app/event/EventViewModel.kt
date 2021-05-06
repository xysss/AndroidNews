package com.xysss.androidnews.app.event

import com.xysss.androidnews.data.model.bean.CollectBus
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.callback.livedata.event.EventLiveData

/**
 * Author:bysd-2
 * Time:2021/4/2917:46
 * 描述　:APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 */
class EventViewModel :BaseViewModel() {
    //全局收藏，在任意一个地方收藏或取消收藏，监听该值的界面都会收到消息
    val collectEvent = EventLiveData<CollectBus>()

    //分享文章通知
    val shareArticleEvent = EventLiveData<Boolean>()

    //添加TODO通知
    val todoEvent = EventLiveData<Boolean>()
}