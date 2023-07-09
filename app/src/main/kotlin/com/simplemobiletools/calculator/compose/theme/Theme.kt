package com.simplemobiletools.calculator.compose.theme

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Build
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
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simplemobiletools.calculator.compose.extensions.getActivity
import com.simplemobiletools.calculator.compose.theme.Theme.Companion.systemDefaultMaterialYou
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.helpers.Config
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.helpers.APP_ICON_IDS
import com.simplemobiletools.commons.helpers.APP_LAUNCHER_NAME

private val darkColorScheme = darkColorScheme(
    primary = color_primary,
    secondary = color_primary_dark,
    tertiary = color_accent,
)

@get:ReadOnlyComposable
val disabledTextColor @Composable get() = if (isInDarkThemeAndSurfaceIsNotLitWell()) Color.DarkGray else Color.LightGray

@get:ReadOnlyComposable
val textSubTitleColor
    @Composable get() = if (isInDarkThemeAndSurfaceIsNotLitWell()) {
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
fun preferenceTitleColor(isEnabled: Boolean) = if (isEnabled) MaterialTheme.colorScheme.onSurface else disabledTextColor

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
            textColorInt = (if (isInDarkThemeAndSurfaceIsNotLitWell()) Color.White else Color.Black).toArgb()
        )
    }
}

@Composable
@ReadOnlyComposable
fun isInDarkThemeAndSurfaceIsNotLitWell() = isSystemInDarkTheme() || isSurfaceNotLitWell()

private const val LUMINANCE_THRESHOLD = 0.5f

@Composable
@ReadOnlyComposable
fun isSurfaceNotLitWell() = MaterialTheme.colorScheme.surface.luminance() < LUMINANCE_THRESHOLD

@Composable
@ReadOnlyComposable
fun isSurfaceLitWell() = MaterialTheme.colorScheme.surface.luminance() > LUMINANCE_THRESHOLD

@Composable
@ReadOnlyComposable
fun Color.isLitWell() = luminance() > LUMINANCE_THRESHOLD

@Composable
@ReadOnlyComposable
fun Color.isNotLitWell() = luminance() < LUMINANCE_THRESHOLD


@Composable
private fun Theme(
    theme: Theme = systemDefaultMaterialYou(),
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val baseConfig = remember { context.config }

    val colorScheme = if (!view.isInEditMode) {

        val colorScheme = when {
            theme is Theme.SystemDefaultMaterialYou && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            theme is Theme.Custom || theme is Theme.Dark -> darkColorScheme(
                primary = theme.primaryColor, surface = theme.backgroundColor,
                onSurface = theme.textColor
            )

            theme is Theme.White -> darkColorScheme(
                primary = Color(theme.accentColor),
                surface = theme.backgroundColor,
                tertiary = theme.primaryColor,
                onSurface = theme.textColor
            )

            theme is Theme.BlackAndWhite -> darkColorScheme(
                primary = Color(theme.accentColor),
                surface = theme.backgroundColor,
                tertiary = theme.primaryColor,
                onSurface = theme.textColor
            )

            else -> darkColorScheme
        }

        colorScheme

    } else darkColorScheme

    SideEffect {
        systemUiController.setNavigationBarColor(colorScheme.surface)
        systemUiController.setSystemBarsColor(colorScheme.surface)
    }

    SideEffect {
        updateRecentsAppIcon(baseConfig, context)
    }


    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}

private fun Context.getAppIconIds(): List<Int> = getActivity().getAppIconIds()
fun Activity.getAppIconIds(): ArrayList<Int> = ArrayList(intent.getIntegerArrayListExtra(APP_ICON_IDS).orEmpty())
private fun Context.getAppLauncherName(): String = getActivity().getAppLauncherName()
fun Activity.getAppLauncherName(): String = intent.getStringExtra(APP_LAUNCHER_NAME).orEmpty()
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

internal fun Context.isDarkMode(): Boolean {
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


