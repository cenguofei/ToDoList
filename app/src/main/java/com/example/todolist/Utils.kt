package com.example.todolist

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

/** 给Toast扩展一个方法show */
fun showToast(context: Context, text:String) {
    Toast.makeText(context,text,Toast.LENGTH_LONG).show()
}

/** dp转px */
fun dp2px(context: Context,dp:Int): Int {
    return (context.resources.displayMetrics.density * dp + 0.5).toInt()
}

fun Fragment.dp2px(dp:Int) : Int {
    return (resources.displayMetrics.density * dp + 0.5).toInt()
}
