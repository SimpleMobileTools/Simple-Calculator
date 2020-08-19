package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation
import java.math.BigDecimal
import java.math.MathContext.DECIMAL128

class DivideOperation(baseValue: BigDecimal, secondValue: BigDecimal) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): BigDecimal {
        return if (secondValue.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal.ZERO
        } else {
            baseValue.divide(secondValue, DECIMAL128)
        }
    }
}
