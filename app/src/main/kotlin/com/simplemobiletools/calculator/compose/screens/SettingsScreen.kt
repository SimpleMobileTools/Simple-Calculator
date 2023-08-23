package com.simplemobiletools.calculator.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.simplemobiletools.calculator.compose.settings.SettingsCheckBoxComponent
import com.simplemobiletools.calculator.compose.settings.SettingsGroup
import com.simplemobiletools.calculator.compose.settings.SettingsPreferenceComponent
import com.simplemobiletools.calculator.compose.settings.SettingsTitleTextComponent
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.compose.extensions.MyDevices
import com.simplemobiletools.commons.compose.settings.scaffold.SettingsScaffold
import com.simplemobiletools.commons.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.compose.theme.divider_grey
import com.simplemobiletools.commons.helpers.isTiramisuPlus
import java.util.Locale

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
    lockedCustomizeColorText: String?
) {
    val displayLanguage = remember { Locale.getDefault().displayLanguage }
    val startingPadding = Modifier.padding(horizontal = 4.dp)
    SettingsScaffold(title = stringResource(id = R.string.settings), goBack = goBack) { paddingValues ->
        SettingsGroup(title = {
            SettingsTitleTextComponent(text = stringResource(id = R.string.color_customization), modifier = startingPadding)
        }) {
            SettingsPreferenceComponent(
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 8.dp)
                    .then(startingPadding),
                preferenceTitle = stringResource(id = R.string.customize_colors),
                doOnPreferenceClick = customizeColors,
                isPreferenceEnabled = isOrWasThankYouInstalled,
                preferenceSummary = lockedCustomizeColorText
            )
            SettingsPreferenceComponent(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .then(startingPadding),
                preferenceTitle = stringResource(id = R.string.customize_widget_colors),
                doOnPreferenceClick = customizeWidgetColors
            )
        }
        HorizontalDivider(color = divider_grey)
        SettingsGroup(title = {
            SettingsTitleTextComponent(text = stringResource(id = R.string.general_settings), modifier = startingPadding)
        }) {
            if (!isOrWasThankYouInstalled) {
                SettingsPreferenceComponent(
                    preferenceTitle = stringResource(id = R.string.purchase_simple_thank_you),
                    doOnPreferenceClick = onThankYou,
                    modifier = startingPadding,
                )
            }
            if (isUseEnglishEnabled) {
                SettingsCheckBoxComponent(
                    title = stringResource(id = R.string.use_english_language),
                    initialValue = isUseEnglishChecked,
                    onChange = onUseEnglishPress,
                    modifier = startingPadding,
                )
            }
            if (isTiramisuPlus()) {
                SettingsPreferenceComponent(
                    preferenceTitle = stringResource(id = R.string.language),
                    preferenceSummary = displayLanguage,
                    doOnPreferenceClick = onSetupLanguagePress,
                    preferenceSummaryColor = MaterialTheme.colorScheme.onSurface,
                    modifier = startingPadding,
                )
            }
            SettingsCheckBoxComponent(
                title = stringResource(id = R.string.vibrate_on_button_press),
                initialValue = vibrateOnButtonPressFlow,
                onChange = onVibrateOnButtonPressFlow,
                modifier = startingPadding,
            )
            SettingsCheckBoxComponent(
                title = stringResource(id = R.string.prevent_phone_from_sleeping),
                initialValue = preventPhoneFromSleeping,
                onChange = onPreventPhoneFromSleeping,
                modifier = startingPadding,
            )
            SettingsCheckBoxComponent(
                title = stringResource(id = com.simplemobiletools.calculator.R.string.use_comma_as_decimal_mark),
                initialValue = useCommaAsDecimalMarkFlow,
                onChange = onUseCommaAsDecimalMarkFlow,
                modifier = startingPadding,
            )
        }
        Spacer(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()))
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
            lockedCustomizeColorText = null
        )
    }
}
