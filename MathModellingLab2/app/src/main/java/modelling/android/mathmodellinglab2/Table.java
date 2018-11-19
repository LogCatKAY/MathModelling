package modelling.android.mathmodellinglab2;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.GaussianSolver;

import java.util.ArrayList;

public class Table {

    private int mRowsCount = 0;
    private ArrayList<Double> mX = new ArrayList<>();
    private ArrayList<Double> mFx = new ArrayList<>();
    private ArrayList<Double> mWeight = new ArrayList<>();

    public Table(double[] x, double[] fx, double[] weight) {
        if( x.length == fx.length && x.length == weight.length) {
            for (int i = 0; i < x.length; i++) {
                mX.add(x[i]);
                mFx.add(fx[i]);
                mWeight.add(weight[i]);
                mRowsCount = x.length;
            }
        } else {
            mX.add(0.0);
            mFx.add(0.0);
            mWeight.add(0.0);
            mRowsCount = 1;
        }

    }

    public ArrayList<Double> getX() {
        return mX;
    }

    public ArrayList<Double> getFx() {
        return mFx;
    }

    public ArrayList<Double> getWeight() {
        return mWeight;
    }

    public double[][] getMatrix() {
        ArrayList<Double> arrayX = getX();
        ArrayList<Double> arrayFx = getFx();
        double[][] matrix = new double[arrayX.size()][arrayFx.size()];

        for (int i = 0; i < arrayX.size(); i++) {
            matrix[0][i] = arrayX.get(i);
            matrix[1][i] = arrayFx.get(i);
        }


        return matrix;
    }


    /**
     * Используется матрица из аргументов [0] и их значений [1], заданная при создании Table
     * @param basis степень полинома при апроксимации плюс еденица
     * */
    public double[][] makeSystem(int basis)
    {

        double[][] xyTable = getMatrix();
        double[][] matrix = new double[basis][basis + 1];
        for (int i = 0; i < basis; i++)
        {
            for (int j = 0; j < basis; j++)
            {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < basis; i++)
        {
            for (int j = 0; j < basis; j++)
            {
                double sumA = 0, sumB = 0;
                for (int k = 0; k < mRowsCount; k++)
                {
                    sumA += Math.pow(xyTable[0][k], i) * Math.pow(xyTable[0][k], j);
                    sumB += xyTable[1][k] * Math.pow(xyTable[0][k], i);
                }
                matrix[i][j] = sumA;
                matrix[i][basis] = sumB;
            }
        }

        return matrix;
    }



    public double[] getApproxResult(int basis) throws IllegalArgumentException {
        double[][] resultCoefs = getAcoefsWithoutVectorB(basis);
        double[] resultVector = getVectorB(basis);

        Matrix matrix = Matrix.from2DArray(resultCoefs);
        Vector vector = Vector.fromArray(resultVector);

        GaussianSolver solver = new GaussianSolver(matrix);
        Vector resVector = solver.solve(vector);

        double[] approxResult = new double[resVector.length()];

        for(int i = 0; i < approxResult.length; i++) {
            approxResult[i] = resVector.get(i);
        }

        return approxResult;
    }

    private double[] getVectorB(int basis){
        double[] vector = new double[basis];
        double[][] system = makeSystem(basis);

        for(int i = 0; i < basis; i++) {
            vector[i] = system[i][basis];
        }

        return vector;
    }

    private double[][] getAcoefsWithoutVectorB(int basis){
        double[][] system = makeSystem(basis);
        double[][] coefs = new double[basis][basis];

        for(int i = 0; i < basis; i++) {
            for(int j = 0; j < basis; j++) {
                coefs[i][j] = system[i][j];
            }
        }

        return coefs;
    }

    ////////////////////////////////////////////
    ///Методы, которые учитывают вес точки/////
    //////////////////////////////////////////

    /**
     * Используется матрица из аргументов [0] и их значений [1], заданная при создании Table
     * @param basis степень полинома при апроксимации плюс еденица
     * @param weight вес опеределнной точки - абсолютная величина
     * */
    public double[][] makeSystem(int basis, double[] weight) {
        double[][] xyTable = getMatrix();
        double[][] matrix = new double[basis][basis + 1];
        for (int i = 0; i < basis; i++)
        {
            for (int j = 0; j < basis; j++)
            {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < basis; i++)
        {
            for (int j = 0; j < basis; j++)
            {
                double sumA = 0, sumB = 0;
                for (int k = 0; k < mRowsCount; k++)
                {
                    sumA += Math.pow(xyTable[0][k], i) * Math.pow(xyTable[0][k], j) * weight[k];
                    sumB += xyTable[1][k] * Math.pow(xyTable[0][k], i) * weight[k];
                }
                matrix[i][j] = sumA;
                matrix[i][basis] = sumB;
            }
        }

        return matrix;
    }

    public double[] getApproxResult(int basis, double[] weight) throws IllegalArgumentException {
        double[][] resultCoefs = getAcoefsWithoutVectorB(basis, weight);
        double[] resultVector = getVectorB(basis, weight);

        Matrix matrix = Matrix.from2DArray(resultCoefs);
        Vector vector = Vector.fromArray(resultVector);

        GaussianSolver solver = new GaussianSolver(matrix);
        Vector resVector = solver.solve(vector);

        double[] approxResult = new double[resVector.length()];

        for(int i = 0; i < approxResult.length; i++) {
            approxResult[i] = resVector.get(i);
        }

        return approxResult;
    }

    private double[] getVectorB(int basis, double[] weight){
        double[] vector = new double[basis];
        double[][] system = makeSystem(basis, weight);

        for(int i = 0; i < basis; i++) {
            vector[i] = system[i][basis];
        }

        return vector;
    }

    private double[][] getAcoefsWithoutVectorB(int basis, double[] weight){
        double[][] system = makeSystem(basis, weight);
        double[][] coefs = new double[basis][basis];

        for(int i = 0; i < basis; i++) {
            for(int j = 0; j < basis; j++) {
                coefs[i][j] = system[i][j];
            }
        }

        return coefs;
    }
}
