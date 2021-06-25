package com.xysss.androidnews.ui.fragment.demo.room_db

import android.os.Bundle
import androidx.lifecycle.Observer
import com.xysss.androidnews.R
import com.xysss.androidnews.app.base.BaseFragment
import com.xysss.androidnews.databinding.FragmentRoomBinding
import kotlinx.android.synthetic.main.fragment_room.*

/**
 * Author:bysd-2
 * Time:2021/6/2217:48
 */
class RoomSampleFragment : BaseFragment<RoomSampleViewModel, FragmentRoomBinding>() {
    override fun layoutId()= R.layout.fragment_room

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click=ProxyClick()

    }

    override fun initData() {
        mViewModel.allUsersLive?.observe(this, Observer {users->
            val text = StringBuilder()
            for (i in users.indices) {
                val user = users[i]
                text.append(user.id).append(":").append(user.age).append(":").append(user.name).append("\n")
            }
            userTest.text = text.toString()
        })
    }

    inner class ProxyClick {
        val user1=User("xys","男",23)
        suspend fun insertUser() {
            mViewModel.insertUsers(user1)
        }
        suspend fun updateUser() {
            user1.name="hhh"
            mViewModel.updateUsers(user1)
        }
        suspend fun queryUser() {
            mViewModel.queryUsers(user1)
        }
        suspend fun deleteUser(){
            mViewModel.deleteUsers(user1)
        }
    }

}