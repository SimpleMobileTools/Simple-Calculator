package com.simplemobiletools.calculator.compose.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplemobiletools.calculator.compose.extensions.MyDevices
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface
import com.simplemobiletools.calculator.compose.theme.preferenceSummaryColor
import com.simplemobiletools.calculator.compose.theme.preferenceTitleColor
import com.simplemobiletools.commons.R

@Composable
fun SettingsPreferenceComponent(
    modifier: Modifier = Modifier,
    preferenceTitle: String,
    preferenceSummary: String? = null,
    isPreferenceEnabled: Boolean = true,
    doOnPreferenceLongClick: (() -> Unit)? = null,
    doOnPreferenceClick: (() -> Unit)? = null,
    preferenceSummaryColor: Color = preferenceSummaryColor(isEnabled = isPreferenceEnabled)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = isPreferenceEnabled,
                onClick = { doOnPreferenceClick?.invoke() },
                onLongClick = { doOnPreferenceLongClick?.invoke() },
            )
            .padding(16.dp),
    ) {
        Text(
            text = preferenceTitle,
            modifier = Modifier.fillMaxWidth(),
            color = preferenceTitleColor(isEnabled = isPreferenceEnabled),
        )
        AnimatedVisibility(visible = !preferenceSummary.isNullOrBlank()) {
            Text(
                text = preferenceSummary.toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                color = preferenceSummaryColor.copy(alpha = 0.6f),
            )
        }
    }
}

@MyDevices
@Composable
private fun SettingsPreferencePreview() {
    AppThemeSurface {
        SettingsPreferenceComponent(
            preferenceTitle = stringResource(id = R.string.language),
            preferenceSummary = stringResource(id = R.string.translation_english),
            isPreferenceEnabled = true,
        )
    }
}
