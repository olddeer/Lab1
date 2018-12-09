package sample.lab4;

abstract public class AbstractInterpolateMethod {

    private final double Q;
    private double[][] substractionYValuesTable;

    public AbstractInterpolateMethod(double h, double[] initialX, double[] initialY) {
        substractionYValuesTable = new double[initialX.length][initialX.length];
        Q = initialX[0];
        substractionYValuesTable[0] = initialY;
    }

    public double[][] fillSubstractionYValuesTable() {
        int offsetCounter = 1;
        for (int i = 1; i < substractionYValuesTable.length; i++, offsetCounter++) {
            for (int j = 0; j < substractionYValuesTable.length - offsetCounter; j++) {
                substractionYValuesTable[i][j] = calculateYSubstractionValue(i, j);
            }
        }
        return substractionYValuesTable;
    }

    public double[][] getSubstractionYValuesTable() {
        return substractionYValuesTable;
    }

    public void setSubstractionYValuesTable(double[][] substractionYValuesTable) {
        this.substractionYValuesTable = substractionYValuesTable;
    }

    private double calculateYSubstractionValue(int i, int j) {
        return substractionYValuesTable[i - 1][j + 1] - substractionYValuesTable[i - 1][j];
    }

}
