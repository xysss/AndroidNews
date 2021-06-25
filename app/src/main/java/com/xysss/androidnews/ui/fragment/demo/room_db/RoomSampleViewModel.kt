package com.xysss.androidnews.ui.fragment.demo.room_db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xysss.jetpackmvvm.base.viewmodel.BaseViewModel
import com.xysss.jetpackmvvm.ext.download.DownloadResultState

/**
 * Author:bysd-2
 * Time:2021/6/2217:49
 */
class RoomSampleViewModel : BaseViewModel() {
    private val userRepository = UserRepository()
    val allUsersLive: LiveData<List<User>>? get() = userRepository.getListLiveData()

    suspend fun insertUsers(users: User) {
        userRepository.insertUsers(users)
    }

    suspend fun updateUsers(users: User) {
        userRepository.updateUsers(users)
    }

    suspend fun deleteUsers(users: User) {
        userRepository.deleteUsers(users)
    }

    suspend fun queryUsers(users: User) {
        userRepository.queryUsers(users)
    }
}