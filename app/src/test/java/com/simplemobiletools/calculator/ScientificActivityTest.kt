package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.activities.ScientificActivity
import com.simplemobiletools.calculator.helpers.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ScientificActivityTest {
    private lateinit var activity: ScientificActivity

    private fun getDisplayedNumber() = activity.calc.displayedNumber

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(ScientificActivity::class.java)
    }



    @Test
    fun SinTest() {
        setDouble(90.0)
        handleOperation(Sin)
        assertEquals("1", getDisplayedNumber())
        checkFormula("Sin(90)")
    }
    @Test
    fun CosTest() {
        setDouble(90.0)
        handleOperation(Cos)
        assertEquals("0", getDisplayedNumber())
        checkFormula("Cos(90)")
    }
    @Test
    fun TanTest() {
        setDouble(45.0)
        handleOperation(Tan)
        assertEquals("1", getDisplayedNumber())
        checkFormula("Tan(45)")
    }
    @Test
    fun ArcsinTest() {
        setDouble(1.0)
        handleOperation(Arcsin)
        assertEquals("90", getDisplayedNumber())
        checkFormula("Arcsin(1)")
    }
    @Test
    fun ArcosTest() {
        setDouble(0.0)
        handleOperation(Arccos)
        assertEquals("90", getDisplayedNumber())
        checkFormula("Arccos(0)")
    }
    @Test
    fun ArctanTest() {
        setDouble(1.0)
        handleOperation(Arctan)
        assertEquals("45", getDisplayedNumber())
        checkFormula("Arctan(1)")
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
