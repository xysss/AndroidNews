package com.xysss.androidnews.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author:bysd-2
 * Time:2021/4/3011:13
 */
/**
 * 文章的标签
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class TagsResponse(var name:String, var url:String): Parcelable
