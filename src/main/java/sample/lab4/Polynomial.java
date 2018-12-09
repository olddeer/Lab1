package sample.lab4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Polynom {
 private List<Variable> variables;

    public Polynom() {
        variables = new ArrayList<>();
    }

    public Polynom addVariable(Variable variable){
        List<Integer> collect = variables.stream()
            .map(Variable::getPower)
            .collect(Collectors.toList());
        if(collect.contains(variable.getPower())){
        variables.stream()
            .filter(x -> x.getPower() == variable.getPower())
            .forEach(y -> y.addTo(variable));
        }
        else
            variables.add(variable);
        return this;
    }

    public  List<Double> getCoefficients(){
        return variables.stream()
            .sorted(Comparator.comparingInt(Variable::getPower))
            .map(Variable::getCoefficient)
            .collect(Collectors.toList());
    }


}
