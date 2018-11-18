package sample.lab2;

import java.util.LinkedHashSet;
import java.util.Set;

public class IntervalDisplayer implements Displayer {
    private RootDerivativeSignFunctionPair maxBound;
    private RootDerivativeSignFunctionPair minBound;
    private Set<RootDerivativeSignFunctionPair> derivativeRoots;

    public RootDerivativeSignFunctionPair getMaxBound() {
        return maxBound;
    }

    public void setMaxBound(RootDerivativeSignFunctionPair maxBound) {
        this.maxBound = maxBound;
    }

    public RootDerivativeSignFunctionPair getMinBound() {
        return minBound;
    }

    public void setMinBound(RootDerivativeSignFunctionPair minBound) {
        this.minBound = minBound;
    }

    public IntervalDisplayer() {
    derivativeRoots = new LinkedHashSet<>();
    }

    public void addRoot(RootDerivativeSignFunctionPair interval){
        derivativeRoots.add(interval);
    }

    @Override
    public String display() {
        StringBuilder table = new StringBuilder();
        table.append("min bound =").append(minBound.getValue())
            .append(", sign = ")
            .append(minBound.isSignOfFunction()?"+":"-")
            .append("\n");
        for (RootDerivativeSignFunctionPair root: derivativeRoots){
          table.append("argument = ").append(root.getValue())
              .append(" sign = ")
              .append(root.isSignOfFunction()?"+":"-")
              .append("\n");
        }
        table.append("max bound =").append(maxBound.getValue())
            .append(", sign = ")
            .append(maxBound.isSignOfFunction()?"+":"-")
            .append("\n");
        return table.toString();
    }
}
