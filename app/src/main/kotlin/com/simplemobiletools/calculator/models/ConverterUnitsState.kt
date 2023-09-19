package com.simplemobiletools.calculator.models

import com.simplemobiletools.calculator.helpers.converters.Converter

data class ConverterUnitsState(
    val topUnit: Converter.Unit,
    val bottomUnit: Converter.Unit,
)
