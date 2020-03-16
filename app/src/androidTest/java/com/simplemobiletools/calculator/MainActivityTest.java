package com.simplemobiletools.calculator;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public final ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addDigits() {
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_3);
        press(R.id.btn_4);
        press(R.id.btn_5);
        press(R.id.btn_6);
        press(R.id.btn_7);
        press(R.id.btn_8);
        press(R.id.btn_9);
        press(R.id.btn_0);
        checkResult("1,234,567,890");
    }

    @Test
    public void removeLeadingZero() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void clearComplexTest(){
        press(R.id.btn_1);
        press(R.id.btn_plus);
        press(R.id.btn_1);
        press(R.id.btn_decimal);
        press(R.id.btn_5);
        press(R.id.btn_5);
        press(R.id.btn_clear);
        press(R.id.btn_1);
        press(R.id.btn_equals);
        checkResult("2.51");
        checkFormula("1+1.51");
    }


    @Test
    public void additionTest() {
        press(R.id.btn_0);
        press(R.id.btn_minus);
        press(R.id.btn_2);
        press(R.id.btn_decimal);
        press(R.id.btn_5);
        press(R.id.btn_plus);
        press(R.id.btn_6);
        press(R.id.btn_equals);
        checkResult("3.5");
        checkFormula("-2.5+6");
        press(R.id.btn_equals);
        checkResult("9.5");
        checkFormula("3.5+6");
    }

    @Test
    public void subtractionTest() {
        press(R.id.btn_7);
        press(R.id.btn_decimal);
        press(R.id.btn_8);
        press(R.id.btn_minus);
        press(R.id.btn_3);
        press(R.id.btn_equals);
        checkResult("4.8");
        checkFormula("7.8-3");
    }

    @Test
    public void multiplyTest() {
        press(R.id.btn_2);
        press(R.id.btn_multiply);
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("8");
        checkFormula("2*4");
    }

    @Test
    public void divisionTest() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_divide);
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("2.5");
        checkFormula("10/4");
    }

    @Test
    public void divisionByZeroTest() {
        press(R.id.btn_8);
        press(R.id.btn_divide);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("0");
        checkFormula("8/0");
    }

    @Test
    public void percentTest() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_percent);
        press(R.id.btn_2);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("2");
        checkFormula("10%20");
    }

    @Test
    public void percentTestInsideOtherOperation() {
        press(R.id.btn_8);
        press(R.id.btn_0);
        press(R.id.btn_minus);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_percent);
        press(R.id.btn_equals);
        checkResult("72");
        checkFormula("80-10%");
    }

    @Test
    public void powerTest() {
        press(R.id.btn_2);
        press(R.id.btn_power);
        press(R.id.btn_3);
        press(R.id.btn_equals);
        checkResult("8");
        checkFormula("2^3");
    }

    @Test
    public void rootTest() {
        press(R.id.btn_9);
        press(R.id.btn_root);
        checkResult("3");
        checkFormula("√9");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_decimal);
        press(R.id.btn_7);
        press(R.id.btn_clear);
        checkResult("25");
        press(R.id.btn_clear);
        checkResult("2");
        press(R.id.btn_clear);
        checkResult("0");
        press(R.id.btn_clear);
        checkResult("0");
    }

    @Test
    public void clearLongTest() {
        press(R.id.btn_2);
        press(R.id.btn_plus);
        press(R.id.btn_5);
        press(R.id.btn_equals);
        longPress((R.id.btn_clear));
        press(R.id.btn_plus);
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("2");
        checkFormula("0+2");
    }

    @Test
    public void complexTest() {
        press(R.id.btn_2);
        press(R.id.btn_plus);
        press(R.id.btn_5);
        press(R.id.btn_minus);
        checkResult("7");
        checkFormula("2+5");

        press(R.id.btn_3);
        press(R.id.btn_multiply);
        checkResult("4");
        checkFormula("7-3");

        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_divide);
        checkResult("40");
        checkFormula("4*10");

        press(R.id.btn_5);
        press(R.id.btn_power);
        checkResult("8");
        checkFormula("40/5");

        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("64");
        checkFormula("8^2");

        press(R.id.btn_root);
        checkResult("8");
        checkFormula("√64");

        press(R.id.btn_clear);
        checkResult("0");
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void checkResult(String desired) {
        onView(withId(R.id.result)).check(matches(withText(desired)));
    }

    private void checkFormula(String desired) {
        onView(withId(R.id.formula)).check(matches(withText(desired)));
    }
}
