package com.xysss.androidnews.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.xysss.androidnews.app.network.apiService
import com.xysss.androidnews.app.network.statecallback.ListDataUiState
import com.xysss.androidnews.data.model.bean.AriticleResponse
import com.xysss.androidnews.data.model.bean.ClassifyResponse
import com.xysss.androidnews.data.repository.request.HttpRequestCoroutine
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.request
import com.xysss.jetpackmvvm.state.ResultState

/**
 * Author:bysd-2
 * Time:2021/4/3014:18
 */

class RequestProjectViewModel : BaseViewModel() {

    //页码
    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    var projectDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    fun getProjectTitleData() {
        request({ apiService.getProjecTitle() }, titleData)
    }

    fun getProjectData(isRefresh: Boolean, cid: Int, isNew: Boolean = false) {
        if (isRefresh) {
            pageNo = if (isNew) 0 else 1
        }
        request({ HttpRequestCoroutine.getProjectData(pageNo, cid, isNew) }, {
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
            projectDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            projectDataState.value = listDataUiState
        })
    }
}