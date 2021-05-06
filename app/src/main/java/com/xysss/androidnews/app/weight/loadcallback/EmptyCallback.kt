package com.xysss.androidnews.app.weight.loadcallback

import com.kingja.loadsir.callback.Callback
import com.xysss.androidnews.R

/**
 * Author:bysd-2
 * Time:2021/4/2917:59
 */
class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}