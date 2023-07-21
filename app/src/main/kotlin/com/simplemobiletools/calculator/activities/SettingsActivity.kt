package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplemobiletools.calculator.compose.extensions.TransparentSystemBars
import com.simplemobiletools.calculator.compose.extensions.onEventValue
import com.simplemobiletools.calculator.compose.screens.SettingsScreen
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface
import com.simplemobiletools.calculator.compose.theme.OnLifecycleEvent
import com.simplemobiletools.calculator.compose.theme.getAppIconIds
import com.simplemobiletools.calculator.compose.theme.getAppLauncherName
import com.simplemobiletools.calculator.extensions.*
import com.simplemobiletools.commons.activities.CustomizationActivity
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.*
import java.util.Locale
import kotlin.system.exitProcess

class SettingsActivity : AppCompatActivity() {

    private val preferences by lazy { config }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TransparentSystemBars()
            AppThemeSurface {
                val context = LocalContext.current
                val preventPhoneFromSleeping by preferences.preventPhoneFromSleepingFlow.collectAsStateWithLifecycle(preferences.preventPhoneFromSleeping)
                val vibrateOnButtonPressFlow by preferences.vibrateOnButtonPressFlow.collectAsStateWithLifecycle(preferences.vibrateOnButtonPress)
                val wasUseEnglishToggledFlow by preferences.wasUseEnglishToggledFlow.collectAsStateWithLifecycle(preferences.wasUseEnglishToggled)
                val useEnglishFlow by preferences.useEnglishFlow.collectAsStateWithLifecycle(preferences.useEnglish)
                val useCommaAsDecimalMarkFlow by preferences.useCommaAsDecimalMarkFlow.collectAsStateWithLifecycle(preferences.useCommaAsDecimalMark)
                val isUseEnglishEnabled by remember(wasUseEnglishToggledFlow) {
                    derivedStateOf {
                        (wasUseEnglishToggledFlow || Locale.getDefault().language != "en") && !isTiramisuPlus()
                    }
                }
                val isOrWasThankYouInstalled = onEventValue { context.isOrWasThankYouInstalled() }
                val statusBarColor = onEventValue { context.getColoredMaterialStatusBarColor() }
                val contrastColor = onEventValue { statusBarColor.getContrastColor() }

                SettingsScreen(
                    topBarsContentColor = Color(contrastColor),
                    topBarsScrolledContainerColor = Color(statusBarColor),
                    preventPhoneFromSleeping = preventPhoneFromSleeping,
                    customizeColors = ::handleCustomizeColorsClick,
                    goBack = ::finish,
                    customizeWidgetColors = ::setupCustomizeWidgetColors,
                    onPreventPhoneFromSleeping = preferences::preventPhoneFromSleeping::set,
                    vibrateOnButtonPressFlow = vibrateOnButtonPressFlow,
                    onVibrateOnButtonPressFlow = preferences::vibrateOnButtonPress::set,
                    isOrWasThankYouInstalled = isOrWasThankYouInstalled,
                    onThankYou = ::launchPurchaseThankYouIntent,
                    isUseEnglishEnabled = isUseEnglishEnabled,
                    isUseEnglishChecked = useEnglishFlow,
                    onUseEnglishPress = { isChecked ->
                        preferences.useEnglish = isChecked
                        exitProcess(0)
                    },
                    onSetupLanguagePress = ::launchChangeAppLanguageIntent,
                    useCommaAsDecimalMarkFlow = useCommaAsDecimalMarkFlow,
                    onUseCommaAsDecimalMarkFlow = { isChecked ->
                        preferences.useCommaAsDecimalMark = isChecked
                        updateWidgets()
                        ensureBackgroundThread {
                            applicationContext.calculatorDB.deleteHistory()
                        }
                    },
                    lockedCustomizeColorText = if (isOrWasThankYouInstalled) null else getCustomizeColorsString()
                )
            }
        }
    }

    private fun handleCustomizeColorsClick() {
        Intent(applicationContext, CustomizationActivity::class.java).apply {
            putExtra(APP_ICON_IDS, getAppIconIds())
            putExtra(APP_LAUNCHER_NAME, getAppLauncherName())
            startActivity(this)
        }
    }

    private fun setupCustomizeWidgetColors() {
        Intent(this, WidgetConfigureActivity::class.java).apply {
            putExtra(IS_CUSTOMIZING_COLORS, true)
            startActivity(this)
        }
    }
}
