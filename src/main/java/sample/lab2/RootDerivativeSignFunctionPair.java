package sample.lab2;


import java.util.Objects;

public class RootDerivativeSignFunctionPair implements Comparable<RootDerivativeSignFunctionPair> {

    private Double value;
    private boolean isSignOfFunctionChanged;

    public RootDerivativeSignFunctionPair(Double value, boolean isSignOfFunctionChanged) {
        this.value = value;
        this.isSignOfFunctionChanged = isSignOfFunctionChanged;
    }

    public boolean isSignOfFunctionChanged() {
        return isSignOfFunctionChanged;
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
        return isSignOfFunctionChanged == pair.isSignOfFunctionChanged &&
            Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isSignOfFunctionChanged);
    }

    @Override
    public int compareTo(RootDerivativeSignFunctionPair o) {
        return Double.compare(value,o.getValue());
    }
}
