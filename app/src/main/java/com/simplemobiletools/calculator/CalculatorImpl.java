package com.simplemobiletools.calculator;

public class CalculatorImpl {
    private String mDisplayedValue;
    private String mDisplayedFormula;
    private String mLastKey;
    private String mLastOperation;
    private Calculator mCallback;

    private boolean mIsFirstOperation;
    private boolean mResetValue;
    private double mBaseValue;
    private double mSecondValue;

    public CalculatorImpl(Calculator calculator) {
        mCallback = calculator;
        resetValues();
        setValue("0");
        setFormula("");
    }

    public CalculatorImpl(Calculator calculatorInterface, String value) {
        mCallback = calculatorInterface;
        resetValues();
        mDisplayedValue = value;
        setFormula("");
    }

    private void resetValueIfNeeded() {
        if (mResetValue)
            mDisplayedValue = "0";

        mResetValue = false;
    }

    private void resetValues() {
        mBaseValue = 0;
        mSecondValue = 0;
        mResetValue = false;
        mLastKey = "";
        mLastOperation = "";
        mDisplayedValue = "";
        mDisplayedFormula = "";
        mIsFirstOperation = true;
    }

    public void setValue(String value) {
        mCallback.setValue(value);
        mDisplayedValue = value;
    }

    private void setFormula(String value) {
        mCallback.setFormula(value);
        mDisplayedFormula = value;
    }

    private void updateFormula() {
        final String first = Formatter.doubleToString(mBaseValue);
        final String second = Formatter.doubleToString(mSecondValue);
        final String sign = getSign(mLastOperation);

        if (sign.equals("√")) {
            setFormula(sign + first);
        } else if (!sign.isEmpty()) {
            setFormula(first + sign + second);
        }
    }

    public void setLastKey(String mLastKey) {
        this.mLastKey = mLastKey;
    }

    public void addDigit(int number) {
        final String currentValue = getDisplayedNumber();
        final String newValue = formatString(currentValue + number);
        setValue(newValue);
    }

    private String formatString(String str) {
        // if the number contains a decimal, do not try removing the leading zero anymore, nor add group separator
        // it would prevent writing values like 1.02
        if (str.contains("."))
            return str;

        final double doubleValue = Formatter.stringToDouble(str);
        return Formatter.doubleToString(doubleValue);
    }

    private void updateResult(double value) {
        setValue(Formatter.doubleToString(value));
        mBaseValue = value;
    }

    public String getDisplayedNumber() {
        return mDisplayedValue;
    }

    public double getDisplayedNumberAsDouble() {
        return Formatter.stringToDouble(getDisplayedNumber());
    }

    public String getDisplayedFormula() {
        return mDisplayedFormula;
    }

    public void handleResult() {
        mSecondValue = getDisplayedNumberAsDouble();
        calculateResult();
        mBaseValue = getDisplayedNumberAsDouble();
    }

    public void calculateResult() {
        if (!mIsFirstOperation)
            updateFormula();

        switch (mLastOperation) {
            case Constants.PLUS:
                updateResult(mBaseValue + mSecondValue);
                break;
            case Constants.MINUS:
                updateResult(mBaseValue - mSecondValue);
                break;
            case Constants.MULTIPLY:
                updateResult(mBaseValue * mSecondValue);
                break;
            case Constants.DIVIDE:
                divideNumbers();
                break;
            case Constants.MODULO:
                moduloNumbers();
                break;
            case Constants.POWER:
                powerNumbers();
                break;
            case Constants.ROOT:
                updateResult(Math.sqrt(mBaseValue));
                break;
            default:
                break;
        }
        mIsFirstOperation = false;
    }

    private void divideNumbers() {
        double resultValue = 0;
        if (mSecondValue != 0)
            resultValue = mBaseValue / mSecondValue;

        updateResult(resultValue);
    }

    private void moduloNumbers() {
        double resultValue = 0;
        if (mSecondValue != 0)
            resultValue = mBaseValue % mSecondValue;

        updateResult(resultValue);
    }

    private void powerNumbers() {
        double resultValue = Math.pow(mBaseValue, mSecondValue);
        if (Double.isInfinite(resultValue) || Double.isNaN(resultValue))
            resultValue = 0;
        updateResult(resultValue);
    }

    public void handleOperation(String operation) {
        if (mLastKey.equals(Constants.DIGIT))
            handleResult();

        mResetValue = true;
        mLastKey = operation;
        mLastOperation = operation;

        if (operation.equals(Constants.ROOT))
            calculateResult();
    }

    public void handleClear() {
        final String oldValue = getDisplayedNumber();
        String newValue = "0";
        final int len = oldValue.length();
        int minLen = 1;
        if (oldValue.contains("-"))
            minLen++;

        if (len > minLen)
            newValue = oldValue.substring(0, len - 1);

        newValue = newValue.replaceAll("\\.$", "");
        newValue = formatString(newValue);
        setValue(newValue);
        mBaseValue = Formatter.stringToDouble(newValue);
    }

    public void handleReset() {
        resetValues();
        setValue("0");
        setFormula("");
    }

    public void handleEquals() {
        if (mLastKey.equals(Constants.EQUALS))
            calculateResult();

        if (!mLastKey.equals(Constants.DIGIT))
            return;

        mSecondValue = getDisplayedNumberAsDouble();
        calculateResult();
        mLastKey = Constants.EQUALS;
    }

    public void decimalClicked() {
        String value = getDisplayedNumber();
        if (!value.contains("."))
            value += ".";
        setValue(value);
    }

    public void zeroClicked() {
        String value = getDisplayedNumber();
        if (!value.equals("0"))
            addDigit(0);
    }

    private String getSign(String lastOperation) {
        switch (lastOperation) {
            case Constants.PLUS:
                return "+";
            case Constants.MINUS:
                return "-";
            case Constants.MULTIPLY:
                return "*";
            case Constants.DIVIDE:
                return "/";
            case Constants.MODULO:
                return "%";
            case Constants.POWER:
                return "^";
            case Constants.ROOT:
                return "√";
        }
        return "";
    }

    public void numpadClicked(int id) {
        if (mLastKey.equals(Constants.EQUALS))
            mLastOperation = Constants.EQUALS;
        mLastKey = Constants.DIGIT;
        resetValueIfNeeded();

        switch (id) {
            case R.id.btn_decimal:
                decimalClicked();
                break;
            case R.id.btn_0:
                zeroClicked();
                break;
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
