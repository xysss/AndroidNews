package com.xysss.androidnews.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xysss.androidnews.R
import com.xysss.androidnews.app.ext.setAdapterAnimation
import com.xysss.androidnews.app.util.ColorUtil
import com.xysss.androidnews.app.util.SettingUtil
import com.xysss.androidnews.data.model.bean.AriticleResponse
import com.xysss.jetpackmvvm.ext.util.toHtml

/**
 * Author:bysd-2
 * Time:2021/4/3011:18
 * 重要程度 adapter
 */

class NavigationChildAdapter(data: ArrayList<AriticleResponse>) :
    BaseQuickAdapter<AriticleResponse, BaseViewHolder>(R.layout.flow_layout, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: AriticleResponse) {
        holder.setText(R.id.flow_tag,item.title.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}