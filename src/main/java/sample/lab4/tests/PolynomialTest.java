package sample.lab4.tests;

import java.util.List;
import org.junit.Test;
import sample.lab4.Polynom;
import sample.lab4.Variable;

public class PolynomTest {

    @Test
    public void getCoefficients() {
        Polynom polynom = new Polynom();
        polynom.addVariable(new Variable(3,1));
        polynom.addVariable(new Variable(3,-1));
        polynom.addVariable(new Variable(7,(-23.0/5)));
        polynom.addVariable(new Variable(2,42));
        polynom.addVariable(new Variable(2,-1));
        Variable mult = new Variable(4,1.0/3);
        Variable mult2 = new Variable(3,1.0/3);
        mult.multipleTo(mult2);
        polynom.addVariable(mult);
        List<Double> coefficients = polynom.getCoefficients();
        coefficients.forEach(System.out::println);
    }
}
