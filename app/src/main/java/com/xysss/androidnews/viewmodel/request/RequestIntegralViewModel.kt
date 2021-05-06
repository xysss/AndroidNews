package com.xysss.androidnews.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.xysss.androidnews.app.network.apiService
import com.xysss.androidnews.app.network.statecallback.ListDataUiState
import com.xysss.androidnews.data.model.bean.IntegralHistoryResponse
import com.xysss.androidnews.data.model.bean.IntegralResponse
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.request

/**
 * Author:bysd-2
 * Time:2021/4/3014:17
 */

class RequestIntegralViewModel : BaseViewModel() {

    private var pageNo = 1

    //积分排行数据
    var integralDataState = MutableLiveData<ListDataUiState<IntegralResponse>>()

    //获取积分历史数据
    var integralHistoryDataState = MutableLiveData<ListDataUiState<IntegralHistoryResponse>>()

    fun getIntegralData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getIntegralRank(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            integralDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralResponse>()
                )
            integralDataState.value = listDataUiState
        })
    }

    fun getIntegralHistoryData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getIntegralHistory(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            integralHistoryDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralHistoryResponse>()
                )
            integralHistoryDataState.value = listDataUiState
        })
    }
}