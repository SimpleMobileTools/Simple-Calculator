package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.extensions.sharedPreferencesCallback
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

    val preventPhoneFromSleepingFlow: Flow<Boolean> = prefs.run { sharedPreferencesCallback { preventPhoneFromSleeping } }.filterNotNull()
    val vibrateOnButtonPressFlow: Flow<Boolean> = prefs.run { sharedPreferencesCallback { vibrateOnButtonPress } }.filterNotNull()
    val wasUseEnglishToggledFlow: Flow<Boolean> = prefs.run { sharedPreferencesCallback { wasUseEnglishToggled } }.filterNotNull()
    val useEnglishFlow: Flow<Boolean> = prefs.run { sharedPreferencesCallback { useEnglish } }.filterNotNull()
    val useCommaAsDecimalMarkFlow: Flow<Boolean> = prefs.run { sharedPreferencesCallback { useCommaAsDecimalMark } }.filterNotNull()
}
