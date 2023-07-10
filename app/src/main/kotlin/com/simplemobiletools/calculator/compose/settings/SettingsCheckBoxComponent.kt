package com.simplemobiletools.calculator.compose.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.simplemobiletools.calculator.compose.extensions.MyDevices
import com.simplemobiletools.calculator.compose.extensions.NoRippleTheme
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface
import com.simplemobiletools.calculator.compose.theme.preferenceSummaryColor
import com.simplemobiletools.calculator.compose.theme.preferenceTitleColor
import com.simplemobiletools.commons.R

@Composable
fun SettingsCheckBoxComponent(
    modifier: Modifier = Modifier,
    title: String,
    summary: String? = null,
    initialValue: Boolean = false,
    isPreferenceEnabled: Boolean = true,
    onChange: ((Boolean) -> Unit)? = null,
    checkboxColor: Color = MaterialTheme.colorScheme.primary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onChange?.invoke(!initialValue) }, interactionSource = interactionSource, indication = indication)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                text = title,
                color = preferenceTitleColor(isEnabled = isPreferenceEnabled),
                fontSize = with(LocalDensity.current) {
                    dimensionResource(id = R.dimen.normal_text_size).toSp()
                }
            )
            AnimatedVisibility(visible = !summary.isNullOrBlank()) {
                Text(
                    text = summary.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    color = preferenceSummaryColor(isEnabled = isPreferenceEnabled),
                )
            }
        }
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Checkbox(
                checked = initialValue,
                onCheckedChange = { onChange?.invoke(it) },
                enabled = isPreferenceEnabled,
                colors = CheckboxDefaults.colors(checkedColor = checkboxColor, checkmarkColor = MaterialTheme.colorScheme.surface),
                interactionSource = interactionSource
            )
        }
    }
}

@MyDevices
@Composable
private fun SettingsCheckBoxComponentPreview() {
    AppThemeSurface {
        SettingsCheckBoxComponent(
            title = "Some title",
            summary = "Some summary",
        )
    }
}
