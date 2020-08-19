package com.simplemobiletools.calculator.helpers

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Formatter {
    fun bigDecimalToString(d: BigDecimal): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        val formatter = DecimalFormat()
        formatter.maximumFractionDigits = 50
        formatter.decimalFormatSymbols = symbols
        formatter.isGroupingUsed = true
        return formatter.format(d)
    }

    fun stringToBigDecimal(str: String): BigDecimal = BigDecimal(str.replace(",", ""))
}

fun BigDecimal.format(): String = Formatter.bigDecimalToString(this)
