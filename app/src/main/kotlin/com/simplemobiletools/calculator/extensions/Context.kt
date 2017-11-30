package com.simplemobiletools.calculator.extensions

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.simplemobiletools.calculator.helpers.Config

val Context.config: Config get() = Config.newInstance(applicationContext)

// we are reusing the same layout in the app and widget, but cannot use MyTextView etc in a widget, so color regular view types like this
fun Context.updateViewColors(viewGroup: ViewGroup, textColor: Int) {
    val cnt = viewGroup.childCount
    (0 until cnt).map { viewGroup.getChildAt(it) }
            .forEach {
                when (it) {
                    is TextView -> it.setTextColor(textColor)
                    is Button -> it.setTextColor(textColor)
                    is ViewGroup -> updateViewColors(it, textColor)
                }
            }
}
