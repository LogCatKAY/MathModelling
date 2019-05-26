import common.*
import models.FuncDoubleBhavior
import models.FuncSingleBehavior
import models.FuncTripleBehavior

class Work {
    private val functionSingle = FuncSingleBehavior()
    private val functionDouble = FuncDoubleBhavior()
    private val functionTriple = FuncTripleBehavior()

    fun doSingleIntegrationWork() {
        println("-------Single Integration Start-------")
        val result_1 = rectangularSingle(functionSingle, 0.0, 2.0, 4)
        println("Result of rectangularSingle for |${functionSingle.getStringFunction()}| is $result_1")

        val result_2 = simpsonSingle(functionSingle, 0.0, 2.0, 4)
        println("Result of simpsonSingle for |${functionSingle.getStringFunction()}| is $result_2")

        val result_3 = simpsonSingleKotes(functionSingle, 0.0, 2.0, 4)
        println("Result of simpsonSingleKotes for |${functionSingle.getStringFunction()}| is $result_3")

        println("-------Single Integration End---------")
        println()
    }

    fun doDoubleIntegrationWork() {
        println("-------Double Integration Start-------")

        val result_1 = rectangularDouble(functionDouble, 0.0, 2.0, 2.0, 3.0, 4, 4)
        println("Result of rectangularDouble for |${functionDouble.getStringFunction()}| is $result_1")

        val result_2 = simpsonDouble(functionDouble, 0.0, 2.0, 2.0, 3.0, 4)
        println("Result of simpsonDouble for |${functionDouble.getStringFunction()}| is $result_2")

        println("-------Double Integration End---------")
        println()
    }

    fun doTripleIntegrationWork() {
        println("-------Triple Integration Start-------")
        val result_1 = rectangularTriple(functionTriple, 0.0, 2.0, 2.0, 3.0,-1.0,2.0, 4, 4, 4)
//        val result_1 = rectangularTriple(functionTriple, -1.0, 1.0, 0.0, 1.0,0.0,2.0, 4, 4, 4)
        println("Result of rectangularTriple for |${functionTriple.getStringFunction()}| is $result_1")

        val result_2 = simpsonTriple(functionTriple,0.0, 2.0, 2.0, 3.0,-1.0,2.0, 4)
//        val result_2 = simpsonTriple(functionTriple,-1.0, 1.0, 0.0, 1.0,0.0,2.0, 4)
        println("Result of simpsonTriple for |${functionTriple.getStringFunction()}| is $result_2")
        println("-------Triple Integration End---------")
        println()
    }
}