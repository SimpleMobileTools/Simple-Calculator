package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object TemperatureConverter : Converter {
    override val nameResId: Int = R.string.unit_temperature
    override val imageResId: Int = R.drawable.ic_thermostat_vector
    override val key: String = "TemperatureConverter"

    sealed class Unit(nameResId: Int, symbolResId: Int, factor: Double, key: String) : Converter.Unit(nameResId, symbolResId, factor, key) {

        data object Celsius : Unit(
            nameResId = R.string.unit_temperature_celsius,
            symbolResId = R.string.unit_temperature_celsius_symbol,
            factor = 1.0,
            key = "Celsius"
        ) {
            const val KELVIN_OFFSET = 273.15

            override fun toBase(value: Double): Double = value + KELVIN_OFFSET
            override fun fromBase(value: Double): Double = value - KELVIN_OFFSET
        }

        data object Fahrenheit : Unit(
            nameResId = R.string.unit_temperature_fahrenheit,
            symbolResId = R.string.unit_temperature_fahrenheit_symbol,
            factor = 9.0 / 5,
            key = "Fahrenheit"
        ) {
            private const val CELSIUS_OFFSET = 32

            override fun toBase(value: Double): Double = Celsius.toBase((value - CELSIUS_OFFSET) / factor)

            override fun fromBase(value: Double): Double = (Celsius.fromBase(value) * factor) + CELSIUS_OFFSET
        }

        data object Rankine : Unit(
            nameResId = R.string.unit_temperature_rankine,
            symbolResId = R.string.unit_temperature_rankine_symbol,
            factor = 5.0 / 9,
            key = "Rankine"
        )

        data object Kelvin : Unit(
            nameResId = R.string.unit_temperature_kelvin,
            symbolResId = R.string.unit_temperature_kelvin_symbol,
            factor = 1.0,
            key = "Kelvin"
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Celsius,
        Unit.Fahrenheit,
        Unit.Rankine,
        Unit.Kelvin,
    )

    override val defaultTopUnit: Unit = Unit.Celsius
    override val defaultBottomUnit: Unit = Unit.Kelvin
}
