package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.helpers.CONSTANT.VIBRATE_ON_BUTTON_PRESS
import com.simplemobiletools.commons.helpers.BaseConfig

class Config(context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    var vibrateOnButtonPress: Boolean
        get() = prefs.getBoolean(VIBRATE_ON_BUTTON_PRESS, true)
        set(vibrateOnButton) = prefs.edit().putBoolean(VIBRATE_ON_BUTTON_PRESS, vibrateOnButton).apply()
}
