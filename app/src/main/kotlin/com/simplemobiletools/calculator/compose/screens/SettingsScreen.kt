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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.simplemobiletools.calculator.compose.extensions.MyDevices
import com.simplemobiletools.calculator.compose.settings.SettingsCheckBoxComponent
import com.simplemobiletools.calculator.compose.settings.SettingsGroup
import com.simplemobiletools.calculator.compose.settings.SettingsPreferenceComponent
import com.simplemobiletools.calculator.compose.settings.SettingsTitleTextComponent
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.R

@Composable
fun SettingsScreen(
    goBack: () -> Unit,
    customizeColors: () -> Unit,
    topBarsScrolledContainerColor: Color = MaterialTheme.colorScheme.primary
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
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
                            .fillMaxWidth()
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier
                            .clickable { goBack() }
                            .padding(start = 8.dp)
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    scrolledContainerColor = topBarsScrolledContainerColor,
                ),
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsGroup(title = {
                SettingsTitleTextComponent(text = stringResource(id = R.string.color_customization))
            }) {
                SettingsPreferenceComponent(preferenceTitle = stringResource(id = R.string.customize_colors), doOnPreferenceClick = customizeColors)
                SettingsPreferenceComponent(preferenceTitle = stringResource(id = R.string.customize_widget_colors))
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
            Divider()
            SettingsGroup(title = {
                SettingsTitleTextComponent(text = stringResource(id = R.string.general_settings))
            }) {
                SettingsPreferenceComponent(preferenceTitle = stringResource(id = R.string.purchase_simple_thank_you))
                SettingsCheckBoxComponent(title = stringResource(id = R.string.use_english_language))
                SettingsPreferenceComponent(preferenceTitle = stringResource(id = R.string.language), preferenceSummary = "English")
                SettingsCheckBoxComponent(title = stringResource(id = R.string.vibrate_on_button_press))
                SettingsCheckBoxComponent(title = stringResource(id = R.string.prevent_phone_from_sleeping))
                SettingsCheckBoxComponent(title = stringResource(id = com.simplemobiletools.calculator.R.string.use_comma_as_decimal_mark))
            }
        }
    }
}

@MyDevices
@Composable
private fun SettingsScreenPreview() {
    AppThemeSurface { SettingsScreen(goBack = {}, customizeColors = {}) }
}
