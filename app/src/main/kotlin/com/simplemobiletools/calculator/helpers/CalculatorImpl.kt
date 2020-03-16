package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.util.Log
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.operation.OperationFactory

class CalculatorImpl(calculator: Calculator, val context: Context) {
    var displayedNumber: String? = null
    var displayedFormula: String? = null
    var lastKey: String? = null
    private var lastOperation: String? = null
    private var callback: Calculator? = calculator

    private var isFirstOperation = false
    private var resetValue = false
    private var baseValue = 0.0
    private var secondValue = 0.0

    init {
        resetValues()
        setValue("0")
        setFormula("")
    }

    private fun resetValueIfNeeded() {
        if (resetValue)
            displayedNumber = "0"

        resetValue = false
    }

    private fun resetValues() {
        baseValue = 0.0
        secondValue = 0.0
        resetValue = false
        lastOperation = ""
        displayedNumber = ""
        displayedFormula = ""
        isFirstOperation = true
        lastKey = ""
    }

    fun setValue(value: String) {
        callback!!.setValue(value, context)
        displayedNumber = value
    }

    private fun setFormula(value: String) {
        callback!!.setFormula(value, context)
        displayedFormula = value
    }

    private fun updateFormula() {
        val first = baseValue.format()
        val second = secondValue.format()
        val sign = getSign(lastOperation)

        if (sign == "√") {
            setFormula(sign + first)
        } else if (sign == "!") {
            setFormula(first + sign)
        } else if (!sign.isEmpty()) {
            var formula = first + sign + second
            setFormula(formula)
        }
    }

    fun addDigit(number: Int) {
        val currentValue = displayedNumber
        val newValue = formatString(currentValue!! + number)
        setValue(newValue)
    }

    private fun formatString(str: String): String {
        // if the number contains a decimal, do not try removing the leading zero anymore, nor add group separator
        // it would prevent writing values like 1.02
        if (str.contains(".")) {
            return str
        }

        val doubleValue = Formatter.stringToDouble(str)
        return doubleValue.format()
    }

    private fun updateResult(value: Double) {
        setValue(value.format())
        baseValue = value
    }

    private fun getDisplayedNumberAsDouble() = Formatter.stringToDouble(displayedNumber!!)

    fun handleResult() {
        secondValue = getDisplayedNumberAsDouble()
        calculateResult()
        baseValue = getDisplayedNumberAsDouble()
    }

    private fun handleRoot() {
        baseValue = getDisplayedNumberAsDouble()
        calculateResult()
    }

    private fun handleFactorial() {
        baseValue = getDisplayedNumberAsDouble()
        calculateResult()
    }

    private fun calculateResult(update: Boolean = true) {
        if (update) updateFormula()

        val operation = OperationFactory.forId(lastOperation!!, baseValue, secondValue)
        Log.i("ANGELINA", "oper $lastOperation")
        if (operation != null) {
            updateResult(operation.getResult())
        }

        isFirstOperation = false
    }

    fun handleOperation(operation: String) {
        if (lastKey == DIGIT && !lastOperation.isNullOrEmpty() && operation == PERCENT) {
            val tempOp = lastOperation
            handlePercent()
            lastKey = tempOp
            lastOperation = tempOp
        } else if (lastKey == DIGIT && operation != ROOT && operation != FACTORIAL) {
            handleResult()
        }

        resetValue = true
        lastKey = operation
        lastOperation = operation

        if (operation == ROOT) {
            handleRoot()
            resetValue = false
        }
        if (operation == FACTORIAL) {
            handleFactorial()
            resetValue = false
        }
    }

    private fun handlePercent() {
        OperationFactory.forId(PERCENT, baseValue, getDisplayedNumberAsDouble())?.let {
            val result = it.getResult()
            setFormula("${baseValue.format()}${getSign(lastOperation)}${getDisplayedNumberAsDouble().format()}%")
            secondValue = result
            calculateResult(false)
        }
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
        }
    }

    fun handleReset() {
        resetValues()
        setValue("0")
        setFormula("")
    }

    fun handleEquals() {
        if (lastKey == EQUALS)
            calculateResult()

        if (lastKey != DIGIT)
            return

        secondValue = getDisplayedNumberAsDouble()
        calculateResult()
        lastKey = EQUALS
    }

    private fun decimalClicked() {
        var value = displayedNumber
        if (!value!!.contains(".")) {
            value += "."
        }
        setValue(value)
    }

    private fun zeroClicked() {
        val value = displayedNumber
        if (value != "0") {
            addDigit(0)
        }
    }

    private fun getSign(lastOperation: String?) = when (lastOperation) {
        PLUS -> "+"
        MINUS -> "-"
        MULTIPLY -> "*"
        DIVIDE -> "/"
        PERCENT -> "%"
        POWER -> "^"
        ROOT -> "√"
        FACTORIAL -> "!"
        else -> ""
    }

    fun numpadClicked(id: Int) {
        if (lastKey == EQUALS) {
            lastOperation = EQUALS
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
