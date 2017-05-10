package com.simplemobiletools.calculator.operation;

import android.support.annotation.Nullable;

import com.simplemobiletools.calculator.Constants;
import com.simplemobiletools.calculator.operation.base.Operation;

public class OperationFactory {

    @Nullable
    public static Operation forId(String id, double baseValue, double secondValue) {
        switch (id) {
            case Constants.PLUS:
                return new PlusOperation(baseValue, secondValue);
            case Constants.MINUS:
                return new MinusOperation(baseValue, secondValue);
            case Constants.DIVIDE:
                return new DivideOperation(baseValue, secondValue);
            case Constants.MULTIPLY:
                return new MultiplyOperation(baseValue, secondValue);
            case Constants.MODULO:
                return new ModuloOperation(baseValue, secondValue);
            case Constants.POWER:
                return new PowerOperation(baseValue, secondValue);
            case Constants.ROOT:
                return new RootOperation(baseValue);
            default:
                return null;
        }
    }
}
