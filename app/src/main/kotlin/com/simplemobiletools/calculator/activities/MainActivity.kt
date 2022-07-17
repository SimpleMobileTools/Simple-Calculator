package com.simplemobiletools.calculator.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.databases.CalculatorDatabase
import com.simplemobiletools.calculator.dialogs.HistoryDialog
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.extensions.updateWidgets
import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.models.FAQItem
import com.simplemobiletools.commons.models.Release
import kotlinx.android.synthetic.main.activity_main.*
import me.grantland.widget.AutofitHelper

class MainActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseCommaAsDecimalMark = false
    private var decimalSeparator = DOT
    private var groupingSeparator = COMMA

    private lateinit var calc: CalculatorImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appLaunched(BuildConfig.APPLICATION_ID)
        setupOptionsMenu()

        calc = CalculatorImpl(this, applicationContext)

        btn_plus.setOnClickOperation(PLUS)
        btn_minus.setOnClickOperation(MINUS)
        btn_multiply.setOnClickOperation(MULTIPLY)
        btn_divide.setOnClickOperation(DIVIDE)
        btn_percent.setOnClickOperation(PERCENT)
        btn_power.setOnClickOperation(POWER)
        btn_root.setOnClickOperation(ROOT)
        btn_minus.setOnLongClickListener { calc.turnToNegative() }

        btn_clear.setVibratingOnClickListener { calc.handleClear() }
        btn_clear.setOnLongClickListener {
            calc.handleReset()
            true
        }

        getButtonIds().forEach {
            it.setVibratingOnClickListener { view ->
                calc.numpadClicked(view.id)
            }
        }

        btn_equals.setVibratingOnClickListener { calc.handleEquals() }
        formula.setOnLongClickListener { copyToClipboard(false) }
        result.setOnLongClickListener { copyToClipboard(true) }

        AutofitHelper.create(result)
        AutofitHelper.create(formula)
        storeStateVariables()
        updateViewColors(calculator_holder, getProperTextColor())
        setupDecimalSeparator()
        checkWhatsNewDialog()
        checkAppOnSDCard()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(main_toolbar)
        if (storedTextColor != config.textColor) {
            updateViewColors(calculator_holder, getProperTextColor())
        }

        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        if (storedUseCommaAsDecimalMark != config.useCommaAsDecimalMark) {
            setupDecimalSeparator()
            updateWidgets()
        }

        vibrateOnButtonPress = config.vibrateOnButtonPress

        val properPrimaryColor = getProperPrimaryColor()
        arrayOf(btn_percent, btn_power, btn_root, btn_clear, btn_reset, btn_divide, btn_multiply, btn_plus, btn_minus, btn_equals, btn_decimal).forEach {
            it.setTextColor(properPrimaryColor)
        }
    }

    override fun onPause() {
        super.onPause()
        storeStateVariables()
        if (config.preventPhoneFromSleeping) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            CalculatorDatabase.destroyInstance()
        }
    }

    private fun setupOptionsMenu() {
        main_toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.history -> showHistory()
                R.id.settings -> launchSettings()
                R.id.about -> launchAbout()
                else -> return@setOnMenuItemClickListener false
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun storeStateVariables() {
        config.apply {
            storedTextColor = textColor
            storedUseCommaAsDecimalMark = useCommaAsDecimalMark
        }
    }

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun showHistory() {
        HistoryHelper(this).getHistory {
            if (it.isEmpty()) {
                toast(R.string.history_empty)
            } else {
                HistoryDialog(this, it, calc)
            }
        }
    }

    private fun launchSettings() {
        hideKeyboard()
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        val licenses = LICENSE_AUTOFITTEXTVIEW

        val faqItems = arrayListOf(
            FAQItem(R.string.faq_1_title, R.string.faq_1_text),
            FAQItem(R.string.faq_1_title_commons, R.string.faq_1_text_commons),
            FAQItem(R.string.faq_4_title_commons, R.string.faq_4_text_commons)
        )

        if (!resources.getBoolean(R.bool.hide_google_relations)) {
            faqItems.add(FAQItem(R.string.faq_2_title_commons, R.string.faq_2_text_commons))
            faqItems.add(FAQItem(R.string.faq_6_title_commons, R.string.faq_6_text_commons))
        }

        startAboutActivity(R.string.app_name, licenses, BuildConfig.VERSION_NAME, faqItems, true)
    }

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = formula.value
        if (copyResult) {
            value = result.value
        }

        return if (value.isEmpty()) {
            false
        } else {
            copyToClipboard(value)
            true
        }
    }

    override fun showNewResult(value: String, context: Context) {
        result.text = value
    }

    private fun checkWhatsNewDialog() {
        arrayListOf<Release>().apply {
            add(Release(18, R.string.release_18))
            add(Release(28, R.string.release_28))
            checkWhatsNew(this, BuildConfig.VERSION_CODE)
        }
    }

    override fun showNewFormula(value: String, context: Context) {
        formula.text = value
    }

    private fun setupDecimalSeparator() {
        storedUseCommaAsDecimalMark = config.useCommaAsDecimalMark
        if (storedUseCommaAsDecimalMark) {
            decimalSeparator = COMMA
            groupingSeparator = DOT
        } else {
            decimalSeparator = DOT
            groupingSeparator = COMMA
        }
        calc.updateSeparators(decimalSeparator, groupingSeparator)
        btn_decimal.text = decimalSeparator
    }

    private fun View.setVibratingOnClickListener(callback: (view: View) -> Unit) {
        setOnClickListener {
            callback(it)
            checkHaptic(it)
        }
    }

    private fun View.setOnClickOperation(operation: String) {
        setVibratingOnClickListener {
            calc.handleOperation(operation)
        }
    }
}
