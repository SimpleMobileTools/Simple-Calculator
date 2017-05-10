package com.simplemobiletools.calculator.operation;

import com.simplemobiletools.calculator.operation.base.BinaryOperation;
import com.simplemobiletools.calculator.operation.base.Operation;

public class DivideOperation extends BinaryOperation implements Operation {

    public DivideOperation(double baseValue, double secondValue) {
        super(baseValue, secondValue);
    }

    @Override
    public double getResult() {
        double result = 0;
        if (secondValue != 0) {
            result = baseValue / secondValue;
        }
        return result;
    }
}
