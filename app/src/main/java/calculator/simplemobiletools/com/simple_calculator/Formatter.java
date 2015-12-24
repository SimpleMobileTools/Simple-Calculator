package calculator.simplemobiletools.com.simple_calculator;

import java.text.DecimalFormat;

public class Formatter {
    public static String doubleToString(double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else {
            final DecimalFormat formatter = new DecimalFormat("0.0#############");
            return formatter.format(d);
        }
    }
}
