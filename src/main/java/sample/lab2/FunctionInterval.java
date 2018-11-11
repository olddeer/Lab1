package sample.lab2;

public class FunctionInterval {

    private Double a;
    private Double b;

    public FunctionInterval(Double a, Double b) {
        this.a = a;
        if(a > b){
            throw new IllegalArgumentException("Bad intervals");
        }
        this.b = b;
    }

    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
    }

    public Double getB() {
        return b;
    }

    @Override
    public String toString() {
        return "[" + a
            + ","
            + b
            + ']';
    }

    public void setB(Double b) {
        this.b = b;
    }
}
