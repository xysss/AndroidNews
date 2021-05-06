package com.xysss.androidnews.viewmodel.state

import androidx.databinding.ObservableField
import com.xysss.androidnews.data.model.bean.IntegralResponse
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * Author:bysd-2
 * Time:2021/4/3013:54
 */
class IntegralViewModel : BaseViewModel() {

    var rank = ObservableField<IntegralResponse>()
}