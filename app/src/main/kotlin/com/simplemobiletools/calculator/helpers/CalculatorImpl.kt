package com.simplemobiletools.calculator.helpers
import android.content.Context
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.javaluator.*
import com.simplemobiletools.calculator.helpers.CONSTANT.DIGIT
import com.simplemobiletools.calculator.helpers.CONSTANT.DIVIDE
import com.simplemobiletools.calculator.helpers.CONSTANT.EQUALS
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_READ_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_SAVE_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.LEFT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_ONE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_THREE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_TWO
import com.simplemobiletools.calculator.helpers.CONSTANT.MINUS
import com.simplemobiletools.calculator.helpers.CONSTANT.MODULO
import com.simplemobiletools.calculator.helpers.CONSTANT.MULTIPLY
import com.simplemobiletools.calculator.helpers.CONSTANT.PLUS
import com.simplemobiletools.calculator.helpers.CONSTANT.POWER
import com.simplemobiletools.calculator.helpers.CONSTANT.RIGHT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.ROOT
import com.simplemobiletools.calculator.helpers.CONSTANT.SINE
import java.io.File

//TODO: Allow number to be placed immediately before opened bracket. 4(3+3) should work.
class CalculatorImpl(calculator: Calculator, private val context: Context) {
    var displayedNumber: String? = null
    var displayedFormula: String? = null
    var lastKey: String? = null
    private var mLastOperation: String? = null
    private var mCallback: Calculator? = calculator
    private var mIsFirstOperation = false
    private var mResetValue = false
    private var mBaseValue = 0.0
    private var mSecondValue = 0.0
    private var mSavedValue1: File
    private var mSavedValue2: File
    private var mSavedValue3: File

    init {
        resetValues()
        setValue("")
        setFormula("")
        mSavedValue1 = createTempFile("one",".tmp")
        mSavedValue2 = createTempFile("two",".tmp")
        mSavedValue3 = createTempFile("three",".tmp")
        mSavedValue1.deleteOnExit()
        mSavedValue2.deleteOnExit()
        mSavedValue3.deleteOnExit()

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
        //Big Text ANSWER
        mCallback!!.setValue(value, context)
        displayedNumber = value
    }

    private fun setFormula(value: String) { //Small text OPERATIONS
        mCallback!!.setFormula(value, context)
        displayedFormula = value
    }

    private fun addDigit(number: Int) {
        setFormula(number.toString())
    }

    private fun updateResult(value: Double) {
        setValue(Formatter.doubleToString(value))
        mBaseValue = value
    }

    private fun calculateResult(str: String) {

        val evaluator = DoubleEvaluator()
        try {
            val result = evaluator.evaluate(str)
            updateResult(result)
        } catch (e: IllegalArgumentException) {
            throw e
        }
        mIsFirstOperation = false
    }

    fun handleOperation(operation : String) {
        setFormula(getSign(operation))
        mResetValue = true
        lastKey = operation
        mLastOperation = operation
    }

    fun handleStore(value : String, id: String) {
        if (lastKey == EQUALS && displayedNumber != "") {
            when (id) {
                //SetFormula: small text, SetValue BIG TEXT
                MEMORY_ONE -> { mSavedValue1.writeText(value); setFormula(""); setValue(value) }
                MEMORY_TWO -> { mSavedValue2.writeText(value); setFormula(""); setValue(value)}
                MEMORY_THREE -> { mSavedValue3.writeText(value); setFormula(""); setValue(value) }
            }
        }
        else {
            val message = Toast.makeText(context, ERROR_SAVE_VALUE, Toast.LENGTH_SHORT)
            message.show()
        }
    }

    fun handleViewValue(id: String) {
        val variable: String?
        when (id) {
            MEMORY_ONE -> { variable = mSavedValue1.readText()
                if(variable == "") {
                    val message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
            MEMORY_TWO -> { variable = mSavedValue2.readText()
                if(variable == "") {
                    val message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
            MEMORY_THREE -> { variable = mSavedValue3.readText()
                if(variable == "") {
                    val message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
        }
    }

    fun handleClear(formula : String) {

        val len = formula.length
        val newValue: String
        if(formula.isNotEmpty())
        {
            formula.takeLast(1)
            newValue = formula.substring(0, len - 1)
            setFormula("")
            setFormula(newValue)
            setValue("")
        }
    }

    fun handleReset() {
        resetValues()
        setValue("")
        setFormula("")
    }

    fun handleEquals(str: String) {
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

    private fun getSign(lastOperation: String?) = when (lastOperation) {
        PLUS -> "+"
        MINUS -> "-"
        MULTIPLY -> "*"
        DIVIDE -> "/"
        MODULO -> "%"
        POWER -> "^"
        ROOT -> "^.5"
        LEFT_BRACKET -> "("
        RIGHT_BRACKET -> ")"
        SINE -> "sin("
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
            R.id.btn_0 -> addDigit(0)
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