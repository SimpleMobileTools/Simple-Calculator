package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.*
import com.simplemobiletools.calculator.operation.base.Operation

object OperationFactory {

    fun forId(id: String, baseValue: Double, secondValue: Double): Operation? {
        when (id) {
            PLUS -> return PlusOperation(baseValue, secondValue)
            MINUS -> return MinusOperation(baseValue, secondValue)
            DIVIDE -> return DivideOperation(baseValue, secondValue)
            MULTIPLY -> return MultiplyOperation(baseValue, secondValue)
            MODULO -> return ModuloOperation(baseValue, secondValue)
            POWER -> return PowerOperation(baseValue, secondValue)
            ROOT -> return RootOperation(baseValue)
            else -> return null
        }
    }
}
