package com.simplemobiletools.calculator.compose.theme

import android.app.ActivityManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.graphics.toColor
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simplemobiletools.calculator.compose.extensions.getActivity
import com.simplemobiletools.calculator.compose.theme.Theme.Companion.systemDefaultMaterialYou
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.helpers.Config
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.APP_ICON_IDS
import com.simplemobiletools.commons.helpers.APP_LAUNCHER_NAME
import com.simplemobiletools.commons.helpers.DARK_GREY
import com.simplemobiletools.commons.helpers.HIGHER_ALPHA

private val DarkColorScheme = darkColorScheme(
    primary = color_primary,
    secondary = color_primary_dark,
    tertiary = color_accent,
)

@get:ReadOnlyComposable
val disabledTextColor @Composable get() = if (isSystemInDarkTheme() || MaterialTheme.colorScheme.background.luminance() < 0.5) Color.DarkGray else Color.LightGray

@get:ReadOnlyComposable
val textSubTitleColor
    @Composable get() = if (isSystemInDarkTheme() || MaterialTheme.colorScheme.background.luminance() < 0.5) {
        Color.White.copy(0.5f)
    } else {
        Color.Black.copy(
            0.5f,
        )
    }


@Composable
@ReadOnlyComposable
fun preferenceSummaryColor(isEnabled: Boolean) =
    if (isEnabled) textSubTitleColor else disabledTextColor

@Composable
@ReadOnlyComposable
fun preferenceTitleColor(isEnabled: Boolean) = if (isEnabled) LocalContentColor.current else disabledTextColor

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

    data class White(
        val accentColor: Int,
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
            primaryColorInt = LocalContext.current.config.primaryColor,
            backgroundColorInt = LocalContext.current.config.backgroundColor,
            textColorInt = (if (isSystemInDarkTheme() || MaterialTheme.colorScheme.background.luminance() < 0.5) Color.White else Color.Black).toArgb()
        )
    }
}


@Composable
fun Theme(
    useTransparentNavigation: Boolean = true,
    theme: Theme,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val window = context.getActivity().window
    val baseConfig = remember { context.config }

    val colorScheme = when {
        theme is Theme.SystemDefaultMaterialYou && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme is Theme.Custom || theme is Theme.Dark -> darkColorScheme(
            primary = theme.primaryColor, surface = theme.backgroundColor,
            onSurface = theme.textColor
        )

        theme is Theme.White -> lightColorScheme(
            primary = theme.primaryColor,
            surface = theme.backgroundColor,
            tertiary = Color(theme.accentColor),
            onSurface = theme.textColor
        )

        theme is Theme.BlackAndWhite -> darkColorScheme(
            primary = theme.primaryColor, surface = theme.backgroundColor, tertiary = Color(theme.accentColor),
            onSurface = theme.textColor
        )

        else -> DarkColorScheme
    }
    LaunchedEffect(Unit) {
        /* if (context.navigationBarHeight > 0 || context.isUsingGestureNavigation() && useTransparentNavigation) {
             systemUiController.isNavigationBarVisible = false
         } else {
             systemUiController.isNavigationBarVisible = true
         }*/

        /* if (context.navigationBarHeight > 0 || context.isUsingGestureNavigation()) {
             window.decorView.systemUiVisibility = window.decorView.systemUiVisibility.addBit(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
             *//* updateTopBottomInsets(statusBarHeight, navigationBarHeight)
             // Don't touch this. Window Inset API often has a domino effect and things will most likely break.
             onApplyWindowInsets {
                 val insets = it.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
                 updateTopBottomInsets(insets.top, insets.bottom)
             }*//*
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility.removeBit(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
            //updateTopBottomInsets(0, 0)
        }*/
        systemUiController.setStatusBarColor(
            color = colorScheme.surface
        )
        context.getActivity().setTaskDescription(ActivityManager.TaskDescription(null, null, colorScheme.surface.toArgb()))
        systemUiController.setNavigationBarColor(Color(theme.backgroundColor.toArgb().adjustAlpha(HIGHER_ALPHA)))
    }

    SideEffect {
        updateRecentsAppIcon(baseConfig, context)
    }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

private fun Context.getAppIconIds(): List<Int> = getActivity().intent.getIntegerArrayListExtra(APP_ICON_IDS).orEmpty()
private fun Context.getAppLauncherName(): String = getActivity().intent.getStringExtra(APP_LAUNCHER_NAME).orEmpty()
private fun updateRecentsAppIcon(baseConfig: Config, context: Context) {
    if (baseConfig.isUsingModifiedAppIcon) {
        val appIconIDs = context.getAppIconIds()
        val currentAppIconColorIndex = baseConfig.getCurrentAppIconColorIndex(context)
        if (appIconIDs.size - 1 < currentAppIconColorIndex) {
            return
        }

        val recentsIcon = BitmapFactory.decodeResource(context.resources, appIconIDs[currentAppIconColorIndex])
        val title = context.getAppLauncherName()
        val color = baseConfig.primaryColor

        val description = ActivityManager.TaskDescription(title, recentsIcon, color)
        context.getActivity().setTaskDescription(description)
    }
}

private fun Config.getCurrentAppIconColorIndex(context: Context): Int {
    val appIconColor = appIconColor
    context.getAppIconColors().forEachIndexed { index, color ->
        if (color == appIconColor) {
            return index
        }
    }
    return 0
}

private fun Context.getAppIconColors() = resources.getIntArray(R.array.md_app_icon_colors).toCollection(ArrayList())

@Composable
fun AppThemeSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val materialYouTheme = systemDefaultMaterialYou()
    var currentTheme by remember { mutableStateOf(getTheme(context = context, materialYouTheme = materialYouTheme)) }
    Log.d("Current theme", currentTheme.toString())
    OnLifecycleEvent {
        if (it == Lifecycle.Event.ON_RESUME) {
            currentTheme = getTheme(context = context, materialYouTheme = materialYouTheme)
        }
    }
    Theme(theme = currentTheme) {
        Surface(
            modifier = modifier
                .fillMaxSize()
        ) {
            content()
        }
    }
}

fun Context.isDarkMode(): Boolean {
    val darkModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
}

@Composable
fun OnLifecycleEvent(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (event: Lifecycle.Event) -> Unit
) {
    val currentOnEvent by rememberUpdatedState(onEvent)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            currentOnEvent(event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


