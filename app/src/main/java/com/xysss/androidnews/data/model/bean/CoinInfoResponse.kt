package com.xysss.androidnews.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author:bysd-2
 * Time:2021/4/3011:12
 */
/**
 * 分享人信息
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CoinInfoResponse(
    var coinCount: Int,
    var rank: String,
    var userId: Int,
    var username: String
) : Parcelable
