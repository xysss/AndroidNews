package com.xysss.androidnews.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.xysss.androidnews.app.network.apiService
import com.xysss.androidnews.app.network.statecallback.ListDataUiState
import com.xysss.androidnews.data.model.bean.AriticleResponse
import com.xysss.androidnews.data.model.bean.ClassifyResponse
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.request
import com.xysss.jetpackmvvm.state.ResultState

/**
 * Author:bysd-2
 * Time:2021/4/3014:18
 */

class RequestPublicNumberViewModel : BaseViewModel() {

    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    var publicDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()


    fun getPublicTitleData() {
        request({ apiService.getPublicTitle() }, titleData)
    }

    fun getPublicData(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getPublicData(pageNo, cid) }, {
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
            publicDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            publicDataState.value = listDataUiState
        })
    }
}