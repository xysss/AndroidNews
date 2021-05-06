package com.xysss.androidnews.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.xysss.androidnews.app.network.apiService
import com.xysss.androidnews.app.util.CacheUtil
import com.xysss.androidnews.data.model.bean.ApiPagerResponse
import com.xysss.androidnews.data.model.bean.AriticleResponse
import com.xysss.androidnews.data.model.bean.SearchResponse
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.launch
import com.xysss.jetpackmvvm.ext.request
import com.xysss.jetpackmvvm.state.ResultState

/**
 * Author:bysd-2
 * Time:2021/4/3014:18
 */

class RequestSearchViewModel : BaseViewModel() {

    var pageNo = 0

    //搜索热词数据
    var hotData: MutableLiveData<ResultState<ArrayList<SearchResponse>>> = MutableLiveData()

    //搜索结果数据
    var seachResultData: MutableLiveData<ResultState<ApiPagerResponse<ArrayList<AriticleResponse>>>> =
        MutableLiveData()

    //搜索历史词数据
    var historyData: MutableLiveData<ArrayList<String>> = MutableLiveData()

    /**
     * 获取热门数据
     */
    fun getHotData() {
        request({ apiService.getSearchData() }, hotData)
    }

    /**
     * 获取历史数据
     */
    fun getHistoryData() {
        launch({
            CacheUtil.getSearchHistoryData()
        }, {
            historyData.value = it
        }, {
            //获取本地历史数据出异常了
        })
    }

    /**
     * 根据字符串搜索结果
     */
    fun getSearchResultData(searchKey: String, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request(
            { apiService.getSearchDataByKey(pageNo, searchKey) },
            seachResultData
        )
    }
}