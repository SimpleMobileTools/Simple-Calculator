package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object TemperatureConverter : Converter {
    override val nameResId: Int = R.string.unit_temperature
    override val imageResId: Int = R.drawable.ic_thermostat_vector

    sealed class Unit(nameResId: Int, formatResId: Int, factor: Double) : Converter.Unit(nameResId, formatResId, factor) {

        data object Celsius : Unit(
            nameResId = R.string.unit_temperature_celsius,
            formatResId = R.string.unit_temperature_celsius_format,
            factor = 1.0
        ) {
            const val KELVIN_OFFSET = 273.15

            override fun toBase(value: Double): Double = value + KELVIN_OFFSET
            override fun fromBase(value: Double): Double = value - KELVIN_OFFSET
        }

        data object Delisle : Unit(
            nameResId = R.string.unit_temperature_delisle,
            formatResId = R.string.unit_temperature_delisle_format,
            factor = 2.0 / 3
        ) {
            private const val KELVIN_OFFSET = 373.15

            override fun toBase(value: Double): Double = KELVIN_OFFSET - value * factor

            override fun fromBase(value: Double): Double = (KELVIN_OFFSET - value) / factor
        }

        data object Fahrenheit : Unit(
            nameResId = R.string.unit_temperature_fahrenheit,
            formatResId = R.string.unit_temperature_fahrenheit_format,
            factor = 9.0 / 5
        ) {
            private const val CELSIUS_OFFSET = 32

            override fun toBase(value: Double): Double = Celsius.toBase((value - CELSIUS_OFFSET) / factor)

            override fun fromBase(value: Double): Double = (Celsius.fromBase(value) * factor) + CELSIUS_OFFSET
        }

        data object Newton : Unit(
            nameResId = R.string.unit_temperature_newton,
            formatResId = R.string.unit_temperature_newton_format,
            factor = 100.0 / 33
        ) {
            private const val KELVIN_OFFSET = Celsius.KELVIN_OFFSET

            override fun toBase(value: Double): Double = (value * factor) + KELVIN_OFFSET

            override fun fromBase(value: Double): Double = (value - KELVIN_OFFSET) / factor
        }

        data object Rankine : Unit(
            nameResId = R.string.unit_temperature_rankine,
            formatResId = R.string.unit_temperature_rankine_format,
            factor = 5.0 / 9
        )

        data object Reaumur : Unit(
            nameResId = R.string.unit_temperature_reaumur,
            formatResId = R.string.unit_temperature_reaumur_format,
            factor =  5.0 / 4
        ) {
            private const val KELVIN_OFFSET = Celsius.KELVIN_OFFSET

            override fun toBase(value: Double): Double = (value * factor) + KELVIN_OFFSET

            override fun fromBase(value: Double): Double = (value - KELVIN_OFFSET) / factor
        }

        data object Romer : Unit(
            nameResId = R.string.unit_temperature_romer,
            formatResId = R.string.unit_temperature_romer_format,
            factor = 40.0 / 21
        ) {
            private const val KELVIN_OFFSET = Celsius.KELVIN_OFFSET
            private const val ADJUSTMENT = 7.5

            override fun toBase(value: Double): Double = (value - ADJUSTMENT) * factor + KELVIN_OFFSET

            override fun fromBase(value: Double): Double = ((value - KELVIN_OFFSET) / factor) + ADJUSTMENT
        }

        data object GasMark : Unit(
            nameResId = R.string.unit_temperature_gas_mark,
            formatResId = R.string.unit_temperature_gas_mark_format,
            factor = 25.0
        ) {
            private const val FAHRENHEIT_OFFSET = 250

            override fun toBase(value: Double): Double = Fahrenheit.toBase(value * factor + FAHRENHEIT_OFFSET)

            override fun fromBase(value: Double): Double = (Fahrenheit.fromBase(value) - FAHRENHEIT_OFFSET) / factor
        }

        data object Kelvin : Unit(
            nameResId = R.string.unit_temperature_kelvin,
            formatResId = R.string.unit_temperature_kelvin_format,
            factor = 1.0
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Celsius,
        Unit.Delisle,
        Unit.Fahrenheit,
        Unit.Newton,
        Unit.Rankine,
        Unit.Reaumur,
        Unit.Romer,
        Unit.GasMark,
        Unit.Kelvin,
    )

    override val defaultTopUnit: Unit = Unit.Celsius
    override val defaultBottomUnit: Unit = Unit.Kelvin
}
