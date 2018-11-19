package modelling.android.mathmodellinglab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.GaussianSolver;

import static org.junit.Assert.*;

public class TableTest {

    double[] mX = {0.75, 1.50, 2.25, 3.0, 3.75};
    double[] mFx = {2.50, 1.20, 1.12, 2.25, 4.28};
    double[] mW = {1.0, 1.0, 1.0, 1.0, 1.0};
    Table mTable;

    @Before
    public void setUp() throws Exception {
        mTable = new Table(mX, mFx, mW);
    }

    @Test
    public void makeSystemTest() {
        double[][] result = mTable.makeSystem(3);

        double[] expexted1 = {5.0, 11.25, 30.9375, 11.350000000000001};
        double[] expexted2 = {11.25, 30.9375, 94.921875, 28.995};
        double[] expexted3 = {30.9375, 94.921875, 309.76171875, 90.21375};

        for(int i = 0; i < expexted1.length; i++)
            Assert.assertEquals(expexted1[i] ,result[0][i], 0.001);

        for(int i = 0; i < expexted2.length; i++)
            Assert.assertEquals(expexted2[i] ,result[1][i], 0.001);

        for(int i = 0; i < expexted3.length; i++)
            Assert.assertEquals(expexted3[i] ,result[2][i], 0.001);


        // для использования с этой библиотекой придется из получившейся матрицы из метода makeSystem
        // выдернуть из каждого массива последнее значение - это будет то, чему равняется каждое уравнение из СЛАУ
        // ax + b = f
        // последнее значение будет как раз f
        Matrix matrix = Matrix.from2DArray(new double[][]{{5.0, 11.25, 30.9375}, {11.25, 30.9375, 94.921875}, {30.9375, 94.921875, 309.76171875}});
        Vector vector = Vector.fromArray(new double[]{11.350000000000001, 28.995, 90.21375});

        GaussianSolver solver = new GaussianSolver(matrix);
        Vector resVector = solver.solve(vector);

        double[] expectedVector = {4.822, -3.882, 0.999};
        double[] actualVector = new double[resVector.length()];

        for(int i = 0; i < resVector.length(); i++) {
            actualVector[i] = resVector.get(i);
        }

        Assert.assertArrayEquals(expectedVector, actualVector, 0.001);
    }

    @Test
    public void getApproxResultTest() {
        double[] expectedVector = {4.822, -3.882, 0.999};
        double[] actualVector = mTable.getApproxResult(3);

        Assert.assertArrayEquals(expectedVector, actualVector, 0.001);
    }

    @Test
    public void getApproxResultWithWeightTest() {
        double[] expectedVector = {4.822, -3.882, 0.999};
        double[] actualVector = mTable.getApproxResult(3, mW);

        Assert.assertArrayEquals(expectedVector, actualVector, 0.001);
    }
}