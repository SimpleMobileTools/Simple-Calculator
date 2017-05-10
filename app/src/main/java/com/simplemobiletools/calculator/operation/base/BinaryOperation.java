package com.simplemobiletools.calculator.operation.base;

public class BinaryOperation {
    protected double baseValue;
    protected double secondValue;

    protected BinaryOperation(double baseValue, double secondValue) {
        this.baseValue = baseValue;
        this.secondValue = secondValue;
    }
}
