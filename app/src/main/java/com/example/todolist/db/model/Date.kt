package com.example.todolist.db.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 日期管理类
 */
@Parcelize
data class Date(
    var year:Int,

    var month:Int,

    var day:Int
) : Parcelable
