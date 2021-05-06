package com.xysss.androidnews.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xysss.androidnews.R
import com.xysss.androidnews.app.ext.setAdapterAnimation
import com.xysss.androidnews.app.util.SettingUtil

/**
 * Author:bysd-2
 * Time:2021/4/3011:18
 */
class SearcHistoryAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_history, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.item_history_text, item)
    }

}