package com.simplemobiletools.calculator.operation
/**
 * Created by Ahmed Ashraf Hamza with Ahmad Osama on 2018-12-18.
 */
import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation

class SinOperation(value: Double) : UnaryOperation(value), Operation {
    override fun getResult() = Math.sin(Math.toRadians(value))
}
