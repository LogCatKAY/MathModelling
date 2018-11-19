package modelling.android.mathmodellinglab1;

import android.support.v4.math.MathUtils;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class RootFunction {

    /**
     * Поиск корней функции методом итерации с точностью 0.001.
     * @param startX начало интервала поиска корней
     * @param endX конец интервала поиска корней
     * @return массив корней
     * */
    public static ArrayList<Double> iterationMethod(int startX, int endX) {
        final double eps = 0.001;
        startX = -10;       //начало интервала поиска корней
        endX = 10 - 1;    //конец интервала поиска корней минус шаг

        ArrayList<Double> roots = new ArrayList<>();

        double x, x1, lambda;
        int xi, n;

        n = 1;       //номер очередного корня

        for(xi = startX; xi <= endX; xi++) {

            if ((function(xi) * function(xi + 1)) <= 0) {       //если разного знака, то на интервале xi..xi+1 есть корень
                lambda = 1 / (function(xi + 1) - function(xi)); //надо ещё разделить знаменатель на шаг по x, но шаг у нас = 1
                x1 = xi;    //начальное приближение

                //цикл уточнения корня
                do {
                    x = x1; //запоминаем прошлое приближение
                    x1 = x - lambda * function(x);  //ищем очередное приближение корня
                }while (!(Math.abs(x1 - x) < eps));                  //если разница между соседними приближениями < eps, корень найден
                //конец цикла уточнения корня
                roots.add(x1);
                n++;                                //счетчик для отладки или подсчета корней
            }
        }

        return roots;
    }

    private static double function(double x) {
        return (((x - 4.1) * x + 1) * x - 5.1) * x + 4.1;
    }
}
