package com.xysss.androidnews.data.model.bean

import com.xysss.androidnews.data.model.enums.MeItemType

/**
 * Author:bysd-2
 * Time:2021/4/3011:13
 */
data class MeItemEntity(
    var itemType: MeItemType,
    var itemPosition: Int,
    var data: Any
)