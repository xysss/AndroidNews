package com.xysss.androidnews.app.network.statecallback

/**
 * Author:bysd-2
 * Time:2021/4/2917:53
 * 描述　:收藏数据状态类
 */
data class CollectUiState(
    //请求是否成功
    var isSuccess: Boolean = true,
    //收藏
    var collect: Boolean = false,
    //收藏Id
    var id: Int = -1,
    //请求失败的错误信息
    var errorMsg: String = ""
)