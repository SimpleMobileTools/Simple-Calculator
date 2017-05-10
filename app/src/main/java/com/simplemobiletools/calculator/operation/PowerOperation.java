package com.simplemobiletools.calculator.operation;

import com.simplemobiletools.calculator.operation.base.BinaryOperation;
import com.simplemobiletools.calculator.operation.base.Operation;
import com.simplemobiletools.calculator.operation.base.UnaryOperation;

public class PowerOperation extends BinaryOperation implements Operation {

    protected PowerOperation(double baseValue, double secondValue) {
        super(baseValue, secondValue);
    }

    @Override
    public double getResult() {
        double result = Math.pow(baseValue, secondValue);
        if (Double.isInfinite(result) || Double.isNaN(result))
            result = 0;
        return result;
    }
}
