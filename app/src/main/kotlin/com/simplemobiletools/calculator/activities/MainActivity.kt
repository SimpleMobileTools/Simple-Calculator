package com.simplemobiletools.calculator.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.databases.CalculatorDatabase
import com.simplemobiletools.calculator.databinding.ActivityMainBinding
import com.simplemobiletools.calculator.dialogs.HistoryDialog
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.extensions.updateWidgets
import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.helpers.LOWER_ALPHA_INT
import com.simplemobiletools.commons.helpers.MEDIUM_ALPHA_INT
import com.simplemobiletools.commons.models.FAQItem
import com.simplemobiletools.commons.models.Release
import me.grantland.widget.AutofitHelper

class MainActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseCommaAsDecimalMark = false
    private var decimalSeparator = DOT
    private var groupingSeparator = COMMA
    private var saveCalculatorState: String = ""
    private lateinit var calc: CalculatorImpl

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        isMaterialActivity = true
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appLaunched(BuildConfig.APPLICATION_ID)
        setupOptionsMenu()
        refreshMenuItems()
        updateMaterialActivityViews(binding.mainCoordinator, null, useTransparentNavigation = false, useTopSearchMenu = false)
        setupMaterialScrollListener(binding.mainNestedScrollview, binding.mainToolbar)

        if (savedInstanceState != null) {
            saveCalculatorState = savedInstanceState.getCharSequence(CALCULATOR_STATE) as String
        }

        calc = CalculatorImpl(this, applicationContext, decimalSeparator, groupingSeparator, saveCalculatorState)
        binding.btnPlus?.setOnClickOperation(PLUS)
        binding.btnMinus?.setOnClickOperation(MINUS)
        binding.btnMultiply?.setOnClickOperation(MULTIPLY)
        binding.btnDivide?.setOnClickOperation(DIVIDE)
        binding.btnPercent?.setOnClickOperation(PERCENT)
        binding.btnPower?.setOnClickOperation(POWER)
        binding.btnRoot?.setOnClickOperation(ROOT)
        binding.btnMinus?.setOnLongClickListener { calc.turnToNegative() }
        binding.btnClear?.setVibratingOnClickListener { calc.handleClear() }
        binding.btnClear?.setOnLongClickListener {
            calc.handleReset()
            true
        }

        getButtonIds().forEach {
            it?.setVibratingOnClickListener { view ->
                calc.numpadClicked(view.id)
            }
        }

        binding.btnEquals?.setVibratingOnClickListener { calc.handleEquals() }
        binding.formula?.setOnLongClickListener { copyToClipboard(false) }
        binding.result?.setOnLongClickListener { copyToClipboard(true) }
        AutofitHelper.create(binding.result)
        AutofitHelper.create(binding.formula)
        storeStateVariables()
        binding.calculatorHolder?.let { updateViewColors(it, getProperTextColor()) }
        setupDecimalSeparator()
        checkWhatsNewDialog()
        checkAppOnSDCard()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(binding.mainToolbar)
        if (storedTextColor != config.textColor) {
            binding.calculatorHolder?.let { updateViewColors(it, getProperTextColor()) }
        }

        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        if (storedUseCommaAsDecimalMark != config.useCommaAsDecimalMark) {
            setupDecimalSeparator()
            updateWidgets()
        }

        vibrateOnButtonPress = config.vibrateOnButtonPress

        binding.apply {
            arrayOf(btnPercent, btnPower, btnRoot, btnClear, btnReset, btnDivide, btnMultiply, btnPlus, btnMinus, btnEquals, btnDecimal).forEach {
                it?.background = ResourcesCompat.getDrawable(resources, com.simplemobiletools.commons.R.drawable.pill_background, theme)
                it?.background?.alpha = MEDIUM_ALPHA_INT
            }

            arrayOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9).forEach {
                it?.background = ResourcesCompat.getDrawable(resources, com.simplemobiletools.commons.R.drawable.pill_background, theme)
                it?.background?.alpha = LOWER_ALPHA_INT
            }
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

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putString(CALCULATOR_STATE, calc.getCalculatorStateJson().toString())
    }

    private fun setupOptionsMenu() {
        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.history -> showHistory()
                R.id.more_apps_from_us -> launchMoreAppsFromUsIntent()
                R.id.unit_converter -> launchUnitConverter()
                R.id.settings -> launchSettings()
                R.id.about -> launchAbout()
                else -> return@setOnMenuItemClickListener false
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun refreshMenuItems() {
        binding.mainToolbar.menu.apply {
            findItem(R.id.more_apps_from_us).isVisible = !resources.getBoolean(com.simplemobiletools.commons.R.bool.hide_google_relations)
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

    private fun launchUnitConverter() {
        hideKeyboard()
        startActivity(Intent(applicationContext, UnitConverterActivity::class.java))
    }

    private fun launchSettings() {
        hideKeyboard()
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        val licenses = LICENSE_AUTOFITTEXTVIEW

        val faqItems = arrayListOf(
            FAQItem(R.string.faq_1_title, R.string.faq_1_text),
            FAQItem(com.simplemobiletools.commons.R.string.faq_1_title_commons, com.simplemobiletools.commons.R.string.faq_1_text_commons),
            FAQItem(com.simplemobiletools.commons.R.string.faq_4_title_commons, com.simplemobiletools.commons.R.string.faq_4_text_commons)
        )

        if (!resources.getBoolean(com.simplemobiletools.commons.R.bool.hide_google_relations)) {
            faqItems.add(FAQItem(com.simplemobiletools.commons.R.string.faq_2_title_commons, com.simplemobiletools.commons.R.string.faq_2_text_commons))
            faqItems.add(FAQItem(com.simplemobiletools.commons.R.string.faq_6_title_commons, com.simplemobiletools.commons.R.string.faq_6_text_commons))
        }

        startAboutActivity(R.string.app_name, licenses, BuildConfig.VERSION_NAME, faqItems, true)
    }

    private fun getButtonIds() = binding.run {
        arrayOf(btnDecimal, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
    }

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = binding.formula?.value
        if (copyResult) {
            value = binding.result?.value
        }

        return if (value.isNullOrEmpty()) {
            false
        } else {
            copyToClipboard(value)
            true
        }
    }

    override fun showNewResult(value: String, context: Context) {
        binding.result?.text = value
    }

    private fun checkWhatsNewDialog() {
        arrayListOf<Release>().apply {
            add(Release(18, R.string.release_18))
            add(Release(28, R.string.release_28))
            checkWhatsNew(this, BuildConfig.VERSION_CODE)
        }
    }

    override fun showNewFormula(value: String, context: Context) {
        binding.formula?.text = value
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
        binding.btnDecimal?.text = decimalSeparator
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
