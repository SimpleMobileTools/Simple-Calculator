package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.R
import com.fathzer.soft.javaluator.DoubleEvaluator

import android.widget.Toast


class CalculatorImpl(calculator: Calculator, val context: Context) {
    var displayedNumber: String? = null
    var displayedFormula: String? = null
    var lastKey: String? = null
    private var mLastOperation: String? = null
    private var mCallback: Calculator? = calculator
    private var mIsFirstOperation = false
    private var mResetValue = false
    private var mBaseValue = 0.0
    private var mSecondValue = 0.0




    init {
        resetValues()
        setValue("0")
        setFormula("")
    }

    private fun resetValueIfNeeded() {
        if (mResetValue)
            displayedNumber = "0"

        mResetValue = false
    }

    private fun resetValues() {
        mBaseValue = 0.0
        mSecondValue = 0.0
        mResetValue = false
        mLastOperation = ""
        displayedNumber = ""
        displayedFormula = ""
        mIsFirstOperation = true
        lastKey = ""

    }

    fun setValue(value: String) {
        mCallback!!.setValue(value, context)
        displayedNumber = value
    }

    private fun setFormula(value: String) {
        mCallback!!.setFormula(value, context)
        displayedFormula = value
    }

    fun addDigit(number: Int) {
        val currentValue = displayedNumber
        val newValue = formatString(currentValue!! + number)
        setValue(newValue)
        setFormula(number.toString())
    }

    private fun formatString(str: String): String {
        // if the number contains a decimal, do not try removing the leading zero anymore, nor add group separator
        // it would prevent writing values like 1.02
        if (str.contains(".")) {
            return str
        }

        val doubleValue = Formatter.stringToDouble(str)
        return Formatter.doubleToString(doubleValue)
    }

    private fun updateResult(value: Double) {
        setValue(Formatter.doubleToString(value))
        mBaseValue = value
    }

    private fun getDisplayedNumberAsDouble() = Formatter.stringToDouble(displayedNumber!!)

    fun handleResult() {
        mSecondValue = getDisplayedNumberAsDouble()
        mBaseValue = getDisplayedNumberAsDouble()
    }


    private fun calculateResult(str:String) {

        val evaluator = DoubleEvaluator()
        val expression = str
        try {
            val result = evaluator.evaluate(expression)
            updateResult(result)
        } catch (e: IllegalArgumentException) {
            throw e
        }
        mIsFirstOperation = false
    }










    fun handleOperation(operation: String) {

        setFormula(getSign(operation))
//        if (lastKey == DIGIT && operation != ROOT)
//            handleResult()

        mResetValue = true
        lastKey = operation
        mLastOperation = operation

//        if (operation == ROOT) {
//            handleRoot()
//            mResetValue = false
//        }

    }

    fun handleClear() {
        if (displayedNumber.equals(NAN)) {
            handleReset()
        } else {
            val oldValue = displayedNumber
            var newValue = "0"
            val len = oldValue!!.length
            var minLen = 1
            if (oldValue.contains("-"))
                minLen++

            if (len > minLen) {
                newValue = oldValue.substring(0, len - 1)
            }

            newValue = newValue.replace("\\.$".toRegex(), "")
            newValue = formatString(newValue)
            setValue(newValue)
            mBaseValue = Formatter.stringToDouble(newValue)
        }
    }

    fun handleReset() {
        resetValues()
        setValue("0")
        setFormula("")
    }

    fun handleEquals(str: String) {

        //mSecondValue = getDisplayedNumberAsDouble()
        calculateResult(str)
        lastKey = EQUALS
    }

    private fun decimalClicked() {
        var value = displayedNumber
        if (!value!!.contains(".")) {
            value += "."
            setFormula(".")
        }
        setValue(value)
    }

    private fun zeroClicked() {
        val value = displayedNumber
        if (value != "0")
            addDigit(0)
    }

    private fun getSign(lastOperation: String?) = when (lastOperation) {
        PLUS -> "+"
        MINUS -> "-"
        MULTIPLY -> "*"
        DIVIDE -> "/"
        MODULO -> "%"
        POWER -> "^"
        ROOT -> "^.5"
        LEFTBRACKET -> "("
        RIGHTBRACKET -> ")"
        else -> ""
    }




    fun numpadClicked(id: Int) {
        if (lastKey == EQUALS) {
            mLastOperation = EQUALS
        }

        lastKey = DIGIT
        resetValueIfNeeded()

        when (id) {
            R.id.btn_decimal -> decimalClicked()
            R.id.btn_0 -> zeroClicked()
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
        }
    }
}
