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
import com.simplemobiletools.commons.dialogs.WidgetLockedDialog
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.IS_CUSTOMIZING_COLORS
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.widget_config.*

class WidgetConfigureActivity : SimpleActivity() {
    private var mBgAlpha = 0f
    private var mWidgetId = 0
    private var mBgColor = 0
    private var mTextColor = 0
    private var mBgColorWithoutTransparency = 0
    private var mWidgetLockedDialog: WidgetLockedDialog? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        useDynamicTheme = false
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.widget_config)
        initVariables()

        val isCustomizingColors = intent.extras?.getBoolean(IS_CUSTOMIZING_COLORS) ?: false
        mWidgetId = intent.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID && !isCustomizingColors) {
            finish()
        }

        config_save.setOnClickListener { saveConfig() }
        config_bg_color.setOnClickListener { pickBackgroundColor() }
        config_text_color.setOnClickListener { pickTextColor() }

        val primaryColor = getProperPrimaryColor()
        config_bg_seekbar.setColors(mTextColor, primaryColor, primaryColor)

        if (!isCustomizingColors && !isOrWasThankYouInstalled()) {
            mWidgetLockedDialog = WidgetLockedDialog(this) {
                if (!isOrWasThankYouInstalled()) {
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.setBackgroundColor(0)

        if (mWidgetLockedDialog != null && isOrWasThankYouInstalled()) {
            mWidgetLockedDialog?.dismissDialog()
        }
    }

    private fun initVariables() {
        mBgColor = config.widgetBgColor
        mBgAlpha = Color.alpha(mBgColor) / 255.toFloat()

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
        val appWidgetManager = AppWidgetManager.getInstance(this) ?: return
        val views = RemoteViews(packageName, R.layout.widget).apply {
            applyColorFilter(R.id.widget_background, mBgColor)
        }

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
        widget_background.applyColorFilter(mBgColor)
        config_bg_color.setFillWithStroke(mBgColor, mBgColor)
    }

    private fun updateTextColor() {
        config_text_color.setFillWithStroke(mTextColor, mTextColor)

        val viewIds = intArrayOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
            R.id.btn_9, R.id.btn_percent, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide, R.id.btn_multiply,
            R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals
        )
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

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }
}
