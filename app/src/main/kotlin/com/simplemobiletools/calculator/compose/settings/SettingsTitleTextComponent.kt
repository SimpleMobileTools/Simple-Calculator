package com.simplemobiletools.calculator.compose.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.simplemobiletools.calculator.compose.extensions.MyDevices
import com.simplemobiletools.calculator.compose.theme.AppThemeSurface

@Composable
fun SettingsTitleTextComponent(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)) {
        Text(
            text = text.uppercase(),
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
            fontSize = with(LocalDensity.current) {
                dimensionResource(id = com.simplemobiletools.commons.R.dimen.normal_text_size).toSp()
            },
        )
    }
}

@MyDevices
@Composable
private fun SettingsTitleTextComponentPreview() = AppThemeSurface {
    SettingsTitleTextComponent(text = "Color customization")
}
