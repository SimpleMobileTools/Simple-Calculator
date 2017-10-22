package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class DivideOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): Double {
        var result = 0.0
        if (secondValue != 0.0) {
            result = baseValue / secondValue
        }
        return result
    }
}
