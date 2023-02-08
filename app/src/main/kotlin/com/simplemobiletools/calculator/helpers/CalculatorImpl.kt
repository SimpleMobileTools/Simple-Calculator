package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.util.Log
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.models.History
import com.simplemobiletools.commons.extensions.showErrorToast
import com.simplemobiletools.commons.extensions.toast
import net.objecthunter.exp4j.ExpressionBuilder
import java.math.BigDecimal

class CalculatorImpl(
    calculator: Calculator,
    private val context: Context,

    private var decimalSeparator: String = DOT,
    private var groupingSeparator: String = COMMA,

    //============================================================
    aRes: String = "123",
    aSavedLastOperation: String = "",
    aLastKey: String = "",
    aLastOperation: String = "",
    aBaseValue: Double = 0.0,
    aSecondValue: Double = 99.0

    //============================================================
) {
    private var callback: Calculator? = calculator

    //============================================================
    // Trying Fix it
    public var mResult = aRes
    public var previousCalculation = aSavedLastOperation
    public var lastKey = aLastKey
    public var lastOperation = aLastOperation
    public var baseValue = aBaseValue
    private var secondValue = aSecondValue
    //============================================================

    //private var baseValue = 0.0
    //private var secondValue = 0.0
    private var inputDisplayedFormula = "0"
    //private var lastKey = ""
    //private var lastOperation = ""
    private val operations = listOf("+", "-", "×", "÷", "^", "%", "√")
    private val operationsRegex = "[-+×÷^%√]".toPattern()
    private val numbersRegex = "[^0-9,.]".toRegex()

    private val formatter = NumberFormatHelper(
        decimalSeparator = decimalSeparator, groupingSeparator = groupingSeparator
    )

    init {
        //============================================================
        Log.v("BASEVALUE INIT :", baseValue.toString())
        Log.v("SECONDVALUE INIT :", secondValue.toString())
        //showNewResult("0")
        showNewResult(mResult)
        showNewFormula(previousCalculation)
        //============================================================
    }

    private fun addDigit(number: Int) {
        if (inputDisplayedFormula == "0") {
            inputDisplayedFormula = ""
        }

        inputDisplayedFormula += number
        addThousandsDelimiter()
        showNewResult(inputDisplayedFormula)
    }

    private fun zeroClicked() {
        val valueToCheck = inputDisplayedFormula.trimStart('-').removeGroupSeparator()
        val value = valueToCheck.substring(valueToCheck.indexOfAny(operations) + 1)
        if (value != "0" || value.contains(decimalSeparator)) {
            addDigit(0)
        }
    }

    private fun decimalClicked() {
        val valueToCheck = inputDisplayedFormula.trimStart('-').replace(groupingSeparator, "")
        val value = valueToCheck.substring(valueToCheck.indexOfAny(operations) + 1)
        if (!value.contains(decimalSeparator)) {
            when {
                value == "0" && !valueToCheck.contains(operationsRegex.toRegex()) -> inputDisplayedFormula = "0$decimalSeparator"
                value == "" -> inputDisplayedFormula += "0$decimalSeparator"
                else -> inputDisplayedFormula += decimalSeparator
            }
        }

        lastKey = DECIMAL
        showNewResult(inputDisplayedFormula)
    }

    private fun addThousandsDelimiter() {
        val valuesToCheck = numbersRegex.split(inputDisplayedFormula).filter { it.trim().isNotEmpty() }
        valuesToCheck.forEach {
            var newString = formatter.addGroupingSeparators(it)

            // allow writing numbers like 0.003
            if (it.contains(decimalSeparator)) {
                val firstPart = newString.substringBefore(decimalSeparator)
                val lastPart = it.substringAfter(decimalSeparator)
                newString = "$firstPart$decimalSeparator$lastPart"
            }

            inputDisplayedFormula = inputDisplayedFormula.replace(it, newString)
        }
    }

    fun handleOperation(operation: String) {
        if (inputDisplayedFormula == Double.NaN.toString()) {
            inputDisplayedFormula = "0"
        }

        if (inputDisplayedFormula == "") {
            inputDisplayedFormula = "0"
        }

        if (operation == ROOT && inputDisplayedFormula == "0") {
            if (lastKey != DIGIT) {
                inputDisplayedFormula = "1√"
            }
        }

        val lastChar = inputDisplayedFormula.last().toString()
        if (lastChar == decimalSeparator) {
            inputDisplayedFormula = inputDisplayedFormula.dropLast(1)
        } else if (operations.contains(lastChar)) {
            inputDisplayedFormula = inputDisplayedFormula.dropLast(1)
            inputDisplayedFormula += getSign(operation)
        } else if (!inputDisplayedFormula.trimStart('-').contains(operationsRegex.toRegex())) {
            inputDisplayedFormula += getSign(operation)
        }

        if (lastKey == DIGIT || lastKey == DECIMAL) {
            if (lastOperation != "" && operation == PERCENT) {
                handlePercent()
            } else {
                // split to multiple lines just to see when does the crash happen
                secondValue = when (operation) {
                    PLUS -> getSecondValue()
                    MINUS -> getSecondValue()
                    MULTIPLY -> getSecondValue()
                    DIVIDE -> getSecondValue()
                    ROOT -> getSecondValue()
                    POWER -> getSecondValue()
                    PERCENT -> getSecondValue()
                    else -> getSecondValue()
                }

                calculateResult()

                if (!operations.contains(inputDisplayedFormula.last().toString())) {
                    if (!inputDisplayedFormula.contains("÷")) {
                        inputDisplayedFormula += getSign(operation)
                    }
                }
            }
        }

        if (getSecondValue() == 0.0 && inputDisplayedFormula.contains("÷")) {
            lastKey = DIVIDE
            lastOperation = DIVIDE
        } else {
            lastKey = operation
            lastOperation = operation
        }

        showNewResult(inputDisplayedFormula)
    }

    fun turnToNegative(): Boolean {
        if (inputDisplayedFormula.isEmpty()) {
            return false
        }

        if (!inputDisplayedFormula.trimStart('-').any { it.toString() in operations } && inputDisplayedFormula.removeGroupSeparator().toDouble() != 0.0) {
            inputDisplayedFormula = if (inputDisplayedFormula.first() == '-') {
                inputDisplayedFormula.substring(1)
            } else {
                "-$inputDisplayedFormula"
            }

            showNewResult(inputDisplayedFormula)
            return true
        }

        return false
    }

    // handle percents manually, it doesn't seem to be possible via net.objecthunter:exp4j. "%" is used only for modulo there
    // handle cases like 10+200% here
    private fun handlePercent() {
        var result = calculatePercentage(baseValue, getSecondValue(), lastOperation)
        if (result.isInfinite() || result.isNaN()) {
            result = 0.0
        }

        showNewFormula("${baseValue.format()}${getSign(lastOperation)}${getSecondValue().format()}%")
        inputDisplayedFormula = result.format()
        showNewResult(result.format())
        baseValue = result
    }

    fun handleEquals() {
        if (lastKey == EQUALS) {
            calculateResult()
        }

        if (lastKey != DIGIT && lastKey != DECIMAL) {
            return
        }

        secondValue = getSecondValue()
        calculateResult()
        if ((lastOperation == DIVIDE || lastOperation == PERCENT) && secondValue == 0.0) {
            lastKey = DIGIT
            return
        }

        lastKey = EQUALS
    }

    public fun getSecondValue(): Double {
        val valueToCheck = inputDisplayedFormula.trimStart('-').removeGroupSeparator()
        var value = valueToCheck.substring(valueToCheck.indexOfAny(operations) + 1)
        if (value == "") {
            value = "0"
        }

        return try {
            value.toDouble()
        } catch (e: NumberFormatException) {
            context.showErrorToast(e)
            0.0
        }
    }

    private fun calculateResult() {
        if (lastOperation == ROOT && inputDisplayedFormula.startsWith("√")) {
            baseValue = 1.0
        }

        Log.v("LASKEY CR :", lastKey)

        if (lastKey != EQUALS) {
            val valueToCheck = inputDisplayedFormula.trimStart('-').removeGroupSeparator()
            val parts = valueToCheck.split(operationsRegex).filter { it != "" }
            if (parts.isEmpty()) {
                return
            }

            baseValue = parts.first().toDouble()

            if (inputDisplayedFormula.startsWith("-")) {
                baseValue *= -1
            }

            secondValue = parts.getOrNull(1)?.toDouble() ?: secondValue


        }

        if (lastOperation != "") {
            val sign = getSign(lastOperation)
            val formattedBaseValue = baseValue.format().removeGroupSeparator()
            val formatterSecondValue = secondValue.format().removeGroupSeparator()
            val expression = "$formattedBaseValue$sign$formatterSecondValue"
                .replace("√", "sqrt")
                .replace("×", "*")
                .replace("÷", "/")

            try {
                if (sign == "÷" && secondValue == 0.0) {
                    context.toast(R.string.formula_divide_by_zero_error)
                    return
                }

                // handle percents manually, it doesn't seem to be possible via net.objecthunter:exp4j. "%" is used only for modulo there
                // handle cases like 10%200 here
                val result = if (sign == "%") {
                    val second = (secondValue / 100f).format().removeGroupSeparator()
                    ExpressionBuilder("$formattedBaseValue*$second").build().evaluate()
                } else {
                    // avoid Double rounding errors at expressions like 5250,74 + 14,98
                    if (sign == "+" || sign == "-") {
                        val first = BigDecimal.valueOf(baseValue)
                        val second = BigDecimal.valueOf(secondValue)
                        val bigDecimalResult = when (sign) {
                            "-" -> first.minus(second)
                            else -> first.plus(second)
                        }
                        bigDecimalResult.toDouble()
                    } else {
                        ExpressionBuilder(expression).build().evaluate()
                    }
                }

                if (result.isInfinite() || result.isNaN()) {
                    context.toast(R.string.unknown_error_occurred)
                    return
                }

                //============================================================

                //mResult = result.format()
                Log.v("CalculResult", result.format())

                //============================================================
                showNewResult(result.format())
                val newFormula = "${baseValue.format()}$sign${secondValue.format()}"
                HistoryHelper(context).insertOrUpdateHistoryEntry(
                    History(id = null, formula = newFormula, result = result.format(), timestamp = System.currentTimeMillis())
                )
                showNewFormula(newFormula)

                //============================================================
                //previousCalculation = newFormula
                //============================================================

                inputDisplayedFormula = result.format()
                baseValue = result
            } catch (e: Exception) {
                context.toast(R.string.unknown_error_occurred)
            }
        }
    }

    private fun calculatePercentage(baseValue: Double, secondValue: Double, sign: String): Double {
        return when (sign) {
            MULTIPLY -> {
                val partial = 100 / secondValue
                baseValue / partial
            }
            DIVIDE -> {
                val partial = 100 / secondValue
                baseValue * partial
            }
            PLUS -> {
                val partial = baseValue / (100 / secondValue)
                baseValue.plus(partial)
            }
            MINUS -> {
                val partial = baseValue / (100 / secondValue)
                baseValue.minus(partial)
            }
            PERCENT -> {
                val partial = (baseValue % secondValue) / 100
                partial
            }
            else -> baseValue / (100 * secondValue)
        }
    }

    private fun showNewResult(value: String) {
        //============================================================
        mResult = value;
        //============================================================
        callback!!.showNewResult(value, context)
    }

    private fun showNewFormula(value: String) {
        //============================================================
        previousCalculation = value;
        //============================================================
        callback!!.showNewFormula(value, context)
    }

    fun handleClear() {
        val lastDeletedValue = inputDisplayedFormula.lastOrNull().toString()

        var newValue = inputDisplayedFormula.dropLast(1)
        if (newValue == "" || newValue == "0") {
            newValue = "0"
            lastKey = CLEAR
        } else {
            if (operations.contains(lastDeletedValue) || lastKey == EQUALS) {
                lastOperation = ""
            }
            val lastValue = newValue.last().toString()
            lastKey = when {
                operations.contains(lastValue) -> CLEAR
                lastValue == decimalSeparator -> DECIMAL
                else -> DIGIT
            }
        }

        newValue = newValue.trimEnd(groupingSeparator.single())
        inputDisplayedFormula = newValue
        addThousandsDelimiter()
        showNewResult(inputDisplayedFormula)
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
        lastKey = ""
        lastOperation = ""
    }

    private fun getSign(lastOperation: String) = when (lastOperation) {
        MINUS -> "-"
        MULTIPLY -> "×"
        DIVIDE -> "÷"
        PERCENT -> "%"
        POWER -> "^"
        ROOT -> "√"
        else -> "+"
    }

    fun numpadClicked(id: Int) {
        if (inputDisplayedFormula == Double.NaN.toString()) {
            inputDisplayedFormula = ""
        }

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

    fun addNumberToFormula(number: String) {
        handleReset()
        inputDisplayedFormula = number
        addThousandsDelimiter()
        showNewResult(inputDisplayedFormula)
    }

    fun updateSeparators(decimalSeparator: String, groupingSeparator: String) {
        if (this.decimalSeparator != decimalSeparator || this.groupingSeparator != groupingSeparator) {
            this.decimalSeparator = decimalSeparator
            this.groupingSeparator = groupingSeparator
            formatter.decimalSeparator = decimalSeparator
            formatter.groupingSeparator = groupingSeparator
            // future: maybe update the formulas with new separators instead of resetting the whole thing
            handleReset()
        }
    }

    private fun Double.format() = formatter.doubleToString(this)

    private fun String.removeGroupSeparator() = formatter.removeGroupingSeparator(this)
}
