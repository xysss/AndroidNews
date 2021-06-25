package com.xysss.androidnews.ui.fragment.demo.room_db

import android.content.Context
import android.provider.CalendarContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xysss.jetpackmvvm.base.appContext

/**
 * Author:bysd-2
 * Time:2021/6/2311:42
 */
@Database(version = 1, entities = [User::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao?

    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getDatabase(): AppDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(appContext, AppDataBase::class.java, "app_database").build().apply {
                    instance = this
                }
        }
    }
}