package com.simplemobiletools.calculator.compose.theme.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
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
