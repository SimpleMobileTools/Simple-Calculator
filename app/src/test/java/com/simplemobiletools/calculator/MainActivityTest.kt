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

//TODO: Add tests for clear character, clear string, square root, more complex calculations
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MainActivityTest {
    private lateinit var activity: MainActivity

    private val evaluator = DoubleEvaluator()

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

    @Test
    fun bracketsTest() {
        val result = evaluator.evaluate("(3-2)*5")
        assertEquals(5.0, result)
    }

    @Test
    fun equationTest() {
        val result = evaluator.evaluate("(4+3/1)*3+2")
        assertEquals(23.0, result)
    }

    @Test
    fun squareRootTest() {
        val result = evaluator.evaluate("9^(1/2)")
        assertEquals(3.0, result)
    }

    @Test
    fun orderOfOperationsTest() {
        val result = evaluator.evaluate("1+2*(3-1)-1")
        assertEquals(4.0, result)
    }

    //@Test
    fun multiplyWithBracketsTest() {
        val result = evaluator.evaluate("2(3-1)")
        assertEquals(4.0, result)
    }//currently does not work, needs to be addressed
}
