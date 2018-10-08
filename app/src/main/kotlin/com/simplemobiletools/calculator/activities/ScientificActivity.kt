package com.simplemobiletools.calculator.activities

import android.os.Bundle
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import kotlinx.android.synthetic.main.activity_main.*
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.commons.extensions.*

class ScientificActivity : SimpleActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scientific_activity)
        appLaunched(BuildConfig.APPLICATION_ID)
        updateViewColors(calculator_holder, config.textColor)
    }
}
