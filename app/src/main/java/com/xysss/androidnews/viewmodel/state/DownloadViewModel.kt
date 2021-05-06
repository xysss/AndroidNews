package com.xysss.androidnews.viewmodel.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.download.DownLoadManager
import com.xysss.jetpackmvvm.ext.download.DownloadResultState
import com.xysss.jetpackmvvm.ext.download.downLoadExt
import kotlinx.coroutines.launch

/**
 * Author:bysd-2
 * Time:2021/4/3013:54
 */

class DownloadViewModel : BaseViewModel() {

    var downloadData: MutableLiveData<DownloadResultState> = MutableLiveData()

    /**
     * Apk普通下载 框架自带
     * @param path String 文件保存路径
     * @param url String 文件下载url
     * @param tag String 下载标识，根据该值可取消，暂停
     */
    fun downloadApk(path: String, url: String, tag: String) {
        viewModelScope.launch {
            //直接强制下载，不管文件是否存在 ，如果需要每次都重新下载可以设置为true
            DownLoadManager.downLoad(tag, url, path, "tmd.apk", false, downLoadExt(downloadData))
        }
    }

    /**
     * 取消下载
     * @param tag String
     */
    fun downloadCancel(tag: String) {
        DownLoadManager.cancel(tag)
    }

    /**
     * Apk暂停下载
     * @param tag String
     */
    fun downloadPause(tag: String) {
        DownLoadManager.pause(tag)
    }

}