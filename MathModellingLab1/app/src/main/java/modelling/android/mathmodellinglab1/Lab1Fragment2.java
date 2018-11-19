package modelling.android.mathmodellinglab1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lab1Fragment2 extends Fragment {

    private Button mCalculateRootBtn;
    private EditText mEditPolynomGrade;
    private TextView mTextIntResult;
    private TextView mTextCalcResult;

    private int mPolynomialGrade;

    private FunctionFromLnXMinusOne mFunction;

    public static Lab1Fragment2 newInstance() {
        return new Lab1Fragment2();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFunction = new FunctionFromLnXMinusOne();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lab1_2, container, false);

        mCalculateRootBtn = (Button) v.findViewById(R.id.btn_calculate_root);
        mEditPolynomGrade = (EditText) v.findViewById(R.id.et_n_grade_2);
        mTextIntResult = (TextView) v.findViewById(R.id.tv_int_result_root);
        mTextCalcResult = (TextView) v.findViewById(R.id.tv_calculated_result_root);

        mCalculateRootBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEditPolynomGrade.getText())) {
                    Toast.makeText(getActivity(),
                            "Заполните все поля!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    calculateRoot();
                }

            }
        });



        return v;
    }

    private void calculateRoot() {
        mPolynomialGrade = Integer.parseInt(mEditPolynomGrade.getText().toString());

        double result = Polynomial.calculateViaNewton(0, mPolynomialGrade,
                mFunction.getMasY(), mFunction.getMasX());

        mTextIntResult.setText(String.valueOf(result));

        double calcResult = Math.E;
        BigDecimal formatedResult = new BigDecimal(calcResult);
        String resultStr = formatedResult.setScale(3, BigDecimal.ROUND_HALF_UP).toString();

        mTextCalcResult.setText(resultStr);
    }

}
