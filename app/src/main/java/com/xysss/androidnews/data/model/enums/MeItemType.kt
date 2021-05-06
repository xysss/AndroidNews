package com.xysss.androidnews.data.model.enums

/**
 * Author:bysd-2
 * Time:2021/4/3011:14
 * 描述　: 个人中心类型
 */
enum class MeItemType(val type: Int) {
    //头部布局
    TopItem(1),
    //圆角Item
    RoundItem(2),
    //分割线
    BackGroundItem(3),
    //普通的列表
    ListItem(4),
    //右边有数据的列表
    ListRightItem(4)
}