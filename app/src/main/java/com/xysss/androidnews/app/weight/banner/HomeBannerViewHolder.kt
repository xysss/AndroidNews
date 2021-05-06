package com.xysss.androidnews.app.weight.banner

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xysss.androidnews.R
import com.xysss.androidnews.data.model.bean.BannerResponse
import com.xysss.jetpackmvvm.base.appContext
import com.zhpan.bannerview.BaseViewHolder

/**
 * Author:bysd-2
 * Time:2021/4/2917:58
 */

class HomeBannerViewHolder(view: View) : BaseViewHolder<BannerResponse>(view) {
    override fun bindData(data: BannerResponse?, position: Int, pageSize: Int) {
        val img = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.let {
            Glide.with(appContext)
                .load(it.imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(img)
        }
    }

}
