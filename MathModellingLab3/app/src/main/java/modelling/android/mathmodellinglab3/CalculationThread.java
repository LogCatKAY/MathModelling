package modelling.android.mathmodellinglab3;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.gauss.GaussIntegrator;
import org.apache.commons.math3.analysis.integration.gauss.GaussIntegratorFactory;

//в этом потоке будет считаться верхний предел
//в UI треде должна быть крутилка ProgressBar
public class CalculationThread extends HandlerThread {

    private static final int MESSAGE_VAR_ONE_START_BUTTON_CLICKED = 1;
    private static final int MESSAGE_VAR_TWO_START_BUTTON_CLICKED = 2;

    private Handler mMainHandler;
    private Handler mBackgroundHandler;
    private Callback mCallback;         //наш интерфейс - что отправлять или получать

    private boolean isRun = false;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        int getNodes();
        double getExpectedResult();
        void sendResult(double result);
        void sendProgress(boolean isRunning);
    }

    public CalculationThread(String name) {
        super(name);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mMainHandler = new Handler(Looper.getMainLooper());

        mBackgroundHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_VAR_ONE_START_BUTTON_CLICKED:
                        if (!isRun) {
                            calculationResult_varOne(mCallback.getExpectedResult());
                        }
                        break;
                    case MESSAGE_VAR_TWO_START_BUTTON_CLICKED:
                        if(!isRun) {
                            calculationResult_varTwo(mCallback.getExpectedResult());
                        }
                }
                try {
                    msg.recycle(); //it can work in some situations
                } catch (IllegalStateException e) {
                    this.removeMessages(msg.what); //if recycle doesnt work we do it manually
                }
            }
        };
    }

    //////////////////////////////
    //для формулы 1 - Lab3varOne//
    //////////////////////////////
    private double calculateIntegral_varOne(int nodes, double xUpperBound) {
        GaussIntegratorFactory integratorFactory = new GaussIntegratorFactory();
        UnivariateFunction fun = new UnivariateFunction() {
            public double value(double v) {
                return Math.exp(-(Math.pow(v, 2) / 2));
            }
        };
        GaussIntegrator gaussIntegrator = integratorFactory.legendre(nodes, 0, xUpperBound);
        return gaussIntegrator.integrate(fun) * (1 / Math.sqrt(2 * Math.PI));
    }

    private void calculationResult_varOne(double expectedResult) {

        double x = 0.0;
        double step = 0.0001;
        double actualResult = 0.0;
        final double resultXtoSend;

        isRun = true;

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.sendProgress(isRun);
            }
        });

        if (expectedResult == 0) {
            isRun = false;
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.sendProgress(isRun);
                    mCallback.sendResult(0);
                }
            });
            return;
        } else {
            while (expectedResult >= actualResult) {
                x += step;
                actualResult = calculateIntegral_varOne(mCallback.getNodes(), x);
            }
            resultXtoSend = x;
        }

        isRun = false;

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.sendProgress(isRun);
                mCallback.sendResult(resultXtoSend);
            }
        });
    }

    //этот метод будет вызываться из главного потока
    public void startOperation_varOne() {
        //создаем message от backgroundHandler'а и отправляем в очередь
        mBackgroundHandler
                .obtainMessage(MESSAGE_VAR_ONE_START_BUTTON_CLICKED)
                .sendToTarget();
        // созданное сообщение попадает в очередь фонового потока
        // т.к. хендлер связан с лупером фонового потока
        // сейчас вызовется метод handleMessage,
        // который переопределен выше в методе onLooperPrepared
    }

    //////////////////////////////
    //для формулы 2 - Lab3varTwo//
    //////////////////////////////
    private double calculateIntegral_varTwo(int nodes, double xUpperBound) {
        GaussIntegratorFactory integratorFactory = new GaussIntegratorFactory();
        UnivariateFunction fun = new UnivariateFunction() {
            public double value(double v) {
                return Math.exp(-(Math.pow(v, 2) / 2));
            }
        };
        GaussIntegrator gaussIntegrator = integratorFactory.legendre(nodes, 0, xUpperBound);
        return gaussIntegrator.integrate(fun) * (2 / Math.sqrt(2 * Math.PI));
    }

    private void calculationResult_varTwo(double expectedResult) {

        double x = 0.0;
        double step = 0.0001;
        double actualResult = 0.0;
        final double resultXtoSend;

        isRun = true;

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.sendProgress(isRun);
            }
        });

        if (expectedResult == 0) {
            isRun = false;
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.sendProgress(isRun);
                    mCallback.sendResult(0);
                }
            });
            return;
        } else {
            while (expectedResult >= actualResult) {
                x += step;
                actualResult = calculateIntegral_varTwo(mCallback.getNodes(), x);
            }
            resultXtoSend = x;
        }

        isRun = false;

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.sendProgress(isRun);
                mCallback.sendResult(resultXtoSend);
            }
        });
    }

    //этот метод будет вызываться из главного потока
    public void startOperation_varTwo() {
        //создаем message от backgroundHandler'а и отправляем в очередь
        mBackgroundHandler
                .obtainMessage(MESSAGE_VAR_TWO_START_BUTTON_CLICKED)
                .sendToTarget();
        // созданное сообщение попадает в очередь фонового потока
        // т.к. хендлер связан с лупером фонового потока
        // сейчас вызовется метод handleMessage,
        // который переопределен выше в методе onLooperPrepared
    }
}
