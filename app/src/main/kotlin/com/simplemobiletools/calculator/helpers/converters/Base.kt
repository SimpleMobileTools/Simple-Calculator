package com.simplemobiletools.calculator.helpers.converters

interface Converter {
    companion object {
        val ALL = listOf(
            LengthConverter,
            AreaConverter,
            VolumeConverter,
            MassConverter,
            TemperatureConverter
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
        val factor: Double
    ) {

        open fun toBase(value: Double) = value * factor

        open fun fromBase(value: Double) = value / factor

        fun withValue(value: Double) = ValueWithUnit(value, this)
    }
}

data class ValueWithUnit<T : Converter.Unit>(val value: Double, val unit: T)
