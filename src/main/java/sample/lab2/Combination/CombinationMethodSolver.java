package sample.lab2.Combination;

import org.mariuszgromada.math.mxparser.Function;
import sample.lab2.FunctionInterval;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CombinationMethodSolver {

    private FunctionInterval interval;
    private Function function;
    private Function2Displayer functionDisplayer;

    public CombinationMethodSolver(String expression,
                                   Function2Displayer functionDisplayer,
                                   FunctionInterval interval) {
        this.functionDisplayer = functionDisplayer;
        function = new Function("f", expression, "x");
        this.interval = interval;
    }

    private Double func(Double x) {
        return function.calculate(x);
    }

    private Double negativeB(double a, double b) {
        return b - ((func(b) * (b - a)) / (func(b) - func(a)));
    }

    private Double positiveA(double a, double b) {
        return a - ((func(a) * (b - a)) / (func(b) - func(a)));
    }

    private Double negativeA(double a) {
        return a - (func(a) / derivate(a));
    }

    private Double posiriveB(double b) {
        return b - (func(b) / derivate(b));
    }

    private double derivate(double x) {
        return (func(x + 0.001) - func(x)) / 0.001;
    }

    private double derivate2(Double x) {
        return (derivate(x + 0.001) - derivate(x)) / 0.001;
    }

    private Boolean check(double x) {
        if (derivate(x) * derivate2(x) > 0)
            return true;
        else return false;
    }

    public Set<Double> solve() {
        Set<Double> roots = new LinkedHashSet<>();
        roots.add(solve(interval));
        functionDisplayer.setRoots(roots);
        return roots;
    }

    private Double solve(FunctionInterval interval) {
        double a = interval.getA();
        double b = interval.getB();
        List<Boolean> signs = new LinkedList<>();
        Set<Double> lefts = new LinkedHashSet<>();
        Set<Double> rights = new LinkedHashSet<>();
        List<Double> x = new LinkedList<>();
        List<Double> derivate1 = new LinkedList<>();
        List<Double> derivate2 = new LinkedList<>();
        double prom;
        double left = a;
        double right = b;
        do {
            prom = (left + right) / 2;
            if (check(prom)) {
                signs.add(check(prom));
                left = positiveA(left, right);
                right = posiriveB(right);
                lefts.add(left);
                rights.add(right);
                x.add(prom);
                derivate1.add(derivate(prom));
                derivate2.add(derivate2(prom));
            } else {
                x.add(prom);
                signs.add(check(prom));
                left = negativeA(left);
                right = negativeB(left, right);
                lefts.add(left);
                rights.add(right);
                x.add(prom);
                derivate1.add(derivate(prom));
                derivate2.add(derivate2(prom));
            }
        } while (Math.abs(right - left) > 0.001);
        prom = (left + right) / 2;
        functionDisplayer.addX(x);
        functionDisplayer.addDerivate1(derivate1);
        functionDisplayer.addDerivate2(derivate2);
        functionDisplayer.addSigns(signs);
        functionDisplayer.addLeft(lefts);
        functionDisplayer.addRights(rights);
        return prom;
    }
}
