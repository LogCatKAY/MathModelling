package modelling.android.mathmodellinglab3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Lab3varTwoFragment extends Fragment implements CalculationThread.Callback {

    private ImageView mImageFormula;
    private EditText mEditNodes;
    private EditText mEditExpectedResult;
    private TextView mTextUpperBound;
    private Button mCalculateBtn;
    private ProgressBar mProgressBar;

    private int mNodes;
    private double mExpectedResult;

    private CalculationThread mCalculationThread;

    public static Lab3varTwoFragment newInstance(){
        return new Lab3varTwoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lab3_fragment, container, false);

        mImageFormula = (ImageView) v.findViewById(R.id.image_laplas);
        mEditNodes = (EditText) v.findViewById(R.id.et_nodes);
        mEditExpectedResult = (EditText) v.findViewById(R.id.et_f);
        mTextUpperBound = (TextView) v.findViewById(R.id.tv_upper_bound_result);

        mImageFormula.setImageResource(R.drawable.laplas2);

        mCalculateBtn = (Button) v.findViewById(R.id.btn_calculate);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        //запускаем поток и инициализируем Looper
        mCalculationThread = new CalculationThread("Background ");
        mCalculationThread.start();
        mCalculationThread.onLooperPrepared();
        mCalculationThread.setCallback(this);
        //теперь в фоновом потоке будет крутиться лупер и ждать задач

        mCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mEditExpectedResult.getText()) &&
                        !TextUtils.isEmpty(mEditNodes.getText()) &&
                        Double.parseDouble(mEditExpectedResult.getText().toString()) < 1.0 &&
                        Double.parseDouble(mEditExpectedResult.getText().toString()) >= 0.0 &&
                        Integer.parseInt(mEditNodes.getText().toString()) > 0) {
                    mExpectedResult = Double.parseDouble(mEditExpectedResult.getText().toString());
                    mNodes = Integer.parseInt(mEditNodes.getText().toString());

                    mCalculationThread.startOperation_varTwo();
                }
            }
        });

        return v;
    }

    @Override
    public int getNodes() {
        return mNodes;
    }

    @Override
    public double getExpectedResult() {
        return mExpectedResult;
    }

    @Override
    public void sendResult(double result) {
        mTextUpperBound.setText(String.valueOf(result));
    }

    @Override
    public void sendProgress(boolean isRunning) {
        if(isRunning) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCalculateBtn.setClickable(false);
            mTextUpperBound.setText("Рассчитывается...");
        } else {
            mProgressBar.setVisibility(View.GONE);
            mCalculateBtn.setClickable(true);
        }
    }

    @Override
    public void onDestroy() {
        mCalculationThread.quit();
        super.onDestroy();
    }

}
