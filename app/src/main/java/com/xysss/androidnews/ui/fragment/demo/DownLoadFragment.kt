package com.xysss.androidnews.ui.fragment.demo

import android.os.Bundle
import androidx.lifecycle.Observer
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.app.ext.initClose
import com.xysss.androidnews.app.ext.showMessage
import com.xysss.androidnews.databinding.FragmentDownloadBinding
import com.xysss.androidnews.viewmodel.state.DownloadViewModel
import com.xysss.jetpackmvvm.ext.download.DownloadResultState
import com.xysss.jetpackmvvm.ext.download.FileTool
import com.xysss.jetpackmvvm.ext.download.FileTool.getBasePath
import com.xysss.jetpackmvvm.ext.nav
import com.xysss.jetpackmvvm.ext.util.logd
import kotlinx.android.synthetic.main.fragment_download.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Author:bysd-2
 * Time:2021/4/3013:41
 * 文件下载 示例 框架自带的，功能比较简单，肯定木得三方库那么强大
 */

class DownLoadFragment : BaseFragment<DownloadViewModel, FragmentDownloadBinding>() {

    override fun layoutId() = R.layout.fragment_download

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        toolbar.initClose("框架自带普通下载") {
            nav().navigateUp()
        }
    }

    override fun createObserver() {
        mViewModel.downloadData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DownloadResultState.Pending -> {
                    //开始下载
                    "开始下载".logd()
                }
                is DownloadResultState.Progress -> {
                    //下载中
                    downloadProgressBar.progress = it.progress
                    "下载中 ${it.soFarBytes}/${it.totalBytes}".logd()
                    downloadProgress.text = "${it.progress}%"
                    downloadSize.text ="${FileTool.bytes2kb(it.soFarBytes)}/${FileTool.bytes2kb(it.totalBytes)}"
                }
                is DownloadResultState.Success -> {
                    //下载成功
                    downloadProgressBar.progress = 100
                    downloadProgress.text = "100%"
                    downloadSize.text ="${FileTool.bytes2kb(it.totalBytes)}/${FileTool.bytes2kb(it.totalBytes)}"
                    showMessage("下载成功--文件地址：${it.filePath}")
                }
                is DownloadResultState.Pause -> {
                    showMessage("下载暂停")
                }
                is DownloadResultState.Error -> {
                    //下载失败
                    showMessage("错误信息:${it.errorMsg}")
                }
            }
        })
    }

    inner class ProxyClick {
        fun download() {
            //普通下载
            mViewModel.downloadApk(
                getBasePath(),
                "https://down.qq.com/qqweb/QQlite/Android_apk/qqlite_4.0.1.1060_537064364.apk",
                "qq"
            )
        }

        fun cancel() {
            mViewModel.downloadCancel("qq")
        }

        fun pause() {
            mViewModel.downloadPause("qq")
        }
    }


}