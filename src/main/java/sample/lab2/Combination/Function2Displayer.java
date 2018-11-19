package sample.lab2.Combination;

import sample.lab2.Displayer;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Function2Displayer implements Displayer {
    private Set<Double> roots;
    private Set<Set<Boolean>> signs;
    private Set<Set<Double>> lefts;
    private Set<Set<Double>> rights;

    public Function2Displayer() {
        signs =new LinkedHashSet<>();
        lefts = new LinkedHashSet<>();
        rights = new LinkedHashSet<>();
    }

    public Set<Double> getRoots() {
        return roots;
    }

    public void setRoots(Set<Double> roots) {
        this.roots = roots;
    }

    public void addSigns(Set<Boolean> signs) {
        this.signs.add(signs);
    }

    public void addLeft(Set<Double> left) {
        lefts.add(left);
    }

    public void addRights(Set<Double> right) {
        rights.add(right);
    }

    private void setLeftsRights(Iterator<Double> left, Iterator<Double> right, Iterator<Boolean> sign, StringBuilder builder) {
        Boolean bool;
        int i=1;
        while (left.hasNext() && right.hasNext() && sign.hasNext()) {
            bool = sign.next();
            if (bool)
                builder.append("f'(x)*f''(x)>0").append("\n");
            else
                builder.append("f'(x)*f''(x)<0").append("\n");
            builder.append("a"+i+" = ").append(left.next()).append("\n");
            builder.append("b"+i+" = ").append(right.next()).append("\n");
        }
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Set<Double>> leftIterator = lefts.iterator();
        Iterator<Set<Double>> rightIterator = rights.iterator();
        Iterator<Set<Boolean>> signIterator = signs.iterator();
        int i = 0;
        if (roots != null) {
            for (Double root : roots) {
                setLeftsRights(leftIterator.next().iterator(),
                        rightIterator.next().iterator(), signIterator.next().iterator(), builder);
                builder.append("x_")
                        .append(i)
                        .append("=")
                        .append(root)
                        .append("\n");
                i++;
            }
        } else {
            builder.append("No roots");
        }
        return builder.toString();
    }

}
