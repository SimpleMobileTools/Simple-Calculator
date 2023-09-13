package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object VolumeConverter : Converter {
    override val nameResId: Int = R.string.unit_volume
    override val imageResId: Int = R.drawable.ic_drop_vector

    sealed class Unit(nameResId: Int, symbolResId: Int, factor: Double) : Converter.Unit(nameResId, symbolResId, factor) {
        data object CubicMeter : Unit(
            nameResId = R.string.unit_volume_cubic_meter,
            symbolResId = R.string.unit_volume_cubic_meter_symbol,
            factor = 1.0
        )

        data object CubicDecimeter : Unit(
            nameResId = R.string.unit_volume_cubic_decimeter,
            symbolResId = R.string.unit_volume_cubic_decimeter_symbol,
            factor = 0.001
        )

        data object CubicCentimeter : Unit(
            nameResId = R.string.unit_volume_cubic_centimeter,
            symbolResId = R.string.unit_volume_cubic_centimeter_symbol,
            factor = 0.000001
        )

        data object CubicMillimeter : Unit(
            nameResId = R.string.unit_volume_cubic_millimeter,
            symbolResId = R.string.unit_volume_cubic_millimeter_symbol,
            factor = 0.000000001
        )

        data object Liter : Unit(
            nameResId = R.string.unit_volume_liter,
            symbolResId = R.string.unit_volume_liter_symbol,
            factor = 0.001
        )

        data object Centiliter : Unit(
            nameResId = R.string.unit_volume_centiliter,
            symbolResId = R.string.unit_volume_centiliter_symbol,
            factor = 0.0001
        )

        data object Deciliter : Unit(
            nameResId = R.string.unit_volume_deciliter,
            symbolResId = R.string.unit_volume_deciliter_symbol,
            factor = 0.00001
        )

        data object Milliliter : Unit(
            nameResId = R.string.unit_volume_milliliter,
            symbolResId = R.string.unit_volume_milliliter_symbol,
            factor = 0.000001
        )

        data object AcreFoot : Unit(
            nameResId = R.string.unit_volume_acre_foot,
            symbolResId = R.string.unit_volume_acre_foot_symbol,
            factor = 1_233.48183754752
        )

        data object CubicYard : Unit(
            nameResId = R.string.unit_volume_cubic_yard,
            symbolResId = R.string.unit_volume_cubic_yard_symbol,
            factor = 0.764554857984
        )

        data object CubicFoot : Unit(
            nameResId = R.string.unit_volume_cubic_foot,
            symbolResId = R.string.unit_volume_cubic_foot_symbol,
            factor = 0.028316846592
        )

        data object CubicInch : Unit(
            nameResId = R.string.unit_volume_cubic_inch,
            symbolResId = R.string.unit_volume_cubic_inch_symbol,
            factor = 0.000016387064
        )

        data object BarrelUS : Unit(
            nameResId = R.string.unit_volume_barrel_us,
            symbolResId = R.string.unit_volume_barrel_us_symbol,
            factor = 0.119240471196
        )

        data object GallonUS : Unit(
            nameResId = R.string.unit_volume_gallon_us,
            symbolResId = R.string.unit_volume_gallon_us_symbol,
            factor = 0.003785411784
        )

        data object QuartUS : Unit(
            nameResId = R.string.unit_volume_quart_us,
            symbolResId = R.string.unit_volume_quart_us_symbol,
            factor = 0.000946352946
        )

        data object PintUS : Unit(
            nameResId = R.string.unit_volume_pint_us,
            symbolResId = R.string.unit_volume_pint_us_symbol,
            factor = 0.000473176473
        )

        data object GillUS : Unit(
            nameResId = R.string.unit_volume_gill_us,
            symbolResId = R.string.unit_volume_gill_us_symbol,
            factor = 0.00011829411825
        )

        data object FluidOunceUS : Unit(
            nameResId = R.string.unit_volume_fluid_ounce_us,
            symbolResId = R.string.unit_volume_fluid_ounce_us_symbol,
            factor = 0.00003
        )

        data object DropUS : Unit(
            nameResId = R.string.unit_volume_drop_us,
            symbolResId = R.string.unit_volume_drop_us_symbol,
            factor = 0.00000008214869322916
        )

        data object TeaspoonUS : Unit(
            nameResId = R.string.unit_volume_teaspoon_us,
            symbolResId = R.string.unit_volume_teaspoon_us_symbol,
            factor = 0.000005
        )

        data object TablespoonUS : Unit(
            nameResId = R.string.unit_volume_tablespoon_us,
            symbolResId = R.string.unit_volume_tablespoon_us_symbol,
            factor = 0.000015
        )

        data object CupUS : Unit(
            nameResId = R.string.unit_volume_cup_us,
            symbolResId = R.string.unit_volume_cup_us_symbol,
            factor = 0.00024
        )

        data object BarrelImperial : Unit(
            nameResId = R.string.unit_volume_barrel_imperial,
            symbolResId = R.string.unit_volume_barrel_imperial_symbol,
            factor = 0.16365924
        )

        data object GallonImperial : Unit(
            nameResId = R.string.unit_volume_gallon_imperial,
            symbolResId = R.string.unit_volume_gallon_imperial_symbol,
            factor = 0.00454609
        )

        data object QuartImperial : Unit(
            nameResId = R.string.unit_volume_quart_imperial,
            symbolResId = R.string.unit_volume_quart_imperial_symbol,
            factor = 0.0011365225
        )

        data object PintImperial : Unit(
            nameResId = R.string.unit_volume_pint_imperial,
            symbolResId = R.string.unit_volume_pint_imperial_symbol,
            factor = 0.00056826125
        )

        data object GillImperial : Unit(
            nameResId = R.string.unit_volume_gill_imperial,
            symbolResId = R.string.unit_volume_gill_imperial_symbol,
            factor = 0.0001420653125
        )

        data object FluidOunceImperial : Unit(
            nameResId = R.string.unit_volume_fluid_ounce_imperial,
            symbolResId = R.string.unit_volume_fluid_ounce_imperial_symbol,
            factor = 0.0000284130625
        )

        data object DropImperial : Unit(
            nameResId = R.string.unit_volume_drop_imperial,
            symbolResId = R.string.unit_volume_drop_imperial_symbol,
            factor = 0.0000000986564670138
        )

        data object TeaspoonImperial : Unit(
            nameResId = R.string.unit_volume_teaspoon_imperial,
            symbolResId = R.string.unit_volume_teaspoon_imperial_symbol,
            factor = 0.00000591938802083
        )

        data object TablespoonImperial : Unit(
            nameResId = R.string.unit_volume_tablespoon_imperial,
            symbolResId = R.string.unit_volume_tablespoon_imperial_symbol,
            factor = 0.0000177581640625
        )
    }

    override val units: List<Unit> = listOf(
        Unit.CubicMeter,
        Unit.CubicDecimeter,
        Unit.CubicCentimeter,
        Unit.CubicMillimeter,
        Unit.Liter,
        Unit.Centiliter,
        Unit.Deciliter,
        Unit.Milliliter,
        Unit.AcreFoot,
        Unit.CubicYard,
        Unit.CubicFoot,
        Unit.CubicInch,
        Unit.BarrelUS,
        Unit.GallonUS,
        Unit.QuartUS,
        Unit.PintUS,
        Unit.GillUS,
        Unit.FluidOunceUS,
        Unit.DropUS,
        Unit.TeaspoonUS,
        Unit.TablespoonUS,
        Unit.CupUS,
        Unit.BarrelImperial,
        Unit.GallonImperial,
        Unit.QuartImperial,
        Unit.PintImperial,
        Unit.GillImperial,
        Unit.FluidOunceImperial,
        Unit.DropImperial,
        Unit.TeaspoonImperial,
        Unit.TablespoonImperial,
    )

    override val defaultTopUnit: Unit = Unit.Liter
    override val defaultBottomUnit: Unit = Unit.CubicMeter
}
