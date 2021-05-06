package com.xysss.androidnews.app.weight.banner

import android.view.View
import com.xysss.androidnews.R
import com.xysss.androidnews.data.model.bean.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * Author:bysd-2
 * Time:2021/4/2917:58
 */

class HomeBannerAdapter : BaseBannerAdapter<BannerResponse, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemhome
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView);
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }


}
