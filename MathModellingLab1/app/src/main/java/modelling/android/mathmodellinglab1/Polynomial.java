package modelling.android.mathmodellinglab1;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class Polynomial {

    private Polynomial(){}

    /**
     * @param x координата, в которой необходимо вычислить значение полинома Ньютона
     * @param n степень полинома
     * @param tabledArray массив аргументов
     * */
    private static double[] createTempArayX(double x, int n, double[] tabledArray) {
        int nodes = n + 1;
        double[] newArray = new double[nodes];
        int counteredArg = 0;

        for(int i = 0; i < tabledArray.length; i++) {
            counteredArg++;
            if(tabledArray[i] >= x) {
                if((counteredArg - nodes/2) <= 0) {    //у вехней границы
                    for(int j = 0; j < nodes; j++) {
                        newArray[j] = tabledArray[j];
                    }
                    break;
                } else if((tabledArray.length - counteredArg - nodes/2) < 0) {   //у нижней границы и снизу не хватает чисел
                    for(int j = 0; j < nodes; j++) {
                        newArray[j] = tabledArray[counteredArg - nodes + 1 + j];
                    }
                    break;
                } else {
                    for(int j = 0; j < nodes; j++) {
                        newArray[j] = tabledArray[counteredArg - nodes/2 - 1 + j];
                    }
                    break;
                }
            }
        }
        return newArray;
    }

    private static double[] createTempArayY(double[] tempArrayX, double[] oldArrayX, double[] masY) {
        double[] newArray = new double[tempArrayX.length];
        int counter = 0;
        while (counter < oldArrayX.length) {
            if(oldArrayX[counter] == tempArrayX[0]) {
                for(int i = 0; i < tempArrayX.length; i++) {
                    newArray[i] = masY[counter + i];
                }
                break;
            } else {
                counter++;
            }
        }
        return newArray;
    }

    /**
     * x - координата, в которой необходимо вычислить значение полинома Ньютона;
     * n - степень полинома - количество узлов будет на 1 больше;
     * MasX - массив x;
     * MasY - массив значений x;
     * step - шаг между соседними значениями
     * */
    public static double calculateViaNewton(double x, int n, double[] MasX, double[] MasY, double step) {

        double[] tempArayX = createTempArayX(x, n, MasX);
        MasY = createTempArayY(tempArayX, MasX, MasY);
        MasX = tempArayX;

        double[][] mas = new double[n + 2][n + 1];
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < n + 1; j++)
            {
                if (i == 0)
                    mas[i][j] = MasX[j];
                    else if (i == 1)
                        mas[i][j] = MasY[j];
            }
        }
        int m = n;
        for (int i = 2; i < n + 2; i++)
        {
            for (int j = 0; j < m; j++)
            {
                mas[i][j] = mas[i - 1][j + 1] - mas[i - 1][j];
            }
            m--;
        }

        double[] dy0 = new double[n + 1];

        for (int i = 0; i < n + 1; i++)
        {
            dy0[i] = mas[i + 1][0];
        }

        double res = dy0[0];
        double[] xn = new double[n];
        xn[0] = x - mas[0][0];

        for (int i = 1; i < n; i++)
        {
            double ans = xn[i - 1] * (x - mas[0][i]);
            xn[i] = ans;
            ans = 0;
        }

        int m1 = n + 1;
        int fact = 1;
        for (int i = 1; i < m1; i++)
        {
            fact = fact * i;
            res = res + (dy0[i] * xn[i - 1]) / (fact * step);
        }

        BigDecimal formatedResult = new BigDecimal(res);
        String resultStr = formatedResult.setScale(3, BigDecimal.ROUND_HALF_UP).toString();

        return Double.parseDouble(resultStr);
    }

    /**
     * x - координата, в которой необходимо вычислить значение полинома Ньютона;
     * n - степень полинома - количество узлов будет на 1 больше;
     * MasX - массив x;
     * MasY - массив значений x;
     * step - шаг между соседними значениями считается автоматически внутри метода в зависимости от n
     * */
    public static double calculateViaNewton(double x, int n, double[] MasX, double[] MasY) {

        double[] tempArayX = createTempArayX(x, n, MasX);
        MasY = createTempArayY(tempArayX, MasX, MasY);
        MasX = tempArayX;
        double step = getStep(MasX, n);

        double[][] mas = new double[n + 2][n + 1];
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < n + 1; j++)
            {
                if (i == 0)
                    mas[i][j] = MasX[j];
                else if (i == 1)
                    mas[i][j] = MasY[j];
            }
        }
        int m = n;
        for (int i = 2; i < n + 2; i++)
        {
            for (int j = 0; j < m; j++)
            {
                mas[i][j] = mas[i - 1][j + 1] - mas[i - 1][j];
            }
            m--;
        }

        double[] dy0 = new double[n + 1];

        for (int i = 0; i < n + 1; i++)
        {
            dy0[i] = mas[i + 1][0];
        }

        double res = dy0[0];
        double[] xn = new double[n];
        xn[0] = x - mas[0][0];

        for (int i = 1; i < n; i++)
        {
            double ans = xn[i - 1] * (x - mas[0][i]);
            xn[i] = ans;
            ans = 0;
        }

        int m1 = n + 1;
        int fact = 1;
        for (int i = 1; i < m1; i++)
        {
            fact = fact * i;
            res = res + (dy0[i] * xn[i - 1]) / (fact * step);
        }

        BigDecimal formatedResult = new BigDecimal(res);
        String resultStr = formatedResult.setScale(3, BigDecimal.ROUND_HALF_UP).toString();

        return Double.parseDouble(resultStr);
    }

    private static double getStep(double[] masX, int n) {
        double sum = masX[masX.length - 1] - masX[0];
        return sum / n;
    }
}
