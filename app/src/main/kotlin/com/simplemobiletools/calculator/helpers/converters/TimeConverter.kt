package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object TimeConverter : Converter {
    override val nameResId: Int = R.string.unit_time
    override val imageResId: Int = com.simplemobiletools.commons.R.drawable.ic_clock_vector

    sealed class Unit(nameResId: Int, factor: Double) : Converter.Unit(nameResId, factor) {
        companion object {
            private const val MINUTE = 60.0
            private const val HOUR = 60.0 * MINUTE
            private const val DAY = HOUR * 24.0
            private const val GREGORIAN_YEAR = DAY * 365.2425
        }

        data object Hour : Unit(
            nameResId = R.string.unit_time_hour,
            factor = HOUR
        )

        data object Minute : Unit(
            nameResId = R.string.unit_time_minute,
            factor = MINUTE
        )

        data object Second : Unit(
            nameResId = R.string.unit_time_second,
            factor = 1.0
        )

        data object Millisecond : Unit(
            nameResId = R.string.unit_time_millisecond,
            factor = 0.001
        )

        data object Microsecond : Unit(
            nameResId = R.string.unit_time_microsecond,
            factor = 0.000001
        )

        data object Nanosecond : Unit(
            nameResId = R.string.unit_time_nanosecond,
            factor = 0.000000001
        )

        data object Picosecond : Unit(
            nameResId = R.string.unit_time_picosecond,
            factor = 0.000000000001
        )

        data object Day : Unit(
            nameResId = R.string.unit_time_day,
            factor = DAY
        )

        data object Week : Unit(
            nameResId = R.string.unit_time_week,
            factor = DAY * 7
        )

        data object MonthFull : Unit(
            nameResId = R.string.unit_time_month_full,
            factor = DAY * 30
        )

        data object MonthHollow : Unit(
            nameResId = R.string.unit_time_month_hollow,
            factor = DAY * 29
        )

        data object MonthSynodic : Unit(
            nameResId = R.string.unit_time_month_synodic,
            factor = DAY * 29.530589
        )

        data object MonthGregorianAverage : Unit(
            nameResId = R.string.unit_time_month_gregorian_average,
            factor = DAY * 30.346875
        )

        data object YearLeap : Unit(
            nameResId = R.string.unit_time_year_leap,
            factor = DAY * 366
        )

        data object YearGregorian : Unit(
            nameResId = R.string.unit_time_year_gregorian,
            factor = GREGORIAN_YEAR
        )

        data object YearJulian : Unit(
            nameResId = R.string.unit_time_year_julian,
            factor = DAY * 365.25
        )

        data object Jiffy : Unit(
            nameResId = R.string.unit_time_jiffy,
            factor = 1 / 60.0
        )

        data object Moment : Unit(
            nameResId = R.string.unit_time_moment,
            factor = 90.0
        )

        data object Fortnight : Unit(
            nameResId = R.string.unit_time_fortnight,
            factor = DAY * 7 * 2
        )

        data object Decade : Unit(
            nameResId = R.string.unit_time_decade,
            factor = GREGORIAN_YEAR * 10
        )

        data object Century : Unit(
            nameResId = R.string.unit_time_century,
            factor = GREGORIAN_YEAR * 100
        )

        data object Millennium : Unit(
            nameResId = R.string.unit_time_millennium,
            factor = GREGORIAN_YEAR * 1000
        )

        data object AtomicUnit : Unit(
            nameResId = R.string.unit_time_atomic_unit,
            factor = 0.00000000000000002418884254
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Hour,
        Unit.Minute,
        Unit.Second,
        Unit.Millisecond,
        Unit.Microsecond,
        Unit.Nanosecond,
        Unit.Picosecond,
        Unit.Day,
        Unit.Week,
        Unit.MonthFull,
        Unit.MonthHollow,
        Unit.MonthSynodic,
        Unit.MonthGregorianAverage,
        Unit.YearLeap,
        Unit.YearGregorian,
        Unit.YearJulian,
        Unit.Jiffy,
        Unit.Moment,
        Unit.Fortnight,
        Unit.Decade,
        Unit.Century,
        Unit.Millennium,
        Unit.AtomicUnit
    )

    override val defaultTopUnit: Unit = Unit.Hour
    override val defaultBottomUnit: Unit = Unit.Second
}
