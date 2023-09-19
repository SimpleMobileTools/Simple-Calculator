package com.simplemobiletools.calculator.compose

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.compose.extensions.MyDevices
import com.simplemobiletools.commons.compose.settings.SettingsCheckBoxComponent
import com.simplemobiletools.commons.compose.settings.SettingsGroup
import com.simplemobiletools.commons.compose.settings.SettingsPreferenceComponent
import com.simplemobiletools.commons.compose.settings.SettingsTitleTextComponent
import com.simplemobiletools.commons.compose.settings.scaffold.SettingsScaffold
import com.simplemobiletools.commons.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.compose.theme.divider_grey
import com.simplemobiletools.commons.helpers.isTiramisuPlus

@Composable
internal fun SettingsScreen(
    goBack: () -> Unit,
    customizeColors: () -> Unit,
    customizeWidgetColors: () -> Unit,
    preventPhoneFromSleeping: Boolean,
    onPreventPhoneFromSleeping: (Boolean) -> Unit,
    vibrateOnButtonPressFlow: Boolean,
    onVibrateOnButtonPressFlow: (Boolean) -> Unit,
    isOrWasThankYouInstalled: Boolean,
    onThankYou: () -> Unit,
    isUseEnglishEnabled: Boolean,
    isUseEnglishChecked: Boolean,
    onUseEnglishPress: (Boolean) -> Unit,
    onSetupLanguagePress: () -> Unit,
    useCommaAsDecimalMarkFlow: Boolean,
    onUseCommaAsDecimalMarkFlow: (Boolean) -> Unit,
    lockedCustomizeColorText: String?,
    displayLanguage: String
) {
    SettingsScaffold(title = stringResource(id = R.string.settings), goBack = goBack) {
        SettingsGroup(title = {
            SettingsTitleTextComponent(text = stringResource(id = R.string.color_customization))
        }) {
            SettingsPreferenceComponent(
                preferenceTitle = stringResource(id = R.string.customize_colors),
                doOnPreferenceClick = customizeColors,
                isPreferenceEnabled = isOrWasThankYouInstalled,
                preferenceSummary = lockedCustomizeColorText
            )
            SettingsPreferenceComponent(
                preferenceTitle = stringResource(id = R.string.customize_widget_colors),
                doOnPreferenceClick = customizeWidgetColors
            )
        }
        HorizontalDivider(color = divider_grey)
        SettingsGroup(title = {
            SettingsTitleTextComponent(text = stringResource(id = R.string.general_settings))
        }) {
            if (!isOrWasThankYouInstalled) {
                SettingsPreferenceComponent(
                    preferenceTitle = stringResource(id = R.string.purchase_simple_thank_you),
                    doOnPreferenceClick = onThankYou,
                )
            }
            if (isUseEnglishEnabled) {
                SettingsCheckBoxComponent(
                    title = stringResource(id = R.string.use_english_language),
                    initialValue = isUseEnglishChecked,
                    onChange = onUseEnglishPress,
                )
            }
            if (isTiramisuPlus()) {
                SettingsPreferenceComponent(
                    preferenceTitle = stringResource(id = R.string.language),
                    preferenceSummary = displayLanguage,
                    doOnPreferenceClick = onSetupLanguagePress,
                    preferenceSummaryColor = MaterialTheme.colorScheme.onSurface,
                )
            }
            SettingsCheckBoxComponent(
                title = stringResource(id = R.string.vibrate_on_button_press),
                initialValue = vibrateOnButtonPressFlow,
                onChange = onVibrateOnButtonPressFlow,
            )
            SettingsCheckBoxComponent(
                title = stringResource(id = R.string.prevent_phone_from_sleeping),
                initialValue = preventPhoneFromSleeping,
                onChange = onPreventPhoneFromSleeping,
            )
            SettingsCheckBoxComponent(
                title = stringResource(id = com.simplemobiletools.calculator.R.string.use_comma_as_decimal_mark),
                initialValue = useCommaAsDecimalMarkFlow,
                onChange = onUseCommaAsDecimalMarkFlow,
            )
        }
    }
}

@MyDevices
@Composable
private fun SettingsScreenPreview() {
    AppThemeSurface {
        SettingsScreen(
            goBack = {},
            customizeColors = {},
            customizeWidgetColors = {},
            preventPhoneFromSleeping = false,
            onPreventPhoneFromSleeping = {},
            vibrateOnButtonPressFlow = false,
            onVibrateOnButtonPressFlow = {},
            isOrWasThankYouInstalled = false,
            onThankYou = {},
            isUseEnglishEnabled = false,
            isUseEnglishChecked = false,
            onUseEnglishPress = {},
            onSetupLanguagePress = {},
            useCommaAsDecimalMarkFlow = false,
            onUseCommaAsDecimalMarkFlow = {},
            lockedCustomizeColorText = null,
            displayLanguage = "English"
        )
    }
}
