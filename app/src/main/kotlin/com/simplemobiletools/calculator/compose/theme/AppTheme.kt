package com.simplemobiletools.calculator.compose.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.Lifecycle
import com.simplemobiletools.calculator.compose.extensions.OnLifecycleEvent
import com.simplemobiletools.calculator.compose.theme.model.Theme.Companion.systemDefaultMaterialYou

@Composable
fun AppThemeSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current

    val context = LocalContext.current
    val materialYouTheme = systemDefaultMaterialYou()
    var currentTheme by remember {
        mutableStateOf(
            if (view.isInEditMode) materialYouTheme else getTheme(
                context = context,
                materialYouTheme = materialYouTheme
            )
        )
    }
    OnLifecycleEvent { event ->
        if (event == Lifecycle.Event.ON_START && !view.isInEditMode) {
            currentTheme = getTheme(context = context, materialYouTheme = materialYouTheme)
        }
    }
    Theme(theme = currentTheme) {
        Surface(modifier = modifier.fillMaxSize()) {
            content()
        }
    }
}
