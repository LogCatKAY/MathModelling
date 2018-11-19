package modelling.android.mathmodellinglab1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;


public class Lab1Fragment extends Fragment {

    private Button mCalculateBtn;
    private EditText mEditArgX;
    private EditText mEditPolynomGrade;
    private TextView mTextIntResult;
    private TextView mTextCalculatedResult;

    private FunctionFromSqrtX mFunSqrtX;

    private double mArgX;
    private int mPolynomialGrade;

    public static Lab1Fragment newInstance() {
        return new Lab1Fragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        mFunSqrtX = new FunctionFromSqrtX();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lab1_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_root_of_function:
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, Lab1Fragment2.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
                default:
                    Toast.makeText(getActivity(), "Что-то пошло не так...", Toast.LENGTH_SHORT).show();
                    return super.onOptionsItemSelected(item);
        }
    }

    public void showRootOfFunction(Context context) {
        Toast.makeText(context,
                "Корень функции sqrt(x) на промежутке от -1000 до 1000 с точностью 0.001 это 0",
                Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lab1, container, false);

        mCalculateBtn = (Button) v.findViewById(R.id.btn_calculate);
        mEditArgX = (EditText) v.findViewById(R.id.et_x_arg);
        mEditPolynomGrade = (EditText) v.findViewById(R.id.et_n_grade);
        mTextIntResult = (TextView) v.findViewById(R.id.tv_int_result);
        mTextCalculatedResult = (TextView) v.findViewById(R.id.tv_calculated_result);

        mCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditArgX.getText()) ||
                        TextUtils.isEmpty(mEditPolynomGrade.getText())) {
                    Toast.makeText(getActivity(),
                            "Заполните все поля!",
                            Toast.LENGTH_SHORT).show();
                } else if(Double.parseDouble(mEditArgX.getText().toString()) < 0.0) {
                    Toast.makeText(getActivity(),
                            "Поле аргумента не может иметь отрицательное значение!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    calculate();
                }
            }
        });

        return v;
    }

    private void calculate() {
        mArgX = Double.parseDouble(mEditArgX.getText().toString());
        mPolynomialGrade = Integer.parseInt(mEditPolynomGrade.getText().toString());

        double result = Polynomial.calculateViaNewton(mArgX,
                mPolynomialGrade,
                mFunSqrtX.getMasX(),
                mFunSqrtX.getMasY(), 1);

        mTextIntResult.setText(String.valueOf(result));

        double calcResult = Math.sqrt(mArgX);
        BigDecimal formatedResult = new BigDecimal(calcResult);
        String resultStr = formatedResult.setScale(3, BigDecimal.ROUND_HALF_UP).toString();

        mTextCalculatedResult.setText(resultStr);
    }
}