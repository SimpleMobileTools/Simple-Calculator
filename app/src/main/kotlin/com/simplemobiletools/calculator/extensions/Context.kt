package com.simplemobiletools.calculator.extensions

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.simplemobiletools.calculator.databases.CalculatorDatabase
import com.simplemobiletools.calculator.helpers.Config
import com.simplemobiletools.calculator.interfaces.CalculatorDao

val Context.config: Config get() = Config.newInstance(applicationContext)

val Context.calculatorDB: CalculatorDao get() = CalculatorDatabase.getInstance(applicationContext).CalculatorDao()

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

inline fun <reified T> Context.refreshAppWidget() {
    val intent = Intent(this, T::class.java)
    intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
    val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(ComponentName(this, T::class.java))
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
    sendBroadcast(intent)
}
