package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.extensions.sharedPreferencesCallback
import com.simplemobiletools.calculator.helpers.converters.Converter
import com.simplemobiletools.calculator.models.ConverterUnitsState
import com.simplemobiletools.commons.helpers.BaseConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class Config(context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    var useCommaAsDecimalMark: Boolean
        get() = prefs.getBoolean(USE_COMMA_AS_DECIMAL_MARK, getDecimalSeparator() == COMMA)
        set(useCommaAsDecimalMark) = prefs.edit().putBoolean(USE_COMMA_AS_DECIMAL_MARK, useCommaAsDecimalMark).apply()

    fun getLastConverterUnits(converter: Converter): ConverterUnitsState? {
        val storedState = prefs.getString("$CONVERTER_UNITS_PREFIX.${converter.key}", null)
        return if (!storedState.isNullOrEmpty()) {
            val parts = storedState.split(",").map { part ->
                converter.units.first { it.key == part }
            }
            if (parts.size == 2) {
                ConverterUnitsState(parts[0], parts[1])
            } else {
                null
            }
        } else {
            null
        }
    }

    fun putLastConverterUnits(converter: Converter, topUnit: Converter.Unit, bottomUnit: Converter.Unit) {
        prefs.edit().putString("$CONVERTER_UNITS_PREFIX.${converter.key}", "${topUnit.key},${bottomUnit.key}").apply()
    }

    val preventPhoneFromSleepingFlow: Flow<Boolean> = ::preventPhoneFromSleeping.asFlowNonNull()
    val vibrateOnButtonPressFlow: Flow<Boolean> = ::vibrateOnButtonPress.asFlowNonNull()
    val useCommaAsDecimalMarkFlow: Flow<Boolean> = ::useCommaAsDecimalMark.asFlowNonNull()
}
