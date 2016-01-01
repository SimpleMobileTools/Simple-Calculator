package calculator.simplemobiletools.com.simple_calculator;

public class CalculatorImpl {
    private String displayedValue;
    private double baseValue;
    private double secondValue;
    private boolean resetValue;
    private String lastKey;
    private String lastOperation;
    private Calculator callback;

    public CalculatorImpl(Calculator calculatorInterface) {
        callback = calculatorInterface;
        resetValues();
    }

    private void resetValueIfNeeded() {
        if (resetValue)
            setValue("0");

        resetValue = false;
    }

    private void resetValues() {
        baseValue = 0;
        secondValue = 0;
        resetValue = false;
        lastKey = "";
        lastOperation = "";
        displayedValue = "";
        setValue("0");
    }

    public void setValue(String value) {
        callback.setValue(value);
        displayedValue = value;
    }

    public void setLastKey(String lastKey) {
        this.lastKey = lastKey;
    }

    public void addDigit(int number) {
        final String currentValue = getDisplayedNumber();
        final String newValue = removeLeadingZero(currentValue + number);
        setValue(newValue);
    }

    private String removeLeadingZero(String str) {
        final double doubleValue = Double.parseDouble(str);
        return Formatter.doubleToString(doubleValue);
    }

    private void updateResult(double value) {
        setValue(Formatter.doubleToString(value));
        baseValue = value;
    }

    public String getDisplayedNumber() {
        return displayedValue;
    }

    public double getDisplayedNumberAsDouble() {
        return Double.parseDouble(getDisplayedNumber());
    }

    public void handleResult() {
        secondValue = getDisplayedNumberAsDouble();
        calculateResult();
        baseValue = getDisplayedNumberAsDouble();
    }

    public void calculateResult() {
        switch (lastOperation) {
            case Constants.PLUS:
                updateResult(baseValue + secondValue);
                break;
            case Constants.MINUS:
                updateResult(baseValue - secondValue);
                break;
            case Constants.MULTIPLY:
                updateResult(baseValue * secondValue);
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
                updateResult(Math.sqrt(baseValue));
                break;
            default:
                break;
        }
    }

    private void divideNumbers() {
        double resultValue = 0;
        if (secondValue != 0)
            resultValue = baseValue / secondValue;

        updateResult(resultValue);
    }

    private void moduloNumbers() {
        double resultValue = 0;
        if (secondValue != 0)
            resultValue = baseValue % secondValue;

        updateResult(resultValue);
    }

    private void powerNumbers() {
        double resultValue = Math.pow(baseValue, secondValue);
        if (Double.isInfinite(resultValue) || Double.isNaN(resultValue))
            resultValue = 0;
        updateResult(resultValue);
    }

    public void handleOperation(String operation) {
        if (lastKey.equals(Constants.DIGIT))
            handleResult();

        resetValue = true;
        lastKey = operation;
        lastOperation = operation;

        if (operation.equals(Constants.ROOT))
            calculateResult();
    }

    public void handleClear() {
        final String oldValue = getDisplayedNumber();
        String newValue;
        final int len = oldValue.length();
        int minLen = 1;
        if (oldValue.contains("-"))
            minLen++;

        if (len > minLen)
            newValue = oldValue.substring(0, len - 1);
        else
            newValue = "0";

        if (newValue.equals("-0"))
            newValue = "0";

        setValue(newValue);
        baseValue = Double.parseDouble(newValue);
    }

    public void handleReset() {
        resetValues();
    }

    public void handleEquals() {
        if (lastKey.equals(Constants.EQUALS))
            calculateResult();

        if (!lastKey.equals(Constants.DIGIT))
            return;

        secondValue = getDisplayedNumberAsDouble();
        calculateResult();
        lastKey = Constants.EQUALS;
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
            value += "0";
        setValue(value);
    }

    public void numpadClicked(int id) {
        if (lastKey.equals(Constants.EQUALS))
            lastOperation = Constants.EQUALS;
        lastKey = Constants.DIGIT;
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
