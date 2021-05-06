package com.xysss.androidnews.app.weight.banner

import android.view.View
import com.xysss.androidnews.R
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * Author:bysd-2
 * Time:2021/4/2917:58
 */

class WelcomeBannerAdapter : BaseBannerAdapter<String, WelcomeBannerViewHolder>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemwelcome
    }

    override fun createViewHolder(itemView: View, viewType: Int): WelcomeBannerViewHolder {
        return WelcomeBannerViewHolder(itemView);
    }

    override fun onBind(
        holder: WelcomeBannerViewHolder?,
        data: String?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }
}
