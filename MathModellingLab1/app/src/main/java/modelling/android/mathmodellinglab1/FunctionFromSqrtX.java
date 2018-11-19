package modelling.android.mathmodellinglab1;

public class FunctionFromSqrtX {

    //заранее вычисленные таблицы соответствия f(x)
    double[] mMasX = {
            0,
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            18,
            19,
            20,
            21
    };
    double[] mMasY = {
            0,
            1,
            1.414213562,
            1.732050808,
            2,
            2.236067977,
            2.449489743,
            2.645751311,
            2.828427125,
            3,
            3.16227766,
            3.31662479,
            3.464101615,
            3.605551275,
            3.741657387,
            3.872983346,
            4,
            4.123105626,
            4.242640687,
            4.358898944,
            4.472135955,
            4.582575695
    };

    public FunctionFromSqrtX(){}

    /**
     * Можно подставить собственные таблицы.
     * @param masX таблица аргументов функции
     * @param masY таблица значение функции в соответствии с masY
     * */
    public FunctionFromSqrtX(double[] masX, double[] masY) {
        this.mMasX = masX;
        this.mMasY = masY;
    }

    public double[] getMasX() {
        return mMasX;
    }

    public double[] getMasY() {
        return mMasY;
    }

    public double getCurrentX(int index) {
        return mMasX[index];
    }

    public double getCurrentY(int index) {
        return mMasY[index];
    }
}
