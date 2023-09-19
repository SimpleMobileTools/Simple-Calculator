package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object TimeConverter : Converter {
    override val nameResId: Int = R.string.unit_time
    override val imageResId: Int = com.simplemobiletools.commons.R.drawable.ic_clock_vector
    override val key: String = "TimeConverter"

    sealed class Unit(nameResId: Int, symbolResId: Int, factor: Double, key: String) : Converter.Unit(nameResId, symbolResId, factor, key) {
        companion object {
            private const val MINUTE = 60.0
            private const val HOUR = 60.0 * MINUTE
            private const val DAY = HOUR * 24.0
            private const val GREGORIAN_YEAR = DAY * 365.2425
        }

        data object Hour : Unit(
            nameResId = R.string.unit_time_hour,
            symbolResId = R.string.unit_time_hour_symbol,
            factor = HOUR,
            key = "Hour"
        )

        data object Minute : Unit(
            nameResId = R.string.unit_time_minute,
            symbolResId = R.string.unit_time_minute_symbol,
            factor = MINUTE,
            key = "Minute"
        )

        data object Second : Unit(
            nameResId = R.string.unit_time_second,
            symbolResId = R.string.unit_time_second_symbol,
            factor = 1.0,
            key = "Second"
        )

        data object Millisecond : Unit(
            nameResId = R.string.unit_time_millisecond,
            symbolResId = R.string.unit_time_millisecond_symbol,
            factor = 0.001,
            key = "Millisecond"
        )

        data object Day : Unit(
            nameResId = R.string.unit_time_day,
            symbolResId = R.string.unit_time_day_symbol,
            factor = DAY,
            key = "Day"
        )

        data object Week : Unit(
            nameResId = R.string.unit_time_week,
            symbolResId = R.string.unit_time_week_symbol,
            factor = DAY * 7,
            key = "Week"
        )

        data object Year : Unit(
            nameResId = R.string.unit_time_year,
            symbolResId = R.string.unit_time_year_symbol,
            factor = GREGORIAN_YEAR,
            key = "Year"
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Hour,
        Unit.Minute,
        Unit.Second,
        Unit.Millisecond,
        Unit.Day,
        Unit.Week,
        Unit.Year,
    )

    override val defaultTopUnit: Unit = Unit.Hour
    override val defaultBottomUnit: Unit = Unit.Second
}
