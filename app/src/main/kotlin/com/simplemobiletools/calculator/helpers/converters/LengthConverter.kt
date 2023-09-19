package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object LengthConverter : Converter {
    override val nameResId: Int = R.string.unit_length
    override val imageResId: Int = R.drawable.ic_height_vector
    override val key: String = "LengthConverter"

    sealed class Unit(nameResId: Int, symbolResId: Int, factor: Double, key: String) : Converter.Unit(nameResId, symbolResId, factor, key) {
        data object Kilometer : Unit(
            nameResId = R.string.unit_length_kilometer,
            symbolResId = R.string.unit_length_kilometer_symbol,
            factor = 1000.0,
            key = "Kilometer"
        )

        data object Meter : Unit(
            nameResId = R.string.unit_length_meter,
            symbolResId = R.string.unit_length_meter_symbol,
            factor = 1.0,
            key = "Meter"
        )

        data object Centimeter : Unit(
            nameResId = R.string.unit_length_centimeter,
            symbolResId = R.string.unit_length_centimeter_symbol,
            factor = 0.01,
            key = "Centimeter"
        )

        data object Millimeter : Unit(
            nameResId = R.string.unit_length_millimeter,
            symbolResId = R.string.unit_length_millimeter_symbol,
            factor = 0.001,
            key = "Millimeter"
        )

        data object Micrometer : Unit(
            nameResId = R.string.unit_length_micrometer,
            symbolResId = R.string.unit_length_micrometer_symbol,
            factor = 0.000001,
            key = "Micrometer"
        )

        data object Nanometer : Unit(
            nameResId = R.string.unit_length_nanometer,
            symbolResId = R.string.unit_length_nanometer_symbol,
            factor = 0.000000001,
            key = "Nanometer"
        )

        data object Angstrom : Unit(
            nameResId = R.string.unit_length_angstrom,
            symbolResId = R.string.unit_length_angstrom_symbol,
            factor = 1e-10,
            key = "Angstrom"
        )

        data object Mile : Unit(
            nameResId = R.string.unit_length_mile,
            symbolResId = R.string.unit_length_mile_symbol,
            factor = 1_609.344,
            key = "Mile"
        )

        data object Yard : Unit(
            nameResId = R.string.unit_length_yard,
            symbolResId = R.string.unit_length_yard_symbol,
            factor = 0.9144,
            key = "Yard"
        )

        data object Foot : Unit(
            nameResId = R.string.unit_length_foot,
            symbolResId = R.string.unit_length_foot_symbol,
            factor = 0.3048,
            key = "Foot"
        )

        data object Inch : Unit(
            nameResId = R.string.unit_length_inch,
            symbolResId = R.string.unit_length_inch_symbol,
            factor = 0.0254,
            key = "Inch"
        )

        data object Fathom : Unit(
            nameResId = R.string.unit_length_fathom,
            symbolResId = R.string.unit_length_fathom_symbol,
            factor = 1.852,
            key = "Fathom"
        )

        data object NauticalMile : Unit(
            nameResId = R.string.unit_length_nautical_mile,
            symbolResId = R.string.unit_length_nautical_mile_symbol,
            factor = 1_852.0,
            key = "NauticalMile"
        )

        data object AstronomicalUnit : Unit(
            nameResId = R.string.unit_length_astronomical_unit,
            symbolResId = R.string.unit_length_astronomical_unit_symbol,
            factor = 1.495979e+11,
            key = "AstronomicalUnit"
        )

        data object Parsec : Unit(
            nameResId = R.string.unit_length_parsec,
            symbolResId = R.string.unit_length_parsec_symbol,
            factor = 3.0857e+16,
            key = "Parsec"
        )

        data object LightYear : Unit(
            nameResId = R.string.unit_length_light_year,
            symbolResId = R.string.unit_length_light_year_symbol,
            factor = 9.4607e+15,
            key = "LightYear"
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Kilometer,
        Unit.Meter,
        Unit.Centimeter,
        Unit.Millimeter,
        Unit.Micrometer,
        Unit.Nanometer,
        Unit.Angstrom,
        Unit.Mile,
        Unit.Yard,
        Unit.Foot,
        Unit.Inch,
        Unit.Fathom,
        Unit.NauticalMile,
        Unit.AstronomicalUnit,
        Unit.Parsec,
        Unit.LightYear
    )
    override val defaultTopUnit: Unit = Unit.Kilometer
    override val defaultBottomUnit: Unit = Unit.Meter
}
