package modelling.android.mathmodellinglab3;

public class LaplasItem {
    private double mX;
    private double mFx;

    public LaplasItem(double x, double fx) {
        mX = x;
        mFx = fx;
    }

    public double getX() {
        return mX;
    }

    public double getFx() {
        return mFx;
    }
}
