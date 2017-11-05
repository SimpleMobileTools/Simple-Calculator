package com.simplemobiletools.calculator.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.commons.extensions.toast
import com.simplemobiletools.commons.extensions.value
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.helpers.LICENSE_ESPRESSO
import com.simplemobiletools.commons.helpers.LICENSE_KOTLIN
import com.simplemobiletools.commons.helpers.LICENSE_ROBOLECTRIC
import kotlinx.android.synthetic.main.activity_main.*
import me.grantland.widget.AutofitHelper

class MainActivity : SimpleActivity(), Calculator {
    companion object {
        private lateinit var mCalc: CalculatorImpl
    }

    val calc: CalculatorImpl?
        get() = mCalc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_plus.setOnClickListener { mCalc.handleOperation(PLUS) }
        btn_minus.setOnClickListener { mCalc.handleOperation(MINUS) }
        btn_multiply.setOnClickListener { mCalc.handleOperation(MULTIPLY) }
        btn_divide.setOnClickListener { mCalc.handleOperation(DIVIDE) }
        btn_modulo.setOnClickListener { mCalc.handleOperation(MODULO) }
        btn_power.setOnClickListener { mCalc.handleOperation(POWER) }
        btn_root.setOnClickListener { mCalc.handleOperation(ROOT) }

        btn_clear.setOnClickListener { mCalc.handleClear() }
        btn_clear.setOnLongClickListener { mCalc.handleReset(); true }

        arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9).forEach {
            it.setOnClickListener { mCalc.numpadClicked(it.id) }
        }

        btn_equals.setOnClickListener { mCalc.handleEquals() }
        formula.setOnLongClickListener { copyToClipboard(false) }
        result.setOnLongClickListener { copyToClipboard(true) }

        mCalc = CalculatorImpl(this)
        AutofitHelper.create(result)
        AutofitHelper.create(formula)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> launchSettings()
            R.id.about -> launchAbout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun launchSettings() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        startAboutActivity(R.string.app_name, LICENSE_KOTLIN or LICENSE_AUTOFITTEXTVIEW or LICENSE_ROBOLECTRIC or LICENSE_ESPRESSO, BuildConfig.VERSION_NAME)
    }

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = formula.value
        if (copyResult) {
            value = result.value
        }

        if (value.isEmpty())
            return false

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(resources.getString(R.string.app_name), value)
        clipboard.primaryClip = clip
        toast(R.string.copied_to_clipboard)
        return true
    }

    override fun setValue(value: String) {
        result.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        mCalc.setValue(Formatter.doubleToString(d))
        mCalc.setLastKey(DIGIT)
    }

    override fun setFormula(value: String) {
        formula.text = value
    }
}
