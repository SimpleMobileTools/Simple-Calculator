package com.simplemobiletools.calculator.activities

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import android.widget.SeekBar
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.helpers.MyWidgetProvider
import com.simplemobiletools.commons.dialogs.ColorPickerDialog
import com.simplemobiletools.commons.extensions.adjustAlpha
import com.simplemobiletools.commons.extensions.beVisible
import com.simplemobiletools.commons.extensions.setBackgroundColor
import com.simplemobiletools.commons.extensions.setFillWithStroke
import com.simplemobiletools.commons.helpers.IS_CUSTOMIZING_COLORS
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.widget_config.*

class WidgetConfigureActivity : SimpleActivity() {
    private var mBgColor = 0
    private var mBgColorWithoutTransparency = 0
    private var mWidgetId = 0
    private var mTextColor = 0
    private var mBgAlpha = 0f
    private var mIsCustomizingColors = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        useDynamicTheme = false
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.widget_config)
        initVariables()

        mIsCustomizingColors = intent.extras?.getBoolean(IS_CUSTOMIZING_COLORS) ?: false
        mWidgetId = intent.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID && !mIsCustomizingColors) {
            finish()
        }

        config_save.setOnClickListener { saveConfig() }
        config_bg_color.setOnClickListener { pickBackgroundColor() }
        config_text_color.setOnClickListener { pickTextColor() }
    }

    private fun initVariables() {
        mBgColor = config.widgetBgColor
        if (mBgColor == 1) {
            mBgColor = Color.BLACK
            mBgAlpha = .2f
        } else {
            mBgAlpha = Color.alpha(mBgColor) / 255.toFloat()
        }

        btn_reset.beVisible()
        mBgColorWithoutTransparency = Color.rgb(Color.red(mBgColor), Color.green(mBgColor), Color.blue(mBgColor))
        config_bg_seekbar.setOnSeekBarChangeListener(seekbarChangeListener)
        config_bg_seekbar.progress = (mBgAlpha * 100).toInt()
        updateBackgroundColor()

        mTextColor = config.widgetTextColor
        updateTextColor()

        formula.text = "15,937*5"
        result.text = "79,685"
    }

    private fun saveConfig() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val views = RemoteViews(packageName, R.layout.activity_main)
        views.setBackgroundColor(R.id.calculator_holder, mBgColor)
        appWidgetManager.updateAppWidget(mWidgetId, views)

        storeWidgetColors()
        requestWidgetUpdate()

        Intent().apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId)
            setResult(Activity.RESULT_OK, this)
        }
        finish()
    }

    private fun storeWidgetColors() {
        config.apply {
            widgetBgColor = mBgColor
            widgetTextColor = mTextColor
        }
    }

    private fun requestWidgetUpdate() {
        Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, MyWidgetProvider::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(mWidgetId))
            sendBroadcast(this)
        }
    }

    private fun updateBackgroundColor() {
        mBgColor = mBgColorWithoutTransparency.adjustAlpha(mBgAlpha)
        config_calc.setBackgroundColor(mBgColor)
        config_bg_color.setFillWithStroke(mBgColor, Color.BLACK)
        config_save.setBackgroundColor(mBgColor)
    }

    private fun updateTextColor() {
        config_text_color.setFillWithStroke(mTextColor, Color.BLACK)
        config_save.setTextColor(mTextColor)

        val viewIds = intArrayOf(R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9, R.id.btn_percent, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide, R.id.btn_multiply,
                R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals)
        result.setTextColor(mTextColor)
        formula.setTextColor(mTextColor)

        viewIds.forEach {
            (findViewById<Button>(it)).setTextColor(mTextColor)
        }
    }

    private fun pickBackgroundColor() {
        ColorPickerDialog(this, mBgColorWithoutTransparency) { wasPositivePressed, color ->
            if (wasPositivePressed) {
                mBgColorWithoutTransparency = color
                updateBackgroundColor()
            }
        }
    }

    private fun pickTextColor() {
        ColorPickerDialog(this, mTextColor) { wasPositivePressed, color ->
            if (wasPositivePressed) {
                mTextColor = color
                updateTextColor()
            }
        }
    }

    private val seekbarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mBgAlpha = progress.toFloat() / 100.toFloat()
            updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }
}
