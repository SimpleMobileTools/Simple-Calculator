package com.simplemobiletools.calculator.activities

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import android.widget.SeekBar
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.MyWidgetProvider
import com.simplemobiletools.commons.dialogs.ColorPickerDialog
import com.simplemobiletools.commons.helpers.PREFS_KEY
import com.simplemobiletools.commons.helpers.WIDGET_BG_COLOR
import com.simplemobiletools.commons.helpers.WIDGET_TEXT_COLOR
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.widget_config.*

class WidgetConfigureActivity : AppCompatActivity() {

    private var mBgColor: Int = 0
    private var mBgColorWithoutTransparency: Int = 0
    private var mWidgetId: Int = 0
    private var mTextColor: Int = 0
    private var mBgAlpha: Float = 0.toFloat()

    private val bgSeekbarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mBgAlpha = progress.toFloat() / 100.toFloat()
            updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.widget_config)

        config_save.setOnClickListener { saveConfig() }
        config_bg_color.setOnClickListener { pickBackgroundColor() }
        config_text_color.setOnClickListener { pickTextColor() }

        initVariables()

        val intent = intent
        val extras = intent.extras
        if (extras != null)
            mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

        if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish()
    }

    private fun initVariables() {
        val prefs = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        mBgColor = prefs.getInt(WIDGET_BG_COLOR, 1)
        if (mBgColor == 1) {
            mBgColor = Color.BLACK
            mBgAlpha = .2f
        } else {
            mBgAlpha = Color.alpha(mBgColor) / 255.toFloat()
        }

        btn_reset.visibility = View.VISIBLE
        mBgColorWithoutTransparency = Color.rgb(Color.red(mBgColor), Color.green(mBgColor), Color.blue(mBgColor))
        config_bg_seekbar.setOnSeekBarChangeListener(bgSeekbarChangeListener)
        config_bg_seekbar.progress = (mBgAlpha * 100).toInt()
        updateBackgroundColor()

        mTextColor = prefs.getInt(WIDGET_TEXT_COLOR, resources.getColor(R.color.color_primary))
        updateTextColor()

        formula.text = "15,937*5"
        result.text = "79,685"
    }

    fun saveConfig() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val views = RemoteViews(packageName, R.layout.activity_main)
        views.setInt(R.id.calculator_holder, "setBackgroundColor", mBgColor)
        appWidgetManager.updateAppWidget(mWidgetId, views)

        storeWidgetBackground()
        requestWidgetUpdate()

        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }

    private fun storeWidgetBackground() {
        val prefs = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        prefs.edit().putInt(WIDGET_BG_COLOR, mBgColor).apply()
        prefs.edit().putInt(WIDGET_TEXT_COLOR, mTextColor).apply()
    }

    private fun requestWidgetUpdate() {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, MyWidgetProvider::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(mWidgetId))
        sendBroadcast(intent)
    }

    private fun updateBackgroundColor() {
        mBgColor = adjustAlpha(mBgColorWithoutTransparency, mBgAlpha)
        config_calc.setBackgroundColor(mBgColor)
        config_bg_color.setBackgroundColor(mBgColor)
        config_save.setBackgroundColor(mBgColor)
    }

    private fun updateTextColor() {
        config_text_color.setBackgroundColor(mTextColor)
        config_save.setTextColor(mTextColor)

        val viewIds = intArrayOf(R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_modulo, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide, R.id.btn_multiply, R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals)
        result.setTextColor(mTextColor)
        formula.setTextColor(mTextColor)

        var btn: Button
        for (i in viewIds) {
            btn = findViewById<Button>(i)
            btn.setTextColor(mTextColor)
        }
    }

    fun pickBackgroundColor() {
        ColorPickerDialog(this, mBgColorWithoutTransparency) {
            mBgColorWithoutTransparency = it
            updateBackgroundColor()
        }
    }

    fun pickTextColor() {
        ColorPickerDialog(this, mTextColor) {
            mTextColor = it
            updateTextColor()
        }
    }

    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}
