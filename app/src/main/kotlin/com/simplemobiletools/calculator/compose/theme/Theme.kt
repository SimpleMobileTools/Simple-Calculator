package com.simplemobiletools.calculator.compose.theme

import android.os.Build
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simplemobiletools.calculator.compose.theme.Theme.Companion.systemDefaultMaterialYou
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.commons.extensions.adjustAlpha
import com.simplemobiletools.commons.extensions.getProperStatusBarColor
import com.simplemobiletools.commons.extensions.isUsingGestureNavigation
import com.simplemobiletools.commons.extensions.navigationBarHeight
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
fun getTheme(): Theme {
    val context = LocalContext.current
    val config = remember { context.config }
    //todo ask for help to create all of these mappings for the theme
    return systemDefaultMaterialYou()
}

interface CommonTheme {
    val primaryColorInt: Int
    val backgroundColorInt: Int
    val appIconColorInt: Int
    val textColorInt: Int

    val primaryColor get() = Color(primaryColorInt)
    val backgroundColor get() = Color(backgroundColorInt)
    val appIconColor get() = Color(appIconColorInt)
    val textColor get() = Color(textColorInt)
}

@Stable
sealed class Theme : CommonTheme {
    data class SystemDefaultMaterialYou(
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    data class AutoLightDark(
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) :
        Theme()

    data class Light(
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    data class Dark(
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    data class DarkRed(
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    data class White(
        val accentColor: Int,
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    data class BlackAndWhite(
        val accentColor: Int,
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    data class Custom(
        override val primaryColorInt: Int,
        override val backgroundColorInt: Int,
        override val appIconColorInt: Int,
        override val textColorInt: Int
    ) : Theme()

    companion object {
        @Composable
        fun systemDefaultMaterialYou() = SystemDefaultMaterialYou(
            appIconColorInt = LocalContext.current.config.appIconColor,
            primaryColorInt = MaterialTheme.colorScheme.primary.toArgb(),
            backgroundColorInt = MaterialTheme.colorScheme.background.toArgb(),
            textColorInt = (if (isSystemInDarkTheme() || MaterialTheme.colorScheme.background.luminance() < 0.5) Color.White else Color.Black).toArgb()
        )
    }
}

@Composable
fun Theme(
    useTransparentNavigation: Boolean = true,
    statusBarColor: Int,
    theme: Theme,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    val colorScheme = when {
        theme is Theme.SystemDefaultMaterialYou && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme is Theme.AutoLightDark -> if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
        theme is Theme.Dark || theme is Theme.DarkRed -> darkColorScheme(primary = theme.primaryColor, background = theme.backgroundColor)
        theme is Theme.Light -> lightColorScheme(primary = theme.primaryColor, background = theme.backgroundColor)
        theme is Theme.White -> lightColorScheme(primary = theme.primaryColor, background = theme.backgroundColor, tertiary = Color(theme.accentColor))
        theme is Theme.BlackAndWhite -> darkColorScheme(primary = theme.primaryColor, background = theme.backgroundColor, tertiary = Color(theme.accentColor))
        theme is Theme.Custom -> darkColorScheme(primary = theme.primaryColor, background = theme.backgroundColor)
        else -> LightColorScheme
    }
    LaunchedEffect(Unit) {
        if (context.navigationBarHeight > 0 || context.isUsingGestureNavigation() && useTransparentNavigation) {
            systemUiController.isNavigationBarVisible = false
        }
        systemUiController.setStatusBarColor(
            color = Color(statusBarColor)
        )
        systemUiController.setNavigationBarColor(Color(theme.backgroundColorInt.adjustAlpha(HIGHER_ALPHA)))
    }

    CompositionLocalProvider(
        LocalContentColor provides theme.textColor,
        LocalOverscrollConfiguration provides null,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}


@Composable
fun AppThemeSurface(
    modifier: Modifier = Modifier,
    theme: Theme = systemDefaultMaterialYou(),
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val statusBarColor = remember { context.getProperStatusBarColor() }
    Theme(statusBarColor = statusBarColor, theme = theme) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .background(theme.backgroundColor)
        ) {
            content()
        }
    }
}

