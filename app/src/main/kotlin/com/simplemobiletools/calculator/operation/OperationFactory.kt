package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.calculator.operation.base.Operation
import java.math.BigDecimal

object OperationFactory {

    fun forId(id: String, baseValue: BigDecimal, secondValue: BigDecimal): Operation? {
        return when (id) {
            PLUS -> PlusOperation(baseValue, secondValue)
            MINUS -> MinusOperation(baseValue, secondValue)
            DIVIDE -> DivideOperation(baseValue, secondValue)
            MULTIPLY -> MultiplyOperation(baseValue, secondValue)
            PERCENT -> PercentOperation(baseValue, secondValue)
            POWER -> PowerOperation(baseValue, secondValue)
            ROOT -> RootOperation(baseValue, secondValue)
            else -> null
        }
    }
}
