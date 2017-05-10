package com.simplemobiletools.calculator.operation;

import com.simplemobiletools.calculator.operation.base.BinaryOperation;
import com.simplemobiletools.calculator.operation.base.Operation;

public class PlusOperation extends BinaryOperation implements Operation {

    protected PlusOperation(double baseValue, double secondValue) {
        super(baseValue, secondValue);
    }

    @Override
    public double getResult() {
        return baseValue + secondValue;
    }
}
