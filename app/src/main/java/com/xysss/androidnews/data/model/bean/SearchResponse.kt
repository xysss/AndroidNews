package com.xysss.androidnews.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author:bysd-2
 * Time:2021/4/3011:13
 */
/**
 * 搜索热词
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SearchResponse(var id: Int,
                          var link: String,
                          var name: String,
                          var order: Int,
                          var visible: Int) : Parcelable
