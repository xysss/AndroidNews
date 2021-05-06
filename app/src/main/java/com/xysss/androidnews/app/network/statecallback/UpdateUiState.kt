package com.xysss.androidnews.app.network.statecallback

/**
 * Author:bysd-2
 * Time:2021/4/2917:53
 * 描述　:操作数据的状态类
 */
data class UpdateUiState<T>(
    //请求是否成功
    var isSuccess: Boolean = true,
    //操作的对象
    var data: T? = null,
    //请求失败的错误信息
    var errorMsg: String = ""
)