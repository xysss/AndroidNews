package com.xysss.androidnews.ui.fragment.demo.form

import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * Author:bysd-2
 * Time:2021/7/2010:46
 */
class FormViewModel : BaseViewModel() {
    var entity: FormEntity? = null
    var entityLiveData = SingleLiveEvent<FormEntity?>()
}