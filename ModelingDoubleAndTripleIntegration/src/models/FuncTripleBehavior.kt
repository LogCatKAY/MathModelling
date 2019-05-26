package models

import interfaces.FunctionTriple

class FuncTripleBehavior : FunctionTriple {
    override fun getFunction(x: Double, y: Double, z: Double): Double {
        return 2 * x + y - 4 * z
//        return x + y - z
    }

    override fun getStringFunction() : String {
        return "S S S { 2*x + y - 4*z } dx dy dz"
//        return "S S S { x + y - z } dx dy dz"
    }
}