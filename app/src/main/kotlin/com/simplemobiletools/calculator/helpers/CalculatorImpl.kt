package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.operation.PercentOperation
import com.simplemobiletools.commons.extensions.areDigitsOnly
import com.simplemobiletools.commons.extensions.toast
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorImpl(calculator: Calculator, private val context: Context) {
    private var callback: Calculator? = calculator

    private var baseValue = 0.0
    private var secondValue = 0.0
    private var inputDisplayedFormula = "0"
    private var displayedNumber = ""
    private var lastKey = ""
    private var lastOperation = ""
    private val operations = listOf("+", "-", "*", "/", "^", "%", "√")
    private val operationsRegex = "[-+*/^%√]".toPattern()

    init {
        showNewResult("0")
    }

    private fun addDigit(number: Int) {
        if (inputDisplayedFormula == "0" && number.toString().areDigitsOnly()) {
            inputDisplayedFormula = ""
        }

        val value = getSecondValue().toString()
        if (value == "0" && number.toString().areDigitsOnly()) {
            inputDisplayedFormula = inputDisplayedFormula.dropLast(1)
        }

        inputDisplayedFormula += number
        showNewResult(inputDisplayedFormula)
    }

    private fun formatString(str: String): String {
        if (str.contains(".")) {
            return str
        }

        val doubleValue = Formatter.stringToDouble(str)
        return doubleValue.format()
    }

    private fun updateResult(value: Double) {
        showNewResult(value.format())
        baseValue = value
    }

    private fun showNewResult(value: String) {
        callback!!.showNewResult(value, context)
        displayedNumber = value
    }

    private fun showNewFormula(value: String) {
        callback!!.showNewFormula(value, context)
    }

    private fun getDisplayedNumberAsDouble() = Formatter.stringToDouble(displayedNumber)

    private fun handleResult() {
        secondValue = getSecondValue()
        calculateResult()
        baseValue = getDisplayedNumberAsDouble()
        showNewResult(inputDisplayedFormula)
    }

    private fun calculateResult() {
        if (lastOperation == ROOT && inputDisplayedFormula.startsWith("√")) {
            baseValue = 1.0
        }

        if (lastKey != EQUALS) {
            val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
                inputDisplayedFormula.substring(1)
            } else {
                inputDisplayedFormula
            }

            val parts = valueToCheck.split(operationsRegex).filter { it.trim().isNotEmpty() }
            baseValue = parts.first().replace(",", "").toDouble()
            if (inputDisplayedFormula.startsWith("-")) {
                baseValue *= -1
            }

            secondValue = parts.getOrNull(1)?.replace(",", "")?.toDouble() ?: secondValue
        }

        if (lastOperation != "") {
            val expression = "${baseValue.format()}${getSign(lastOperation)}${secondValue.format()}".replace("√", "sqrt")
            try {
                val result = ExpressionBuilder(expression.replace(",", "")).build().evaluate()
                updateResult(result)
                baseValue = result
                inputDisplayedFormula = result.format()
                showNewFormula(expression.replace("sqrt", "√"))
            } catch (e: Exception) {
                context.toast(R.string.unknown_error_occurred)
            }
        }
    }

    // handle percents manually, it doesn't seem to be possible via net.objecthunter:exp4j. % is used only for modulo there
    private fun handlePercent() {
        val operation = PercentOperation(baseValue, getSecondValue(), lastOperation)
        val result = operation.getResult()
        showNewFormula("${baseValue.format()}${getSign(lastOperation)}${getSecondValue().format()}%")
        inputDisplayedFormula = result.format()
        updateResult(result)
    }

    fun handleOperation(operation: String) {
        if (inputDisplayedFormula.isEmpty()) {
            inputDisplayedFormula = "0"
        }

        if (operation == ROOT && inputDisplayedFormula == "0") {
            inputDisplayedFormula = "√"
        }

        if (inputDisplayedFormula.last() == '+' ||
            inputDisplayedFormula.last() == '-' ||
            inputDisplayedFormula.last() == '*' ||
            inputDisplayedFormula.last() == '/' ||
            inputDisplayedFormula.last() == '^' ||
            inputDisplayedFormula.last() == '%' ||
            inputDisplayedFormula.last() == '√') {
            inputDisplayedFormula = inputDisplayedFormula.dropLast(1)
            inputDisplayedFormula += getSign(operation)
        } else {
            if (!inputDisplayedFormula.contains('+') &&
                !inputDisplayedFormula.substring(1).contains('-') &&
                !inputDisplayedFormula.contains('*') &&
                !inputDisplayedFormula.contains('/') &&
                !inputDisplayedFormula.contains('^') &&
                !inputDisplayedFormula.contains('%') &&
                !inputDisplayedFormula.contains('√')) {
                inputDisplayedFormula += getSign(operation)
            }
        }

        if (lastKey == DIGIT && lastOperation != "" && operation == PERCENT) {
            val tempOperation = lastOperation
            handlePercent()
            lastKey = tempOperation
            lastOperation = tempOperation
        } else if (lastKey == DIGIT) {
            handleResult()
            if (inputDisplayedFormula.last() != '+' &&
                inputDisplayedFormula.last() != '-' &&
                inputDisplayedFormula.last() != '*' &&
                inputDisplayedFormula.last() != '/' &&
                inputDisplayedFormula.last() != '^' &&
                inputDisplayedFormula.last() != '%' &&
                inputDisplayedFormula.last() != '√') {
                inputDisplayedFormula += getSign(operation)
            }
        }

        lastKey = operation
        lastOperation = operation
        showNewResult(inputDisplayedFormula)
    }

    fun handleClear() {
        val oldValue = displayedNumber
        var newValue = "0"
        val len = oldValue.length
        var minLen = 1
        if (oldValue.contains("-"))
            minLen++

        if (len > minLen) {
            newValue = oldValue.substring(0, len - 1)
        }

        newValue = newValue.replace("\\.$".toRegex(), "")
        if (!newValue.contains('+') &&
            !newValue.contains('-') &&
            !newValue.contains('*') &&
            !newValue.contains('/') &&
            !newValue.contains('%') &&
            !newValue.contains('^') &&
            !newValue.contains('√')) {
            newValue = formatString(newValue)
        }
        showNewResult(newValue)
        inputDisplayedFormula = if (newValue != "0") newValue else ""
    }

    fun handleReset() {
        resetValues()
        showNewResult("0")
        showNewFormula("")
        inputDisplayedFormula = ""
    }

    private fun resetValues() {
        baseValue = 0.0
        secondValue = 0.0
        displayedNumber = ""
        lastKey = ""
        lastOperation = ""
    }

    fun handleEquals() {
        if (lastKey == EQUALS) {
            calculateResult()
        }

        if (lastKey != DIGIT) {
            return
        }

        secondValue = getSecondValue()
        displayedNumber = secondValue.toString()
        calculateResult()
        lastKey = EQUALS
        inputDisplayedFormula = displayedNumber
        baseValue = getDisplayedNumberAsDouble()
    }

    private fun getSecondValue(): Double {
        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }.replace(",", "")

        var value = valueToCheck.substring(valueToCheck.indexOfAny(operations) + 1)
        if (value.isEmpty()) {
            value = "0"
        }

        return value.toDouble()
    }

    private fun decimalClicked() {
        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }

        val value = valueToCheck.substring(valueToCheck.indexOfAny(operations) + 1)
        if (!value.contains(".")) {
            when {
                value == "0" && !valueToCheck.contains(operationsRegex.toRegex()) -> inputDisplayedFormula = "0."
                value == "" -> inputDisplayedFormula += "0."
                else -> inputDisplayedFormula += "."
            }
        }

        showNewResult(inputDisplayedFormula)
    }

    private fun zeroClicked() {
        val valueToCheck = if (inputDisplayedFormula.startsWith("-")) {
            inputDisplayedFormula.substring(1)
        } else {
            inputDisplayedFormula
        }

        val value = valueToCheck.substring(valueToCheck.indexOfAny(operations) + 1)
        if (value != "0" || value.contains(".")) {
            addDigit(0)
        }
    }

    private fun getSign(lastOperation: String) = when (lastOperation) {
        MINUS -> "-"
        MULTIPLY -> "*"
        DIVIDE -> "/"
        PERCENT -> "%"
        POWER -> "^"
        ROOT -> "√"
        else -> "+"
    }

    fun numpadClicked(id: Int) {
        if (lastKey == EQUALS) {
            lastOperation = EQUALS
        }

        lastKey = DIGIT

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
