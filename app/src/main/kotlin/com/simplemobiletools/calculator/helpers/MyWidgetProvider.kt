package com.simplemobiletools.calculator.helpers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.commons.extensions.applyColorFilter
import com.simplemobiletools.commons.extensions.setText

class MyWidgetProvider : AppWidgetProvider(), Calculator {
    companion object {
        private var calc: CalculatorImpl? = null
        private var storedUseCommaAsDecimalMark = false
        private var decimalSeparator = DOT
        private var groupingSeparator = COMMA
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val config = context.config
        appWidgetManager.getAppWidgetIds(getComponentName(context)).forEach {
            val views = RemoteViews(context.packageName, R.layout.widget)
            setupIntent(context, views, DECIMAL, R.id.btn_decimal)
            setupIntent(context, views, ZERO, R.id.btn_0)
            setupIntent(context, views, ONE, R.id.btn_1)
            setupIntent(context, views, TWO, R.id.btn_2)
            setupIntent(context, views, THREE, R.id.btn_3)
            setupIntent(context, views, FOUR, R.id.btn_4)
            setupIntent(context, views, FIVE, R.id.btn_5)
            setupIntent(context, views, SIX, R.id.btn_6)
            setupIntent(context, views, SEVEN, R.id.btn_7)
            setupIntent(context, views, EIGHT, R.id.btn_8)
            setupIntent(context, views, NINE, R.id.btn_9)

            setupIntent(context, views, EQUALS, R.id.btn_equals)
            setupIntent(context, views, PLUS, R.id.btn_plus)
            setupIntent(context, views, MINUS, R.id.btn_minus)
            setupIntent(context, views, MULTIPLY, R.id.btn_multiply)
            setupIntent(context, views, DIVIDE, R.id.btn_divide)
            setupIntent(context, views, PERCENT, R.id.btn_percent)
            setupIntent(context, views, POWER, R.id.btn_power)
            setupIntent(context, views, ROOT, R.id.btn_root)
            setupIntent(context, views, CLEAR, R.id.btn_clear)
            setupIntent(context, views, RESET, R.id.btn_reset)

            setupAppOpenIntent(context, views, R.id.formula)
            setupAppOpenIntent(context, views, R.id.result)

            views.setViewVisibility(R.id.btn_reset, View.VISIBLE)
            views.applyColorFilter(R.id.widget_background, config.widgetBgColor)

            updateTextColors(views, config.widgetTextColor)
            setupDecimalSeparator(views, config.useCommaAsDecimalMark)
            appWidgetManager.updateAppWidget(it, views)
        }
    }

    private fun getComponentName(context: Context) = ComponentName(context, MyWidgetProvider::class.java)

    private fun setupIntent(context: Context, views: RemoteViews, action: String, id: Int) {
        Intent(context, MyWidgetProvider::class.java).apply {
            this.action = action
            val pendingIntent = PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(id, pendingIntent)
        }
    }

    private fun setupAppOpenIntent(context: Context, views: RemoteViews, id: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        views.setOnClickPendingIntent(id, pendingIntent)
    }

    private fun updateTextColors(views: RemoteViews, color: Int) {
        val viewIds = intArrayOf(
            R.id.formula, R.id.result, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6,
            R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_percent, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide,
            R.id.btn_multiply, R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals
        )

        for (i in viewIds) {
            views.setTextColor(i, color)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (val action = intent.action) {
            DECIMAL, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, EQUALS, CLEAR, RESET, PLUS, MINUS, MULTIPLY, DIVIDE, PERCENT, POWER, ROOT -> myAction(
                action,
                context
            )
            else -> super.onReceive(context, intent)
        }
    }

    private fun myAction(action: String, context: Context) {
        if (calc == null) {
            calc = CalculatorImpl(this, context, decimalSeparator, groupingSeparator)
        }

        when (action) {
            DECIMAL -> calc!!.numpadClicked(R.id.btn_decimal)
            ZERO -> calc!!.numpadClicked(R.id.btn_0)
            ONE -> calc!!.numpadClicked(R.id.btn_1)
            TWO -> calc!!.numpadClicked(R.id.btn_2)
            THREE -> calc!!.numpadClicked(R.id.btn_3)
            FOUR -> calc!!.numpadClicked(R.id.btn_4)
            FIVE -> calc!!.numpadClicked(R.id.btn_5)
            SIX -> calc!!.numpadClicked(R.id.btn_6)
            SEVEN -> calc!!.numpadClicked(R.id.btn_7)
            EIGHT -> calc!!.numpadClicked(R.id.btn_8)
            NINE -> calc!!.numpadClicked(R.id.btn_9)
            EQUALS -> calc!!.handleEquals()
            CLEAR -> calc!!.handleClear()
            RESET -> calc!!.handleReset()
            PLUS, MINUS, MULTIPLY, DIVIDE, PERCENT, POWER, ROOT -> calc!!.handleOperation(action)
        }
    }

    override fun showNewResult(value: String, context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context) ?: return
        appWidgetManager.getAppWidgetIds(getComponentName(context)).forEach {
            val views = RemoteViews(context.packageName, R.layout.widget)
            views.setText(R.id.result, value)
            appWidgetManager.partiallyUpdateAppWidget(it, views)
        }
    }

    override fun showNewFormula(value: String, context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context) ?: return
        appWidgetManager.getAppWidgetIds(getComponentName(context)).forEach {
            val views = RemoteViews(context.packageName, R.layout.widget)
            views.setText(R.id.formula, value)
            appWidgetManager.partiallyUpdateAppWidget(it, views)
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        calc = null
    }

    private fun setupDecimalSeparator(views: RemoteViews, useCommaAsDecimalMark: Boolean) {
        storedUseCommaAsDecimalMark = useCommaAsDecimalMark
        if (storedUseCommaAsDecimalMark) {
            decimalSeparator = COMMA
            groupingSeparator = DOT
        } else {
            decimalSeparator = DOT
            groupingSeparator = COMMA
        }
        calc?.updateSeparators(decimalSeparator, groupingSeparator)
        views.setTextViewText(R.id.btn_decimal, decimalSeparator)
    }
}
