package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object TimeConverter : Converter {
    override val nameResId: Int = R.string.unit_time
    override val imageResId: Int = com.simplemobiletools.commons.R.drawable.ic_clock_vector

    sealed class Unit(nameResId: Int, symbolResId: Int, factor: Double) : Converter.Unit(nameResId, symbolResId, factor) {
        companion object {
            private const val MINUTE = 60.0
            private const val HOUR = 60.0 * MINUTE
            private const val DAY = HOUR * 24.0
            private const val GREGORIAN_YEAR = DAY * 365.2425
        }

        data object Hour : Unit(
            nameResId = R.string.unit_time_hour,
            symbolResId = R.string.unit_time_hour_symbol,
            factor = HOUR
        )

        data object Minute : Unit(
            nameResId = R.string.unit_time_minute,
            symbolResId = R.string.unit_time_minute_symbol,
            factor = MINUTE
        )

        data object Second : Unit(
            nameResId = R.string.unit_time_second,
            symbolResId = R.string.unit_time_second_symbol,
            factor = 1.0
        )

        data object Millisecond : Unit(
            nameResId = R.string.unit_time_millisecond,
            symbolResId = R.string.unit_time_millisecond_symbol,
            factor = 0.001
        )

        data object Day : Unit(
            nameResId = R.string.unit_time_day,
            symbolResId = R.string.unit_time_day_symbol,
            factor = DAY
        )

        data object Week : Unit(
            nameResId = R.string.unit_time_week,
            symbolResId = R.string.unit_time_week_symbol,
            factor = DAY * 7
        )

        data object Year : Unit(
            nameResId = R.string.unit_time_year,
            symbolResId = R.string.unit_time_year_symbol,
            factor = GREGORIAN_YEAR
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
