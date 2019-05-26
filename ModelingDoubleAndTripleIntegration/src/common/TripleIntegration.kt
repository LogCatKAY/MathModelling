package common

import interfaces.FunctionTriple

/**
 * Интеграл вида: { S[e,f] S[c,d] S[a,b] function }
 *
 * function - подынтегральная функция
 * a, b, c, d, e, f - границы прямоугольной области интегрирования
 * nx, ny, nz - количество частичных отрезков по x, y и z соответственно
 * */
fun rectangularTriple(
    function: FunctionTriple,
    a: Double,
    b: Double,
    c: Double,
    d: Double,
    e: Double,
    f: Double,
    nx: Int,
    ny: Int,
    nz: Int
): Double {
    val hx: Double = (b - a)/nx
    val hy: Double = (d - c)/ny
    val hz: Double = (f - e)/nz
    var I: Double = 0.0

    for (i in 0 until nx) {
        for (j in 0 until ny) {
            for (k in 0 until nz) {
                val xi = a + hx/2 + i*hx
                val yj = c + hy/2 + j*hy
                val zk = e + hz/2 + k*hz
                I += hx*hy*hz*function.getFunction(xi,yj,zk)
            }
        }
    }
    return I
}

fun simpsonTriple(
    function: FunctionTriple,
    a: Double,
    b: Double,
    c: Double,
    d: Double,
    e: Double,
    f: Double,
    n: Int,
    eps: Double
): Double {

    if((n%2 != 0) && n > 0) {
        throw IllegalArgumentException("Argument {n} must be even and more than 0")
    }

    var N = n               //новая переменная, чтобы можно было уменьшать шаг в 2 раза на каждом цикле, повышая точность
    var I: Double = 0.0     //старое значение интеграла
    var I1: Double = 0.0    //новое значение интеграла, чтобы сравнить точность и выйти из цикла
    var sum = 0.0           //значение интеграла после всех сложений

    do {
        val h1=(b-a)/(2*N)
        val h2=(d-c)/(2*N)
        val h3=(f-e)/(2*N)

        for (i in 0 until N){
            for (j in 0 until N)
                for (k in 0 until N) {

                    sum += ((function.getFunction(a+2*i*h1,c+2*j*h2, e+2*k*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+2*j*h2, e+2*k*h3) +
                            function.getFunction(a+2*i*h1,c+2*(j+1)*h2, e+2*k*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2, e+2*k*h3) +
                            function.getFunction(a+2*i*h1,c+2*j*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+2*j*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+2*i*h1,c+2*(j+1)*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2, e+2*(k+1)*h3)) +
                            4 * (function.getFunction(a+(2*i+1)*h1,c+2*j*h2, e+2*k*h3) +
                            function.getFunction(a+2*i*h1,c+(2*j+1)*h2, e+2*k*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2, e+2*k*h3) +
                            function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2, e+2*k*h3) +
                            function.getFunction(a+2*i*h1,c+2*j*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+2*j*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+2*i*h1,c+2*(j+1)*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+(2*i+1)*h1,c+2*j*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+2*i*h1,c+(2*j+1)*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2, e+2*(k+1)*h3)) +
                            16 * (function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2, e+2*k*h3) +
                            function.getFunction(a+(2*i+1)*h1,c+2*j*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+2*i*h1,c+(2*j+1)*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2, e+(2*k+1)*h3) +
                            function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2, e+2*(k+1)*h3) +
                            function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2, e+(2*k+1)*h3)) +
                            64 * function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2, e+(2*k+1)*h3))
            }
        }
        I = I1
        sum *= h1 * h2 * h3 / 27
        I1 = sum
        N *= 2              //для более мелкого шага
    }while(Math.abs(I1-I) >= eps)
    return I1
}

fun simpsonTriple(
    function: FunctionTriple,
    a: Double,
    b: Double,
    c: Double,
    d: Double,
    e: Double,
    f: Double,
    n: Int
) : Double {

    if((n%2 != 0) && n > 0) {
        throw IllegalArgumentException("Argument {n} must be even and more than 0")
    }

    var sum = 0.0           //значение интеграла после всех сложений
    val h1=(b-a)/(2*n)
    val h2=(d-c)/(2*n)
    val h3=(f-e)/(2*n)

    for (i in 0 until n){
        for (j in 0 until n)
            for (k in 0 until n) {

                sum += ((function.getFunction(a+2*i*h1,c+2*j*h2, e+2*k*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+2*j*h2, e+2*k*h3) +
                        function.getFunction(a+2*i*h1,c+2*(j+1)*h2, e+2*k*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2, e+2*k*h3) +
                        function.getFunction(a+2*i*h1,c+2*j*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+2*j*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+2*i*h1,c+2*(j+1)*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2, e+2*(k+1)*h3)) +
                        4 * (function.getFunction(a+(2*i+1)*h1,c+2*j*h2, e+2*k*h3) +
                        function.getFunction(a+2*i*h1,c+(2*j+1)*h2, e+2*k*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2, e+2*k*h3) +
                        function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2, e+2*k*h3) +
                        function.getFunction(a+2*i*h1,c+2*j*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+2*j*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+2*i*h1,c+2*(j+1)*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+2*(j+1)*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+(2*i+1)*h1,c+2*j*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+2*i*h1,c+(2*j+1)*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2, e+2*(k+1)*h3)) +
                        16 * (function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2, e+2*k*h3) +
                        function.getFunction(a+(2*i+1)*h1,c+2*j*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+2*i*h1,c+(2*j+1)*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+2*(i+1)*h1,c+(2*j+1)*h2, e+(2*k+1)*h3) +
                        function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2, e+2*(k+1)*h3) +
                        function.getFunction(a+(2*i+1)*h1,c+2*(j+1)*h2, e+(2*k+1)*h3)) +
                        64 * function.getFunction(a+(2*i+1)*h1,c+(2*j+1)*h2, e+(2*k+1)*h3))
            }
    }
    sum *= h1 * h2 * h3 / 27

    return sum
}