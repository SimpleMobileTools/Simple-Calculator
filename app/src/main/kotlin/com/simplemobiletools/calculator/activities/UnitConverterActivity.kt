package com.simplemobiletools.calculator.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.databinding.ActivityUnitConverterBinding
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.helpers.COMMA
import com.simplemobiletools.calculator.helpers.DOT
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.LOWER_ALPHA_INT
import com.simplemobiletools.commons.helpers.MEDIUM_ALPHA_INT
import com.simplemobiletools.commons.helpers.NavigationIcon

class UnitConverterActivity : SimpleActivity() {
    private val binding by viewBinding(ActivityUnitConverterBinding::inflate)
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseCommaAsDecimalMark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isMaterialActivity = true
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appLaunched(BuildConfig.APPLICATION_ID)
        updateMaterialActivityViews(binding.unitConverterCoordinator, null, useTransparentNavigation = false, useTopSearchMenu = false)
        setupMaterialScrollListener(binding.nestedScrollview, binding.unitConverterToolbar)
        storeStateVariables()

        binding.viewUnitConverter.unitsTabLayout.onTabSelectionChanged(
            tabSelectedAction = {
                binding.viewUnitConverter.viewConverter.root.setConverter(Converter.ALL[it.id])
            }
        )
        binding.viewUnitConverter.btnClear.setVibratingOnClickListener {
            binding.viewUnitConverter.viewConverter.root.clear()
        }

        getButtonIds().forEach {
            it.setVibratingOnClickListener { view ->
                binding.viewUnitConverter.viewConverter.root.numpadClicked(view.id)
            }
        }

        Converter.ALL.forEachIndexed { index, converter ->
            binding.viewUnitConverter.unitsTabLayout.newTab()
                .setId(index)
                .setText(converter.nameResId)
                .apply { binding.viewUnitConverter.unitsTabLayout.addTab(this) }
        }
        binding.viewUnitConverter.viewConverter.root.setConverter(Converter.ALL.first())
    }

    override fun onResume() {
        super.onResume()

        setupToolbar(binding.unitConverterToolbar, NavigationIcon.Cross)
        binding.viewUnitConverter.unitsTabLayout.setBackgroundColor(getProperBackgroundColor())
        binding.viewUnitConverter.viewConverter.root.updateColors()
        if (storedTextColor != config.textColor) {
            binding.viewUnitConverter.converterHolder.let { updateViewColors(it, getProperTextColor()) }
        }

        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        if (storedUseCommaAsDecimalMark != config.useCommaAsDecimalMark) {
            setupDecimalSeparator()
        }

        vibrateOnButtonPress = config.vibrateOnButtonPress

        binding.viewUnitConverter.apply {
            arrayOf(btnClear, btnDecimal).forEach {
                it.background = ResourcesCompat.getDrawable(resources, com.simplemobiletools.commons.R.drawable.pill_background, theme)
                it.background?.alpha = MEDIUM_ALPHA_INT
            }

            arrayOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9).forEach {
                it.background = ResourcesCompat.getDrawable(resources, com.simplemobiletools.commons.R.drawable.pill_background, theme)
                it.background?.alpha = LOWER_ALPHA_INT
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

    private fun getButtonIds() = binding.viewUnitConverter.run {
        arrayOf(btnDecimal, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
    }

    private fun View.setVibratingOnClickListener(callback: (view: View) -> Unit) {
        setOnClickListener {
            callback(it)
            checkHaptic(it)
        }
    }

    private fun setupDecimalSeparator() {
        var decimalSeparator = DOT
        var groupingSeparator = COMMA
        storedUseCommaAsDecimalMark = config.useCommaAsDecimalMark
        if (storedUseCommaAsDecimalMark) {
            decimalSeparator = COMMA
            groupingSeparator = DOT
        } else {
            decimalSeparator = DOT
            groupingSeparator = COMMA
        }
        binding.viewUnitConverter.viewConverter.root.updateSeparators(decimalSeparator, groupingSeparator)
        binding.viewUnitConverter.btnDecimal.text = decimalSeparator
    }
}
