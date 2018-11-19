package modelling.android.mathmodellinglab3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.gauss.GaussIntegrator;
import org.apache.commons.math3.analysis.integration.gauss.GaussIntegratorFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class Lab3varOneFragment extends Fragment {

    private ImageView mImageFormula;
    private EditText mEditNodes;
    private EditText mEditExpectedResult;
    private TextView mTextUpperBound;
    private Button mCalculateBtn;

    private int mNodes;
    private double mExpectedResult;
    private double mX;
    private List<LaplasItem> mResultsFromTable;

    public static Lab3varOneFragment newInstance() {
        return new Lab3varOneFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lab3_fragment, container, false);

        mImageFormula = (ImageView) v.findViewById(R.id.image_laplas);
        mEditNodes = (EditText) v.findViewById(R.id.et_nodes);
        mEditExpectedResult = (EditText) v.findViewById(R.id.et_f);
        mTextUpperBound = (TextView) v.findViewById(R.id.tv_upper_bound_result);
        mCalculateBtn = (Button) v.findViewById(R.id.btn_calculate);

        mCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mEditExpectedResult.getText()) &&
                        !TextUtils.isEmpty(mEditNodes.getText()) &&
                        Double.parseDouble(mEditExpectedResult.getText().toString()) <= 0.5 &&
                        Double.parseDouble(mEditExpectedResult.getText().toString()) >= 0.0 &&
                        Integer.parseInt(mEditNodes.getText().toString()) > 0) {
                    mExpectedResult = Double.parseDouble(mEditExpectedResult.getText().toString());
                    mNodes = Integer.parseInt(mEditNodes.getText().toString());
                    mX = 0;
                    double step = 0.01;
                    double actualResult = 0;
                    if(mExpectedResult == 0)
                        mTextUpperBound.setText("0.00");
                    else {
                        while (mExpectedResult >= actualResult) {
                            mX += step;
                            actualResult = calculateIntegral(mNodes, mX);
                        }
//                        DecimalFormat df = new DecimalFormat("#.##");
//                        df.setRoundingMode(RoundingMode.HALF_UP);
//                        mTextUpperBound.setText(String.valueOf(df.format(mX)));
                        mTextUpperBound.setText(String.valueOf(mX));
                    }
                }
            }
        });

        mImageFormula.setImageResource(R.drawable.laplas1);
        return v;
    }

    private double calculateIntegral(int nodes, double xUpperBound) {
        GaussIntegratorFactory integratorFactory = new GaussIntegratorFactory();
        UnivariateFunction fun = new UnivariateFunction() {
            public double value(double v) {
                return Math.exp(-(Math.pow(v, 2) / 2));
            }
        };
        GaussIntegrator gaussIntegrator = integratorFactory.legendre(nodes, 0, xUpperBound);
        return gaussIntegrator.integrate(fun) * (1 / Math.sqrt(2 * Math.PI));
    }
}
