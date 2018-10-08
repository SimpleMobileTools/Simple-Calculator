package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation

class FactorialOperation(value: Double) : UnaryOperation(value), Operation {

    override fun getResult(): Double{
        var result = 1.0
        if (value==0.0 || value==1.0){
            return result
        }else{
            var base = value.toInt()
            for(i in 1..base){
                result *= i
            }
        }
        return result
    }
}
