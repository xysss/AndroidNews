package com.xysss.jetpackmvvm.callback.databind

import androidx.databinding.ObservableField

/**
 * Author:bysd-2
 * Time:2021/5/617:02
 * 描述　:自定义的 Short 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class ShortObservableField(value: Short = 0) : ObservableField<Short>(value) {

    override fun get(): Short {
        return super.get()!!
    }

}