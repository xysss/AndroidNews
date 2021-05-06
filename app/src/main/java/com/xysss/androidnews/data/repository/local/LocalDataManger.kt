package com.xysss.androidnews.data.repository.local

/**
 * Author:bysd-2
 * Time:2021/4/3011:15
 */

class LocalDataManger {

    companion object {
        val instance: LocalDataManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalDataManger()
        }
    }
}