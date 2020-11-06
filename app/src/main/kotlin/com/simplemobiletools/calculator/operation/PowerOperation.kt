package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation
import kotlin.math.pow

class PowerOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): Double {
        val result = baseValue.pow(secondValue)

        return if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result))
            0.0
        else {
            result
        }
    }
}
