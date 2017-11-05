package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class MultiplyOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult() = baseValue * secondValue
}
