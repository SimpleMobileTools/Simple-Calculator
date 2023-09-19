package com.simplemobiletools.calculator.activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.databinding.ActivityUnitConverterBinding
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.helpers.COMMA
import com.simplemobiletools.calculator.helpers.CONVERTER_STATE
import com.simplemobiletools.calculator.helpers.DOT
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.commons.extensions.getProperTextColor
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simplemobiletools.commons.extensions.viewBinding
import com.simplemobiletools.commons.helpers.LOWER_ALPHA_INT
import com.simplemobiletools.commons.helpers.MEDIUM_ALPHA_INT
import com.simplemobiletools.commons.helpers.NavigationIcon

class UnitConverterActivity : SimpleActivity() {
    companion object {
        const val EXTRA_CONVERTER_ID = "converter_id"
    }

    private val binding by viewBinding(ActivityUnitConverterBinding::inflate)
    private var vibrateOnButtonPress = true
    private lateinit var converter: Converter

    override fun onCreate(savedInstanceState: Bundle?) {
        isMaterialActivity = true
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.unitConverterToolbar.inflateMenu(R.menu.converter_menu)
            setupOptionsMenu()
        }

        updateMaterialActivityViews(binding.unitConverterCoordinator, null, useTransparentNavigation = false, useTopSearchMenu = false)
        setupMaterialScrollListener(binding.nestedScrollview, binding.unitConverterToolbar)

        val converter = Converter.ALL.getOrNull(intent.getIntExtra(EXTRA_CONVERTER_ID, 0))

        if (converter == null) {
            finish()
            return
        }
        this.converter = converter

        binding.viewUnitConverter.btnClear.setVibratingOnClickListener {
            binding.viewUnitConverter.viewConverter.root.deleteCharacter()
        }
        binding.viewUnitConverter.btnClear.setOnLongClickListener {
            binding.viewUnitConverter.viewConverter.root.clear()
            true
        }

        getButtonIds().forEach {
            it.setVibratingOnClickListener { view ->
                binding.viewUnitConverter.viewConverter.root.numpadClicked(view.id)
            }
        }

        binding.viewUnitConverter.viewConverter.root.setConverter(converter)
        binding.unitConverterToolbar.setTitle(converter.nameResId)

        if (savedInstanceState != null) {
            savedInstanceState.getBundle(CONVERTER_STATE)?.also {
                binding.viewUnitConverter.viewConverter.root.restoreFromSavedState(it)
            }
        } else {
            val storedState = config.getLastConverterUnits(converter)
            if (storedState != null) {
                binding.viewUnitConverter.viewConverter.root.updateUnits(storedState.topUnit, storedState.bottomUnit)
            }
        }
    }

    private fun setupOptionsMenu() {
        binding.unitConverterToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.swap_units -> binding.viewUnitConverter.viewConverter.root.switch()
                else -> return@setOnMenuItemClickListener false
            }
            return@setOnMenuItemClickListener true
        }
    }

    override fun onResume() {
        super.onResume()

        setupToolbar(binding.unitConverterToolbar, NavigationIcon.Arrow)
        binding.viewUnitConverter.viewConverter.root.updateColors()
        binding.viewUnitConverter.converterHolder.let { updateViewColors(it, getProperTextColor()) }

        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        setupDecimalSeparator()

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
        if (config.preventPhoneFromSleeping) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(CONVERTER_STATE, binding.viewUnitConverter.viewConverter.root.saveState())
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
        val decimalSeparator: String
        val groupingSeparator: String
        if (config.useCommaAsDecimalMark) {
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
