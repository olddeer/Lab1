package sample.lab5.shittyTestsPicard;

import org.junit.Test;
import sample.lab5.AdaptiveQudrature.InsufficientDataPointsException;
import sample.lab5.AdaptiveQudrature.UnequalSpacedDataPointsException;
import sample.lab5.FunctionOneVariable.UndefinedVariableException;
import sample.lab5.FunctionTwoVariable;
import sample.lab5.PicardsDifferentialEquationSolver;


public class PicardsDifferentialEquationSolverTest {

    @Test
    public void main()
        throws UnequalSpacedDataPointsException, UndefinedVariableException, InsufficientDataPointsException {
        String expression = "( x * x * x - y * y *y )";
        FunctionTwoVariable ftw = new FunctionTwoVariable(expression);
        double x0 = 0;
        double y0 = 0;
        double x = 2;
        PicardsDifferentialEquationSolver pdf = new PicardsDifferentialEquationSolver(x0, y0, ftw);
        double v = pdf.solveForX(x, 0.1);
        System.out.println(v);
    }
}