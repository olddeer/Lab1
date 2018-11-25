package sample.lab2;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.mariuszgromada.math.mxparser.Function;


public class DihotomyMethodSolver {

    private List<FunctionInterval> intervals;
    private Function function;
    private FunctionDisplayer functionDisplayer;

    public DihotomyMethodSolver(String expression,
        FunctionDisplayer functionDisplayer,
        Double a, Double b) {
        this.functionDisplayer = functionDisplayer;
        function = new Function("f", expression, "x");
        intervals = new ArrayList<>();
        intervals.add(new FunctionInterval(a,b));
    }

    public Set<Double> solve() {
        Set<Double> roots = new LinkedHashSet<>();
        for (FunctionInterval interval : intervals) {
            roots.add(solve(interval));
        }
        functionDisplayer.setRoots(roots);
        return roots;
    }

    private Double func(Double x) {
        return function.calculate(x);
    }

    private Double solve(FunctionInterval interval) {
        double a = interval.getA();
        double b = interval.getB();
        Set<Double> lefts = new LinkedHashSet<>();
        Set<Double> rights = new LinkedHashSet<>();
        if (func(a) == 0) {
            return a;
        }
        if (func(b) == 0) {
            return b;
        }
        double prom;
        double left = a;
        double right = b;
        do {
            prom = (right + left) / 2.0;
            if ((func(left) * func(prom)) >= 0) {
                left = prom;
                lefts.add(left);
            } else {
                right = prom;
                rights.add(right);
            }
        } while (Math.abs(func(prom)) > 0.001);
        functionDisplayer.addLeft(lefts);
        functionDisplayer.addRights(rights);
        return prom;
    }


}
