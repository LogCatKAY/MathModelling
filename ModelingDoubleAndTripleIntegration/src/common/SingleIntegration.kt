package common

import interfaces.FunctionSingle


/**
 * Интеграл вида: { S[a,b] function }
 *
 * function - подынтегральная функция
 * a, b - границы прямоугольной области интегрирования
 * nx - количество частичных отрезков по x
 * */
fun rectangularSingle(
    function: FunctionSingle,
    a: Double,
    b: Double,
    n: Int
) : Double {
    val h: Double = (b - a)/n
    var result: Double = function.getFunction(a+0.5*h)
    for (i in 1 until n) {
        result += function.getFunction(a + 0.5*h + i*h)
    }
    result *= h
    return result
}

fun simpsonSingle(
    function: FunctionSingle,
    a: Double,
    b: Double,
    n: Int
) : Double {
    var sum = 0.0
    val N = 2 * n
    val h = (b - a) / N
    var k = 1
    while (k <= N - 1 ) {
        sum += (function.getFunction(a + h * (k-1)) +
                4 * function.getFunction(a + h * k ) +
                function.getFunction(a + h * (k + 1)))
        k += 2
    }

    return h / 3 * sum
}

fun simpsonSingleKotes(
    function: FunctionSingle,
    a: Double,
    b: Double,
    n: Int
) : Double {
    var sum = 0.0
    val N = 2 * n
    val h = (b - a) / N
    var sum2 = 0.0
    var sum4 = 0.0

    for (i in 1 until n) {
        sum2 += function.getFunction(a + h * 2 * i)
    }
    for (i in 1..n) {
        sum4 += function.getFunction(a + h * (2 * i - 1))
    }
    sum += function.getFunction(a) + 4 * sum4 + 2 * sum2 + function.getFunction(b)

    return h / 3 * sum
}