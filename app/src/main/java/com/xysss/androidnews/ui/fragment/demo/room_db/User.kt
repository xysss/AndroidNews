package com.xysss.androidnews.ui.fragment.demo.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Author:bysd-2
 * Time:2021/6/2310:35
 */
@Entity
data class User(var name:String,var gender:String,var age:Int){
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
}