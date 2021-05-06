package com.xysss.androidnews.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.xysss.androidnews.app.network.apiService
import com.xysss.androidnews.app.network.statecallback.ListDataUiState
import com.xysss.androidnews.app.network.statecallback.UpdateUiState
import com.xysss.androidnews.data.model.bean.AriticleResponse
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.request
import com.xysss.jetpackmvvm.state.ResultState

/**
 * Author:bysd-2
 * Time:2021/4/3014:17
 * 描述　: 只做一件事，拿数据源
 */

class RequestAriticleViewModel : BaseViewModel() {

    var pageNo = 0

    var addData = MutableLiveData<ResultState<Any?>>()

    //分享的列表集合数据
    var shareDataState = MutableLiveData<ListDataUiState<AriticleResponse>>()

    //删除分享文章回调数据
    var delDataState = MutableLiveData<UpdateUiState<Int>>()

    fun addAriticle(shareTitle: String, shareUrl: String) {
        request(
            { apiService.addAriticle(shareTitle, shareUrl) },
            addData,
            true,
            "正在分享文章中..."
        )
    }

    fun getShareData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ apiService.getShareData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.shareArticles.isEmpty(),
                    hasMore = it.shareArticles.hasMore(),
                    isFirstEmpty = isRefresh && it.shareArticles.isEmpty(),
                    listData = it.shareArticles.datas
                )
            shareDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            shareDataState.value = listDataUiState
        })
    }

    fun deleteShareData(id: Int, position: Int) {
        request({ apiService.deleteShareData(id) }, {
            val updateUiState = UpdateUiState(isSuccess = true, data = position)
            delDataState.value = updateUiState
        }, {
            val updateUiState =
                UpdateUiState(isSuccess = false, data = position, errorMsg = it.errorMsg)
            delDataState.value = updateUiState
        })
    }
}