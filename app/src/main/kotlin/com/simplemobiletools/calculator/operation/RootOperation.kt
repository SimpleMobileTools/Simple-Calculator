package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation
import java.math.BigDecimal
import kotlin.math.sqrt

class RootOperation(baseValue: BigDecimal, secondValue: BigDecimal) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult() = BigDecimal(sqrt(secondValue.toDouble()).times(baseValue.toDouble()))
}
