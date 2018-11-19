package modelling.android.mathmodellinglab2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {

    private static final String INTENT_EXTRA_X = "doubleX";
    private static final String INTENT_EXTRA_FX = "doubleFx";
    private static final String INTENT_EXTRA_WEIGHT = "doubleWeight";
    private static final String INTENT_EXTRA_GRADE = "grade";

    private Table mTable;

    private double[] mX;
    private double[] mFx;
    private double[] mWeight;

    private int mGrade;

    public static Intent newIntent(Context context, double[] x, double[] fx, double[] weight, int grade){
        Intent intent = new Intent(context, GraphActivity.class);
        intent.putExtra(INTENT_EXTRA_X, x);
        intent.putExtra(INTENT_EXTRA_FX, fx);
        intent.putExtra(INTENT_EXTRA_WEIGHT, weight);
        intent.putExtra(INTENT_EXTRA_GRADE, grade);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mX = intent.getDoubleArrayExtra(INTENT_EXTRA_X);
        mFx = intent.getDoubleArrayExtra(INTENT_EXTRA_FX);
        mWeight = intent.getDoubleArrayExtra(INTENT_EXTRA_WEIGHT);
        mGrade = intent.getIntExtra(INTENT_EXTRA_GRADE, 1);

        mTable = new Table(mX, mFx, mWeight);

        GraphView graph = (GraphView) findViewById(R.id.view_graph);
        try {
            initGraph(graph);
        } catch (IllegalArgumentException iae) {
            Toast.makeText(this, iae.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void initGraph(GraphView graphView) throws IllegalArgumentException {
        GridLabelRenderer gridLabelRenderer = graphView.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("x");
        gridLabelRenderer.setVerticalAxisTitle("F(x)");
        graphView.setTitle("Аппроксимация функции");

        Viewport viewport = graphView.getViewport();
        viewport.setXAxisBoundsManual(true);
        viewport.setYAxisBoundsManual(false);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        viewport.setMinX(mX[0] - 1);
        viewport.setMaxX(mX[mX.length - 1] + 1);

        DataPoint[] dataPoints = new DataPoint[mTable.getX().size()];
        for(int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = new DataPoint(mTable.getX().get(i), mTable.getFx().get(i));
        }

        DataPoint[] dataPointsAprox = getApproxGraphPoints(mGrade + 1, mWeight);
        LineGraphSeries<DataPoint> approxGraphSeries = new LineGraphSeries<>(dataPointsAprox);
        approxGraphSeries.setTitle("Аппроксимация");
        approxGraphSeries.setColor(Color.BLUE);

        PointsGraphSeries<DataPoint> seriesOurPoints = new PointsGraphSeries<>(dataPoints);
        seriesOurPoints.setTitle("Исходные точки");
        seriesOurPoints.setColor(Color.RED);
        seriesOurPoints.setSize(15.0f);

        graphView.addSeries(seriesOurPoints);
        graphView.addSeries(approxGraphSeries);

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private DataPoint[] getApproxGraphPoints(int basis, double[] weight) throws IllegalArgumentException {
        double step = 0.1;
        double[] approxCoefs = mTable.getApproxResult(basis, weight);
        Integer counter = 0;
        Double startX = mTable.getX().get(0);
        Double endX = mTable.getX().get(mTable.getX().size() - 1);
        Double currentX = startX;

        Map<Integer, ArrayList<Double>> points = new HashMap<>();

        while(currentX <= endX) {
            Double sum = 0.0;
            ArrayList<Double> p = new ArrayList<>(2);
            for(int i = 0; i < approxCoefs.length; i++) {
                sum += approxCoefs[i] * Math.pow(currentX, i);
            }
            p.add(currentX);
            p.add(sum);
            points.put(counter, p);
            counter++;
            currentX += step;
        }

        DataPoint[] dataPoints = new DataPoint[points.size()];
        counter = 0;
        for(int i = 0; i < points.size(); i++) {
            ArrayList<Double> p = points.get(counter);
            dataPoints[i] = new DataPoint(p.get(0), p.get(1));
            counter++;
        }

        return dataPoints;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
