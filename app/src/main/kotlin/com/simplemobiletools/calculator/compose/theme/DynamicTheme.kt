package com.simplemobiletools.calculator.compose.theme

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.commons.extensions.getContrastColor
import com.simplemobiletools.commons.extensions.isBlackAndWhiteTheme
import com.simplemobiletools.commons.extensions.isWhiteTheme
import com.simplemobiletools.commons.helpers.DARK_GREY

fun getTheme(showTransparentTop: Boolean = false, context: Context, materialYouTheme: Theme.SystemDefaultMaterialYou): Theme {
    val baseConfig = context.config
    val primaryColorInt = baseConfig.primaryColor
    val isSystemInDarkTheme = context.isDarkMode()


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
        context.isBlackAndWhiteTheme() -> when {//todo fix
            showTransparentTop -> Theme.BlackAndWhite(
                accentColor = md_grey_black.toArgb(),
                primaryColorInt = primaryColorInt,
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = textColor
            )

            baseConfig.primaryColor.getContrastColor() == DARK_GREY -> Theme.BlackAndWhite(
                accentColor = md_grey_black.toArgb(),
                primaryColorInt = primaryColorInt,
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = theme_dark_background_color.toArgb()
            )

            else -> Theme.BlackAndWhite(
                accentColor = md_grey_black.toArgb(),
                primaryColorInt = primaryColorInt,
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = textColor
            )
        }

        context.isWhiteTheme() -> when {//todo fix
            showTransparentTop -> Theme.White(
                accentColor = md_grey_white.toArgb(),
                primaryColorInt = primaryColorInt,
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = theme_dark_background_color.toArgb()
            )

            baseConfig.primaryColor.getContrastColor() == android.graphics.Color.WHITE -> Theme.White(
                accentColor = md_grey_white.toArgb(),
                primaryColorInt = primaryColorInt,
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = theme_light_background_color.toArgb()
            )

            else -> Theme.White(
                accentColor = md_grey_white.toArgb(),
                primaryColorInt = primaryColorInt,
                backgroundColorInt = backgroundColor,
                appIconColorInt = appIconColor,
                textColorInt = theme_dark_background_color.toArgb()
            )
        }

        else -> {
            val customPrimaryColor = when (primaryColorInt) {
                -12846 -> if (isSystemInDarkTheme) md_red_100_dark else md_red_100
                -1074534 -> if (isSystemInDarkTheme) md_red_200_dark else md_red_200
                -1739917 -> if (isSystemInDarkTheme) md_red_300_dark else md_red_300
                -1092784 -> if (isSystemInDarkTheme) md_red_400_dark else md_red_400
                -769226 -> if (isSystemInDarkTheme) md_red_500_dark else md_red_500
                -1754827 -> if (isSystemInDarkTheme) md_red_600_dark else md_red_600
                -2937041 -> if (isSystemInDarkTheme) md_red_700_dark else md_red_700
                -3790808 -> if (isSystemInDarkTheme) md_red_800_dark else md_red_800
                -4776932 -> if (isSystemInDarkTheme) md_red_900_dark else md_red_900

                -476208 -> if (isSystemInDarkTheme) md_pink_100_dark else md_pink_100
                -749647 -> if (isSystemInDarkTheme) md_pink_200_dark else md_pink_200
                -1023342 -> if (isSystemInDarkTheme) md_pink_300_dark else md_pink_300
                -1294214 -> if (isSystemInDarkTheme) md_pink_400_dark else md_pink_400
                -1499549 -> if (isSystemInDarkTheme) md_pink_500_dark else md_pink_500
                -2614432 -> if (isSystemInDarkTheme) md_pink_600_dark else md_pink_600
                -4056997 -> if (isSystemInDarkTheme) md_pink_700_dark else md_pink_700
                -5434281 -> if (isSystemInDarkTheme) md_pink_800_dark else md_pink_800
                -7860657 -> if (isSystemInDarkTheme) md_pink_900_dark else md_pink_900

                -1982745 -> if (isSystemInDarkTheme) md_purple_100_dark else md_purple_100
                -3238952 -> if (isSystemInDarkTheme) md_purple_200_dark else md_purple_200
                -4560696 -> if (isSystemInDarkTheme) md_purple_300_dark else md_purple_300
                -5552196 -> if (isSystemInDarkTheme) md_purple_400_dark else md_purple_400
                -6543440 -> if (isSystemInDarkTheme) md_purple_500_dark else md_purple_500
                -7461718 -> if (isSystemInDarkTheme) md_purple_600_dark else md_purple_600
                -8708190 -> if (isSystemInDarkTheme) md_purple_700_dark else md_purple_700
                -9823334 -> if (isSystemInDarkTheme) md_purple_800_dark else md_purple_800
                -11922292 -> if (isSystemInDarkTheme) md_purple_900_dark else md_purple_900

                -3029783 -> if (isSystemInDarkTheme) md_deep_purple_100_dark else md_deep_purple_100
                -5005861 -> if (isSystemInDarkTheme) md_deep_purple_200_dark else md_deep_purple_200
                -6982195 -> if (isSystemInDarkTheme) md_deep_purple_300_dark else md_deep_purple_300
                -8497214 -> if (isSystemInDarkTheme) md_deep_purple_400_dark else md_deep_purple_400
                -10011977 -> if (isSystemInDarkTheme) md_deep_purple_500_dark else md_deep_purple_500
                -10603087 -> if (isSystemInDarkTheme) md_deep_purple_600_dark else md_deep_purple_600
                -11457112 -> if (isSystemInDarkTheme) md_deep_purple_700_dark else md_deep_purple_700
                -12245088 -> if (isSystemInDarkTheme) md_deep_purple_800_dark else md_deep_purple_800
                -13558894 -> if (isSystemInDarkTheme) md_deep_purple_900_dark else md_deep_purple_900

                -3814679 -> if (isSystemInDarkTheme) md_indigo_100_dark else md_indigo_100
                -6313766 -> if (isSystemInDarkTheme) md_indigo_200_dark else md_indigo_200
                -8812853 -> if (isSystemInDarkTheme) md_indigo_300_dark else md_indigo_300
                -10720320 -> if (isSystemInDarkTheme) md_indigo_400_dark else md_indigo_400
                -12627531 -> if (isSystemInDarkTheme) md_indigo_500_dark else md_indigo_500
                -13022805 -> if (isSystemInDarkTheme) md_indigo_600_dark else md_indigo_600
                -13615201 -> if (isSystemInDarkTheme) md_indigo_700_dark else md_indigo_700
                -14142061 -> if (isSystemInDarkTheme) md_indigo_800_dark else md_indigo_800
                -15064194 -> if (isSystemInDarkTheme) md_indigo_900_dark else md_indigo_900

                -4464901 -> if (isSystemInDarkTheme) md_blue_100_dark else md_blue_100
                -7288071 -> if (isSystemInDarkTheme) md_blue_200_dark else md_blue_200
                -10177034 -> if (isSystemInDarkTheme) md_blue_300_dark else md_blue_300
                -12409355 -> if (isSystemInDarkTheme) md_blue_400_dark else md_blue_400
                -14575885 -> if (isSystemInDarkTheme) md_blue_500_dark else md_blue_500
                -14776091 -> if (isSystemInDarkTheme) md_blue_600_dark else md_blue_600
                -15108398 -> if (isSystemInDarkTheme) md_blue_700_dark else md_blue_700
                -15374912 -> if (isSystemInDarkTheme) md_blue_800_dark else md_blue_800
                -15906911 -> if (isSystemInDarkTheme) md_blue_900_dark else md_blue_900

                -4987396 -> if (isSystemInDarkTheme) md_light_blue_100_dark else md_light_blue_100
                -8268550 -> if (isSystemInDarkTheme) md_light_blue_200_dark else md_light_blue_200
                -11549705 -> if (isSystemInDarkTheme) md_light_blue_300_dark else md_light_blue_300
                -14043396 -> if (isSystemInDarkTheme) md_light_blue_400_dark else md_light_blue_400
                -16537100 -> if (isSystemInDarkTheme) md_light_blue_500_dark else md_light_blue_500
                -16540699 -> if (isSystemInDarkTheme) md_light_blue_600_dark else md_light_blue_600
                -16611119 -> if (isSystemInDarkTheme) md_light_blue_700_dark else md_light_blue_700
                -16615491 -> if (isSystemInDarkTheme) md_light_blue_800_dark else md_light_blue_800
                -16689253 -> if (isSystemInDarkTheme) md_light_blue_900_dark else md_light_blue_900

                -5051406 -> if (isSystemInDarkTheme) md_cyan_100_dark else md_cyan_100
                -8331542 -> if (isSystemInDarkTheme) md_cyan_200_dark else md_cyan_200
                -11677471 -> if (isSystemInDarkTheme) md_cyan_300_dark else md_cyan_300
                -14235942 -> if (isSystemInDarkTheme) md_cyan_400_dark else md_cyan_400
                -16728876 -> if (isSystemInDarkTheme) md_cyan_500_dark else md_cyan_500
                -16732991 -> if (isSystemInDarkTheme) md_cyan_600_dark else md_cyan_600
                -16738393 -> if (isSystemInDarkTheme) md_cyan_700_dark else md_cyan_700
                -16743537 -> if (isSystemInDarkTheme) md_cyan_800_dark else md_cyan_800
                -16752540 -> if (isSystemInDarkTheme) md_cyan_900_dark else md_cyan_900

                -5054501 -> if (isSystemInDarkTheme) md_teal_100_dark else md_teal_100
                -8336444 -> if (isSystemInDarkTheme) md_teal_200_dark else md_teal_200
                -11684180 -> if (isSystemInDarkTheme) md_teal_300_dark else md_teal_300
                -14244198 -> if (isSystemInDarkTheme) md_teal_400_dark else md_teal_400
                -16738680 -> if (isSystemInDarkTheme) md_teal_500_dark else md_teal_500
                -16742021 -> if (isSystemInDarkTheme) md_teal_600_dark else md_teal_600
                -16746133 -> if (isSystemInDarkTheme) md_teal_700_dark else md_teal_700
                -16750244 -> if (isSystemInDarkTheme) md_teal_800_dark else md_teal_800
                -16757440 -> if (isSystemInDarkTheme) md_teal_900_dark else md_teal_900

                -3610935 -> if (isSystemInDarkTheme) md_green_100_dark else md_green_100
                -5908825 -> if (isSystemInDarkTheme) md_green_200_dark else md_green_200
                -8271996 -> if (isSystemInDarkTheme) md_green_300_dark else md_green_300
                -10044566 -> if (isSystemInDarkTheme) md_green_400_dark else md_green_400
                -11751600 -> if (isSystemInDarkTheme) md_green_500_dark else md_green_500
                -12345273 -> if (isSystemInDarkTheme) md_green_600_dark else md_green_600
                -13070788 -> if (isSystemInDarkTheme) md_green_700_dark else md_green_700
                -13730510 -> if (isSystemInDarkTheme) md_green_800_dark else md_green_800
                -14983648 -> if (isSystemInDarkTheme) md_green_900_dark else md_green_900

                -2298424 -> if (isSystemInDarkTheme) md_light_green_100_dark else md_light_green_100
                -3808859 -> if (isSystemInDarkTheme) md_light_green_200_dark else md_light_green_200
                -5319295 -> if (isSystemInDarkTheme) md_light_green_300_dark else md_light_green_300
                -6501275 -> if (isSystemInDarkTheme) md_light_green_400_dark else md_light_green_400
                -7617718 -> if (isSystemInDarkTheme) md_light_green_500_dark else md_light_green_500
                -8604862 -> if (isSystemInDarkTheme) md_light_green_600_dark else md_light_green_600
                -9920712 -> if (isSystemInDarkTheme) md_light_green_700_dark else md_light_green_700
                -11171025 -> if (isSystemInDarkTheme) md_light_green_800_dark else md_light_green_800
                -13407970 -> if (isSystemInDarkTheme) md_light_green_900_dark else md_light_green_900

                -985917 -> if (isSystemInDarkTheme) md_lime_100_dark else md_lime_100
                -1642852 -> if (isSystemInDarkTheme) md_lime_200_dark else md_lime_200
                -2300043 -> if (isSystemInDarkTheme) md_lime_300_dark else md_lime_300
                -2825897 -> if (isSystemInDarkTheme) md_lime_400_dark else md_lime_400
                -3285959 -> if (isSystemInDarkTheme) md_lime_500_dark else md_lime_500
                -4142541 -> if (isSystemInDarkTheme) md_lime_600_dark else md_lime_600
                -5983189 -> if (isSystemInDarkTheme) md_lime_700_dark else md_lime_700
                -6382300 -> if (isSystemInDarkTheme) md_lime_800_dark else md_lime_800
                -8227049 -> if (isSystemInDarkTheme) md_lime_900_dark else md_lime_900

                -1596 -> if (isSystemInDarkTheme) md_yellow_100_dark else md_yellow_100
                -2672 -> if (isSystemInDarkTheme) md_yellow_200_dark else md_yellow_200
                -3722 -> if (isSystemInDarkTheme) md_yellow_300_dark else md_yellow_300
                -4520 -> if (isSystemInDarkTheme) md_yellow_400_dark else md_yellow_400
                -5317 -> if (isSystemInDarkTheme) md_yellow_500_dark else md_yellow_500
                -141259 -> if (isSystemInDarkTheme) md_yellow_600_dark else md_yellow_600
                -278483 -> if (isSystemInDarkTheme) md_yellow_700_dark else md_yellow_700
                -415707 -> if (isSystemInDarkTheme) md_yellow_800_dark else md_yellow_800
                -688361 -> if (isSystemInDarkTheme) md_yellow_900_dark else md_yellow_900

                -4941 -> if (isSystemInDarkTheme) md_amber_100_dark else md_amber_100
                -8062 -> if (isSystemInDarkTheme) md_amber_200_dark else md_amber_200
                -10929 -> if (isSystemInDarkTheme) md_amber_300_dark else md_amber_300
                -13784 -> if (isSystemInDarkTheme) md_amber_400_dark else md_amber_400
                -16121 -> if (isSystemInDarkTheme) md_amber_500_dark else md_amber_500
                -19712 -> if (isSystemInDarkTheme) md_amber_600_dark else md_amber_600
                -24576 -> if (isSystemInDarkTheme) md_amber_700_dark else md_amber_700
                -28928 -> if (isSystemInDarkTheme) md_amber_800_dark else md_amber_800
                -37120 -> if (isSystemInDarkTheme) md_amber_900_dark else md_amber_900

                -8014 -> if (isSystemInDarkTheme) md_orange_100_dark else md_orange_100
                -13184 -> if (isSystemInDarkTheme) md_orange_200_dark else md_orange_200
                -18611 -> if (isSystemInDarkTheme) md_orange_300_dark else md_orange_300
                -22746 -> if (isSystemInDarkTheme) md_orange_400_dark else md_orange_400
                -26624 -> if (isSystemInDarkTheme) md_orange_500_dark else md_orange_500
                -291840 -> if (isSystemInDarkTheme) md_orange_600_dark else md_orange_600
                -689152 -> if (isSystemInDarkTheme) md_orange_700_dark else md_orange_700
                -1086464 -> if (isSystemInDarkTheme) md_orange_800_dark else md_orange_800
                -1683200 -> if (isSystemInDarkTheme) md_orange_900_dark else md_orange_900

                -13124 -> if (isSystemInDarkTheme) md_deep_orange_100_dark else md_deep_orange_100
                -21615 -> if (isSystemInDarkTheme) md_deep_orange_200_dark else md_deep_orange_200
                -30107 -> if (isSystemInDarkTheme) md_deep_orange_300_dark else md_deep_orange_300
                -36797 -> if (isSystemInDarkTheme) md_deep_orange_400_dark else md_deep_orange_400
                -43230 -> if (isSystemInDarkTheme) md_deep_orange_500_dark else md_deep_orange_500
                -765666 -> if (isSystemInDarkTheme) md_deep_orange_600_dark else md_deep_orange_600
                -1684967 -> if (isSystemInDarkTheme) md_deep_orange_700_dark else md_deep_orange_700
                -2604267 -> if (isSystemInDarkTheme) md_deep_orange_800_dark else md_deep_orange_800
                -4246004 -> if (isSystemInDarkTheme) md_deep_orange_900_dark else md_deep_orange_900

                -2634552 -> if (isSystemInDarkTheme) md_brown_100_dark else md_brown_100
                -4412764 -> if (isSystemInDarkTheme) md_brown_200_dark else md_brown_200
                -6190977 -> if (isSystemInDarkTheme) md_brown_300_dark else md_brown_300
                -7508381 -> if (isSystemInDarkTheme) md_brown_400_dark else md_brown_400
                -8825528 -> if (isSystemInDarkTheme) md_brown_500_dark else md_brown_500
                -9614271 -> if (isSystemInDarkTheme) md_brown_600_dark else md_brown_600
                -10665929 -> if (isSystemInDarkTheme) md_brown_700_dark else md_brown_700
                -11652050 -> if (isSystemInDarkTheme) md_brown_800_dark else md_brown_800
                -12703965 -> if (isSystemInDarkTheme) md_brown_900_dark else md_brown_900

                -3155748 -> if (isSystemInDarkTheme) md_blue_grey_100_dark else md_blue_grey_100
                -5194811 -> if (isSystemInDarkTheme) md_blue_grey_200_dark else md_blue_grey_200
                -7297874 -> if (isSystemInDarkTheme) md_blue_grey_300_dark else md_blue_grey_300
                -8875876 -> if (isSystemInDarkTheme) md_blue_grey_400_dark else md_blue_grey_400
                -10453621 -> if (isSystemInDarkTheme) md_blue_grey_500_dark else md_blue_grey_500
                -11243910 -> if (isSystemInDarkTheme) md_blue_grey_600_dark else md_blue_grey_600
                -12232092 -> if (isSystemInDarkTheme) md_blue_grey_700_dark else md_blue_grey_700
                -13154481 -> if (isSystemInDarkTheme) md_blue_grey_800_dark else md_blue_grey_800
                -14273992 -> if (isSystemInDarkTheme) md_blue_grey_900_dark else md_blue_grey_900

                -1 -> if (isSystemInDarkTheme) md_grey_white_dark else md_grey_black_dark
                -1118482 -> if (isSystemInDarkTheme) md_grey_200_dark else md_grey_200
                -2039584 -> if (isSystemInDarkTheme) md_grey_300_dark else md_grey_300
                -4342339 -> if (isSystemInDarkTheme) md_grey_400_dark else md_grey_400
                -6381922 -> if (isSystemInDarkTheme) md_grey_500_dark else md_grey_500
                -9079435 -> if (isSystemInDarkTheme) md_grey_600_dark else md_grey_600
                -10395295 -> if (isSystemInDarkTheme) md_grey_700_dark else md_grey_700
                -12434878 -> if (isSystemInDarkTheme) md_grey_800_dark else md_grey_800
                -16777216 -> if (isSystemInDarkTheme) md_grey_black else md_grey_black_dark

                else -> if (isSystemInDarkTheme) md_orange_700_dark else md_orange_700
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
