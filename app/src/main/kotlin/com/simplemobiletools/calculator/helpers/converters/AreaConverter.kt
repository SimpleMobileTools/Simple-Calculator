package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object AreaConverter : Converter {
    override val nameResId: Int = R.string.unit_area
    override val imageResId: Int = R.drawable.ic_box_vector

    sealed class Unit(nameResId: Int, formatResId: Int, factor: Double) : Converter.Unit(nameResId, formatResId, factor) {
        data object SquareKilometer : Unit(
            nameResId = R.string.unit_area_square_kilometer,
            formatResId = R.string.unit_area_square_kilometer_format,
            factor = 1000000.0
        )

        data object SquareMeter : Unit(
            nameResId = R.string.unit_area_square_meter,
            formatResId = R.string.unit_area_square_meter_format,
            factor = 1.0
        )

        data object SquareCentimeter : Unit(
            nameResId = R.string.unit_area_square_centimeter,
            formatResId = R.string.unit_area_square_centimeter_format,
            factor = 0.0001
        )

        data object SquareMillimeter : Unit(
            nameResId = R.string.unit_area_square_millimeter,
            formatResId = R.string.unit_area_square_millimeter_format,
            factor = 0.000001
        )

        data object SquareMile : Unit(
            nameResId = R.string.unit_area_square_mile,
            formatResId = R.string.unit_area_square_mile_format,
            factor = 2_589_988.110336
        )

        data object SquareYard : Unit(
            nameResId = R.string.unit_area_square_yard,
            formatResId = R.string.unit_area_square_yard_format,
            factor = 0.83612736
        )

        data object SquareFoot : Unit(
            nameResId = R.string.unit_area_square_foot,
            formatResId = R.string.unit_area_square_foot_format,
            factor = 0.09290304
        )

        data object SquareInch : Unit(
            nameResId = R.string.unit_area_square_inch,
            formatResId = R.string.unit_area_square_inch_format,
            factor = 0.00064516
        )

        data object SquareMil : Unit(
            nameResId = R.string.unit_area_square_mil,
            formatResId = R.string.unit_area_square_mil_format,
            factor = 0.00000000064516
        )

        data object SquareRod : Unit(
            nameResId = R.string.unit_area_square_rod,
            formatResId = R.string.unit_area_square_rod_format,
            factor = 25.29285264
        )

        data object Acre : Unit(
            nameResId = R.string.unit_area_acre,
            formatResId = R.string.unit_area_acre_format,
            factor = 4_046.8564224
        )

        data object Are : Unit(
            nameResId = R.string.unit_area_are,
            formatResId = R.string.unit_area_are_format,
            factor = 100.0
        )

        data object Dunam : Unit(
            nameResId = R.string.unit_area_dunam,
            formatResId = R.string.unit_area_dunam_format,
            factor = 1000.0
        )

        data object Hectare : Unit(
            nameResId = R.string.unit_area_hectare,
            formatResId = R.string.unit_area_hectare_format,
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
