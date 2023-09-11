package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object VolumeConverter : Converter {
    override val nameResId: Int = R.string.unit_volume
    override val imageResId: Int = R.drawable.ic_drop_vector

    sealed class Unit(nameResId: Int, factor: Double) : Converter.Unit(nameResId, factor) {
        data object SquareKilometer : Unit(
            nameResId = R.string.unit_length_kilometer,
            factor = 1000000.0
        )

        data object SquareMeter : Unit(
            nameResId = R.string.unit_length_meter,
            factor = 1.0
        )

        data object SquareCentimeter : Unit(
            nameResId = R.string.unit_length_centimeter,
            factor = 0.0001
        )

        data object SquareMillimeter : Unit(
            nameResId = R.string.unit_length_millimeter,
            factor = 0.000001
        )

        data object SquareMile : Unit(
            nameResId = R.string.unit_length_mile,
            factor = 2_589_988.110336
        )

        data object SquareYard : Unit(
            nameResId = R.string.unit_length_yard,
            factor = 0.83612736
        )

        data object SquareMil : Unit(
            nameResId = R.string.unit_length_mil,
            factor = 0.00000000064516
        )

        data object SquareInch : Unit(
            nameResId = R.string.unit_length_inch,
            factor = 0.00064516
        )

        data object SquareRod : Unit(
            nameResId = R.string.unit_length_rod,
            factor = 25.29285264
        )

        data object Acre : Unit(
            nameResId = R.string.unit_length_astronomical_unit,
            factor = 4_046.8564224
        )

        data object Are : Unit(
            nameResId = R.string.unit_length_parsec,
            factor = 100.0
        )

        data object Dunam : Unit(
            nameResId = R.string.unit_length_parsec,
            factor = 1000.0
        )

        data object Hectare : Unit(
            nameResId = R.string.unit_length_light_year,
            factor = 10_000.0
        )
    }

    override val units: List<Unit> = listOf(
        Unit.SquareKilometer,
        Unit.SquareMeter,
        Unit.SquareCentimeter,
        Unit.SquareMillimeter,
        Unit.SquareMile,
        Unit.SquareYard,
        Unit.SquareMil,
        Unit.SquareInch,
        Unit.SquareRod,
        Unit.Acre,
        Unit.Are,
        Unit.Dunam,
        Unit.Hectare,
    )
    override val defaultTopUnit: Unit = Unit.SquareKilometer
    override val defaultBottomUnit: Unit = Unit.SquareMeter
}
