package com.simplemobiletools.calculator.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simplemobiletools.calculator.compose.extensions.MyDevices
import com.simplemobiletools.calculator.compose.settings.SettingsCheckBoxComponent
import com.simplemobiletools.calculator.compose.settings.SettingsGroup
import com.simplemobiletools.calculator.compose.settings.SettingsPreferenceComponent
import com.simplemobiletools.calculator.compose.settings.SettingsTitleTextComponent
import com.simplemobiletools.calculator.compose.theme.*
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.helpers.isTiramisuPlus
import java.util.Locale

@Composable
fun SettingsScreen(
    goBack: () -> Unit,
    customizeColors: () -> Unit,
    customizeWidgetColors: () -> Unit,
    topBarsScrolledContainerColor: Color = MaterialTheme.colorScheme.primary,
    nonScrolledTextColor: Color = if (isSurfaceLitWell()) Color.Black else Color.White,
    scrolledTextColor: Color = if (topBarsScrolledContainerColor.isLitWell()) Color.Black else Color.White,
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
    topBarsContentColor: Color
) {
    val systemUiController = rememberSystemUiController()
    val displayLanguage = remember { Locale.getDefault().displayLanguage }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val colorTransitionFraction = scrollBehavior.state.overlappedFraction
    val fraction = if (colorTransitionFraction > 0.01f) 1f else 0f
    val scrolledColor = lerp(
        start = nonScrolledTextColor,
        stop = scrolledTextColor,
        fraction = fraction
    )
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = scrolledColor.isNotLitWell())
    }
    val startingPadding = Modifier.padding(horizontal = 4.dp)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
                        color = scrolledColor
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier
                            .clickable { goBack() }
                            .padding(start = 8.dp),
                        tint = scrolledColor
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    scrolledContainerColor = topBarsScrolledContainerColor,
                    containerColor = if (colorTransitionFraction == 1f) topBarsContentColor else MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = if (colorTransitionFraction == 1f) topBarsContentColor else MaterialTheme.colorScheme.surface
                ),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                Modifier
                    .matchParentSize()
                    .verticalScroll(rememberScrollState()),
            ) {
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
                Divider(color = divider_grey)
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
            }
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
            topBarsScrolledContainerColor = MaterialTheme.colorScheme.primary,
            nonScrolledTextColor = Color.White,
            scrolledTextColor = Color.Black,
            preventPhoneFromSleeping = false,
            onPreventPhoneFromSleeping = {},
            vibrateOnButtonPressFlow = false,
            onVibrateOnButtonPressFlow = {},
            isOrWasThankYouInstalled = false,
            onThankYou = {},
            isUseEnglishEnabled = false,
            isUseEnglishChecked = false,
            onUseEnglishPress = {},
            onSetupLanguagePress = {}, useCommaAsDecimalMarkFlow = false, onUseCommaAsDecimalMarkFlow = {},
            lockedCustomizeColorText = null,
            topBarsContentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}
