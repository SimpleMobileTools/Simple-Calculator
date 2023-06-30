package com.simplemobiletools.calculator.activities

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.simplemobiletools.calculator.compose.screens.SettingsScreen
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface
import com.simplemobiletools.calculator.databinding.ActivitySettingsBinding
import com.simplemobiletools.calculator.extensions.calculatorDB
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateWidgets
import com.simplemobiletools.calculator.extensions.viewBinding
import com.simplemobiletools.commons.activities.CustomizationActivity
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.*
import java.util.Locale
import kotlin.system.exitProcess

class SettingsActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySettingsBinding::inflate)
    private val preferences by lazy { config }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppThemeSurface {
                SettingsScreen(
                    customizeColors = ::handleCustomizeColorsClick, goBack = ::finish
                )
            }
        }

    }


    private fun setupPurchaseThankYou() {
        binding.settingsPurchaseThankYouHolder.beGoneIf(isOrWasThankYouInstalled())
        binding.settingsPurchaseThankYouHolder.setOnClickListener {
            launchPurchaseThankYouIntent()
        }
    }

    private fun setupCustomizeColors() {
        binding.settingsWidgetColorCustomizationLabel.text = getCustomizeColorsString()
        binding.settingsWidgetColorCustomizationHolder.setOnClickListener {
            handleCustomizeColorsClick()
        }
    }

    private fun handleCustomizeColorsClick() {
        Intent(applicationContext, CustomizationActivity::class.java).apply {
            /*putExtra(APP_ICON_IDS, getAppIconIDs())
            putExtra(APP_LAUNCHER_NAME, getAppLauncherName())*/
            startActivity(this)
        }
    }

    private fun setupUseEnglish() {
        binding.settingsUseEnglishHolder.beVisibleIf((preferences.wasUseEnglishToggled || Locale.getDefault().language != "en") && !isTiramisuPlus())
        binding.settingsUseEnglish.isChecked = preferences.useEnglish
        binding.settingsUseEnglishHolder.setOnClickListener {
            binding.settingsUseEnglish.toggle()
            preferences.useEnglish = binding.settingsUseEnglish.isChecked
            exitProcess(0)
        }
    }

    private fun setupLanguage() {
        binding.settingsLanguage.text = Locale.getDefault().displayLanguage
        binding.settingsLanguageHolder.beVisibleIf(isTiramisuPlus())
        binding.settingsLanguageHolder.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // launchChangeAppLanguageIntent()
            }
        }
    }

    private fun setupVibrate() {
        binding.settingsVibrate.isChecked = preferences.vibrateOnButtonPress
        binding.settingsVibrateHolder.setOnClickListener {
            binding.settingsVibrate.toggle()
            preferences.vibrateOnButtonPress = binding.settingsVibrate.isChecked
        }
    }

    private fun setupPreventPhoneFromSleeping() {
        binding.settingsPreventPhoneFromSleeping.isChecked = preferences.preventPhoneFromSleeping
        binding.settingsPreventPhoneFromSleepingHolder.setOnClickListener {
            binding.settingsPreventPhoneFromSleeping.toggle()
            preferences.preventPhoneFromSleeping = binding.settingsPreventPhoneFromSleeping.isChecked
        }
    }

    private fun setupUseCommaAsDecimalMark() {
        binding.settingsUseCommaAsDecimalMark.isChecked = preferences.useCommaAsDecimalMark
        binding.settingsUseCommaAsDecimalMark.setOnClickListener {
            binding.settingsUseCommaAsDecimalMark.toggle()
            preferences.useCommaAsDecimalMark = binding.settingsUseCommaAsDecimalMark.isChecked
            updateWidgets()
            ensureBackgroundThread {
                applicationContext.calculatorDB.deleteHistory()
            }
        }
    }

    private fun setupCustomizeWidgetColors() {
        binding.settingsWidgetColorCustomizationHolder.setOnClickListener {
            Intent(this, WidgetConfigureActivity::class.java).apply {
                putExtra(IS_CUSTOMIZING_COLORS, true)
                startActivity(this)
            }
        }
    }
}
