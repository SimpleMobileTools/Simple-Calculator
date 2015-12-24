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

    private double baseValue;
    private double secondValue;
    private boolean resetValue;
    private int lastKey;
    private int lastOperation;

    private static final int DIGIT = 0;
    private static final int EQUALS = 1;
    private static final int PLUS = 2;
    private static final int MINUS = 3;
    private static final int MULTIPLY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void addDigit(int number) {
        final String currentValue = getDisplayedNumber();
        final String newValue = removeLeadingZero(currentValue + number);
        result.setText(newValue);
    }

    private String removeLeadingZero(String str) {
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
        final double resultValue = baseValue + secondValue;
        result.setText(Formatter.doubleToString(resultValue));
        baseValue = resultValue;
    }

    private void subtractNumbers() {
        final double resultValue = baseValue - secondValue;
        result.setText(Formatter.doubleToString(resultValue));
        baseValue = resultValue;
    }

    private void multiplyNumbers() {
        final double resultValue = baseValue * secondValue;
        result.setText(Formatter.doubleToString(resultValue));
        baseValue = resultValue;
    }

    private void handleOperation(int operation) {
        if (lastKey == operation)
            return;

        if (lastKey == DIGIT) {
            secondValue = getDisplayedNumberAsDouble();
            handleEquals();
            baseValue = getDisplayedNumberAsDouble();
        }
        resetValue = true;
        lastKey = operation;
        lastOperation = operation;
    }

    @OnClick(R.id.btn_plus)
    public void plusClicked() {
        handleOperation(PLUS);
    }

    @OnClick(R.id.btn_minus)
    public void minusClicked() {
        handleOperation(MINUS);
    }

    @OnClick(R.id.btn_multiply)
    public void multiplyClicked() {
        handleOperation(MULTIPLY);
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
        else if (lastOperation == MINUS)
            subtractNumbers();
        else if (lastOperation == MULTIPLY)
            multiplyNumbers();
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
