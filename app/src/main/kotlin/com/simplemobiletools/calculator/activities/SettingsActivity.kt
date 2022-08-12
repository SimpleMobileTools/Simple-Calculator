package com.simplemobiletools.calculator.activities

import android.content.Intent
import android.os.Bundle
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.calculatorDB
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateWidgets
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.IS_CUSTOMIZING_COLORS
import com.simplemobiletools.commons.helpers.NavigationIcon
import com.simplemobiletools.commons.helpers.ensureBackgroundThread
import com.simplemobiletools.commons.helpers.isTiramisuPlus
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*
import kotlin.system.exitProcess

class SettingsActivity : SimpleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(settings_toolbar, NavigationIcon.Arrow)

        setupPurchaseThankYou()
        setupCustomizeColors()
        setupUseEnglish()
        setupLanguage()
        setupVibrate()
        setupPreventPhoneFromSleeping()
        setupUseCommaAsDecimalMark()
        setupCustomizeWidgetColors()
        updateTextColors(settings_nested_scrollview)

        arrayOf(settings_color_customization_label, settings_general_settings_label).forEach {
            it.setTextColor(getProperPrimaryColor())
        }

        arrayOf(settings_color_customization_holder, settings_general_settings_holder).forEach {
            it.background.applyColorFilter(getProperBackgroundColor().getContrastColor())
        }
    }

    private fun setupPurchaseThankYou() {
        settings_purchase_thank_you_holder.beGoneIf(isOrWasThankYouInstalled())

        // make sure the corners at ripple fit the stroke rounded corners
        if (settings_purchase_thank_you_holder.isGone()) {
            settings_use_english_holder.background = resources.getDrawable(R.drawable.ripple_top_corners, theme)
            settings_language_holder.background = resources.getDrawable(R.drawable.ripple_top_corners, theme)
        }

        settings_purchase_thank_you_holder.setOnClickListener {
            launchPurchaseThankYouIntent()
        }
    }

    private fun setupCustomizeColors() {
        settings_customize_colors_label.text = getCustomizeColorsString()
        settings_customize_colors_holder.setOnClickListener {
            handleCustomizeColorsClick()
        }
    }

    private fun setupUseEnglish() {
        settings_use_english_holder.beVisibleIf((config.wasUseEnglishToggled || Locale.getDefault().language != "en") && !isTiramisuPlus())
        settings_use_english.isChecked = config.useEnglish
        settings_use_english_holder.setOnClickListener {
            settings_use_english.toggle()
            config.useEnglish = settings_use_english.isChecked
            exitProcess(0)
        }
    }

    private fun setupLanguage() {
        settings_language.text = Locale.getDefault().displayLanguage
        settings_language_holder.beVisibleIf(isTiramisuPlus())

        if (settings_use_english_holder.isGone() && settings_language_holder.isGone() && settings_purchase_thank_you_holder.isGone()) {
            settings_vibrate_holder.background = resources.getDrawable(R.drawable.ripple_top_corners, theme)
        }

        settings_language_holder.setOnClickListener {
            launchChangeAppLanguageIntent()
        }
    }

    private fun setupVibrate() {
        settings_vibrate.isChecked = config.vibrateOnButtonPress
        settings_vibrate_holder.setOnClickListener {
            settings_vibrate.toggle()
            config.vibrateOnButtonPress = settings_vibrate.isChecked
        }
    }

    private fun setupPreventPhoneFromSleeping() {
        settings_prevent_phone_from_sleeping.isChecked = config.preventPhoneFromSleeping
        settings_prevent_phone_from_sleeping_holder.setOnClickListener {
            settings_prevent_phone_from_sleeping.toggle()
            config.preventPhoneFromSleeping = settings_prevent_phone_from_sleeping.isChecked
        }
    }

    private fun setupUseCommaAsDecimalMark() {
        settings_use_comma_as_decimal_mark.isChecked = config.useCommaAsDecimalMark
        settings_use_comma_as_decimal_mark_holder.setOnClickListener {
            settings_use_comma_as_decimal_mark.toggle()
            config.useCommaAsDecimalMark = settings_use_comma_as_decimal_mark.isChecked
            updateWidgets()
            ensureBackgroundThread {
                applicationContext.calculatorDB.deleteHistory()
            }
        }
    }

    private fun setupCustomizeWidgetColors() {
        settings_customize_widget_colors_holder.setOnClickListener {
            Intent(this, WidgetConfigureActivity::class.java).apply {
                putExtra(IS_CUSTOMIZING_COLORS, true)
                startActivity(this)
            }
        }
    }
}
