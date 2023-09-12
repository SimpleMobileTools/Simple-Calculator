package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object AreaConverter : Converter {
    override val nameResId: Int = R.string.unit_area
    override val imageResId: Int = R.drawable.ic_box_vector

    sealed class Unit(nameResId: Int, factor: Double) : Converter.Unit(nameResId, factor) {
        data object SquareKilometer : Unit(
            nameResId = R.string.unit_area_square_kilometer,
            factor = 1000000.0
        )

        data object SquareMeter : Unit(
            nameResId = R.string.unit_area_square_meter,
            factor = 1.0
        )

        data object SquareCentimeter : Unit(
            nameResId = R.string.unit_area_square_centimeter,
            factor = 0.0001
        )

        data object SquareMillimeter : Unit(
            nameResId = R.string.unit_area_square_millimeter,
            factor = 0.000001
        )

        data object SquareMile : Unit(
            nameResId = R.string.unit_area_square_mile,
            factor = 2_589_988.110336
        )

        data object SquareYard : Unit(
            nameResId = R.string.unit_area_square_yard,
            factor = 0.83612736
        )

        data object SquareFoot : Unit(
            nameResId = R.string.unit_area_square_foot,
            factor = 0.09290304
        )

        data object SquareInch : Unit(
            nameResId = R.string.unit_area_square_inch,
            factor = 0.00064516
        )

        data object SquareMil : Unit(
            nameResId = R.string.unit_area_square_mil,
            factor = 0.00000000064516
        )

        data object SquareRod : Unit(
            nameResId = R.string.unit_area_square_rod,
            factor = 25.29285264
        )

        data object Acre : Unit(
            nameResId = R.string.unit_area_acre,
            factor = 4_046.8564224
        )

        data object Are : Unit(
            nameResId = R.string.unit_area_are,
            factor = 100.0
        )

        data object Dunam : Unit(
            nameResId = R.string.unit_area_dunam,
            factor = 1000.0
        )

        data object Hectare : Unit(
            nameResId = R.string.unit_area_hectare,
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
        Unit.SquareFoot,
        Unit.SquareInch,
        Unit.SquareMil,
        Unit.SquareRod,
        Unit.Acre,
        Unit.Are,
        Unit.Dunam,
        Unit.Hectare,
    )
    override val defaultTopUnit: Unit = Unit.SquareKilometer
    override val defaultBottomUnit: Unit = Unit.SquareMeter
}
