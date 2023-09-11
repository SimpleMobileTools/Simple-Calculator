package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object LengthConverter : Converter {

    override val nameResId: Int = R.string.unit_length

    sealed class Unit(nameResId: Int, factor: Double) : Converter.Unit(nameResId, factor) {
        data object Kilometer : Unit(
            nameResId = R.string.unit_length_kilometer,
            factor = 1000.0
        )

        data object Hectometer : Unit(
            nameResId = R.string.unit_length_hectometer,
            factor = 100.0
        )

        data object Decameter : Unit(
            nameResId = R.string.unit_length_decameter,
            factor = 10.0
        )

        data object Meter : Unit(
            nameResId = R.string.unit_length_meter,
            factor = 1.0
        )

        data object Decimeter : Unit(
            nameResId = R.string.unit_length_decimeter,
            factor = 0.1
        )

        data object Centimeter : Unit(
            nameResId = R.string.unit_length_centimeter,
            factor = 0.01
        )

        data object Millimeter : Unit(
            nameResId = R.string.unit_length_millimeter,
            factor = 0.001
        )

        data object Micrometer : Unit(
            nameResId = R.string.unit_length_micrometer,
            factor = 0.000001
        )

        data object Nanometer : Unit(
            nameResId = R.string.unit_length_nanometer,
            factor = 0.000000001
        )

        data object Picometer : Unit(
            nameResId = R.string.unit_length_picometer,
            factor = 0.000000000001
        )

        data object Angstrom : Unit(
            nameResId = R.string.unit_length_angstrom,
            factor = 1e-10
        )

        data object Mile : Unit(
            nameResId = R.string.unit_length_mile,
            factor = 1_609.344
        )

        data object Chain : Unit(
            nameResId = R.string.unit_length_chain,
            factor = 20.1168
        )

        data object Yard : Unit(
            nameResId = R.string.unit_length_yard,
            factor = 0.9144
        )

        data object Foot : Unit(
            nameResId = R.string.unit_length_foot,
            factor = 0.3048
        )

        data object Mil : Unit(
            nameResId = R.string.unit_length_mil,
            factor = 0.0000254
        )

        data object Inch : Unit(
            nameResId = R.string.unit_length_inch,
            factor = 0.0254
        )

        data object Fathom : Unit(
            nameResId = R.string.unit_length_fathom,
            factor = 1.852
        )

        data object Cable : Unit(
            nameResId = R.string.unit_length_cable,
            factor = 185.2
        )

        data object NauticalMile : Unit(
            nameResId = R.string.unit_length_nautical_mile,
            factor = 1_852.0
        )

        data object Link : Unit(
            nameResId = R.string.unit_length_link,
            factor = 0.201168
        )

        data object Rod : Unit(
            nameResId = R.string.unit_length_rod,
            factor = 5.0292
        )

        data object AstronomicalUnit : Unit(
            nameResId = R.string.unit_length_astronomical_unit,
            factor = 1.495979e+11
        )

        data object Parsec : Unit(
            nameResId = R.string.unit_length_parsec,
            factor = 3.0857e+16
        )

        data object LightYear : Unit(
            nameResId = R.string.unit_length_light_year,
            factor = 9.4607e+15
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Kilometer,
        Unit.Hectometer,
        Unit.Decameter,
        Unit.Meter,
        Unit.Decimeter,
        Unit.Centimeter,
        Unit.Millimeter,
        Unit.Micrometer,
        Unit.Nanometer,
        Unit.Picometer,
        Unit.Angstrom,
        Unit.Mile,
        Unit.Chain,
        Unit.Yard,
        Unit.Foot,
        Unit.Mil,
        Unit.Inch,
        Unit.Fathom,
        Unit.Cable,
        Unit.NauticalMile,
        Unit.Link,
        Unit.Rod,
        Unit.AstronomicalUnit,
        Unit.Parsec,
        Unit.LightYear
    )
    override val defaultTopUnit: Unit = Unit.Kilometer
    override val defaultBottomUnit: Unit = Unit.Meter
}
