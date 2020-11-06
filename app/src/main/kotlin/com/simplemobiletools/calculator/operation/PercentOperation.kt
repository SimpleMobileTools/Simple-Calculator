package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.DIVIDE
import com.simplemobiletools.calculator.helpers.MINUS
import com.simplemobiletools.calculator.helpers.MULTIPLY
import com.simplemobiletools.calculator.helpers.PLUS
import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation
import java.math.BigDecimal
import java.math.MathContext

class PercentOperation(baseValue: BigDecimal, secondValue: BigDecimal, val sign: String) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): BigDecimal {
        return when {
            secondValue.compareTo(BigDecimal.ZERO) == 0 -> BigDecimal.ZERO
            sign == MULTIPLY -> {
                val partial = BigDecimal(100).divide(secondValue, MathContext.DECIMAL32)
                baseValue.divide(partial, MathContext.DECIMAL32)
            }
            sign == DIVIDE -> {
                val partial = BigDecimal(100).divide(secondValue, MathContext.DECIMAL32)
                baseValue.multiply(partial, MathContext.DECIMAL32)
            }
            sign == PLUS -> {
                val partial = baseValue.divide(100.toBigDecimal().divide(secondValue, MathContext.DECIMAL32), MathContext.DECIMAL32)
                baseValue.plus(partial)
            }
            sign == MINUS -> {
                val partial = baseValue.divide(100.toBigDecimal().divide(secondValue, MathContext.DECIMAL32), MathContext.DECIMAL32)
                baseValue.minus(partial)
            }
            else -> {
                baseValue.divide(BigDecimal(100)).multiply(secondValue)
            }
        }
    }
}
