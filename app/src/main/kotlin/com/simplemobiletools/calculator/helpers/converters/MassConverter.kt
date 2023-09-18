package com.simplemobiletools.calculator.helpers.converters

import com.simplemobiletools.calculator.R

object MassConverter : Converter {
    override val nameResId: Int = R.string.unit_mass
    override val imageResId: Int = R.drawable.ic_scale_vector

    sealed class Unit(nameResId: Int, symbolResId: Int, factor: Double) : Converter.Unit(nameResId, symbolResId, factor) {
        data object Gram : Unit(
            nameResId = R.string.unit_mass_gram,
            symbolResId = R.string.unit_mass_gram_symbol,
            factor = 0.001
        )

        data object Kilogram : Unit(
            nameResId = R.string.unit_mass_kilogram,
            symbolResId = R.string.unit_mass_kilogram_symbol,
            factor = 1.0
        )

        data object Milligram : Unit(
            nameResId = R.string.unit_mass_milligram,
            symbolResId = R.string.unit_mass_milligram_symbol,
            factor = 0.000001
        )

        data object Microgram : Unit(
            nameResId = R.string.unit_mass_microgram,
            symbolResId = R.string.unit_mass_microgram_symbol,
            factor = 0.000000001
        )

        data object Tonne : Unit(
            nameResId = R.string.unit_mass_tonne,
            symbolResId = R.string.unit_mass_tonne_symbol,
            factor = 1_000.0
        )

        data object Pound : Unit(
            nameResId = R.string.unit_mass_pound,
            symbolResId = R.string.unit_mass_pound_symbol,
            factor = 0.45359237
        )

        data object Ounce : Unit(
            nameResId = R.string.unit_mass_ounce,
            symbolResId = R.string.unit_mass_ounce_symbol,
            factor = 0.028349523125
        )

        data object Grain : Unit(
            nameResId = R.string.unit_mass_grain,
            symbolResId = R.string.unit_mass_grain_symbol,
            factor = 0.00006479891
        )

        data object Dram : Unit(
            nameResId = R.string.unit_mass_dram,
            symbolResId = R.string.unit_mass_dram_symbol,
            factor = 0.0017718451953125
        )

        data object Stone : Unit(
            nameResId = R.string.unit_mass_stone,
            symbolResId = R.string.unit_mass_stone_symbol,
            factor = 6.35029318
        )

        data object LongTon : Unit(
            nameResId = R.string.unit_mass_long_ton,
            symbolResId = R.string.unit_mass_long_ton_symbol,
            factor = 1_016.0469088
        )

        data object ShortTon : Unit(
            nameResId = R.string.unit_mass_short_ton,
            symbolResId = R.string.unit_mass_short_ton_symbol,
            factor = 907.18474
        )

        data object Carat : Unit(
            nameResId = R.string.unit_mass_carat,
            symbolResId = R.string.unit_mass_carat_symbol,
            factor = 0.0002051965483
        )

        data object CaratMetric : Unit(
            nameResId = R.string.unit_mass_carat_metric,
            symbolResId = R.string.unit_mass_carat_metric_symbol,
            factor = 0.0002
        )
    }

    override val units: List<Unit> = listOf(
        Unit.Gram,
        Unit.Kilogram,
        Unit.Milligram,
        Unit.Microgram,
        Unit.Tonne,
        Unit.Pound,
        Unit.Ounce,
        Unit.Grain,
        Unit.Dram,
        Unit.Stone,
        Unit.LongTon,
        Unit.ShortTon,
        Unit.Carat,
        Unit.CaratMetric,
    )

    override val defaultTopUnit: Unit = Unit.Pound
    override val defaultBottomUnit: Unit = Unit.Kilogram
}
