package com.simplemobiletools.calculator;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.MainActivity;
import com.simplemobiletools.calculator.activities.ScientificActivity;

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
/**
 * Created by Ahmed Ashraf Hamza with Ahmad Osama on 2018-12-18.
 */

public class ScientificActivityTest {

    @Rule public final ActivityTestRule<ScientificActivity> activity = new ActivityTestRule<>(ScientificActivity.class);


    @Test
    public void SinTest() {
        press(R.id.btn_9);
        press(R.id.btn_0);
        press(R.id.btn_sin);
        checkResult("1");
        checkFormula("Sin(90)");
    }
    @Test
    public void CosTest() {
        press(R.id.btn_9);
        press(R.id.btn_0);
        press(R.id.btn_cos);
        checkResult("0");
        checkFormula("Cos(90)");
    }
    @Test
    public void TanTest() {
        press(R.id.btn_4);
        press(R.id.btn_5);
        press(R.id.btn_tan);
        checkResult("1");
        checkFormula("Tan(45)");
    }
    @Test
    public void ArcsinTest() {
        press(R.id.btn_1);
        press(R.id.btn_arcsin);
        checkResult("90");
        checkFormula("Arcsin(1)");
    }
    @Test
    public void ArccosTest() {
        press(R.id.btn_0);
        press(R.id.btn_arccos);
        checkResult("90");
        checkFormula("Arccos(0)");
    }
    @Test
    public void ArcTanTest() {
        press(R.id.btn_1);
        press(R.id.btn_arctan);
        checkResult("45");
        checkFormula("Arctan(1)");
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
