package com.xysss.androidnews.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 收藏的网址类
 * Author:bysd-2
 * Time:2021/4/3011:12
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CollectUrlResponse(var icon: String,
                              var id: Int,
                              var link: String,
                              var name: String,
                              var order: Int,
                              var userId: Int,
                              var visible: Int) : Parcelable

