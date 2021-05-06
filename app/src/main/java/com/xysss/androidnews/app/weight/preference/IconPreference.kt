package com.xysss.androidnews.app.weight.preference

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.xysss.androidnews.R
import com.xysss.androidnews.app.util.SettingUtil

/**
 * Author:bysd-2
 * Time:2021/4/2918:00
 */

class IconPreference(context: Context, attrs: AttributeSet) : Preference(context, attrs) {

    var circleImageView: MyColorCircleView? = null

    init {
        widgetLayoutResource = R.layout.item_icon_preference_preview
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val color = SettingUtil.getColor(context)
        circleImageView = holder?.itemView?.findViewById(R.id.iv_preview)
        circleImageView?.color = color
        circleImageView?.border = color
    }

    fun setView() {
        val color = SettingUtil.getColor(context)
        circleImageView?.color = color
        circleImageView?.border = color
    }
}