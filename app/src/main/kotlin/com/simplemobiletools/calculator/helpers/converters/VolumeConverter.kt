package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object VolumeConverter : Converter {
    override val nameResId: Int = R.string.unit_volume
    override val imageResId: Int = R.drawable.ic_drop_vector

    sealed class Unit(nameResId: Int, factor: Double) : Converter.Unit(nameResId, factor) {
        data object CubicMeter : Unit(
            nameResId = R.string.unit_volume_cubic_meter,
            factor = 1.0
        )

        data object CubicDecimeter : Unit(
            nameResId = R.string.unit_volume_cubic_decimeter,
            factor = 0.001
        )

        data object CubicCentimeter : Unit(
            nameResId = R.string.unit_volume_cubic_centimeter,
            factor = 0.000001
        )

        data object CubicMillimeter : Unit(
            nameResId = R.string.unit_volume_cubic_millimeter,
            factor = 0.000000001
        )

        data object Liter : Unit(
            nameResId = R.string.unit_volume_liter,
            factor = 0.001
        )

        data object Centiliter : Unit(
            nameResId = R.string.unit_volume_centiliter,
            factor = 0.0001
        )

        data object Deciliter : Unit(
            nameResId = R.string.unit_volume_deciliter,
            factor = 0.00001
        )

        data object Milliliter : Unit(
            nameResId = R.string.unit_volume_milliliter,
            factor = 0.000001
        )

        data object AcreFoot : Unit(
            nameResId = R.string.unit_volume_acre_foot,
            factor = 1_233.48183754752
        )

        data object CubicYard : Unit(
            nameResId = R.string.unit_volume_cubic_yard,
            factor = 0.764554857984
        )

        data object CubicFoot : Unit(
            nameResId = R.string.unit_volume_cubic_foot,
            factor = 0.028316846592
        )

        data object CubicInch : Unit(
            nameResId = R.string.unit_volume_cubic_inch,
            factor = 0.000016387064
        )

        data object BarrelUS : Unit(
            nameResId = R.string.unit_volume_barrel_us,
            factor = 0.119240471196
        )

        data object GallonUS : Unit(
            nameResId = R.string.unit_volume_gallon_us,
            factor = 0.003785411784
        )

        data object QuartUS : Unit(
            nameResId = R.string.unit_volume_quart_us,
            factor = 0.000946352946
        )

        data object PintUS : Unit(
            nameResId = R.string.unit_volume_pint_us,
            factor = 0.000473176473
        )

        data object GillUS : Unit(
            nameResId = R.string.unit_volume_gill_us,
            factor = 0.00011829411825
        )

        data object FluidOunceUS : Unit(
            nameResId = R.string.unit_volume_fluid_ounce_us,
            factor = 0.00003
        )

        data object DropUS : Unit(
            nameResId = R.string.unit_volume_drop_us,
            factor = 0.00000008214869322916
        )

        data object TeaspoonUS : Unit(
            nameResId = R.string.unit_volume_teaspoon_us,
            factor = 0.000005
        )

        data object TablespoonUS : Unit(
            nameResId = R.string.unit_volume_tablespoon_us,
            factor = 0.000015
        )

        data object CupUS : Unit(
            nameResId = R.string.unit_volume_cup_us,
            factor = 0.00024
        )

        data object BarrelImperial : Unit(
            nameResId = R.string.unit_volume_barrel_imperial,
            factor = 0.16365924
        )

        data object GallonImperial : Unit(
            nameResId = R.string.unit_volume_gallon_imperial,
            factor = 0.00454609
        )

        data object QuartImperial : Unit(
            nameResId = R.string.unit_volume_quart_imperial,
            factor = 0.0011365225
        )

        data object PintImperial : Unit(
            nameResId = R.string.unit_volume_pint_imperial,
            factor = 0.00056826125
        )

        data object GillImperial : Unit(
            nameResId = R.string.unit_volume_gill_imperial,
            factor = 0.0001420653125
        )

        data object FluidOunceImperial : Unit(
            nameResId = R.string.unit_volume_fluid_ounce_imperial,
            factor = 0.0000284130625
        )

        data object DropImperial : Unit(
            nameResId = R.string.unit_volume_drop_imperial,
            factor = 0.0000000986564670138
        )

        data object TeaspoonImperial : Unit(
            nameResId = R.string.unit_volume_teaspoon_imperial,
            factor = 0.00000591938802083
        )

        data object TablespoonImperial : Unit(
            nameResId = R.string.unit_volume_tablespoon_imperial,
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
