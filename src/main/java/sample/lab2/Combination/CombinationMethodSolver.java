package sample.lab2.Combination;

import org.mariuszgromada.math.mxparser.Function;
import sample.lab2.FunctionInterval;
import sample.lab2.FunctionIntervalBuilder;
import sample.lab2.IntervalDisplayer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CombinationMethodSolver {

    private List<FunctionInterval> intervals;
    private Function function;
    private Function2Displayer functionDisplayer;

    public CombinationMethodSolver(String expression,
                                   Function2Displayer functionDisplayer,
                                   IntervalDisplayer intervalDisplayer) {
        this.functionDisplayer = functionDisplayer;
        FunctionIntervalBuilder builder =
                new FunctionIntervalBuilder(expression, intervalDisplayer);
        function = new Function("f", expression, "x");
        intervals = builder.build();
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
        for (FunctionInterval interval : intervals) {
            roots.add(solve(interval));
        }
        functionDisplayer.setRoots(roots);
        return roots;
    }

    private Double solve(FunctionInterval interval) {
        double a = interval.getA();
        double b = interval.getB();
        Set<Boolean> signs = new LinkedHashSet<>();
        Set<Double> lefts = new LinkedHashSet<>();
        Set<Double> rights = new LinkedHashSet<>();
        double prom;
        double left = a;
        double right = b;
        prom=(left+right)/2;
        do {
            if (check(prom)) {
                signs.add(check(prom));
                left = positiveA(left,right);
                right =posiriveB(right);
                lefts.add(left);
                rights.add(right);
            } else {
                signs.add(check(prom));
                left = negativeB(left,right);
                right =negativeA(left);
                lefts.add(left);
                rights.add(right);
            }
        } while (Math.abs(right-left) > 0.001);
        functionDisplayer.addSigns(signs);
        functionDisplayer.addLeft(lefts);
        functionDisplayer.addRights(rights);
        return prom;
    }
}
