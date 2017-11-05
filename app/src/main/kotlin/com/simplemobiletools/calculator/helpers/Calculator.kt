package com.simplemobiletools.calculator.helpers

import android.content.Context

interface Calculator {
    fun setValue(value: String, context: Context)

    fun setValueDouble(d: Double)

    fun setFormula(value: String, context: Context)
}
