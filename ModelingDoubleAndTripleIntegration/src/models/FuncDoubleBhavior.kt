package models

import interfaces.FunctionDouble

class FuncDoubleBhavior : FunctionDouble {
    override fun getFunction(x: Double, y: Double): Double {
        return 2 * x + y
    }

    override fun getStringFunction() : String {
        return "S S { 2*x + y } dx dy"
    }
}