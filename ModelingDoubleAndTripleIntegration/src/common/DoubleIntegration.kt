package common

import interfaces.FunctionDouble

/**
 * Интеграл вида: { S[c,d] S[a,b] function }
 *
 * function - подынтегральная функция
 * a, b, c, d - границы прямоугольной области интегрирования
 * nx, ny - количество частичных отрезков по x и y соответственно
 * */
fun rectangularDouble(
    function: FunctionDouble,
    a: Double,
    b: Double,
    c: Double,
    d: Double,
    nx: Int,
    ny: Int
): Double {
    val hx: Double = (b - a)/nx
    val hy: Double = (d - c)/ny
    var I: Double = 0.0

    for (i in 0 until nx) {
        for (j in 0 until ny) {
            val xi = a + hx/2 + i*hx
            val yj = c + hy/2 + j*hy
            I += hx*hy*function.getFunction(xi,yj)
        }
    }
    return I
}


fun simpsonDoubleEps(
    function: FunctionDouble,
    a: Double,
    b: Double,
    c: Double,
    d: Double,
    n: Int,
    eps: Double = 0.0001
): Double {
    var N = n               //новая переменная, чтобы можно было уменьшать шаг в 2 раза на каждом цикле, повышая точность
    var I: Double = 0.0     //старое значение интеграла
    var I1: Double = 0.0    //новое значение интеграла, чтобы сравнить точность и выйти из цикла
    var sum = 0.0           //значение интеграла после всех сложений

    do {
        val h1=(b-a)/(2*N)
        val h2=(d-c)/(2*N)

        for (i in 0 until N){
            for (j in 0 until N) {
                sum += (function.getFunction(a+2*i*h1,c+2*j*h2)+function.getFunction(a+2*(i+1)*h1,c+2*j*h2)+function.getFunction(a+2*i*h1,c+2*(j+1)*h2)+function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2))
                sum += (4*(function.getFunction(a+(2*i+1)*h1,c+2*j*h2)+function.getFunction(a+2*i*h1,c+(2*j+1)*h2)+function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2)+function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2))+16*function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2))
            }
        }
        I = I1
        sum *= h1 * h2 / 9
        I1 = sum
        N *= 2              //для более мелкого шага
    }while(Math.abs(I1-I) >= eps)
    return I1
}

fun simpsonDouble(
    function: FunctionDouble,
    a: Double,
    b: Double,
    c: Double,
    d: Double,
    n: Int
): Double {
    var sum = 0.0           //значение интеграла после всех сложений

    if((n%2 != 0) && n > 0) {
        throw IllegalArgumentException("Argument {n} must be even and more than 0")
    }

    val h1=(b-a)/(2*n)
    val h2=(d-c)/(2*n)

    for (i in 0 until n){
        for (j in 0 until n) {

            sum += ((function.getFunction(a+2*i*h1,c+2*j*h2) +
                    function.getFunction(a+2*(i+1)*h1,c+2*j*h2) +
                    function.getFunction(a+2*i*h1,c+2*(j+1)*h2) +
                    function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2)) +
                    4 * (function.getFunction(a+(2*i+1)*h1,c+2*j*h2) +
                    function.getFunction(a+2*i*h1,c+(2*j+1)*h2) +
                    function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2) +
                    function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2)) +
                    16 * function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2))
        }
    }
    sum *= h1 * h2 / 9

    return sum
}