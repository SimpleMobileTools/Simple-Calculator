package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.operation.OperationFactory
import com.simplemobiletools.commons.extensions.areDigitsOnly
import com.simplemobiletools.commons.extensions.toast
import java.math.BigDecimal

class CalculatorImpl(calculator: Calculator, val context: Context) {
    var displayedNumber: String? = null
    var displayedFormula: String? = null
    var lastKey: String? = null
    private var inputDisplayedFormula = "0"
    private var lastOperation: String? = null
    private var callback: Calculator? = calculator

    private var isFirstOperation = false
    private var resetValue = false
    private var baseValue: BigDecimal = BigDecimal.ZERO
    private var secondValue: BigDecimal = BigDecimal.ZERO
    private val operations = listOf("+", "-", "*", "/", "^", "%")
    private var moreOperationsInRaw = false

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
        baseValue = BigDecimal.ZERO
        secondValue = BigDecimal.ZERO
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

        when {
            sign == "√" -> setFormula(sign + first)
            sign == "!" -> setFormula(first + sign)
            sign.isNotEmpty() -> {
                if (secondValue.compareTo(BigDecimal.ZERO) == 0 && sign == "/") {
                    context.toast(context.getString(R.string.formula_divide_by_zero_error))
                }

                setFormula(first + sign + second)
            }
        }
    }

    fun addDigit(number: Int) {
        if (inputDisplayedFormula == "0" && number.toString().areDigitsOnly()) {
            inputDisplayedFormula = ""
        }

        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }

        val value = valueToCheck.substring(valueToCheck.indexOfAny(operations, 0, false) + 1)
        if (value == "0" && number.toString().areDigitsOnly()) {
            inputDisplayedFormula = inputDisplayedFormula.dropLast(1)
        }

        inputDisplayedFormula += number
        setValue(inputDisplayedFormula)
    }

    private fun formatString(str: String): String {
        // if the number contains a decimal, do not try removing the leading zero anymore, nor add group separator
        // it would prevent writing values like 1.02
        if (str.contains(".")) {
            return str
        }

        val doubleValue = Formatter.stringToBigDecimal(str)
        return doubleValue.format()
    }

    private fun updateResult(value: BigDecimal) {
        setValue(value.format())
        baseValue = value
    }

    private fun getDisplayedNumberAsDouble() = Formatter.stringToBigDecimal(displayedNumber!!)

    fun handleResult() {
        if (moreOperationsInRaw) {
            val index = displayedNumber!!.indexOfAny(operations, 0, false)
            displayedNumber = displayedNumber!!.substring(index + 1)
        }
        moreOperationsInRaw = false
        secondValue = getDisplayedNumberAsDouble()
        calculateResult()
        baseValue = getDisplayedNumberAsDouble()
        setValue(inputDisplayedFormula)
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
        if (update) {
            updateFormula()
        }

        val operation = OperationFactory.forId(lastOperation!!, baseValue, secondValue)
        if (operation != null) {
            try {
                updateResult(operation.getResult())
                inputDisplayedFormula = displayedNumber ?: ""
            } catch (e: Exception) {
                context.toast(R.string.unknown_error_occurred)
            }
        }

        isFirstOperation = false
    }

    fun handleOperation(operation: String) {
        if (inputDisplayedFormula.isEmpty()) {
            inputDisplayedFormula = "0"
        }

        if (operation != ROOT) {
            if (inputDisplayedFormula.last() == '+' ||
                inputDisplayedFormula.last() == '-' ||
                inputDisplayedFormula.last() == '*' ||
                inputDisplayedFormula.last() == '/' ||
                inputDisplayedFormula.last() == '^' ||
                inputDisplayedFormula.last() == '%') {
                inputDisplayedFormula = inputDisplayedFormula.dropLast(1)
                inputDisplayedFormula += getSign(operation)
            } else {
                if (!inputDisplayedFormula.contains('+') &&
                    !inputDisplayedFormula.substring(1).contains('-') &&
                    !inputDisplayedFormula.contains('*') &&
                    !inputDisplayedFormula.contains('/') &&
                    !inputDisplayedFormula.contains('^') &&
                    !inputDisplayedFormula.contains('%')) {
                    inputDisplayedFormula += getSign(operation)
                } else {
                    moreOperationsInRaw = true
                }
            }
        }

        if (lastKey == DIGIT && !lastOperation.isNullOrEmpty() && operation == PERCENT) {
            val tempOp = lastOperation
            handlePercent()
            lastKey = tempOp
            lastOperation = tempOp
        } else if (lastKey == DIGIT && operation != ROOT && operation != FACTORIAL) {
            handleResult()
            if (inputDisplayedFormula.last() != '+' &&
                inputDisplayedFormula.last() != '-' &&
                inputDisplayedFormula.last() != '*' &&
                inputDisplayedFormula.last() != '/' &&
                inputDisplayedFormula.last() != '^' &&
                inputDisplayedFormula.last() != '%') {
                inputDisplayedFormula += getSign(operation)
            }
        }

        resetValue = true
        lastKey = operation
        lastOperation = operation
        setValue(inputDisplayedFormula)

        if (operation == ROOT) {
            handleRoot()
            resetValue = false
        } else if (operation == FACTORIAL) {
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
            if (!newValue.contains('+') && !newValue.contains('-') && !newValue.contains('*') && !newValue.contains('/') && !newValue.contains('%') && !newValue.contains('^')) {
                newValue = formatString(newValue)
            }
            setValue(newValue)
            inputDisplayedFormula = if (newValue != "0") newValue else ""
        }
    }

    fun handleReset() {
        resetValues()
        setValue("0")
        setFormula("")
        inputDisplayedFormula = ""
    }

    fun handleEquals() {
        if (lastKey == EQUALS) {
            calculateResult()
        }

        if (lastKey != DIGIT) {
            return
        }

        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }

        displayedNumber = valueToCheck.substring(valueToCheck.indexOfAny(operations, 0, false) + 1)
        secondValue = getDisplayedNumberAsDouble()
        calculateResult()
        lastKey = EQUALS
        inputDisplayedFormula = displayedNumber ?: "0"
        baseValue = getDisplayedNumberAsDouble()
    }

    private fun decimalClicked() {
        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }

        var value = valueToCheck.substring(valueToCheck.indexOfAny(operations, 0, false) + 1)
        if (!value.contains(".")) {
            when (value) {
                "0" -> inputDisplayedFormula = "0."
                "" -> inputDisplayedFormula += "0."
                else -> inputDisplayedFormula += "."
            }
        } else {
            value = valueToCheck.substring(valueToCheck.indexOfAny(operations, 0, false) + 1)
            if (!value.contains(".")) {
                inputDisplayedFormula += "."
            }
        }
        setValue(inputDisplayedFormula)
    }

    private fun zeroClicked() {
        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }

        val value = valueToCheck.substring(valueToCheck.indexOfAny(operations, 0, false) + 1)
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
