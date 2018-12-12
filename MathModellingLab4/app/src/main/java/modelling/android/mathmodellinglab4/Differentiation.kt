package modelling.android.mathmodellinglab4

class Differentiation {

    val arrX = arrayListOf<Double>(
        1.0, 2.0, 3.0, 4.0, 5.0,
        6.0, 7.0, 8.0, 9.0, 10.0)

    companion object {
        fun getFormattedDoubleValues(array: List<Double>) : List<Double> {
            val newList = arrayListOf<Double>()

            for(i in 0 until array.size) {
                newList.add(Math.round(array[i] * 1000.0) / 1000.0)
            }

            return newList
        }
    }


    fun getFunction_1(x : Double)
            : Double {
        return Math.exp(x)
    }

    fun getFunction_2(x : Double, a0 : Double, a1 : Double, a2 : Double)
            : Double {
        return (a0*x)/(a1+a2*x)
    }

    /**
     * Значения односторонних разностей у1'
     * для x с 1 по 9
     * */
    fun getColumn_3(arrX: List<Double>, arrY: List<Double>): List<Double> {
        val result = arrayListOf<Double>()


        for (i in 0 until arrX.size - 1) {
            result.add((arrY[i + 1] - arrY[i]) / (arrX[i + 1] - arrX[i]))
        }

        return result
    }

    /**
     * Значения центральных разностей у2'
     * для x со 2 по 9
     * */
    fun getColumn_4(arrX: List<Double>, arrY: List<Double>): List<Double> {
        val result = arrayListOf<Double>()


        for (i in 1 until arrX.size - 1) {
            result.add((arrY[i + 1] - arrY[i - 1]) / (2 * (arrX[i + 1] - arrX[i])))
        }

        return result
    }

    /**
     * Вторая производная y''
     * для x со 2 по 9
     * */
    fun getColumn_5(arrX: List<Double>, arrY: List<Double>): List<Double> {
        val result = arrayListOf<Double>()


        for (i in 1 until arrX.size - 1) {
            result.add((arrY[i + 1] - 2 * arrY[i] + arrY[i - 1]) / (arrX[i + 1] - arrX[i]))
        }

        return result
    }


    /**
     * Значение в крайнем левом узле y0
     * для x == 1
     * */
    fun getColumn_6(arrX: List<Double>, arrY: List<Double>): List<Double> {
        val result = arrayListOf<Double>()

        result.add((4 * arrY[1] - 3 * arrY[0] - arrY[2]) / 2)

        return result
    }

    /**
     * Значение в крайнем првом узле yn
     * для x == 10
     * */
    fun getColumn_7(arrX: List<Double>, arrY: List<Double>): List<Double> {
        val result = arrayListOf<Double>()

        result.add((-4 * arrY[8] + 3 * arrY[9] + arrY[7]) / 2)

        return result
    }

    /**
     * Значения по формулам Рунге для первой (экспонента) функции для x от 1 до 9
     * @param arrY значения односторонних разностей у1'
     * */
    fun getRungeValues_function1(arrY: List<Double>, p: Double): List<Double> {
        val XR = doubleArrayOf(
            1.0, 1.5, 2.0, 2.5, 3.0,
            3.5, 4.0, 4.5, 5.0, 5.5,
            6.0, 6.5, 7.0, 7.5, 8.0,
            8.5, 9.0, 9.5, 10.0, 10.5
        )

        val y1R = DoubleArray(20)

        //значения уR
        for (i in 0..19) {
            y1R[i] = getFunction_1(XR[i])
        }

        //значение разностей для половинного шага
        val y12R = DoubleArray(9)

        var j = 0
        for (i in 0..8) {
            y12R[i] = (y1R[j + 1] - y1R[j]) / 0.5
            j += 2
        }

        //Вторая формула Рунге
        val result = DoubleArray(9)

        for (i in 0..8) {
            result[i] = arrY[i] + (arrY[i] - y12R[i]) / (Math.pow(0.5, p) - 1)
        }

        return result.asList()
    }

    /**
     * Значения по формулам Рунге для второй (линейной) функции для x от 1 до 9
     * @param arrY значения односторонних разностей у1'
     * */
    fun getRungeValues_function2(arrY: List<Double>, a0: Double, a1: Double, a2: Double, p: Double): List<Double> {
        val XR = doubleArrayOf(
            1.0, 1.5, 2.0, 2.5, 3.0,
            3.5, 4.0, 4.5, 5.0, 5.5,
            6.0, 6.5, 7.0, 7.5, 8.0,
            8.5, 9.0, 9.5, 10.0, 10.5
        )

        val y2R = DoubleArray(20)

        //значения уR
        for (i in 0..19) {
            y2R[i] = getFunction_2(XR[i], a0, a1, a2)
        }

        //значение разностей для половинного шага
        val y22R = DoubleArray(9)

        var j = 0
        for (i in 0..8) {
            y22R[i] = (y2R[j + 1] - y2R[j]) / 0.5
            j += 2
        }


        //Вторая формула Рунге
        val result = DoubleArray(9)

        for (i in 0..8) {
            result[i] = arrY[i] + (arrY[i] - y22R[i]) / (Math.pow(0.5, p) - 1)
        }

        return result.asList()
    }

    /**
     * Вырвнивание переменных. Для x от 1 до 9.
     * Для экспоненты новая система координат: X | lnY
     * @param arrX список точек на абсциссе
     * @param arrY список значений функции от arrX, для которых будет выравнивание
     * */
    fun getAlignment_function1(arrX: List<Double>, arrY: List<Double>) : List<Double> {
        val xZ = DoubleArray(10)
        val y1Z = DoubleArray(10)

        for (i in 0..9) {
            xZ[i] = 1.0 / arrX[i]
            y1Z[i] = Math.log(arrY[i])
        }
        //считаем производную в новых координатах
        val y12Z = DoubleArray(9)
        for (i in 0..8) {
            y12Z[i] = (y1Z[i + 1] - y1Z[i]) / (arrX[i + 1] - arrX[i])
        }

        //переходим к обратным координатам
        val result = DoubleArray(9)

        for (i in 0..8) {
            result[i] = y12Z[i] * arrY[i]
        }

        return result.asList()
    }

    /**
     * Вырвнивание переменных. Для x от 1 до 9.
     * Для a0*x/(a1+a2*x) : X | X/Y
     * @param arrX список точек на абсциссе
     * @param arrY список значений функции от arrX, для которых будет выравнивание
     * */
    fun getAlignment_function2(arrX: List<Double>, arrY: List<Double>) : List<Double> {
        val xZ = DoubleArray(10)
        val y2Z = DoubleArray(10)

        for (i in 0..9) {
            xZ[i] = 1.0 / arrX[i]
            y2Z[i] = 1 / arrY[i]
        }
        //считаем производную в новых координатах
        val y22Z = DoubleArray(9)
        for (i in 0..8) {
            y22Z[i] = (y2Z[i + 1] - y2Z[i]) / (xZ[i + 1] - xZ[i])
        }

        //переходим к обратным координатам
        val result = DoubleArray(9)

        for (i in 0..8) {
            result[i] = y22Z[i] * Math.pow(arrY[i], 2.0) / Math.pow(arrX[i], 2.0)
        }

        return result.asList()
    }
}