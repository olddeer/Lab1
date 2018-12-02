package sample.lab3;

import sample.lab2.Displayer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class FaddeevLeVerrierDisplayer implements Displayer {
    Deque<Double> matrixA;
    Deque<Double> matrixB;
    Deque<Double> coefficients;
    Deque<Double> roots;
    StringBuilder builder;

    public FaddeevLeVerrierDisplayer() {
        this.matrixA = new LinkedList<>();
        this.matrixB = new LinkedList<>();
        this.coefficients = new LinkedList<>();
        this.roots = new LinkedList<>();
        this.builder = new StringBuilder();
    }

    public Deque<Double> getMatrixA() {
        return matrixA;
    }

    public Deque<Double> getMatrixB() {
        return matrixB;
    }

    public Deque<Double> getCoefficients() {
        return coefficients;
    }

    public Deque<Double> getRoots() {
        return roots;
    }

    private void displayMatrix(Deque<Double> matrix) {
        for (int i = 0; i < 9; ) {
            for (int j = 0; j < 3; j++,i++)
                this.builder.append(matrix.pop()+" ");
            this.builder.append('\n');
        }
    }

    @Override
    public String display() {
        for(int i=1;i<=3;i++) {
            builder.append("A"+i+":\n");
            this.displayMatrix(matrixA);
            builder.append("SpA"+i+"/"+i+"=p"+i+"= "+coefficients.pop()+'\n');
            builder.append("B"+i+"=A"+i+"-p"+i+"E:\n");
            this.displayMatrix(matrixB);
        }
        for(int i=1;!roots.isEmpty();i++){
            builder.append("l"+i+"="+roots.pop()+"\n");
        }
        return builder.toString();
    }
}
