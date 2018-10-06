package com.simplemobiletools.calculator.activities

import android.os.Bundle
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.helpers.*
import kotlinx.android.synthetic.main.activity_main.*


class ScientificActivity : SimpleActivity() {

   // lateinit var calc: CalculatorImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scientific_activity)
        //appLaunched(BuildConfig.APPLICATION_ID)

       // calc = CalculatorImpl(this, applicationContext)
        updateViewColors(calculator_holder, config.textColor)

    }
}