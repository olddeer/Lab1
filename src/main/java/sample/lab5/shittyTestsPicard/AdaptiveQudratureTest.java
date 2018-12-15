package sample.lab5.shittyTestsPicard;

import static org.junit.Assert.*;

import org.junit.Test;
import sample.lab5.AdaptiveQudrature;
import sample.lab5.AdaptiveQudrature.InsufficientDataPointsException;
import sample.lab5.AdaptiveQudrature.UnequalSpacedDataPointsException;
import sample.lab5.FunctionOneVariable;
import sample.lab5.FunctionOneVariable.UndefinedVariableException;

public class AdaptiveQudratureTest {

    @Test
    public void getNewtonCotesQuadratureIntegral()
        throws UnequalSpacedDataPointsException, UndefinedVariableException, InsufficientDataPointsException {
        AdaptiveQudrature ncqa = new AdaptiveQudrature();
        FunctionOneVariable toIntegerate = new FunctionOneVariable("( 0 + sqrt ( x ) )");
        //System.out.println(ncqa.integrate());
        double newtonCotesQuadratureIntegral = AdaptiveQudrature
            .getNewtonCotesQuadratureIntegral(toIntegerate, new Double[]{0.0, 1.0, 2.0, 3.0});
        System.out.println(newtonCotesQuadratureIntegral);
    }
}