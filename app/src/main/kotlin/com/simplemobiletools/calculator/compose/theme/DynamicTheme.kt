package com.simplemobiletools.calculator.compose.theme

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.simplemobiletools.calculator.compose.theme.model.Theme
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.commons.extensions.isBlackAndWhiteTheme
import com.simplemobiletools.commons.extensions.isWhiteTheme

fun getTheme(context: Context, materialYouTheme: Theme.SystemDefaultMaterialYou): Theme {
    val baseConfig = context.config
    val primaryColorInt = baseConfig.primaryColor
    val isSystemInDarkTheme = context.isDarkMode()
    val accentColor = baseConfig.accentColor


    val backgroundColorTheme = if (baseConfig.isUsingSystemTheme || baseConfig.isUsingAutoTheme) {
        if (isSystemInDarkTheme) theme_dark_background_color else Color.White
    } else {
        Color(baseConfig.backgroundColor)
    }

    val backgroundColor = backgroundColorTheme.toArgb()
    val appIconColor = baseConfig.appIconColor
    val textColor = baseConfig.textColor

    val theme = when {
        baseConfig.isUsingSystemTheme -> materialYouTheme
        context.isBlackAndWhiteTheme() -> Theme.BlackAndWhite(
            accentColor = accentColor,
            primaryColorInt = primaryColorInt,
            backgroundColorInt = backgroundColor,
            appIconColorInt = appIconColor,
            textColorInt = textColor
        )

        context.isWhiteTheme() -> Theme.White(
            accentColor = accentColor,
            primaryColorInt = primaryColorInt,
            backgroundColorInt = backgroundColor,
            appIconColorInt = appIconColor,
            textColorInt = textColor
        )

        else -> {
            val customPrimaryColor = when (primaryColorInt) {
                -12846 -> md_red_100
                -1074534 -> md_red_200
                -1739917 -> md_red_300
                -1092784 -> md_red_400
                -769226 -> md_red_500
                -1754827 -> md_red_600
                -2937041 -> md_red_700
                -3790808 -> md_red_800
                -4776932 -> md_red_900

                -476208 -> md_pink_100
                -749647 -> md_pink_200
                -1023342 -> md_pink_300
                -1294214 -> md_pink_400
                -1499549 -> md_pink_500
                -2614432 -> md_pink_600
                -4056997 -> md_pink_700
                -5434281 -> md_pink_800
                -7860657 -> md_pink_900

                -1982745 -> md_purple_100
                -3238952 -> md_purple_200
                -4560696 -> md_purple_300
                -5552196 -> md_purple_400
                -6543440 -> md_purple_500
                -7461718 -> md_purple_600
                -8708190 -> md_purple_700
                -9823334 -> md_purple_800
                -11922292 -> md_purple_900

                -3029783 -> md_deep_purple_100
                -5005861 -> md_deep_purple_200
                -6982195 -> md_deep_purple_300
                -8497214 -> md_deep_purple_400
                -10011977 -> md_deep_purple_500
                -10603087 -> md_deep_purple_600
                -11457112 -> md_deep_purple_700
                -12245088 -> md_deep_purple_800
                -13558894 -> md_deep_purple_900

                -3814679 -> md_indigo_100
                -6313766 -> md_indigo_200
                -8812853 -> md_indigo_300
                -10720320 -> md_indigo_400
                -12627531 -> md_indigo_500
                -13022805 -> md_indigo_600
                -13615201 -> md_indigo_700
                -14142061 -> md_indigo_800
                -15064194 -> md_indigo_900

                -4464901 -> md_blue_100
                -7288071 -> md_blue_200
                -10177034 -> md_blue_300
                -12409355 -> md_blue_400
                -14575885 -> md_blue_500
                -14776091 -> md_blue_600
                -15108398 -> md_blue_700
                -15374912 -> md_blue_800
                -15906911 -> md_blue_900

                -4987396 -> md_light_blue_100
                -8268550 -> md_light_blue_200
                -11549705 -> md_light_blue_300
                -14043396 -> md_light_blue_400
                -16537100 -> md_light_blue_500
                -16540699 -> md_light_blue_600
                -16611119 -> md_light_blue_700
                -16615491 -> md_light_blue_800
                -16689253 -> md_light_blue_900

                -5051406 -> md_cyan_100
                -8331542 -> md_cyan_200
                -11677471 -> md_cyan_300
                -14235942 -> md_cyan_400
                -16728876 -> md_cyan_500
                -16732991 -> md_cyan_600
                -16738393 -> md_cyan_700
                -16743537 -> md_cyan_800
                -16752540 -> md_cyan_900

                -5054501 -> md_teal_100
                -8336444 -> md_teal_200
                -11684180 -> md_teal_300
                -14244198 -> md_teal_400
                -16738680 -> md_teal_500
                -16742021 -> md_teal_600
                -16746133 -> md_teal_700
                -16750244 -> md_teal_800
                -16757440 -> md_teal_900

                -3610935 -> md_green_100
                -5908825 -> md_green_200
                -8271996 -> md_green_300
                -10044566 -> md_green_400
                -11751600 -> md_green_500
                -12345273 -> md_green_600
                -13070788 -> md_green_700
                -13730510 -> md_green_800
                -14983648 -> md_green_900

                -2298424 -> md_light_green_100
                -3808859 -> md_light_green_200
                -5319295 -> md_light_green_300
                -6501275 -> md_light_green_400
                -7617718 -> md_light_green_500
                -8604862 -> md_light_green_600
                -9920712 -> md_light_green_700
                -11171025 -> md_light_green_800
                -13407970 -> md_light_green_900

                -985917 -> md_lime_100
                -1642852 -> md_lime_200
                -2300043 -> md_lime_300
                -2825897 -> md_lime_400
                -3285959 -> md_lime_500
                -4142541 -> md_lime_600
                -5983189 -> md_lime_700
                -6382300 -> md_lime_800
                -8227049 -> md_lime_900

                -1596 -> md_yellow_100
                -2672 -> md_yellow_200
                -3722 -> md_yellow_300
                -4520 -> md_yellow_400
                -5317 -> md_yellow_500
                -141259 -> md_yellow_600
                -278483 -> md_yellow_700
                -415707 -> md_yellow_800
                -688361 -> md_yellow_900

                -4941 -> md_amber_100
                -8062 -> md_amber_200
                -10929 -> md_amber_300
                -13784 -> md_amber_400
                -16121 -> md_amber_500
                -19712 -> md_amber_600
                -24576 -> md_amber_700
                -28928 -> md_amber_800
                -37120 -> md_amber_900

                -8014 -> md_orange_100
                -13184 -> md_orange_200
                -18611 -> md_orange_300
                -22746 -> md_orange_400
                -26624 -> md_orange_500
                -291840 -> md_orange_600
                -689152 -> md_orange_700
                -1086464 -> md_orange_800
                -1683200 -> md_orange_900

                -13124 -> md_deep_orange_100
                -21615 -> md_deep_orange_200
                -30107 -> md_deep_orange_300
                -36797 -> md_deep_orange_400
                -43230 -> md_deep_orange_500
                -765666 -> md_deep_orange_600
                -1684967 -> md_deep_orange_700
                -2604267 -> md_deep_orange_800
                -4246004 -> md_deep_orange_900

                -2634552 -> md_brown_100
                -4412764 -> md_brown_200
                -6190977 -> md_brown_300
                -7508381 -> md_brown_400
                -8825528 -> md_brown_500
                -9614271 -> md_brown_600
                -10665929 -> md_brown_700
                -11652050 -> md_brown_800
                -12703965 -> md_brown_900

                -3155748 -> md_blue_grey_100
                -5194811 -> md_blue_grey_200
                -7297874 -> md_blue_grey_300
                -8875876 -> md_blue_grey_400
                -10453621 -> md_blue_grey_500
                -11243910 -> md_blue_grey_600
                -12232092 -> md_blue_grey_700
                -13154481 -> md_blue_grey_800
                -14273992 -> md_blue_grey_900

                -1 -> md_grey_black_dark
                -1118482 -> md_grey_200
                -2039584 -> md_grey_300
                -4342339 -> md_grey_400
                -6381922 -> md_grey_500
                -9079435 -> md_grey_600
                -10395295 -> md_grey_700
                -12434878 -> md_grey_800
                -16777216 -> md_grey_black_dark

                else -> md_orange_700
            }
            Theme.Custom(
                primaryColorInt = customPrimaryColor.toArgb(),
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = textColor
            )
        }
    }
    return theme
}
