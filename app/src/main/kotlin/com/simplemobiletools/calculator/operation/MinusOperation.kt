package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation
import java.math.BigDecimal

class MinusOperation(baseValue: BigDecimal, secondValue: BigDecimal) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): BigDecimal = baseValue.subtract(secondValue)
}
