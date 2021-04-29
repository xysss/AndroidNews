package com.xysss.androidnews.app

import com.xysss.androidnews.app.event.AppViewModel
import com.xysss.androidnews.app.event.EventViewModel


/**
 * Author:bysd-2
 * Time:2021/4/1616:19
 */
//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel : AppViewModel by lazy { App.appViewModelInstance }

//Application全局的ViewModel，用于发送全局通知操作
val eventViewModel: EventViewModel by lazy { App.eventViewModelInstance }
class App {
}