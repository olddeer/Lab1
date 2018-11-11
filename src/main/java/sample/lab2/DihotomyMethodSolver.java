package sample.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class DihotomyMethodSolver {
    private List<FunctionInterval> intervals;
    private Function function;

    public DihotomyMethodSolver(String expression){
        FunctionIntervalBuilder builder = new FunctionIntervalBuilder(expression);
        function = new Function("f",expression,"x");
        intervals = builder.build();
    }

    public List<Double> solve(){
        List<Double> roots= new ArrayList<>();
        for (FunctionInterval interval:intervals){
            roots.add(solve(interval));
        }
        return roots;
    }

    private Double func(Double x){
        return function.calculate(x);
    }

    private Double solve(FunctionInterval interval){
        double a = interval.getA();
        Double b = interval.getB();
        if (func(a)==0) return a;
        if (func(b)==0) return b;
        double prom;
        double left = a;
        double right = b;
        do {
            prom=(right+left)/2.0;
            if ((func(left)*func(prom))>=0){
                left=prom;
            }
            else right=prom;
        } while (Math.abs(func(prom))>0.001);

        return prom;
    }


}
