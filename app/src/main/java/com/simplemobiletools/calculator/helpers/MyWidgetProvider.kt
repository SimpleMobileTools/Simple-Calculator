package com.simplemobiletools.calculator.helpers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews
import com.simplemobiletools.calculator.R

import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.commons.helpers.PREFS_KEY
import com.simplemobiletools.commons.helpers.WIDGET_BG_COLOR
import com.simplemobiletools.commons.helpers.WIDGET_TEXT_COLOR

class MyWidgetProvider : AppWidgetProvider(), Calculator {

    companion object {
        private var mRemoteViews: RemoteViews? = null
        private var mCalc: CalculatorImpl? = null
        private var mWidgetManager: AppWidgetManager? = null
        private var mIntent: Intent? = null
        private var mContext: Context? = null
        private var mPrefs: SharedPreferences? = null

        private var mWidgetIds: IntArray? = null
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        initVariables(context)

        mIntent = Intent(context, MyWidgetProvider::class.java)
        setupIntent(DECIMAL, R.id.btn_decimal)
        setupIntent(ZERO, R.id.btn_0)
        setupIntent(ONE, R.id.btn_1)
        setupIntent(TWO, R.id.btn_2)
        setupIntent(THREE, R.id.btn_3)
        setupIntent(FOUR, R.id.btn_4)
        setupIntent(FIVE, R.id.btn_5)
        setupIntent(SIX, R.id.btn_6)
        setupIntent(SEVEN, R.id.btn_7)
        setupIntent(EIGHT, R.id.btn_8)
        setupIntent(NINE, R.id.btn_9)

        setupIntent(EQUALS, R.id.btn_equals)
        setupIntent(PLUS, R.id.btn_plus)
        setupIntent(MINUS, R.id.btn_minus)
        setupIntent(MULTIPLY, R.id.btn_multiply)
        setupIntent(DIVIDE, R.id.btn_divide)
        setupIntent(MODULO, R.id.btn_modulo)
        setupIntent(POWER, R.id.btn_power)
        setupIntent(ROOT, R.id.btn_root)
        setupIntent(CLEAR, R.id.btn_clear)
        setupIntent(RESET, R.id.btn_reset)

        setupAppOpenIntent(R.id.formula)
        setupAppOpenIntent(R.id.result)

        updateWidget()
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun setupIntent(action: String, id: Int) {
        mIntent!!.action = action
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0)
        mRemoteViews!!.setOnClickPendingIntent(id, pendingIntent)
    }

    private fun setupAppOpenIntent(id: Int) {
        val intent = Intent(mContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0)
        mRemoteViews!!.setOnClickPendingIntent(id, pendingIntent)
    }

    private fun initVariables(context: Context) {
        mContext = context
        updateWidgetIds()
        mPrefs = initPrefs(mContext)
        val defaultColor = mContext!!.resources.getColor(R.color.text_grey)
        val newBgColor = mPrefs!!.getInt(WIDGET_BG_COLOR, defaultColor)
        val newTextColor = mPrefs!!.getInt(WIDGET_TEXT_COLOR, Color.WHITE)

        mRemoteViews = RemoteViews(mContext!!.packageName, R.layout.activity_main)
        mRemoteViews!!.setViewVisibility(R.id.btn_reset, View.VISIBLE)
        mRemoteViews!!.setInt(R.id.calculator_holder, "setBackgroundColor", newBgColor)

        updateTextColors(newTextColor)
        mWidgetManager = AppWidgetManager.getInstance(mContext)

        val displayValue = "0"
        mCalc = CalculatorImpl(this, displayValue)
    }

    private fun updateWidgetIds() {
        val component = ComponentName(mContext!!, MyWidgetProvider::class.java)
        mWidgetManager = AppWidgetManager.getInstance(mContext)
        mWidgetIds = mWidgetManager!!.getAppWidgetIds(component)
    }

    private fun updateWidget() {
        for (widgetId in mWidgetIds!!) {
            mWidgetManager!!.updateAppWidget(widgetId, mRemoteViews)
        }
    }

    private fun initPrefs(context: Context?) = context!!.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    private fun updateTextColors(color: Int) {
        val viewIds = intArrayOf(R.id.formula, R.id.result, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_modulo, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide, R.id.btn_multiply, R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals)

        for (i in viewIds) {
            mRemoteViews!!.setInt(i, "setTextColor", color)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        when (action) {
            DECIMAL, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, EQUALS, CLEAR, RESET, PLUS, MINUS, MULTIPLY, DIVIDE, MODULO, POWER, ROOT -> myAction(action, context)
            else -> super.onReceive(context, intent)
        }
    }

    private fun myAction(action: String, context: Context) {
        if (mCalc == null || mRemoteViews == null || mWidgetManager == null || mPrefs == null || mContext == null) {
            initVariables(context)
        }

        when (action) {
            DECIMAL -> mCalc!!.numpadClicked(R.id.btn_decimal)
            ZERO -> mCalc!!.numpadClicked(R.id.btn_0)
            ONE -> mCalc!!.numpadClicked(R.id.btn_1)
            TWO -> mCalc!!.numpadClicked(R.id.btn_2)
            THREE -> mCalc!!.numpadClicked(R.id.btn_3)
            FOUR -> mCalc!!.numpadClicked(R.id.btn_4)
            FIVE -> mCalc!!.numpadClicked(R.id.btn_5)
            SIX -> mCalc!!.numpadClicked(R.id.btn_6)
            SEVEN -> mCalc!!.numpadClicked(R.id.btn_7)
            EIGHT -> mCalc!!.numpadClicked(R.id.btn_8)
            NINE -> mCalc!!.numpadClicked(R.id.btn_9)
            EQUALS -> mCalc!!.handleEquals()
            CLEAR -> mCalc!!.handleClear()
            RESET -> mCalc!!.handleReset()
            PLUS, MINUS, MULTIPLY, DIVIDE, MODULO, POWER, ROOT -> mCalc!!.handleOperation(action)
            else -> {
            }
        }
    }

    override fun setValue(value: String) {
        mRemoteViews!!.setTextViewText(R.id.result, value)
        updateWidget()
    }

    override fun setValueDouble(d: Double) {

    }

    override fun setFormula(value: String) {
        mRemoteViews!!.setTextViewText(R.id.formula, value)
        updateWidget()
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        if (mContext != null)
            updateWidgetIds()
    }
}
