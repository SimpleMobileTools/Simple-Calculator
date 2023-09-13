package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object LengthConverter : Converter {
    override val nameResId: Int = R.string.unit_length
    override val imageResId: Int = R.drawable.ic_height_vector

    sealed class Unit(nameResId: Int, formatResId: Int, factor: Double) : Converter.Unit(nameResId, formatResId, factor) {
        data object Kilometer : Unit(
            nameResId = R.string.unit_length_kilometer,
            formatResId = R.string.unit_length_kilometer_format,
            factor = 1000.0
        )

        data object Hectometer : Unit(
            nameResId = R.string.unit_length_hectometer,
            formatResId = R.string.unit_length_hectometer_format,
            factor = 100.0
        )

        data object Decameter : Unit(
            nameResId = R.string.unit_length_decameter,
            formatResId = R.string.unit_length_decameter_format,
            factor = 10.0
        )

        data object Meter : Unit(
            nameResId = R.string.unit_length_meter,
            formatResId = R.string.unit_length_meter_format,
            factor = 1.0
        )

        data object Decimeter : Unit(
            nameResId = R.string.unit_length_decimeter,
            formatResId = R.string.unit_length_decimeter_format,
            factor = 0.1
        )

        data object Centimeter : Unit(
            nameResId = R.string.unit_length_centimeter,
            formatResId = R.string.unit_length_centimeter_format,
            factor = 0.01
        )

        data object Millimeter : Unit(
            nameResId = R.string.unit_length_millimeter,
            formatResId = R.string.unit_length_millimeter_format,
            factor = 0.001
        )

        data object Micrometer : Unit(
            nameResId = R.string.unit_length_micrometer,
            formatResId = R.string.unit_length_micrometer_format,
            factor = 0.000001
        )

        data object Nanometer : Unit(
            nameResId = R.string.unit_length_nanometer,
            formatResId = R.string.unit_length_nanometer_format,
            factor = 0.000000001
        )

        data object Picometer : Unit(
            nameResId = R.string.unit_length_picometer,
            formatResId = R.string.unit_length_picometer_format,
            factor = 0.000000000001
        )

        data object Angstrom : Unit(
            nameResId = R.string.unit_length_angstrom,
            formatResId = R.string.unit_length_angstrom_format,
            factor = 1e-10
        )

        data object Mile : Unit(
            nameResId = R.string.unit_length_mile,
            formatResId = R.string.unit_length_mile_format,
            factor = 1_609.344
        )

        data object Chain : Unit(
            nameResId = R.string.unit_length_chain,
            formatResId = R.string.unit_length_chain_format,
            factor = 20.1168
        )

        data object Yard : Unit(
            nameResId = R.string.unit_length_yard,
            formatResId = R.string.unit_length_yard_format,
            factor = 0.9144
        )

        data object Foot : Unit(
            nameResId = R.string.unit_length_foot,
            formatResId = R.string.unit_length_foot_format,
            factor = 0.3048
        )

        data object Mil : Unit(
            nameResId = R.string.unit_length_mil,
            formatResId = R.string.unit_length_mil_format,
            factor = 0.0000254
        )

        data object Inch : Unit(
            nameResId = R.string.unit_length_inch,
            formatResId = R.string.unit_length_inch_format,
            factor = 0.0254
        )

        data object Fathom : Unit(
            nameResId = R.string.unit_length_fathom,
            formatResId = R.string.unit_length_fathom_format,
            factor = 1.852
        )

        data object Cable : Unit(
            nameResId = R.string.unit_length_cable,
            formatResId = R.string.unit_length_cable_format,
            factor = 185.2
        )

        data object NauticalMile : Unit(
            nameResId = R.string.unit_length_nautical_mile,
            formatResId = R.string.unit_length_nautical_mile_format,
            factor = 1_852.0
        )

        data object Link : Unit(
            nameResId = R.string.unit_length_link,
            formatResId = R.string.unit_length_link_format,
            factor = 0.201168
        )

        data object Rod : Unit(
            nameResId = R.string.unit_length_rod,
            formatResId = R.string.unit_length_rod_format,
            factor = 5.0292
        )

        data object AstronomicalUnit : Unit(
            nameResId = R.string.unit_length_astronomical_unit,
            formatResId = R.string.unit_length_astronomical_unit_format,
            factor = 1.495979e+11
        )

        data object Parsec : Unit(
            nameResId = R.string.unit_length_parsec,
            formatResId = R.string.unit_length_parsec_format,
            factor = 3.0857e+16
        )

        data object LightYear : Unit(
            nameResId = R.string.unit_length_light_year,
            formatResId = R.string.unit_length_light_year_format,
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
