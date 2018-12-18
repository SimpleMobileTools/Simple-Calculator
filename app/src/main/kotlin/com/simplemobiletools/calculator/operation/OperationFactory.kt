package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.calculator.operation.base.Operation

object OperationFactory {

    fun forId(id: String, baseValue: Double, secondValue: Double): Operation? {
        return when (id) {
            PLUS -> PlusOperation(baseValue, secondValue)
            MINUS -> MinusOperation(baseValue, secondValue)
            DIVIDE -> DivideOperation(baseValue, secondValue)
            MULTIPLY -> MultiplyOperation(baseValue, secondValue)
            PERCENT -> PercentOperation(baseValue, secondValue)
            POWER -> PowerOperation(baseValue, secondValue)
            ROOT -> RootOperation(baseValue)
            FACTORIAL -> FactorialOperation(baseValue)
            Sin->SinOperation(baseValue)
            Tan->TanOperation(baseValue)
            Cos->CosOperation(baseValue)
            Arcsin->ArcsinOperation(baseValue)
            Arccos->ArccosOperation(baseValue)
            Arctan->ArctanOperation(baseValue)
            else -> null
        }
    }
}
