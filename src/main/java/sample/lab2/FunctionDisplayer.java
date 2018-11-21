package sample.lab2;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class FunctionDisplayer implements Displayer {
    private Set<Double> roots;
    private Set<Set<Double>> lefts;
    private Set<Set<Double>> rights;

    public FunctionDisplayer() {
        lefts = new LinkedHashSet<>();
        rights = new LinkedHashSet<>();
    }

    public Set<Double> getRoots() {
        return roots;
    }

    public void setRoots(Set<Double> roots) {
        this.roots = roots;
    }

    public void addLeft(Set<Double> left) {
        lefts.add(left);
    }

    public void addRights(Set<Double> right) {
        rights.add(right);
    }

    private void setLeftsRights(Iterator<Double> left,
                                Iterator<Double> right,
                                StringBuilder builder) {
        while (left.hasNext() && right.hasNext()) {
            builder.append("left = ").append(left.next()).append("\n");
            builder.append("right = ").append(right.next()).append("\n");
        }
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Set<Double>> leftIterator = lefts.iterator();
        Iterator<Set<Double>> rightIterator = rights.iterator();
        int i = 0;
        if (roots != null) {
            for (Double root : roots) {
                setLeftsRights(leftIterator.next().iterator(),
                        rightIterator.next().iterator(), builder);
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
