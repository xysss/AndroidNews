package com.xysss.androidnews.ui.fragment.demo.room_db

import androidx.room.*

/**
 * Author:bysd-2
 * Time:2021/6/2311:10
 */
@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(newUser: User)

    @Query("select * from User")
    fun loadAllUsers(): List<User>

    @Query("select * from User where age>:age")
    fun loadUserOlderThan(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where name=:lastName")
    fun deleteUserByLastName(lastName: String): Int

}