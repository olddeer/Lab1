package sample.lab2;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;

public class FunctionIntervalBuilder {
    private final String  MAX_BOUND = "10000000000";
    private final String  MIN_BOUND = "-10000000000";
    private final String  DERIVATIVE_MAX = "10";
    private final String  DERIVATIVE_MIN = "-10";

    private String functionExpression;
    private  Function f;
    private List<RootDerivativeSignFunctionPair> rootSignFunctionPairs;
    private  List<RootDerivativeSignFunctionPair> foolRootSignFunctionPairs;
    private List<FunctionInterval> functionIntervals;

    public FunctionIntervalBuilder(String functionExpression) {
        this.functionExpression = functionExpression;
        f = new Function("f", this.functionExpression, "x");
        rootSignFunctionPairs = new ArrayList<>();
        functionIntervals = new LinkedList<>();
        foolRootSignFunctionPairs = new ArrayList<>();
    }

    public List<FunctionInterval> getFunctionIntervals() {
        return functionIntervals;
    }

    private FunctionIntervalBuilder setFunctionIntervals(){
        for (int i = 0; i<foolRootSignFunctionPairs.size()-1;i++){
            RootDerivativeSignFunctionPair pair = foolRootSignFunctionPairs.get(i);
            RootDerivativeSignFunctionPair nextPair = foolRootSignFunctionPairs.get(i + 1);
            if(checkIntervalIsChanged(pair,nextPair)){
                functionIntervals.add(new FunctionInterval(pair.getValue(),
                    nextPair.getValue()));
            }
        }

        return this;
    }

    public List<FunctionInterval> build(){
        return this
            .addDerivativeRoots()
            .addBoundMinRoot()
            .addBoundMaxRoot()
            .sort()
            .setFunctionIntervals()
            .getFunctionIntervals();

    }

    private boolean checkSign(double x){
      double  d = f.calculate(x);
        return d > 0;
    }

    private boolean checkIntervalIsChanged(RootDerivativeSignFunctionPair pair1,
        RootDerivativeSignFunctionPair pair2){
        return pair1.isSignOfFunctionChanged() != pair2.isSignOfFunctionChanged();
    }

    private FunctionIntervalBuilder addDerivativeRoots(){
        Expression deritvativeExpression = new Expression(
            String.format("solve( der( %s, x ), x, %s, %s )", functionExpression, DERIVATIVE_MIN,
                DERIVATIVE_MAX), new Argument("x"));
        double[] values = mXparser.getFunctionValues(deritvativeExpression,
            new Argument("x"),
            Double.valueOf(DERIVATIVE_MIN),
            Double.valueOf(DERIVATIVE_MAX),
            0.1);
        for (double x: values){
            RootDerivativeSignFunctionPair pair = new RootDerivativeSignFunctionPair(x,checkSign(x));
            if(!rootSignFunctionPairs.contains(pair))
            rootSignFunctionPairs.add(pair);
        }

        return this;
    }

    private FunctionIntervalBuilder sort(){
        foolRootSignFunctionPairs.sort(RootDerivativeSignFunctionPair::compareTo);
        return this;
    }

    private FunctionIntervalBuilder addBoundMinRoot(){
        Expression expressionMin = new Expression("f("+MIN_BOUND+")",f);
        double minusBoundRoot = expressionMin.calculate();
        foolRootSignFunctionPairs.add(new RootDerivativeSignFunctionPair(minusBoundRoot,checkSign(minusBoundRoot)));
        return this;
    }
    private FunctionIntervalBuilder addBoundMaxRoot(){
        Expression expressionMax = new Expression("f("+MAX_BOUND+")",f);
        double maxBoundRoot = expressionMax.calculate();
        foolRootSignFunctionPairs.addAll(rootSignFunctionPairs);
        foolRootSignFunctionPairs.add(new RootDerivativeSignFunctionPair(maxBoundRoot,checkSign(maxBoundRoot)));

        return this;
    }


}
