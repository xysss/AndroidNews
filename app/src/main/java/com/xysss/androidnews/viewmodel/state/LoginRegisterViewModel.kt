package com.xysss.androidnews.viewmodel.state

import android.view.View
import androidx.databinding.ObservableInt
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.callback.databind.BooleanObservableField
import com.xysss.jetpackmvvm.callback.databind.StringObservableField

/**
 * Author:bysd-2
 * Time:2021/4/3013:55
 * 描述　:登录注册的ViewModel
 */

class LoginRegisterViewModel : BaseViewModel() {

    //用户名
    var username = StringObservableField()

    //密码(登录注册界面)
    var password = StringObservableField()

    var password2 = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()

    var isShowPwd2 = BooleanObservableField()


    //用户名清除按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var clearVisible = object : ObservableInt(username){
        override fun get(): Int {
            return if(username.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }

    //密码显示按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var passwordVisible = object : ObservableInt(password){
        override fun get(): Int {
            return if(password.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }

    //密码显示按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var passwordVisible2 = object : ObservableInt(password2){
        override fun get(): Int {
            return if(password2.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }

}