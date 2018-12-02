package sample.lab3;

import sample.lab2.Displayer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class FaddeevLeVerrier {
    List<Double> inputMatrixA;
    List<Double> matrixA;
    List<Double> matrixB;
    Deque<Double> coefficients;
    List<Double> roots;

    public FaddeevLeVerrier(List<Double> matrixA) {
        this.matrixA = new LinkedList<>();
        this.matrixB = new LinkedList<>();
        this.coefficients = new LinkedList<>();
        this.inputMatrixA = matrixA;
        this.roots = new LinkedList<>();
    }

    private void calculateB() {
        List<Double> resultMatrix = new LinkedList<>();
        for (int i = 0, k = 0; i < 9; k++)
            for (int j = 1; j <= 3; j++, i++) {
                if (j == 1 + k)
                    resultMatrix.add(matrixA.get(i) - coefficients.getLast());
                else resultMatrix.add(matrixA.get(i));
            }
        matrixB.clear();
        matrixB.addAll(resultMatrix);
    }

    private void calculateA() {
        if (!matrixB.isEmpty()) {
            List<Double> resultMatrix = new LinkedList<>();
            double result = 0;
            double[][] a = this.initializeMatrixA();
            double[][] b = this.initializeMatrixB();
            for (int i = 0; i < a.length; i++)
                for (int j = 0; j < 3; j++, result = 0) {
                    for (int k = 0; k < b.length; k++)
                        result += a[i][k] * b[k][j];
                    resultMatrix.add(result);
                }
            matrixA.clear();
            matrixA.addAll(resultMatrix);
        } else matrixA.addAll(inputMatrixA);
    }

    private void calculateCoefficients(int numberOfStep) {
        double sumMainElements = 0;
        for (int i = 0, k = 0; i < 9; k++) {
            for (int j = 1; j <= 3; j++, i++) {
                if (j == 1 + k)
                    sumMainElements += matrixA.get(i);
            }
        }
        coefficients.addLast(sumMainElements / numberOfStep);
    }

    private double[][] initializeMatrixA() {
        double[][] A = new double[3][3];
        for (int i = 0, k = 0; i < 3; i++)
            for (int j = 0; j < 3; j++, k++)
                A[i][j] = inputMatrixA.get(k);
        return A;
    }

    private double[][] initializeMatrixB() {
        double[][] B = new double[3][3];
        for (int i = 0, k = 0; i < 3; i++)
            for (int j = 0; j < 3; j++, k++)
                B[i][j] = matrixB.get(k);
        return B;
    }

    public void solve(FaddeevLeVerrierDisplayer displayer) {
        for (int i = 1; i <= 3; i++) {
            this.calculateA();
            displayer.getMatrixA().addAll(matrixA);
            this.calculateCoefficients(i);
            this.calculateB();
            displayer.getMatrixB().addAll(matrixB);
        }
        displayer.getCoefficients().addAll(coefficients);
        findRooots();
        displayer.getRoots().addAll(roots);
    }

    private double Q(double a, double b) {
        return (a * a - 3 * b) / 9;
    }

    private double R(double a, double b, double c) {
        return (2 * a * a * a - 9 * a * b + 27 * c) / 54;
    }

    private double S(double Q, double R) {
        return Q * Q * Q - R * R;
    }

    private double angle1(double Q, double R) {
        return (1.0 / 3.0) * Math.acos(R / Math.sqrt(Q * Q * Q));
    }

    private double angle2(double Q, double R) {
        return (1.0 / 3.0) * (1.0 / Math.cosh(Math.abs(R) / Math.sqrt(Q * Q * Q)));
    }

    private double angle3(double Q, double R) {
        return (1.0 / 3.0) * (1.0 / Math.sinh(Math.abs(R) / Math.sqrt(Math.sqrt(Q * Q * Q))));
    }

    private void findRooots() {
        double a = -1 * coefficients.pop();
        double b = -1 * coefficients.pop();
        double c = -1 * coefficients.pop();
        double Q = this.Q(a, b);
        double R = this.R(a, b, c);
        double S = this.S(Q, R);
        if (S > 0) {
            double angle = this.angle1(Q, R);
            roots.add(-2 * Math.sqrt(Q) * Math.cos(angle) - a / 3.0);
            roots.add(-2 * Math.sqrt(Q) * Math.cos(angle + Math.PI * 2.0 / 3.0) - a / 3.0);
            roots.add(-2 * Math.sqrt(Q) * Math.cos(angle - Math.PI * 2.0 / 3.0) - a / 3.0);
        }
        if (S < 0) {
            if (Q > 0)
                roots.add(-2 * Math.signum(R) * Math.sqrt(Q) * Math.cosh(angle2(Q, R)) - a / 3.0);
            if (Q < 0)
                roots.add(-2 * Math.signum(R) * Math.sqrt(Q) * Math.sinh(angle3(Q, R)) - a / 3.0);
            if (Q == 0)
                roots.add(-1 * Math.pow(c - ((a * a * a) / 27.0), 1.0 / 3.0) - a / 3.0);
        }
        if (S == 0) {
            roots.add(-2 * Math.pow(R, 1.0 / 3.0) - a / 3.0);
            roots.add(Math.pow(R, 1.0 / 3.0) - a / 3.0);
        }
    }

}
