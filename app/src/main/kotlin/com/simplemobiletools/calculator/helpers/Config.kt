package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.commons.helpers.BaseConfig

class Config(context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    var useCommaAsDecimalMark: Boolean
        get() = prefs.getBoolean(USE_COMMA_AS_DECIMAL_MARK, getDecimalSeparator() == COMMA)
        set(useCommaAsDecimalMark) = prefs.edit().putBoolean(USE_COMMA_AS_DECIMAL_MARK, useCommaAsDecimalMark).apply()
}
