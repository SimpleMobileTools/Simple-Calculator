package calculator.simplemobiletools.com.simple_calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public final ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addSimpleDigit() {
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void removeLeadingZero() {
        press(R.id.btn_0);
        press(R.id.btn_5);
        checkResult("5");
    }

    @Test
    public void additionTest() {
        press(R.id.btn_minus);
        press(R.id.btn_2);
        press(R.id.btn_decimal);
        press(R.id.btn_5);
        press(R.id.btn_plus);
        press(R.id.btn_6);
        press(R.id.btn_equals);
        checkResult("3.5");
        press(R.id.btn_equals);
        checkResult("9.5");
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
    }

    @Test
    public void multiplyTest() {
        press(R.id.btn_2);
        press(R.id.btn_multiply);
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("8");
    }

    @Test
    public void divisionTest() {
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_divide);
        press(R.id.btn_4);
        press(R.id.btn_equals);
        checkResult("2.5");
    }

    @Test
    public void divisionbyZeroTest() {
        press(R.id.btn_8);
        press(R.id.btn_divide);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("0");
    }

    @Test
    public void moduloTest() {
        press(R.id.btn_7);
        press(R.id.btn_modulo);
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("1");
    }

    @Test
    public void powerTest() {
        press(R.id.btn_2);
        press(R.id.btn_power);
        press(R.id.btn_3);
        press(R.id.btn_equals);
        checkResult("8");
    }

    @Test
    public void rootTest() {
        press(R.id.btn_9);
        press(R.id.btn_root);
        checkResult("3");
    }

    @Test
    public void clearTest() {
        press(R.id.btn_2);
        press(R.id.btn_5);
        press(R.id.btn_decimal);
        press(R.id.btn_7);
        press(R.id.btn_clear);
        checkResult("25.");
        press(R.id.btn_clear);
        checkResult("25");
        press(R.id.btn_clear);
        checkResult("2");
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
    }

    @Test
    public void complexTest() {
        press(R.id.btn_2);
        press(R.id.btn_plus);
        press(R.id.btn_5);
        press(R.id.btn_minus);
        checkResult("7");

        press(R.id.btn_3);
        press(R.id.btn_multiply);
        checkResult("4");

        press(R.id.btn_5);
        press(R.id.btn_divide);
        checkResult("20");

        press(R.id.btn_2);
        press(R.id.btn_modulo);
        checkResult("10");

        press(R.id.btn_4);
        press(R.id.btn_power);
        checkResult("2");

        press(R.id.btn_8);
        press(R.id.btn_modulo);
        checkResult("256");

        press(R.id.btn_root);
        checkResult("16");

        press(R.id.btn_clear);
        checkResult("1");

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
}
