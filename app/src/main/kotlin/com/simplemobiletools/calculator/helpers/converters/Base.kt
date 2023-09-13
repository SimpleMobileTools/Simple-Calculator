package com.simplemobiletools.calculator.helpers.converters

import android.content.Context
import com.simplemobiletools.calculator.R

interface Converter {
    companion object {
        val ALL = listOf(
            LengthConverter,
            AreaConverter,
            VolumeConverter,
            MassConverter,
            TemperatureConverter,
            TimeConverter
        )
    }

    val nameResId: Int
    val imageResId: Int
    val units: List<Unit>
    val defaultTopUnit: Unit
    val defaultBottomUnit: Unit

    fun convert(from: ValueWithUnit<Unit>, to: Unit): ValueWithUnit<Unit> {
        return ValueWithUnit(to.fromBase(from.unit.toBase(from.value)), to)
    }

    open class Unit(
        val nameResId: Int,
        val symbolResId: Int,
        val factor: Double
    ) {

        open fun toBase(value: Double) = value * factor

        open fun fromBase(value: Double) = value / factor

        fun withValue(value: Double) = ValueWithUnit(value, this)

        fun getNameWithSymbol(context: Context) = context.getString(
            R.string.unit_name_with_symbol_format,
            context.getString(nameResId),
            context.getString(symbolResId)
        )
    }
}

data class ValueWithUnit<T : Converter.Unit>(val value: Double, val unit: T)
