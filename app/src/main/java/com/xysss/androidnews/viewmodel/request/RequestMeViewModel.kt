package com.xysss.androidnews.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.xysss.androidnews.app.network.apiService
import com.xysss.androidnews.data.model.bean.IntegralResponse
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.request
import com.xysss.jetpackmvvm.state.ResultState

/**
 * Author:bysd-2
 * Time:2021/4/3014:18
 */

class RequestMeViewModel : BaseViewModel() {

    var meData = MutableLiveData<ResultState<IntegralResponse>>()

    fun getIntegral() {
        request({ apiService.getIntegral() }, meData)
    }
}