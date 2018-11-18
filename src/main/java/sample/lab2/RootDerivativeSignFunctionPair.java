package sample.lab2;


import java.util.Objects;

public class RootDerivativeSignFunctionPair implements Comparable<RootDerivativeSignFunctionPair> {

    private Double value;
    private boolean signOfFunction;

    public RootDerivativeSignFunctionPair(Double value, boolean signOfFunction) {
        this.value = value;
        this.signOfFunction = signOfFunction;
    }

    public boolean isSignOfFunction() {
        return signOfFunction;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RootDerivativeSignFunctionPair)) {
            return false;
        }
        RootDerivativeSignFunctionPair pair = (RootDerivativeSignFunctionPair) o;
        return signOfFunction == pair.signOfFunction &&
            Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, signOfFunction);
    }

    @Override
    public int compareTo(RootDerivativeSignFunctionPair o) {
        return Double.compare(value,o.getValue());
    }
}
