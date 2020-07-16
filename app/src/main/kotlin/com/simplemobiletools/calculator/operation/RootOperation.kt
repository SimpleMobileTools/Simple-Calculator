package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation
import java.math.BigDecimal
import kotlin.math.sqrt

class RootOperation(value: BigDecimal) : UnaryOperation(value), Operation {

    override fun getResult() = BigDecimal(sqrt(value.toDouble()))
}
