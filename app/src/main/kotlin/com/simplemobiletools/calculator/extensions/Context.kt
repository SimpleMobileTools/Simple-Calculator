package com.simplemobiletools.calculator.extensions

import android.content.Context
import com.simplemobiletools.calculator.helpers.Config

val Context.config: Config get() = Config.newInstance(this)
