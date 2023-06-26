package com.simplemobiletools.calculator.compose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.HIGHER_ALPHA


val primaryColor = Color(0xFFF57C00)
val secondaryColor = Color(0xFFD76D00)
val tertiaryColor = primaryColor


private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    secondary = secondaryColor,
    tertiary = tertiaryColor,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    secondary = secondaryColor,
    tertiary = tertiaryColor,
)

@get:ReadOnlyComposable
val disabledTextColor @Composable get() = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

@get:ReadOnlyComposable
val textSubTitleColor
    @Composable get() = if (isSystemInDarkTheme()) {
        Color.White.copy(0.5f)
    } else {
        Color.Black.copy(
            0.5f,
        )
    }


@Composable
fun preferenceSummaryColor(isEnabled: Boolean) =
    if (isEnabled) textSubTitleColor else disabledTextColor

@Composable
fun preferenceTitleColor(isEnabled: Boolean) = if (isEnabled) Color.Unspecified else disabledTextColor

@Composable
fun Theme(
    useTransparentNavigation: Boolean = true,
    properBackgroundColor: Int,
    content: @Composable () -> Unit,
    statusBarColor: Int,
) {
    //todo
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        if (context.navigationBarHeight > 0 || context.isUsingGestureNavigation() && useTransparentNavigation) {
            systemUiController.isNavigationBarVisible = false
        }
        systemUiController.setStatusBarColor(
            color = Color(statusBarColor)
        )
        systemUiController.setNavigationBarColor(Color(properBackgroundColor.adjustAlpha(HIGHER_ALPHA)))
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
    )
}

@Composable
fun AppThemeSurface(
    modifier: Modifier = Modifier,
    properBackgroundColor: Int = LocalContext.current.getProperBackgroundColor(),
    backgroundColor: Int = MaterialTheme.colorScheme.background.toArgb(),
    statusBarColor: Int = LocalContext.current.getProperStatusBarColor(),
    content: @Composable () -> Unit,
) {
    Theme(properBackgroundColor = properBackgroundColor, content = {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .background(Color(backgroundColor))
        ) {
            content()
        }
    }, statusBarColor = statusBarColor)
}
