package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.DIVIDE
import com.simplemobiletools.calculator.helpers.MINUS
import com.simplemobiletools.calculator.helpers.MULTIPLY
import com.simplemobiletools.calculator.helpers.PLUS
import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class PercentOperation(baseValue: Double, secondValue: Double, val sign: String) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): Double {
        return when (sign) {
            MULTIPLY -> {
                val partial = 100 / secondValue
                baseValue / partial
            }
            DIVIDE -> {
                val partial = 100 / secondValue
                baseValue * partial
            }
            PLUS -> {
                val partial = baseValue / (100 / secondValue)
                baseValue.plus(partial)
            }
            MINUS -> {
                val partial = baseValue / (100 / secondValue)
                baseValue.minus(partial)
            }
            else -> {
                baseValue / (100 * secondValue)
            }
        }
    }
}
