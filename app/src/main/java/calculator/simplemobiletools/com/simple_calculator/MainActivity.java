package calculator.simplemobiletools.com.simple_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.result) TextView result;

    private double firstValue;
    private double secondValue;
    private boolean resetValue;
    private int lastKey;
    private int lastOperation;

    private static final int DIGIT = 0;
    private static final int EQUALS = 1;
    private static final int PLUS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void addDigit(int number) {
        final String currentValue = getDisplayedNumber();
        final String newValue = getFormattedValue(currentValue + number);
        result.setText(newValue);
    }

    private String getFormattedValue(String str) {
        final double doubleValue = Double.parseDouble(str);
        return Formatter.doubleToString(doubleValue);
    }

    private String getDisplayedNumber() {
        resetValueIfNeeded();
        return result.getText().toString();
    }

    private double getDisplayedNumberAsDouble() {
        return Double.parseDouble(result.getText().toString());
    }

    private void resetValueIfNeeded() {
        if (resetValue)
            result.setText("0");

        resetValue = false;
    }

    private void addNumbers() {
        final double resultValue = firstValue + secondValue;
        result.setText(Formatter.doubleToString(resultValue));
        firstValue = resultValue;
    }

    @OnClick(R.id.btn_plus)
    public void plusClicked() {
        resetValue = true;
        lastOperation = PLUS;

        if (lastKey != DIGIT) {
            lastKey = PLUS;
            return;
        }

        secondValue = getDisplayedNumberAsDouble();
        addNumbers();
        lastKey = PLUS;
    }

    @OnClick(R.id.btn_equals)
    public void equalsClicked() {
        if (lastKey == EQUALS) {
            handleEquals();
            return;
        }

        if (lastKey != DIGIT)
            return;

        secondValue = getDisplayedNumberAsDouble();
        handleEquals();
        lastKey = EQUALS;
    }

    private void handleEquals() {
        if (lastOperation == PLUS)
            addNumbers();
    }

    @OnClick(R.id.btn_decimal)
    public void decimalClicked() {
        String value = getDisplayedNumber();
        if (!value.contains("."))
            value += ".";
        result.setText(value);
        lastKey = DIGIT;
    }

    @OnClick(R.id.btn_0)
    public void zeroClicked() {
        String value = getDisplayedNumber();
        if (!value.isEmpty() && !value.equals("0"))
            value += "0";
        result.setText(value);
        lastKey = DIGIT;
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9})
    public void digitClicked(View view) {
        lastKey = DIGIT;
        switch (view.getId()) {
            case R.id.btn_1:
                addDigit(1);
                break;
            case R.id.btn_2:
                addDigit(2);
                break;
            case R.id.btn_3:
                addDigit(3);
                break;
            case R.id.btn_4:
                addDigit(4);
                break;
            case R.id.btn_5:
                addDigit(5);
                break;
            case R.id.btn_6:
                addDigit(6);
                break;
            case R.id.btn_7:
                addDigit(7);
                break;
            case R.id.btn_8:
                addDigit(8);
                break;
            case R.id.btn_9:
                addDigit(9);
                break;
            default:
                break;
        }
    }
}
