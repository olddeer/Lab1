package sample.lab2.Combination;

import sample.lab2.Displayer;

import java.util.*;

public class Function2Displayer implements Displayer {
    private Set<Double> roots;
    private List<List<Boolean>> signs;
    private List<List<Double>> derivate1;
    private List<List<Double>> derivate2;
    private List<List<Double>> x;
    private Set<Set<Double>> lefts;
    private Set<Set<Double>> rights;

    public Function2Displayer() {
        signs = new LinkedList<>();
        lefts = new LinkedHashSet<>();
        rights = new LinkedHashSet<>();
        derivate1 = new LinkedList<>();
        derivate2 = new LinkedList<>();
        x = new LinkedList<>();
    }

    public Set<Double> getRoots() {
        return roots;
    }

    public void setRoots(Set<Double> roots) {
        this.roots = roots;
    }

    public void addSigns(List<Boolean> signs) {
        this.signs.add(signs);
    }

    public void addLeft(Set<Double> left) {
        lefts.add(left);
    }

    public void addRights(Set<Double> right) {
        rights.add(right);
    }

    public void addX(List<Double> x) {
        this.x.add(x);
    }

    public void addDerivate1(List<Double> derivate) {
        derivate1.add(derivate);
    }

    public void addDerivate2(List<Double> derivate) {
        derivate2.add(derivate);
    }

    private void setLeftsRights(Iterator<Double> left, Iterator<Double> right, Iterator<Boolean> sign,
                                Iterator<Double> derivate1, Iterator<Double> derivate2, Iterator<Double> x,
                                StringBuilder builder) {
        Boolean bool;
        int i = 1;
        while (left.hasNext() && right.hasNext() && sign.hasNext()) {
            builder.append("Итерация "+i).append("\n");
            builder.append("(a"+(i-1)+"+b"+(i-1)+")/2 = ").append(x.next()).append("\n");
            builder.append("f'(x)= ").append(derivate1.next()).append("\n");
            builder.append("f''(x)= ").append(derivate2.next()).append("\n");
            bool = sign.next();
            if (bool)
                builder.append("f'(x)*f''(x)>0").append("\n");
            else
                builder.append("f'(x)*f''(x)<0").append("\n");
            builder.append("a" + i + " = ").append(left.next()).append("\n");
            builder.append("b" + i + " = ").append(right.next()).append("\n");
            i++;
        }
    }

    @Override
    public String display() {
        StringBuilder builder = new StringBuilder();
        Iterator<Set<Double>> leftIterator = lefts.iterator();
        Iterator<Set<Double>> rightIterator = rights.iterator();
        Iterator<List<Boolean>> signIterator = signs.iterator();
        Iterator<List<Double>> xIterator = x.iterator();
        Iterator<List<Double>> derivate1Iterator = derivate1.iterator();
        Iterator<List<Double>> derivate2Iterator = derivate2.iterator();
        int i = 0;
        if (roots != null) {
            for (Double root : roots) {
                setLeftsRights(leftIterator.next().iterator(),
                        rightIterator.next().iterator(), signIterator.next().iterator(),
                        derivate1Iterator.next().iterator(),derivate2Iterator.next().iterator(),
                        xIterator.next().iterator(),builder);
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
