package com.simplemobiletools.calculator.helpers
import android.content.Context
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.CONSTANT.COSINE
import com.simplemobiletools.calculator.javaluator.*
import com.simplemobiletools.calculator.helpers.CONSTANT.DIGIT
import com.simplemobiletools.calculator.helpers.CONSTANT.DIVIDE
import com.simplemobiletools.calculator.helpers.CONSTANT.EQUALS
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_READ_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_SAVE_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.LEFT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_ONE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_THREE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_TWO
import com.simplemobiletools.calculator.helpers.CONSTANT.MINUS
import com.simplemobiletools.calculator.helpers.CONSTANT.MODULO
import com.simplemobiletools.calculator.helpers.CONSTANT.MULTIPLY
import com.simplemobiletools.calculator.helpers.CONSTANT.NATURAL_LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.PI
import com.simplemobiletools.calculator.helpers.CONSTANT.PLUS
import com.simplemobiletools.calculator.helpers.CONSTANT.POWER
import com.simplemobiletools.calculator.helpers.CONSTANT.RIGHT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.ROOT
import com.simplemobiletools.calculator.helpers.CONSTANT.SINE
import com.simplemobiletools.calculator.helpers.CONSTANT.TANGENT
import java.io.File

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

    //If any digit precedes these operations, automatically add a * in between them. 4pi = 4*pi.
    //See implementation in fun handleOperation(operation: String)
    private val listOfSpecialOperations = listOf(LEFT_BRACKET, PI, SINE, COSINE,  TANGENT,
                                                    LOGARITHM, NATURAL_LOGARITHM)

    //Every time a digit or operation is entered, we keep track of the length. In this way, when we
    //delete digits or operations, our program will automatically delete the appropriate amount of
    //characters in the formula string. Example: sin(90) would delete in the following order: ) ->
    //0 -> 9 -> sin( ... This prevents user's from deleting an operation one letter at a time.
    private val listOfInputLengths = mutableListOf<Int>()

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
        listOfInputLengths.add(1)
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
        //if the last character of our formula is a digit and an operation is called from the list,
        //then add a multiplication before the operation
        if(displayedFormula!!.isNotEmpty()){
            if(listOfSpecialOperations.contains(operation) && (displayedFormula!![displayedFormula!!.length - 1].isDigit())) {
                setFormula("*")
                listOfInputLengths.add(1)
            }
        }
        setFormula(getSign(operation))
        listOfInputLengths.add(getSign(operation).length)

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

    //FIX
    fun handleClear(formula : String) {

        val newValue: String
        if(formula.isNotEmpty())
        {
            val removeThisManyCharacters = listOfInputLengths[listOfInputLengths.size - 1]
            newValue = formula.substring(0, (formula.length - removeThisManyCharacters))
            listOfInputLengths.removeAt(listOfInputLengths.size - 1)
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

    //TODO: implement PLUS_MINUS
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
        PI -> "pi"
        SINE -> "sin("
        COSINE -> "cos("
        TANGENT -> "tan("
        LOGARITHM -> "log("
        NATURAL_LOGARITHM -> "ln("
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

    //TODO: Implement reciprocal on final answer
    /*
    fun resultModifier(){
    }
    */
}