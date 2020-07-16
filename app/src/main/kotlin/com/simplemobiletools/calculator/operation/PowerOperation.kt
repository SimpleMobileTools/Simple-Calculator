package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation
import java.math.BigDecimal
import kotlin.math.pow

class PowerOperation(baseValue: BigDecimal, secondValue: BigDecimal) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): BigDecimal {
        val result = baseValue.toDouble().pow(secondValue.toDouble())

        return if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result))
            BigDecimal.ZERO
        else {
            BigDecimal(result)
        }
    }
}
