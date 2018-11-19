package modelling.android.mathmodellinglab3;

/**
 * Собственная реализация интегрирования методом Гаусса.
 * Результаты почти полностью совпадают с apache common math3.
 *      <br>double integral;<br>
 *     integral = gauss(a, b, eps, gaussCalc(a, b));
 * */
@Deprecated
public class IntegralGauss {

    //константы десятиточечного метода Гаусса
    private final double g10c1 = 0.9739065285/6.2012983932;
    private final double g10c2 = 0.8650633667/6.2012983932;
    private final double g10c3 = 0.6794095683/6.2012983932;
    private final double g10c4 = 0.4333953941/6.2012983932;
    private final double g10c5 = 0.1488743390/6.2012983932;

    private final double g10x1 = 0.0666713443/6.2012983932;
    private final double g10x2 = 0.1494513492/6.2012983932;
    private final double g10x3 = 0.2190863625/6.2012983932;
    private final double g10x4 = 0.2692667193/6.2012983932;
    private final double g10x5 = 0.2955242247/6.2012983932;

    //интегрируемая функция - функция Лапласа, но потом после интегрирования надо домножить результат на 2 / Math.sqrt(2 * Math.PI)
    private double f(double t) {
        return Math.exp(-(Math.pow(t, 2) / 2));
    }

    public double gaussCalc(double a, double b) {
        double n,m,s,s1,s2,s3,s4,s5;

        m = (b + a) / 2;
        n = (b - a) / 2;

        s1 = g10c1 * (f(m+n*g10x1) + f(m-n*g10x1));
        s2 = g10c2 * (f(m+n*g10x2) + f(m-n*g10x2));
        s3 = g10c3 * (f(m+n*g10x3) + f(m-n*g10x3));
        s4 = g10c4 * (f(m+n*g10x4) + f(m-n*g10x4));
        s5 = g10c5 * (f(m+n*g10x5) + f(m-n*g10x5));

        s = s1 + s2 + s3 + s4 + s5;

        return s * (b - a);
    }

    //рекурсивная ф-ция подсчета с заданной точностью
    //gc - ранее посчитаный интеграл на интервале (a,b)
    public double gauss(double a, double b, double eps, double gc) {
        double t, ga, gb;

        t = (a + b) / 2;    //разбиваем интервал на две половинки
        //в каждой половинке считаем интеграл
        ga = gaussCalc(a, t);
        gb = gaussCalc(t, b);

        //проверяем точность вычислений
        if(Math.abs(ga + gb - gc) > eps) {
            ga = gauss(a, t, eps/2, ga);    //рекурсия для первой половинки
            gb = gauss(t,b,eps/2, gb);       //рекурсия для второй половинки
        }
        return ga + gb;
    }
}
