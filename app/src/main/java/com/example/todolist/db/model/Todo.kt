package com.example.todolist.db.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("todo_table")
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    var title:String,

    var description:String, //内容

    var priority:Priority, //任务优先级，需要Converter

    @Embedded
    var date:Date,

    @Embedded
    var tag:Tag
) : Parcelable
