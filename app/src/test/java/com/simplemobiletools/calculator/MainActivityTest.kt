package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.helpers.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21])
class MainActivityTest {
    private lateinit var activity: MainActivity

    private fun getDisplayedNumber() = activity.calc.displayedNumber

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun addSimpleDigit() {
        activity.calc.addDigit(2)
        assertEquals("2", getDisplayedNumber())
    }

    @Test
    fun removeLeadingZero() {
        activity.calc.addDigit(0)
        activity.calc.addDigit(5)
        assertEquals("5", getDisplayedNumber())
    }

    @Test
    fun additionTest() {
        val res = calcResult(-1.2, PLUS, 3.4)
        assertEquals("2.2", res)
        checkFormula("-1.2+3.4")
    }

    @Test
    fun subtractionTest() {
        val res = calcResult(7.8, MINUS, 2.5)
        assertEquals("5.3", res)
        checkFormula("7.8-2.5")
    }

    @Test
    fun multiplyTest() {
        val res = calcResult(-3.2, MULTIPLY, 6.6)
        assertEquals("-21.12", res)
        checkFormula("-3.2*6.6")
    }

    @Test
    fun divisionTest() {
        val res = calcResult(18.25, DIVIDE, 5.0)
        assertEquals("3.65", res)
        checkFormula("18.25/5")
    }

    @Test
    fun divisionByZero_returnsZero() {
        val res = calcResult(6.0, DIVIDE, 0.0)
        assertEquals("0", res)
        checkFormula("6/0")
    }

    /*  @Test
      fun moduloTest() {
          val res = calcResult(6.5, MODULO, 3.0)
          assertEquals("0.5", res)
          checkFormula("6.5%3")
      }*/

    @Test
    fun powerTest() {
        val res = calcResult(3.0, POWER, 6.0)
        assertEquals("729", res)
        checkFormula("3^6")
    }

    @Test
    fun rootTest() {
        setDouble(16.0)
        handleOperation(ROOT)
        assertEquals("4", getDisplayedNumber())
        checkFormula("√16")
    }

    @Test
    fun clearBtnSimpleTest() {
        setDouble(156.0)
        activity.calc.handleClear()
        assertEquals("15", getDisplayedNumber())
    }

    @Test
    fun clearBtnComplexTest() {
        setDouble(-26.0)
        activity.calc.handleClear()
        assertEquals("-2", getDisplayedNumber())
        activity.calc.handleClear()
        assertEquals("0", getDisplayedNumber())
    }

    @Test
    fun clearBtnLongClick_resetsEverything() {
        calcResult(-1.2, PLUS, 3.4)
        activity.calc.handleReset()
        handleOperation(PLUS)
        setDouble(3.0)
        activity.calc.handleResult()
        assertEquals("3", getDisplayedNumber())
        checkFormula("")
    }

    @Test
    fun complexTest() {
        setDouble(-12.2)
        handleOperation(PLUS)
        setDouble(21.0)
        handleOperation(MINUS)
        assertEquals("8.8", getDisplayedNumber())
        checkFormula("-12.2+21")

        setDouble(1.6)
        activity.calc.handleEquals()
        assertEquals("7.2", getDisplayedNumber())
        checkFormula("8.8-1.6")
        activity.calc.handleEquals()
        assertEquals("5.6", getDisplayedNumber())
        checkFormula("7.2-1.6")

        handleOperation(MULTIPLY)
        setDouble(5.0)
        handleOperation(DIVIDE)
        assertEquals("28", getDisplayedNumber())
        checkFormula("5.6*5")

        setDouble(4.0)
        handleOperation(DIVIDE)
        assertEquals("7", getDisplayedNumber())
        checkFormula("28/4")

        setDouble(5.0)
        handleOperation(POWER)
        assertEquals("2", getDisplayedNumber())
        checkFormula("7%5")

        setDouble(8.0)
        handleOperation(ROOT)
        assertEquals("16", getDisplayedNumber())
        checkFormula("√256")

        activity.calc.handleClear()
        assertEquals("1", getDisplayedNumber())
    }

    private fun setDouble(d: Double) {
        activity.setValueDouble(d)
    }

    private fun handleOperation(operation: String) {
        activity.calc.handleOperation(operation)
    }

    private fun checkFormula(desired: String) {
        assertEquals(desired, activity.calc.displayedFormula)
    }

    private fun calcResult(baseValue: Double, operation: String, secondValue: Double): String? {
        setDouble(baseValue)
        handleOperation(operation)
        setDouble(secondValue)
        activity.calc.handleResult()
        return getDisplayedNumber()
    }
}
