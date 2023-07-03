package com.example.todolist.db.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 管理标签
 */
@Parcelize
data class Tag(
    val text:String,

    val bgColor:String
) : Parcelable
