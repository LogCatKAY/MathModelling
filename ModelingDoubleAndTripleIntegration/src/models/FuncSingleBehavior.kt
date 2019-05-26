package models

import interfaces.FunctionSingle
import kotlin.math.pow

class FuncSingleBehavior : FunctionSingle {
    override fun getFunction(x: Double): Double {
        return Math.exp(-x.pow(2))
    }

    override fun getStringFunction() : String {
        return "S { exp(-x^2) } dx"
    }
}