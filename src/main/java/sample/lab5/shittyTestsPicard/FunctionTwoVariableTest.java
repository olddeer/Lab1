package sample.lab5.shittyTestsPicard;

import static org.junit.Assert.*;

import org.junit.Test;
import sample.lab5.FunctionTwoVariable;
import sample.lab5.FunctionTwoVariable.UndefinedVariableException;

public class FunctionTwoVariableTest {

    @Test
    public void evaluate() throws UndefinedVariableException {
        System.out.println("answer : " + new FunctionTwoVariable("( x + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) ").evaluate(1, 0));
        System.out.println("answer : " + new FunctionTwoVariable("( ( 1 + sqrt ( pow ( x  y ) ) ) / 2.0 )").evaluate(3, 2));
    }
}