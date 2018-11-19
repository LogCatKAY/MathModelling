package modelling.android.mathmodellinglab1;

//f(x)=ln(x)-1
public class FunctionFromLnXMinusOne {

    private double[] mMasX = {
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

    private double[] mMasY = {
            -1.000,
            -0.307,
            0.099,
            0.386,
            0.609,
            0.792,
            0.946,
            1.079,
            1.197,
            1.303,
            1.398,
            1.485,
            1.565,
            1.639,
            1.708,
            1.773,
            1.833,
            1.890,
            1.944,
            1.996,
            2.045
    };

    public FunctionFromLnXMinusOne(){}

    /**
     * Можно подставить собственные таблицы.
     * @param masX таблица аргументов функции
     * @param masY таблица значение функции в соответствии с masY
     * */
    public FunctionFromLnXMinusOne(double[] masX, double[] masY) {
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
