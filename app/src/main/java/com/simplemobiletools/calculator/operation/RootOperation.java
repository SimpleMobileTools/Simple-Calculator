package com.simplemobiletools.calculator.operation;

import com.simplemobiletools.calculator.operation.base.Operation;
import com.simplemobiletools.calculator.operation.base.UnaryOperation;

public class RootOperation extends UnaryOperation implements Operation {

    protected RootOperation(double value) {
        super(value);
    }

    @Override
    public double getResult() {
        return Math.sqrt(value);
    }
}
