package com.simplemobiletools.calculator.compose.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simplemobiletools.calculator.compose.extensions.MyDevices
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface
import com.simplemobiletools.calculator.compose.theme.preferenceSummaryColor
import com.simplemobiletools.calculator.compose.theme.preferenceTitleColor

@Composable
fun SettingsCheckBoxComponent(
    modifier: Modifier = Modifier,
    title: String,
    summary: String? = null,
    initialValue: Boolean = false,
    isPreferenceEnabled: Boolean = true,
    onChange: ((Boolean) -> Unit)? = null,
    checkboxColor : Color = MaterialTheme.colorScheme.primary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onChange?.invoke(!initialValue) })
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                text = title,
                color = preferenceTitleColor(isEnabled = isPreferenceEnabled),
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
        Checkbox(
            checked = initialValue,
            onCheckedChange = { onChange?.invoke(it) },
            enabled = isPreferenceEnabled,
            colors = CheckboxDefaults.colors(checkedColor = checkboxColor, checkmarkColor = MaterialTheme.colorScheme.surface)
        )
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
