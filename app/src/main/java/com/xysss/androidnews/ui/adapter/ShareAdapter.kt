package com.xysss.androidnews.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xysss.androidnews.R
import com.xysss.androidnews.app.ext.setAdapterAnimation
import com.xysss.androidnews.app.util.SettingUtil
import com.xysss.androidnews.data.model.bean.AriticleResponse

/**
 * Author:bysd-2
 * Time:2021/4/3011:18
 * 分享的文章 adapter
 */

class ShareAdapter(data: ArrayList<AriticleResponse>) :
    BaseQuickAdapter<AriticleResponse, BaseViewHolder>(
        R.layout.item_share_ariticle, data
    ) {
    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(helper: BaseViewHolder, item: AriticleResponse) {
        //赋值
        item.run {
            helper.setText(R.id.item_share_title, title)
            helper.setText(R.id.item_share_date, niceDate)
        }
    }
}


