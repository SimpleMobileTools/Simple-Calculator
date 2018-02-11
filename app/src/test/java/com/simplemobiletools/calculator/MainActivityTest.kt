package com.simplemobiletools.calculator

import com.fathzer.soft.javaluator.DoubleEvaluator
import com.simplemobiletools.calculator.activities.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.mockito.Mockito.*
import android.content.Context
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_ONE
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl

//TODO: Add tests for clear character, clear string, square root, more complex calculations
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MainActivityTest {
    private lateinit var activity: MainActivity

    private val evaluator = DoubleEvaluator()

    //var context = mock(Context::class.java)
    var mockCalc = mock(Calculator::class.java)
    var mockContext = mock(Context::class.java)

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun additionTest() {
        val result = evaluator.evaluate("-1.2+3.4")
        assertEquals(2.2, result)
    }

    @Test
    fun subtractionTest() {
        val result = evaluator.evaluate("7.8-2.5")
        assertEquals(5.3, result)
    }

    @Test
    fun multiplyTest() {
        val result = evaluator.evaluate("-3.2*6.6")
        assertEquals(-21.12, result)
    }

    @Test
    fun divisionTest() {
        val result = evaluator.evaluate("4/2")
        assertEquals(2.0, result)
    }

    @Test
    fun moduloTest() {
        val result = evaluator.evaluate("6.5%3")
        assertEquals(0.5, result)
    }

    @Test
    fun powerTest() {
        val result = evaluator.evaluate("3^6")
        assertEquals(729.0, result)
    }

    //TO-DO: Fix loading, test has to read from file. Added local data.json file to use for testing
    @Test
    fun storageTest() {
        var calc = CalculatorImpl(mockCalc,mockContext)
        calc.handleStore("5.0", MEMORY_ONE)
        System.out.println("Loaded: " + calc.displayedNumber)
        calc.handleViewValue(MEMORY_ONE)
        assertEquals("5.0", calc.displayedFormula) //currently loads null
    }
}
