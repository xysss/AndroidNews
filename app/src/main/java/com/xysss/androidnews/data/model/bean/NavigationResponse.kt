package com.xysss.androidnews.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author:bysd-2
 * Time:2021/4/3011:13
 * 导航数据
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class NavigationResponse(var articles: ArrayList<AriticleResponse>,
                              var cid: Int,
                              var name: String) : Parcelable
