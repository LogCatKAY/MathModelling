package modelling.android.mathmodellinglab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Lab2MainFragment extends Fragment {

    private static final String ROW_COUNT = "row_count";
    private static final String X_ARRAY = "mX";
    private static final String FX_ARRAY = "mFx";
    private static final String WEIGHT_ARRAY = "mWeight";
    private static final String GRADE = "mGrade";

    private Button mCalculateBtn;
    private Button mAddRowBtn;
    private Button mDeleteRowBtn;
    private LinearLayout mLinearX;
    private LinearLayout mLinearFx;
    private LinearLayout mLinearWeight;
    private EditText mEditGrade;

    private double[] mX;
    private double[] mFx;
    private double[] mWeight;

    private int rowCounter;
    private int mGrade;

    public static Lab2MainFragment newInstance() {
        return new Lab2MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lab2main_fragment, container, false);
        setHasOptionsMenu(true);

        mAddRowBtn = (Button) v.findViewById(R.id.btn_add_row);
        mDeleteRowBtn = (Button) v.findViewById(R.id.btn_delete_row);
        mCalculateBtn = (Button) v.findViewById(R.id.btn_calculate);
        mLinearX = (LinearLayout) v.findViewById(R.id.linear_layout_x);
        mLinearFx = (LinearLayout) v.findViewById(R.id.linear_layout_fx);
        mLinearWeight = (LinearLayout) v.findViewById(R.id.linear_layout_weight);
        mEditGrade = (EditText) v.findViewById(R.id.et_grade);

        if(savedInstanceState != null) {
            rowCounter = savedInstanceState.getInt(ROW_COUNT);
            mX = new double[rowCounter];
            mFx = new double[rowCounter];
            mWeight = new double[rowCounter];
            initRows(mLinearX, rowCounter);
            initRows(mLinearFx, rowCounter);
            initRows(mLinearWeight, rowCounter);
            mX = savedInstanceState.getDoubleArray(X_ARRAY);
            mFx = savedInstanceState.getDoubleArray(FX_ARRAY);
            mWeight = savedInstanceState.getDoubleArray(WEIGHT_ARRAY);
        }
        else {
            rowCounter = 0;
        }

        mAddRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowCounter++;
                initRows(mLinearX, 1);
                initRows(mLinearFx, 1);
                initRows(mLinearWeight, 1);
            }
        });

        mDeleteRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rowCounter > 0) {
                    rowCounter--;
                    deleteEditText(mLinearX);
                    deleteEditText(mLinearFx);
                    deleteEditText(mLinearWeight);
                }
            }
        });


        mCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildGraph();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_1:
                if(rowCounter == 0) {
                    Toast.makeText(getActivity(), "Build table", Toast.LENGTH_SHORT).show();
                    generateTable(
                            5,
                            new double[]{0.75, 1.50, 2.25, 3.00, 3.75},
                            new double[]{2.50, 1.20, 1.12, 2.25, 4.28},
                            new double[]{1.0, 1.0, 1.0, 1.0, 1.0}
                            );
                }
                break;
            case R.id.action_2:
                if(rowCounter == 0) {
                    Toast.makeText(getActivity(), "Build table", Toast.LENGTH_SHORT).show();
                    generateTable(
                            8,
                            new double[]{-5.0, -2.5, 1.0, 4.5, 5.5, 6.0, 8.0, 11.0},
                            new double[]{-2.0, -2.5, 5.0, 8.0, 3.0, 1.0, 4.0, 7.5},
                            new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
                    );
                }
                break;
            case R.id.action_3:
                if(rowCounter == 0) {
                    Toast.makeText(getActivity(), "Build table", Toast.LENGTH_SHORT).show();
                    generateTable(
                            7,
                            new double[]{-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0},
                            new double[]{-33.0, -18.125, -10.0, -6.375, -5.0, -3.625, 0.0},
                            new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
                    );
                }
                break;
                default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateTable(int rows, double[] x, double[] fx, double[] weight) {
        rowCounter = rows;
        mX = x;
        mFx = fx;
        mWeight = weight;
        initRows(mLinearX, rowCounter);
        initRows(mLinearFx, rowCounter);
        initRows(mLinearWeight, rowCounter);
        setTextIntoEdits(mX, mLinearX);
        setTextIntoEdits(mFx, mLinearFx);
        setTextIntoEdits(mWeight, mLinearWeight);
    }

    private void buildGraph() {
        for(int i = 0; i < rowCounter; i++){
            if (
                    TextUtils.isEmpty(((EditText) mLinearX.getChildAt(i).findViewById(R.id.et_x_fx_w)).getText())
                    || TextUtils.isEmpty(((EditText) mLinearFx.getChildAt(i).findViewById(R.id.et_x_fx_w)).getText())
                    || TextUtils.isEmpty(((EditText) mLinearWeight.getChildAt(i).findViewById(R.id.et_x_fx_w)).getText())
                    || TextUtils.isEmpty(mEditGrade.getText()) || Integer.parseInt(mEditGrade.getText().toString()) <= 0
                    ) {
                        Toast.makeText(getActivity(), "Заполните все поля!", Toast.LENGTH_LONG).show();
                        break;
            } else {
                mX = new double[rowCounter];
                mFx = new double[rowCounter];
                mWeight = new double[rowCounter];
                mGrade = Integer.parseInt(mEditGrade.getText().toString());
                if(mGrade >= rowCounter) {
                    Toast.makeText(getActivity(), "Выберите степень меньше количества узлов", Toast.LENGTH_LONG).show();
                    break;
                }
                initDoubleArray(mX, mLinearX);
                initDoubleArray(mFx, mLinearFx);
                initDoubleArray(mWeight, mLinearWeight);
                Intent intent = GraphActivity.newIntent(getContext(), mX, mFx, mWeight, mGrade);
                startActivity(intent);
                break;
            }
        }
    }

    private void deleteEditText(LinearLayout linearLayout) {
        int lastViewIndex = linearLayout.getChildCount() - 1;
        linearLayout.removeViewAt(lastViewIndex);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ROW_COUNT, rowCounter);
        mX = new double[rowCounter];
        mFx = new double[rowCounter];
        mWeight = new double[rowCounter];
        initDoubleArray(mX, mLinearX);
        initDoubleArray(mFx, mLinearFx);
        initDoubleArray(mWeight, mLinearWeight);
        outState.putDoubleArray(X_ARRAY, mX);
        outState.putDoubleArray(FX_ARRAY, mFx);
        outState.putDoubleArray(WEIGHT_ARRAY, mWeight);
    }

    /**
     * Использовать при пересоздании активити
     * */
    private void initRows(LinearLayout linearLayout, int rowCounter) {
        for(int i = 0; i < rowCounter; i++) {
            final View view = getLayoutInflater().inflate(R.layout.custom_edittext_layout, null);
            if(view.getParent() != null)
                ((ViewGroup)view.getParent()).removeView(view);
            linearLayout.addView(view);
        }
    }

    private void initDoubleArray(double[] arr, LinearLayout linearLayout) {
        for(int i = 0; i < linearLayout.getChildCount(); i++) {
            EditText editText = (EditText) linearLayout.getChildAt(i).findViewById(R.id.et_x_fx_w);
            if(!TextUtils.isEmpty(editText.getText())) {
                arr[i] = Double.parseDouble(editText.getText().toString());
            } else {
                arr[i] = 0;
            }
        }
    }

    private void setTextIntoEdits(double[] arr, LinearLayout layout) {
        for(int i = 0; i < layout.getChildCount(); i++) {
            EditText editText = (EditText) layout.getChildAt(i).findViewById(R.id.et_x_fx_w);
            editText.setText(String.valueOf(arr[i]));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        setTextIntoEdits(mX, mLinearX);
        setTextIntoEdits(mFx, mLinearFx);
        setTextIntoEdits(mWeight, mLinearWeight);
    }
}
