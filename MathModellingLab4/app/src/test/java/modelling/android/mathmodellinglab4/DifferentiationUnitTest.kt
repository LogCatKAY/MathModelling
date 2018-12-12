package modelling.android.mathmodellinglab4

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DifferentiationUnitTest {

    private val arrY_fun1 = ArrayList<Double>(10)
    private val arrY_fun2 = ArrayList<Double>(10)
    private val dif : Differentiation = Differentiation()

    @Before
    fun setUp() {
        for (x in dif.arrX)
            arrY_fun1.add(dif.getFunction_1(x))

        for (x in dif.arrX)
            arrY_fun2.add(dif.getFunction_2(x, 3.0, 5.0, 7.0))
    }

    @Test
    fun arrY_isCorrect() {
        assertEquals(2.718 ,arrY_fun1[0], 0.001)
        assertEquals(148.413, arrY_fun1[4], 0.001)
        assertEquals(22026.465, arrY_fun1.last(), 0.001)
    }

    @Test
    fun arrY2_isCorrect() {
        assertEquals(0.250 ,arrY_fun2[0], 0.001)
        assertEquals(0.375, arrY_fun2[4], 0.001)
        assertEquals(0.400, arrY_fun2.last(), 0.001)
    }

    @Test
    fun getColumn3_isCorrect() {
        val column3 = dif.getColumn_3(dif.arrX, arrY_fun1)
        assertEquals(4.670, column3.first(), 0.001)
        assertEquals(255.015, column3[4], 0.001)
        assertEquals(13923.381, column3.last(), 0.001)
    }

    @Test
    fun getColumn4_isCorrect() {
        val column4 = dif.getColumn_4(dif.arrX, arrY_fun1)
        assertEquals(8.684, column4.first(), 0.001)
        assertEquals(474.110, column4[4], 0.001)
        assertEquals(9522.754, column4.last(), 0.001)
    }

    @Test
    fun getColumn5_isCorrect() {
        val column5 = dif.getColumn_5(dif.arrX, arrY_fun1)
        assertEquals(8.026, column5.first(), 0.001)
        assertEquals(438.189, column5[4], 0.001)
        assertEquals(8801.256, column5.last(), 0.001)
    }

    @Test
    fun getColumn6_isCorrect() {
        val column6 = dif.getColumn_6(dif.arrX, arrY_fun1)
        assertEquals(0.658, column6.first(), 0.001)
    }

    @Test
    fun getColumn7_isCorrect() {
        val column7_1 = dif.getColumn_7(dif.arrX, arrY_fun1)
        assertEquals(18324.010, column7_1.first(), 0.001)

        val column7_2 = dif.getColumn_7(dif.arrX, arrY_fun2)
        assertEquals(0.003, column7_2.first(), 0.001)
    }

    @Test
    fun getRungeValuesforFunction1_isCorrect() {
        val arrY1 = dif.getColumn_3(dif.arrX, arrY_fun1)
        val rungeValues = dif.getRungeValues_function1(arrY1, 1.0)
        assertEquals(2.383, rungeValues.first(), 0.001)
        assertEquals(130.099, rungeValues[4], 0.001)
        assertEquals(7103.190, rungeValues.last(), 0.001)
    }

    @Test
    fun getFormattedDoubleValues_isCorrect() {
        val array = arrayListOf<Double>(
            0.3333333, 0.5555555, 0.7777777
        )

        val result = Differentiation.getFormattedDoubleValues(array)

        assertEquals(0.333, result.first(), 0.0)
        assertEquals(0.556, result[1], 0.0)
        assertEquals(0.778, result.last(), 0.0)
    }

    @Test
    fun getRungeValuesforFunction2_isCorrect() {
        val arrY2 = dif.getColumn_3(dif.arrX, arrY_fun2)
        val rungeValues = dif.getRungeValues_function2(arrY2, 3.0, 5.0, 7.0, 1.0)
        assertEquals(0.096, rungeValues.first(), 0.001)
        assertEquals(0.009, rungeValues[4], 0.001)
        assertEquals(0.003, rungeValues.last(), 0.001)
    }

    @Test
    fun getAlignmentForFunction1_isCorrect() {
        val alignResult = dif.getAlignment_function1(dif.arrX, arrY_fun1)
        assertEquals(2.718, alignResult.first(), 0.001)
        assertEquals(148.413, alignResult[4], 0.001)
        assertEquals(8103.084, alignResult.last(), 0.001)
    }

    @Test
    fun getAlignmentForFunction2_isCorrect() {
        val alignResult = dif.getAlignment_function2(dif.arrX, arrY_fun2)
        assertEquals(0.104, alignResult.first(), 0.001)
        assertEquals(0.009, alignResult[4], 0.001)
        assertEquals(0.003, alignResult.last(), 0.001)
    }

}