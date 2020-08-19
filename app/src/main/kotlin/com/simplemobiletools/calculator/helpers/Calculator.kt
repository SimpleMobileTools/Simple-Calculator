package com.simplemobiletools.calculator.helpers

import android.content.Context
import java.math.BigDecimal

interface Calculator {
    fun setValue(value: String, context: Context)

    fun setValueBigDecimal(d: BigDecimal)

    fun setFormula(value: String, context: Context)
}
