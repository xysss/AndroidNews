package com.xysss.androidnews.ui.fragment.demo.room_db

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Author:bysd-2
 * Time:2021/6/2416:50
 */
class UserRepository {
    private var listLiveData: LiveData<List<User>>? = null
    private var userDao: UserDao? = null

    init {
        userDao = AppDataBase.getDatabase().userDao()
    }

    fun getListLiveData(): LiveData<List<User>>? {
        return listLiveData
    }

    //suspend 声明为挂起函数
    suspend fun insertUsers(user: User) {
        withContext(IO) {
            user.id = userDao!!.insertUser(user)
        }
    }

    suspend fun updateUsers(user: User) {
        withContext(IO) {
            userDao!!.updateUser(user)
        }
    }

    suspend fun queryUsers(user: User) {
        withContext(IO) {
            for (user in userDao!!.loadAllUsers()) {
                Log.d("xysTest",userDao!!.toString())
            }
        }
    }

    suspend fun deleteUsers(user: User) {
        withContext(IO) {
            userDao!!.deleteUserByLastName("xys")
        }
    }
}