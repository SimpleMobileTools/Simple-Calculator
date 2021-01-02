package com.simplemobiletools.calculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.simplemobiletools.calculator.activities.SimpleActivity
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.commons.extensions.performHapticFeedback
import kotlinx.android.synthetic.main.activity_converter.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_0
import kotlinx.android.synthetic.main.activity_main.btn_1
import kotlinx.android.synthetic.main.activity_main.btn_2
import kotlinx.android.synthetic.main.activity_main.btn_3
import kotlinx.android.synthetic.main.activity_main.btn_4
import kotlinx.android.synthetic.main.activity_main.btn_5
import kotlinx.android.synthetic.main.activity_main.btn_6
import kotlinx.android.synthetic.main.activity_main.btn_7
import kotlinx.android.synthetic.main.activity_main.btn_8
import kotlinx.android.synthetic.main.activity_main.btn_9
import kotlinx.android.synthetic.main.activity_main.btn_clear
import kotlinx.android.synthetic.main.activity_main.btn_decimal

class ConverterActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    lateinit var calc: CalculatorImpl
    lateinit var spinner1: Spinner
    lateinit var spinner2: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)


        spinner1 = findViewById(R.id.spinner_first)
        ArrayAdapter.createFromResource(
                this,
                R.array.units,
                android.R.layout.simple_spinner_item
        ).also { adapter1 ->
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter1
        }

        spinner2 = findViewById(R.id.spinner_second)
        ArrayAdapter.createFromResource(
                this,
                R.array.units,
                android.R.layout.simple_spinner_item
        ).also { adapter2 ->
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter2
        }


        calc = CalculatorImpl(this, applicationContext)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showNewResult(textView_first.text.toString(), applicationContext)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               showNewFormula(textView_first.text.toString(),applicationContext)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        btn_clear.setOnClickListener { calc.handleClear(); checkHaptic(it) }
        btn_clear.setOnLongClickListener { calc.handleReset(); true }

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        if (config.preventPhoneFromSleeping) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        vibrateOnButtonPress = config.vibrateOnButtonPress
    }


    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }


    override fun onPause() {
        super.onPause()
        storeStateVariables()
        if (config.preventPhoneFromSleeping) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        updateMenuItemColors(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun storeStateVariables() {
        config.apply {
            storedTextColor = textColor
        }
    }

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    override fun showNewResult(value: String, context: Context) {
        textView_first.text = value
        showNewFormula(value,context)
    }

    override fun showNewFormula(value: String, context: Context) {
        val first = spinner1.selectedItem.toString()
        val second = spinner2.selectedItem.toString()
        textView_second.text = convertLength(value,first,second)
    }
    fun convertLength(value: String,first: String,second: String): String {
        var res: String = ""
        if (value == "") {
            res = "0"
        } else {
            val number = value.replace(",", "").toDouble()
            if (first == "Kilometer") {
                if (second == "Kilometer")
                    res = value
                else if (second == "Meter")
                    res = (number * 1000).toString()
                else if (second == "Decimeter")
                    res = (number * 10000).toString()
                else if (second == "Centimeter")
                    res = (number * 100000).toString()
                else if (second == "Millimetre")
                    res = (number * 1000000).toString()
                else if (second == "micrometres")
                    res = (number * 1000000000).toString()
                else if (second == "Nanometre")
                    res = (number * 1000000000000).toString()
                else if (second == "Mile")
                    res = (number / 1.609).toString()
                else if (second == "Yard")
                    res = (number * 1094).toString()
                else if (second == "Foot")
                    res = (number * 3281).toString()
                else if (second == "Inch")
                    res = (number * 39370).toString()
                else if (second == "Nautical Mile")
                    res = (number / 1.852).toString()
            } else if (first == "Meter") {
                if (second == "Kilometer")
                    res = (number / 1000).toString()
                else if (second == "Meter")
                    res = value
                else if (second == "Decimeter")
                    res = (number * 10).toString()
                else if (second == "Centimeter")
                    res = (number * 100).toString()
                else if (second == "Millimetre")
                    res = (number * 1000).toString()
                else if (second == "micrometres")
                    res = (number * 1000000).toString()
                else if (second == "Nanometre")
                    res = (number * 1000000000).toString()
                else if (second == "Mile")
                    res = (number / 1609).toString()
                else if (second == "Yard")
                    res = (number * 1.094).toString()
                else if (second == "Foot")
                    res = (number * 3.281).toString()
                else if (second == "Inch")
                    res = (number * 39.37).toString()
                else if (second == "Nautical Mile")
                    res = (number / 1852).toString()
            } else if (first == "Decimeter") {
                if (second == "Kilometer")
                    res = (number / 10000).toString()
                else if (second == "Meter")
                    res = (number / 10).toString()
                else if (second == "Decimeter")
                    res = value
                else if (second == "Centimeter")
                    res = (number * 10).toString()
                else if (second == "Millimetre")
                    res = (number * 100).toString()
                else if (second == "micrometres")
                    res = (number * 100000).toString()
                else if (second == "Nanometre")
                    res = (number * 100000000).toString()
                else if (second == "Mile")
                    res = (number / 16093).toString()
                else if (second == "Yard")
                    res = (number / 9.144).toString()
                else if (second == "Foot")
                    res = (number / 3.048).toString()
                else if (second == "Inch")
                    res = (number * 3.937).toString()
                else if (second == "Nautical Mile")
                    res = (number / 18520).toString()
            } else if (first == "Centimeter") {
                if (second == "Kilometer")
                    res = (number / 100000).toString()
                else if (second == "Meter")
                    res = (number / 100).toString()
                else if (second == "Decimeter")
                    res = (number / 10).toString()
                else if (second == "Centimeter")
                    res = value
                else if (second == "Millimetre")
                    res = (number * 10).toString()
                else if (second == "micrometres")
                    res = (number * 10000).toString()
                else if (second == "Nanometre")
                    res = (number * 10000000).toString()
                else if (second == "Mile")
                    res = (number / 160934).toString()
                else if (second == "Yard")
                    res = (number / 91.44).toString()
                else if (second == "Foot")
                    res = (number / 30.48).toString()
                else if (second == "Inch")
                    res = (number / 2.54).toString()
                else if (second == "Nautical Mile")
                    res = (number / 185200).toString()
            } else if (first == "Millimetre") {
                if (second == "Kilometer")
                    res = (number / 1000000).toString()
                else if (second == "Meter")
                    res = (number / 1000).toString()
                else if (second == "Decimeter")
                    res = (number / 100).toString()
                else if (second == "Centimeter")
                    res = (number / 10).toString()
                else if (second == "Millimetre")
                    res = value
                else if (second == "micrometres")
                    res = (number * 1000).toString()
                else if (second == "Nanometre")
                    res = (number * 1000000).toString()
                else if (second == "Mile")
                    res = (number / 1609000).toString()
                else if (second == "Yard")
                    res = (number / 914).toString()
                else if (second == "Foot")
                    res = (number / 305).toString()
                else if (second == "Inch")
                    res = (number / 25.4).toString()
                else if (second == "Nautical Mile")
                    res = (number / 1852000).toString()
            } else if (first == "micrometres") {
                if (second == "Kilometer")
                    res = (number / 1000000000).toString()
                else if (second == "Meter")
                    res = (number / 1000000).toString()
                else if (second == "Decimeter")
                    res = (number / 100000).toString()
                else if (second == "Centimeter")
                    res = (number / 10000).toString()
                else if (second == "Millimetre")
                    res = (number / 1000).toString()
                else if (second == "micrometres")
                    res = value
                else if (second == "Nanometre")
                    res = (number * 1000).toString()
                else if (second == "Mile")
                    res = (number / 1609000000).toString()
                else if (second == "Yard")
                    res = (number / 914400).toString()
                else if (second == "Foot")
                    res = (number / 304800).toString()
                else if (second == "Inch")
                    res = (number / 25400).toString()
                else if (second == "Nautical Mile")
                    res = (number / 1852000000).toString()
            } else if (first == "Nanometre") {
                if (second == "Kilometer")
                    res = (number / 1000000000000).toString()
                else if (second == "Meter")
                    res = (number / 1000000000).toString()
                else if (second == "Decimeter")
                    res = (number / 100000000).toString()
                else if (second == "Centimeter")
                    res = (number / 10000000).toString()
                else if (second == "Millimetre")
                    res = (number / 1000000).toString()
                else if (second == "micrometres")
                    res = (number / 1000).toString()
                else if (second == "Nanometre")
                    res = value
                else if (second == "Mile")
                    res = (number / 1609000000000).toString()
                else if (second == "Yard")
                    res = (number / 914400000).toString()
                else if (second == "Foot")
                    res = (number / 304800000).toString()
                else if (second == "Inch")
                    res = (number / 25400000).toString()
                else if (second == "Nautical Mile")
                    res = (number / 1852000000000).toString()
            } else if (first == "Mile") {
                if (second == "Kilometer")
                    res = (number * 1.609).toString()
                else if (second == "Meter")
                    res = (number * 1609).toString()
                else if (second == "Decimeter")
                    res = (number * 16093).toString()
                else if (second == "Centimeter")
                    res = (number * 160934).toString()
                else if (second == "Millimetre")
                    res = (number * 1609000).toString()
                else if (second == "micrometres")
                    res = (number * 1609000000).toString()
                else if (second == "Nanometre")
                    res = (number * 1609000000000).toString()
                else if (second == "Mile")
                    res = value
                else if (second == "Yard")
                    res = (number * 1760).toString()
                else if (second == "Foot")
                    res = (number * 5280).toString()
                else if (second == "Inch")
                    res = (number * 63360).toString()
                else if (second == "Nautical Mile")
                    res = (number / 1.151).toString()
            } else if (first == "Yard") {
                if (second == "Kilometer")
                    res = (number / 1094).toString()
                else if (second == "Meter")
                    res = (number / 1.094).toString()
                else if (second == "Decimeter")
                    res = (number * 9.144).toString()
                else if (second == "Centimeter")
                    res = (number * 91.44).toString()
                else if (second == "Millimetre")
                    res = (number * 914).toString()
                else if (second == "micrometres")
                    res = (number * 914400).toString()
                else if (second == "Nanometre")
                    res = (number * 914400000).toString()
                else if (second == "Mile")
                    res = (number / 1760).toString()
                else if (second == "Yard")
                    res = value
                else if (second == "Foot")
                    res = (number * 3).toString()
                else if (second == "Inch")
                    res = (number * 36).toString()
                else if (second == "Nautical Mile")
                    res = (number / 2025).toString()
            } else if (first == "Foot") {
                if (second == "Kilometer")
                    res = (number / 3281).toString()
                else if (second == "Meter")
                    res = (number / 3.281).toString()
                else if (second == "Decimeter")
                    res = (number * 3.048).toString()
                else if (second == "Centimeter")
                    res = (number * 30.48).toString()
                else if (second == "Millimetre")
                    res = (number * 304.8).toString()
                else if (second == "micrometres")
                    res = (number * 304800).toString()
                else if (second == "Nanometre")
                    res = (number * 304800000).toString()
                else if (second == "Mile")
                    res = (number / 5280).toString()
                else if (second == "Yard")
                    res = (number / 3).toString()
                else if (second == "Foot")
                    res = value
                else if (second == "Inch")
                    res = (number * 12).toString()
                else if (second == "Nautical Mile")
                    res = (number / 6076).toString()
            } else if (first == "Inch") {
                if (second == "Kilometer")
                    res = (number / 39370).toString()
                else if (second == "Meter")
                    res = (number / 39.37).toString()
                else if (second == "Decimeter")
                    res = (number / 3.937).toString()
                else if (second == "Centimeter")
                    res = (number * 2.54).toString()
                else if (second == "Millimetre")
                    res = (number * 25.4).toString()
                else if (second == "micrometres")
                    res = (number * 25400).toString()
                else if (second == "Nanometre")
                    res = (number * 25400000).toString()
                else if (second == "Mile")
                    res = (number / 63360).toString()
                else if (second == "Yard")
                    res = (number / 36).toString()
                else if (second == "Foot")
                    res = (number / 12).toString()
                else if (second == "Inch")
                    res = value
                else if (second == "Nautical Mile")
                    res = (number / 72913).toString()
            } else if (first == "Nautical Mile") {
                if (second == "Kilometer")
                    res = (number * 1.852).toString()
                else if (second == "Meter")
                    res = (number * 1852).toString()
                else if (second == "Decimeter")
                    res = (number * 18520).toString()
                else if (second == "Centimeter")
                    res = (number * 185200).toString()
                else if (second == "Millimetre")
                    res = (number * 1852000).toString()
                else if (second == "micrometres")
                    res = (number * 1852000000).toString()
                else if (second == "Nanometre")
                    res = (number * 1852000000000).toString()
                else if (second == "Mile")
                    res = (number * 1.151).toString()
                else if (second == "Yard")
                    res = (number * 2025).toString()
                else if (second == "Foot")
                    res = (number * 6076).toString()
                else if (second == "Inch")
                    res = (number * 72913).toString()
                else if (second == "Nautical Mile")
                    res = value
            }
        }
        return res
    }
}





