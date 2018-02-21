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
import com.simplemobiletools.calculator.helpers.CONSTANT.HISTORY_FILE
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
import com.simplemobiletools.calculator.helpers.CONSTANT.TEMP_FILE
import java.io.*

//TODO: Allow number to be placed immediately before opened bracket. 4(3+3) should work.
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
    private var mSavedValue1: File
    private var mSavedValue2: File
    private var mSavedValue3: File
    private var mEquationHistory: File
    private var mResultHistory: File
    private var fileManager: FileHandler = FileHandler(calculator, context)

    init {
        resetValues()
        setValue("0")
        setFormula("")
        mSavedValue1 = fileManager.chooseFileType(TEMP_FILE, "one")
        mSavedValue2 = fileManager.chooseFileType(TEMP_FILE, "two")
        mSavedValue3 = fileManager.chooseFileType(TEMP_FILE, "three")
        mEquationHistory = fileManager.chooseFileType(HISTORY_FILE, "History")
        mResultHistory = fileManager.chooseFileType(HISTORY_FILE, "Results")
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

    private fun calculateResult(str: String) {

        val evaluator = DoubleEvaluator()
        val expression = str
        try {
            val result = evaluator.evaluate(expression)
            updateResult(result)
            storeResult(result.toString())
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
    //TODO Finish the implementation
    fun handleStore(value : String, id: String) {
        if (lastKey == EQUALS && displayedNumber != "") {
            when (id) {
                //SetFormula: small text, SetValue BIG TEXT
                MEMORY_ONE -> { mSavedValue1!!.writeText(value); setFormula(""); setValue(value) }
                MEMORY_TWO -> { mSavedValue2!!.writeText(value); setFormula(""); setValue(value)}
                MEMORY_THREE -> { mSavedValue3!!.writeText(value); setFormula(""); setValue(value) }
            }
        }
        else {
            val message = Toast.makeText(context, ERROR_SAVE_VALUE, Toast.LENGTH_SHORT)
            message.show()
        }
    }

    fun handleViewValue(id: String) {
        val message: Toast?
        val variable: String?

        when (id) {
            MEMORY_ONE -> { variable = mSavedValue1!!.readText()
                if(variable == "") {
                    message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
            MEMORY_TWO -> { variable = mSavedValue2!!.readText()
                if(variable == "") {
                    message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
            MEMORY_THREE -> { variable = mSavedValue3!!.readText();
                if(variable == "") {
                    message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
        }
    }

    fun handleClear(formula : String) {

        val oldValue = formula
        val len = oldValue!!.length
        var newValue = "0"
        if(formula!!.length > 0)
        {
            var lastChar = oldValue.takeLast(1);
            newValue = oldValue.substring(0, len - 1)
            setFormula("")
            setFormula(newValue)
            setValue("")
        }
    }

    fun handleReset() {
        resetValues()
        setValue("0")
        setFormula("")
    }

    fun handleEquals(str: String) {
        calculateResult(str)
        storeHistory(str)
        lastKey = EQUALS
    }

    //TODO: Finish history method that stores the information with the fie explorer
    private fun storeHistory(equation: String) {
        val write: Writer = BufferedWriter(FileWriter(mEquationHistory, true))
        write.write(equation)
        write.appendln()
        write.flush()
        write.close()
    }

    //TODO: Finish the results history section
    private fun storeResult(result: String) {
        val writer: Writer = BufferedWriter(FileWriter(mResultHistory, true))
        writer.write(result)
        writer.appendln()
        writer.flush()
        writer.close()
    }

    fun getHistoryEntries(): ArrayList<String> {
        val reader: Reader = BufferedReader(FileReader(mEquationHistory))
        val list: ArrayList<String> = ArrayList()
        reader.forEachLine {
            list.add(it)
        }
        reader.close()
        return list
    }

    fun getResults(): ArrayList<String> {
        val reader: Reader = BufferedReader(FileReader(mResultHistory))
        val list: ArrayList<String> = ArrayList()
        reader.forEachLine {
            list.add(it)
        }
        reader.close()
        return list
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
        LEFT_BRACKET -> "("
        RIGHT_BRACKET -> ")"
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

    fun getHistoryFile() : File {
        return mEquationHistory
    }

    fun getResultFile() : File {
        return mResultHistory
    }

    fun setHistoryFile(file : File) {
        mEquationHistory = file
    }

    fun setResultFile(file : File) {
        mResultHistory = file
    }

    fun getFileManager() : FileHandler {
        return fileManager
    }
}