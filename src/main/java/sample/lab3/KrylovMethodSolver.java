package sample.lab3;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

// Класс, функции, которого искать собстсвенный вектор матрицы
public class KrylovMethodSolver {

    private double matrix[][];
    private double[] y0;
    private double[] yn;
    private double[][] y;
    private int numberOfRowsAndColumns;
    private double[] coefficientsP;

    public KrylovMethodSolver(double[][] matrix) {
        this.matrix = matrix;
        numberOfRowsAndColumns = matrix.length;
        initializeInitialYMatrix();
        initializeYMatrixAndYCoefficientsMatrix();
    }

    private void initializeInitialYMatrix() {
        y0 = new double[numberOfRowsAndColumns];
        y0[0] = 1;
    }

    //Вычисляем произведение матриц
    private double[] multiplicateMatrixNMToMatrixM(double[][] matrixNM, double[] matrixN) {
        int n = matrixNM.length;
        double[] resultOfMultiplicationMatrix = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                resultOfMultiplicationMatrix[i] += matrixNM[i][j] * matrixN[j];
            }
        }
        return resultOfMultiplicationMatrix;
    }

    private void initializeYMatrixAndYCoefficientsMatrix() {
        y = new double[numberOfRowsAndColumns][numberOfRowsAndColumns];
        double[] tmp = y0;

        for (int k = numberOfRowsAndColumns - 2; k >= 0; k--) {
            tmp = multiplicateMatrixNMToMatrixM(matrix, tmp);
            for (int j = 0; j < numberOfRowsAndColumns; j++) {
                y[j][k] = tmp[j];
            }
        }
        y[numberOfRowsAndColumns - 1][numberOfRowsAndColumns - 1] = 1;
        yn = setNegativeValuesToArray
            (multiplicateMatrixNMToMatrixM(matrix, tmp));
    }
    // поиск собственных значений
    public double[] getValues() {
        coefficientsP = solveSystemEquationByGauss(y, yn);
        Expression expression = new Expression(
            "solve(-x^3" + getMinusOrPlusCoefficient(coefficientsP[0]) + "*x^2"
                + getMinusOrPlusCoefficient(coefficientsP[1]) + "*x"
                + getMinusOrPlusCoefficient(coefficientsP[2]) + ",x,-100,100)",
            new Argument("x"));
        double[] xes = mXparser.getFunctionValues(expression,
            new Argument("x"),
            -20,
            20,
            0.1);
        Set<Double> roots = new HashSet<>();
        for (double x : xes) {
            roots.add(x);
        }
        double[] array = new double[roots.size()];
        int i = 0;
        for (double x : roots) {

            array[i] = x;
            i++;
        }
        return array;
    }

    private String getMinusOrPlusCoefficient(double x) {
        return (x >= 0 ? " " : " + ") + x * (-1);
    }

    private double[] setNegativeValuesToArray(double[] initialMatrix) {
        int numberOfRows = initialMatrix.length;
        double[] resultMatrix = new double[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            resultMatrix[i] = -initialMatrix[i];
        }
        return resultMatrix;
    }

    private double[] solveSystemEquationByGauss(double[][] coefficientsMatrix,
        double[] constantsMatrix) {
        RealMatrix coefficients =
            new Array2DRowRealMatrix(coefficientsMatrix,
                false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(constantsMatrix, false);
        RealVector solution = solver.solve(constants);
        return solution.toArray();
    }

    public double[] getYn() {
        return yn;
    }

    public double[][] getY() {
        return y;
    }

    public double[] getCoefficientsP() {
        return coefficientsP;
    }

}
