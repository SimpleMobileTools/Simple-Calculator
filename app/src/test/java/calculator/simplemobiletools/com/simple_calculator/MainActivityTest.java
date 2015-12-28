package calculator.simplemobiletools.com.simple_calculator;

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
        activity.addDigit(2);
        assertEquals(2.0, activity.getDisplayedNumberAsDouble());
    }

    @Test
    public void removeLeadingZero() {
        activity.addDigit(0);
        activity.addDigit(5);
        assertEquals(5.0, activity.getDisplayedNumberAsDouble());
    }

    @Test
    public void addition_test() {
        activity.setResult("-1.2");
        activity.handleOperation(MainActivity.PLUS);
        activity.setResult("3.4");
        activity.getResult();
        assertEquals(2.2, activity.getDisplayedNumberAsDouble());
    }
}
