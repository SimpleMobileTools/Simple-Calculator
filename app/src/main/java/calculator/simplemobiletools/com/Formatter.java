package calculator.simplemobiletools.com;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formatter {
    public static String doubleToString(double d) {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');

        final DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(12);
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setGroupingUsed(true);
        return formatter.format(d);
    }

    public static Double stringToDouble(String str) {
        str = str.replaceAll(",", "");
        return Double.parseDouble(str);
    }
}
