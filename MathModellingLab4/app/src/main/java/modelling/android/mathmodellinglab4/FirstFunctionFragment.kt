package modelling.android.mathmodellinglab4


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class FirstFunctionFragment : Fragment() {

    private lateinit var mTextFunction : TextView
    private lateinit var mColumnYLayout : LinearLayout
    private lateinit var mColumn3Layout : LinearLayout
    private lateinit var mColumn4Layout : LinearLayout
    private lateinit var mColumn5Layout : LinearLayout
    private lateinit var mColumn6Layout : LinearLayout
    private lateinit var mColumn7Layout : LinearLayout
    private lateinit var mColumn8Layout : LinearLayout
    private lateinit var mColumn9Layout : LinearLayout

    private lateinit var mCalculateButton : Button
    private lateinit var mEditRungeValue : EditText

    private val mDiff : Differentiation = Differentiation()

    private lateinit var arrY_fun1 : ArrayList<Double>
    private lateinit var column3 : List<Double>
    private lateinit var column4 : List<Double>
    private lateinit var column5 : List<Double>
    private lateinit var column6 : List<Double>
    private lateinit var column7 : List<Double>
    private lateinit var column8 : List<Double>
    private lateinit var column9 : List<Double>

    private var rungeValue: Double? = null

    companion object {
        fun newInstance() : FirstFunctionFragment {
            return FirstFunctionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_table, container, false)

        mTextFunction = view.findViewById(R.id.tv_which_function) as TextView
        mTextFunction.text = "y=exp(x)"
        mCalculateButton = view.findViewById(R.id.btn_calculate) as Button
        mEditRungeValue = view.findViewById(R.id.et_runge) as EditText

        findColumnLayouts(view)

        mCalculateButton.setOnClickListener {
            rungeValue = mEditRungeValue.text.toString().toDoubleOrNull()

            if (rungeValue == null) {
                activity!!.toast("Не заполнено число Рунге")
                return@setOnClickListener
            } else {
                calculateColumnValues(rungeValue!!)
                initColumns()
            }
        }

        return view
    }

    private fun findColumnLayouts(view: View) {
        mColumnYLayout = view.findViewById(R.id.linear_column_y) as LinearLayout
        mColumn3Layout = view.findViewById(R.id.linear_column_3) as LinearLayout
        mColumn4Layout = view.findViewById(R.id.linear_column_4) as LinearLayout
        mColumn5Layout = view.findViewById(R.id.linear_column_5) as LinearLayout
        mColumn6Layout = view.findViewById(R.id.linear_column_6) as LinearLayout
        mColumn7Layout = view.findViewById(R.id.linear_column_7) as LinearLayout
        mColumn8Layout = view.findViewById(R.id.linear_column_8) as LinearLayout
        mColumn9Layout = view.findViewById(R.id.linear_column_9) as LinearLayout
    }

    //вычисление значений
    private fun calculateColumnValues(rungeValue: Double) {
        arrY_fun1 = ArrayList<Double>(10)

        for (x in mDiff.arrX) {
            arrY_fun1.add(mDiff.getFunction_1(x))
        }

        column3 = mDiff.getColumn_3(mDiff.arrX, arrY_fun1)
        column4 = mDiff.getColumn_4(mDiff.arrX, arrY_fun1)
        column5 = mDiff.getColumn_5(mDiff.arrX, arrY_fun1)
        column6 = mDiff.getColumn_6(mDiff.arrX, arrY_fun1)
        column7 = mDiff.getColumn_7(mDiff.arrX, arrY_fun1)

        val arrY1 = mDiff.getColumn_3(mDiff.arrX, arrY_fun1)
        column8 = mDiff.getRungeValues_function1(arrY1, rungeValue)

        column9 = mDiff.getAlignment_function1(mDiff.arrX, arrY_fun1)
    }

    //заполнение ячеек в колонках заранее вычисленными значениями
    private fun initColumns() {

        //заполнение колонки 2 с 1 по 10 ячейки
        var formattedArray = Differentiation.getFormattedDoubleValues(arrY_fun1)

        for (i in 0 until mColumnYLayout.childCount) {
            val view = mColumnYLayout.getChildAt(i) as TextView
            view.text = formattedArray[i].toString()
        }

        //заполнение колонки 3 с 1 по 9 ячейки
        formattedArray = Differentiation.getFormattedDoubleValues(column3)

        for (i in 0 until mColumn3Layout.childCount - 1) {
            val view = mColumn3Layout.getChildAt(i) as TextView
            view.text = formattedArray[i].toString()
        }

        //заполение колонки 4 со 2 по 9 ячейки
        formattedArray = Differentiation.getFormattedDoubleValues(column4)

        for (i in 1 until mColumn4Layout.childCount - 1) {
            val view = mColumn4Layout.getChildAt(i) as TextView
            view.text = formattedArray[i-1].toString()
        }

        //заполнение колонки 5 со 2 по 9 ячейки
        formattedArray = Differentiation.getFormattedDoubleValues(column5)

        for (i in 1 until mColumn5Layout.childCount - 1) {
            val view = mColumn5Layout.getChildAt(i) as TextView
            view.text = formattedArray[i-1].toString()
        }

        //заполнение колонки 6 в 1 ячейке
        formattedArray = Differentiation.getFormattedDoubleValues(column6)


        var v = mColumn6Layout.getChildAt(0) as TextView
        v.text = formattedArray[0].toString()

        //заполнение колонки 7 в 10 ячейке
        formattedArray = Differentiation.getFormattedDoubleValues(column7)

        v = mColumn7Layout.getChildAt(9) as TextView
        v.text = formattedArray[0].toString()

        //заполнение колонки 8 с 1 по 9 ячейки
        formattedArray = Differentiation.getFormattedDoubleValues(column8)

        for (i in 0 until mColumn8Layout.childCount - 1) {
            val view = mColumn8Layout.getChildAt(i) as TextView
            view.text = formattedArray[i].toString()
        }

        //заполнение колонки 9 с 1 по 9 ячейки
        formattedArray = Differentiation.getFormattedDoubleValues(column9)

        for (i in 0 until mColumn9Layout.childCount - 1) {
            val view = mColumn9Layout.getChildAt(i) as TextView
            view.text = formattedArray[i].toString()
        }
    }


}
