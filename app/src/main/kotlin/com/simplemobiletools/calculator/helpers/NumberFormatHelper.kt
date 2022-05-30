package com.simplemobiletools.calculator.helpers

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class NumberFormatHelper(
    var decimalSeparator: String = DOT,
    var groupingSeparator: String = COMMA
) {

    fun doubleToString(d: Double): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = decimalSeparator.single()
        symbols.groupingSeparator = groupingSeparator.single()

        val formatter = DecimalFormat()
        formatter.maximumFractionDigits = 12
        formatter.decimalFormatSymbols = symbols
        formatter.isGroupingUsed = true
        return formatter.format(d)
    }

    fun addGroupingSeparators(str: String) = doubleToString(
        removeGroupingSeparator(str).toDouble()
    )

    fun removeGroupingSeparator(str: String) =
        str.replace(groupingSeparator, "").replace(decimalSeparator, DOT)
}
