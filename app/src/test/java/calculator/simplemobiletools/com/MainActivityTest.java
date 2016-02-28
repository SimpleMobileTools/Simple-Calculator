package com.simplemobiletools.calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import butterknife.ButterKnife;

import static junit.framework.Assert.assertEquals;

@RunWith(MyTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "app/src/main/AndroidManifest.xml", sdk = 21)
public class MainActivityTest {
    MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
        ButterKnife.bind(activity);
    }

    @Test
    public void addSimpleDigit() {
        activity.getCalc().addDigit(2);
        assertEquals("2", getDisplayedNumber());
    }

    @Test
    public void removeLeadingZero() {
        activity.getCalc().addDigit(0);
        activity.getCalc().addDigit(5);
        assertEquals("5", getDisplayedNumber());
    }

    @Test
    public void additionTest() {
        String res = calcResult(-1.2, Constants.PLUS, 3.4);
        assertEquals("2.2", res);
        checkFormula("-1.2+3.4");
    }

    @Test
    public void subtractionTest() {
        String res = calcResult(7.8, Constants.MINUS, 2.5);
        assertEquals("5.3", res);
        checkFormula("7.8-2.5");
    }

    @Test
    public void multiplyTest() {
        String res = calcResult(-3.2, Constants.MULTIPLY, 6.6);
        assertEquals("-21.12", res);
        checkFormula("-3.2*6.6");
    }

    @Test
    public void divisionTest() {
        String res = calcResult(18.25, Constants.DIVIDE, 5);
        assertEquals("3.65", res);
        checkFormula("18.25/5");
    }

    @Test
    public void divisionByZero_returnsZero() {
        String res = calcResult(6, Constants.DIVIDE, 0);
        assertEquals("0", res);
        checkFormula("6/0");
    }

    @Test
    public void moduloTest() {
        String res = calcResult(6.5, Constants.MODULO, 3);
        assertEquals("0.5", res);
        checkFormula("6.5%3");
    }

    @Test
    public void powerTest() {
        String res = calcResult(3, Constants.POWER, 6);
        assertEquals("729", res);
        checkFormula("3^6");
    }

    @Test
    public void rootTest() {
        setDouble(16);
        handleOperation(Constants.ROOT);
        assertEquals("4", getDisplayedNumber());
        checkFormula("√16");
    }

    @Test
    public void clearBtnSimpleTest() {
        setDouble(156);
        activity.getCalc().handleClear();
        assertEquals("15", getDisplayedNumber());
    }

    @Test
    public void clearBtnComplexTest() {
        setDouble(-26);
        activity.getCalc().handleClear();
        assertEquals("-2", getDisplayedNumber());
        activity.getCalc().handleClear();
        assertEquals("0", getDisplayedNumber());
    }

    @Test
    public void clearBtnLongClick_resetsEverything() {
        calcResult(-1.2, Constants.PLUS, 3.4);
        activity.getCalc().handleReset();
        handleOperation(Constants.PLUS);
        setDouble(3);
        activity.getCalc().handleResult();
        assertEquals("3", getDisplayedNumber());
        checkFormula("");
    }

    @Test
    public void complexTest() {
        setDouble(-12.2);
        handleOperation(Constants.PLUS);
        setDouble(21);
        handleOperation(Constants.MINUS);
        assertEquals("8.8", getDisplayedNumber());
        checkFormula("-12.2+21");

        setDouble(1.6);
        activity.getCalc().handleEquals();
        assertEquals("7.2", getDisplayedNumber());
        checkFormula("8.8-1.6");
        activity.getCalc().handleEquals();
        assertEquals("5.6", getDisplayedNumber());
        checkFormula("7.2-1.6");

        handleOperation(Constants.MULTIPLY);
        setDouble(5);
        handleOperation(Constants.DIVIDE);
        assertEquals("28", getDisplayedNumber());
        checkFormula("5.6*5");

        setDouble(4);
        handleOperation(Constants.MODULO);
        assertEquals("7", getDisplayedNumber());
        checkFormula("28/4");

        setDouble(5);
        handleOperation(Constants.POWER);
        assertEquals("2", getDisplayedNumber());
        checkFormula("7%5");

        setDouble(8);
        handleOperation(Constants.ROOT);
        assertEquals("16", getDisplayedNumber());
        checkFormula("√256");

        activity.getCalc().handleClear();
        assertEquals("1", getDisplayedNumber());
    }

    private void setDouble(double d) {
        activity.setValueDouble(d);
    }

    private void handleOperation(String operation) {
        activity.getCalc().handleOperation(operation);
    }

    private void checkFormula(String desired) {
        assertEquals(desired, getDisplayedFormula());
    }

    private String calcResult(double baseValue, String operation, double secondValue) {
        setDouble(baseValue);
        handleOperation(operation);
        setDouble(secondValue);
        activity.getCalc().handleResult();
        return getDisplayedNumber();
    }

    private String getDisplayedNumber() {
        return activity.getCalc().getDisplayedNumber();
    }

    private String getDisplayedFormula() {
        return activity.getCalc().getDisplayedFormula();
    }
}
