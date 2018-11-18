package sample.lab2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    private IntervalDisplayer intervalDisplayer;

    public FunctionIntervalBuilder(String functionExpression, IntervalDisplayer displayer) {
        this.functionExpression = functionExpression;
        f = new Function("f", this.functionExpression, "x");
        foolRootSignFunctionPairs = new ArrayList<>();
        rootSignFunctionPairs = new ArrayList<>();
        functionIntervals = new LinkedList<>();
        intervalDisplayer = displayer;
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
        return pair1.isSignOfFunction() != pair2.isSignOfFunction();
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
            RootDerivativeSignFunctionPair pair =
                new RootDerivativeSignFunctionPair(x,checkSign(x));
            if(!rootSignFunctionPairs.contains(pair))
            {   intervalDisplayer.addRoot(pair);
                rootSignFunctionPairs.add(pair);}
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
        RootDerivativeSignFunctionPair minimumRoot =
            new RootDerivativeSignFunctionPair(minusBoundRoot,
            checkSign(minusBoundRoot));
        foolRootSignFunctionPairs.add(minimumRoot);
        intervalDisplayer.setMinBound(minimumRoot);
        return this;
    }

    private FunctionIntervalBuilder addBoundMaxRoot(){
        Expression expressionMax = new Expression("f("+MAX_BOUND+")",f);
        double maxBoundRoot = expressionMax.calculate();
        foolRootSignFunctionPairs.addAll(rootSignFunctionPairs);
        RootDerivativeSignFunctionPair maxRoot = new RootDerivativeSignFunctionPair(maxBoundRoot,
            checkSign(maxBoundRoot));
        foolRootSignFunctionPairs.add(maxRoot);
        intervalDisplayer.setMaxBound(maxRoot);
        return this;
    }


}
