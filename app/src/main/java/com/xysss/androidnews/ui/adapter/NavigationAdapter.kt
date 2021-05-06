package com.xysss.androidnews.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.xysss.androidnews.R
import com.xysss.androidnews.app.ext.setAdapterAnimation
import com.xysss.androidnews.app.util.SettingUtil
import com.xysss.androidnews.data.model.bean.AriticleResponse
import com.xysss.androidnews.data.model.bean.NavigationResponse
import com.xysss.jetpackmvvm.ext.util.toHtml

/**
 * Author:bysd-2
 * Time:2021/4/3011:17
 */

class NavigationAdapter(data: ArrayList<NavigationResponse>) :
    BaseQuickAdapter<NavigationResponse, BaseViewHolder>(R.layout.item_system, data) {

    private var navigationAction: (item: AriticleResponse, view: View) -> Unit =
        { _: AriticleResponse, _: View -> }


    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: NavigationResponse) {
        holder.setText(R.id.item_system_title, item.name.toHtml())
        holder.getView<RecyclerView>(R.id.item_system_rv).run {
            val foxLayoutManager: FlexboxLayoutManager by lazy {
                FlexboxLayoutManager(context).apply {
                    //方向 主轴为水平方向，起点在左端
                    flexDirection = FlexDirection.ROW
                    //左对齐
                    justifyContent = JustifyContent.FLEX_START
                }
            }
            layoutManager = foxLayoutManager
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = NavigationChildAdapter(item.articles).apply {
                setOnItemClickListener { _, view, position ->
                    navigationAction.invoke(item.articles[position], view)
                }
            }
        }
    }

    fun setNavigationAction(inputNavigationAction: (item: AriticleResponse, view: View) -> Unit) {
        this.navigationAction = inputNavigationAction
    }
}