package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation
import java.math.BigDecimal

class FactorialOperation(value: BigDecimal) : UnaryOperation(value), Operation {

    override fun getResult(): BigDecimal {
        return if (value.compareTo(BigDecimal.ZERO) == 0 || value.compareTo(BigDecimal.ONE) == 0 ){
            BigDecimal.ONE
        } else{
            var result = BigDecimal.ONE
            val base = value.toInt()
            for(i in 1..base){
                result = result.multiply(BigDecimal(i))
            }
            result
        }
    }
}
