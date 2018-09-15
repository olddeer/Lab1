package sample;

public class MethodChlesky {
    private int number_of_variables=3;//Число не известных
    private double []ArrayA; //Исходная матрица
    private double []ArrayB; //Нижне триугольная матрица
    private double []ArrayC; //Верхне триугольная матрица
    private double [] ArrayD; //исходня матрица ответов
    private double []ArrayResultSet;
    private String SolutionOfB = "";
    private String SolutionOfC = "";
    private String SolutionOfY = "";
    private String SolutionOfX = "";
    private String SolutionOfB_1 = "";

    public String getSolutionOfX() {
        return SolutionOfX;
    }

    public void setSolutionOfX(String solutionOfX) {
        SolutionOfX = solutionOfX;
    }

    public String getSolutionOfY() {
        return SolutionOfY;
    }

    public void setSolutionOfY(String solutionOfY) {
        SolutionOfY = solutionOfY;
    }

    public String getSolutionOfC() {
        return SolutionOfC;
    }

    public void setSolutionOfC(String solutionOfC) {
        SolutionOfC = solutionOfC;
    }

    public String getSolutionOfB() {
        return SolutionOfB;
    }

    public void setSolutionOfB(String solutionOfB) {
        SolutionOfB = solutionOfB;
    }

    public double[] getArrayD() {
        return ArrayD;
    }

    public void setArrayD(double[] arrayD) {
        ArrayD = arrayD;
    }

    public double[] getArrayResultSet() {
        return ArrayResultSet;
    }

    public void setArrayResultSet(double[] arrayResultSet) {
        ArrayResultSet = arrayResultSet;
    }

    public double[] getArrayA() {
        return ArrayA;
    }

    public void setArrayA(double[] arrayA) {
        ArrayA = arrayA;
    }

    public double[] getArrayB() {
        return ArrayB;
    }

    public void setArrayB(double[] arrayB) {
        ArrayB = arrayB;
    }

    public double[] getArrayC() {
        return ArrayC;
    }

    public void setArrayC(double[] arrayC) {
        ArrayC = arrayC;
    }

    public MethodChlesky(double []ArrayA, double [] ArrayD){
        this.ArrayA = ArrayA;
        ArrayB = new double[number_of_variables*number_of_variables];
        ArrayC = new double[number_of_variables*number_of_variables];
        this.ArrayD = ArrayD;
        ArrayResultSet = new double[2*number_of_variables];
    }

    private int SizeOfArrays(){
        int count = number_of_variables;
        int result=1+number_of_variables;
        while(2<count){
            count--;
            result+=count;
        }
        return result;
    }

    private int Ilteratol(int i,int j){
        return i*number_of_variables-(number_of_variables+1-j);
    }

    private double SumOfLineForB(int i,int j,int il){
        double result=0;
        for(int k=1;k<=il;k++) {
            if(k!=il) {
                SolutionOfB += "b(" + i + k + ")*c(" + k + j + ")+";
                SolutionOfB_1+=bacMin(ArrayB[Ilteratol(i, k)])+"*"+bacMin(ArrayC[Ilteratol(k, j)])+"+";
            }
            else {
                SolutionOfB += "b(" + i + k + ")*c(" + k + j + ")";
                SolutionOfB_1+=bacMin(ArrayB[Ilteratol(i, k)])+"*"+bacMin(ArrayC[Ilteratol(k, j)]);
            }
            result += ArrayB[Ilteratol(i, k)] * ArrayC[Ilteratol(k, j)];
        }
        return result;
    }

    private double SumOfLineForC(int i,int j,int il){
        double result=0;
        for(int k=1;k<=il;k++) {
            if(k!=il) {
                SolutionOfC += "b(" + i + k + ")*c(" + k + j + ")+";
                SolutionOfB_1+=bacMin(ArrayB[Ilteratol(i, k)])+"*"+bacMin(ArrayC[Ilteratol(k, j)])+"+";
            }
            else {
                SolutionOfC += "b(" + i + k + ")*c(" + k + j + ")";
                SolutionOfB_1+=bacMin(ArrayB[Ilteratol(i, k)])+"*"+bacMin(ArrayC[Ilteratol(k, j)]);
            }
            result += ArrayB[Ilteratol(i, k)] * ArrayC[Ilteratol(k, j)];
        }
        return result;
    }

    private void InitializeArrays(){
        for (int i=1;i<=number_of_variables;i++) {
            for (int j = 1; j <= number_of_variables; j++) {
                if(j==1) {
                    ArrayB[Ilteratol(i, j)] = ArrayA[Ilteratol(i, j)];
                    SolutionOfB+="b("+i+j+")=a("+i+j+")= "+ArrayB[Ilteratol(i, j)]+"\n";
                }
                if(i==1) {
                    ArrayC[Ilteratol(i, j)] = ArrayA[Ilteratol(i, j)] / ArrayB[0];
                    SolutionOfC+="c("+i+j+")=a("+i+j+")/b(11)= "+ArrayA[Ilteratol(i, j)]+"/"+bacMin(ArrayB[0])+"= "+ArrayC[Ilteratol(i, j)]+"\n";
                }
                if(j==i) {
                    ArrayC[Ilteratol(i, j)] = 1;
                    SolutionOfC+="c("+i+j+")= "+ ArrayC[Ilteratol(i, j)]+"\n";
                }
                if(i>=j&&j>1) {
                    SolutionOfB+="b("+i+j+")=a("+i+j+")-(";
                    ArrayB[Ilteratol(i, j)] = ArrayA[Ilteratol(i, j)] - SumOfLineForB(i, j, j - 1);
                    SolutionOfB+=")="+ArrayA[Ilteratol(i, j)]+"-("+SolutionOfB_1+")="+ ArrayB[Ilteratol(i, j)]+"\n";
                    SolutionOfB_1="";
                }
                if(1<i&&i<j) {
                    SolutionOfC+="c("+i+j+")=(1/b("+i+i+"))*(a("+i+j+")-(";
                    ArrayC[Ilteratol(i, j)] = (1 / ArrayB[Ilteratol(i, i)]) * (ArrayA[Ilteratol(i, j)] - SumOfLineForC(i, j, i - 1));
                    SolutionOfC+="))=1/"+bacMin(ArrayA[Ilteratol(i, j)])+"-("+SolutionOfB_1+")="+ ArrayC[Ilteratol(i, j)]+"\n";
                    SolutionOfB_1 = "";
                }
            }
        }
    }

    public boolean isZeroDiagonal(){
        for(int i=1;i<=number_of_variables;i++)
            if (ArrayA[Ilteratol(i, i)] == 0)
                return true;
        return false;
    }

    public boolean isZeroDiagonal(double [] array){
        for(int i=1;i<=number_of_variables;i++)
            if (array[Ilteratol(i, i)] == 0)
                return true;
        return false;
    }

    public boolean isZeroMinor(){
        if(Minor(4,8,5,7)==0)
            return true;
        if(Minor(0,8,2,6)==0)
            return true;
        if(Minor(0,4,1,3)==0)
            return true;
        return false;
    }

    private double Minor(int i,int j,int k,int r){
        return ArrayA[i]*ArrayA[j]-ArrayA[k]*ArrayA[r];
    }

    public static boolean isZeroMinor(double []arr){
        if(Minor(4,8,5,7,arr)==0)
            return true;
        if(Minor(0,8,2,6,arr)==0)
            return true;
        if(Minor(0,4,1,3,arr)==0)
            return true;
        return false;
    }

    private static double Minor(int i,int j,int k,int r,double [] arr){
        return arr[i]*arr[j]-arr[k]*arr[r];
    }

    public double SumForResultY(int il,int i){
        double result=0;
        for(int k=1;k<=il;k++) {
            if(k!=il) {
                SolutionOfY += "b(" + i + k + ")*y" + k + "+";
                SolutionOfB_1+=bacMin(ArrayB[Ilteratol(i, k)])+"*"+bacMin(ArrayResultSet[k - 1])+"+";
            }else{
                SolutionOfY += "b(" + i + k + ")*y" + k;
                SolutionOfB_1+=bacMin(ArrayB[Ilteratol(i, k)])+"*"+bacMin(ArrayResultSet[k - 1]);
            }
            result += ArrayB[Ilteratol(il+1, k)] * ArrayResultSet[k - 1];
        }
        return result;
    }

    public double SumForResultX(int i){
        double result=0;
        for(int k=1+i;k<=number_of_variables;k++) {
            if(k!=number_of_variables) {
                SolutionOfX += "c(" + i + k + ")*x" + k + "+";
                SolutionOfB_1+=bacMin(ArrayC[Ilteratol(i, k)])+"*"+bacMin(ArrayResultSet[number_of_variables + k - 1])+"+";
            }else{
                SolutionOfX += "c(" + i + k + ")*x" + k ;
                SolutionOfB_1+=bacMin(ArrayC[Ilteratol(i, k)])+"*"+bacMin(ArrayResultSet[number_of_variables + k - 1]);
            }
            result += ArrayC[Ilteratol(i, k)] * ArrayResultSet[number_of_variables + k - 1];
        }
        return result;
    }

    private Object bacMin(double tmp){
        if(tmp<0)
            return "("+tmp+")";
        return tmp;
    }
    public void DoMethod(){
        this.InitializeArrays();
        SolutionOfY+="y1=d(1)/b(11)= ";
        ArrayResultSet[0]= ArrayD[0]/ArrayB[0];
        SolutionOfY+=ArrayResultSet[0]+"\n";
        for(int i=2;i<=number_of_variables;i++) {
            SolutionOfY+="y"+i+"=(1/b("+i+i+"))*(d("+i+")-(";
            ArrayResultSet[i - 1] = (1 / ArrayB[Ilteratol(i, i)]) * (ArrayD[i - 1] - SumForResultY(i - 1,i));
            SolutionOfY+=")= "+"(1/"+ bacMin(ArrayB[Ilteratol(i, i)])+")*("+ ArrayD[i - 1]+"-("+SolutionOfB_1+"))= "+ArrayResultSet[i - 1]+"\n";
            SolutionOfB_1="";
        }
        ArrayResultSet[2*number_of_variables-1]=ArrayResultSet[number_of_variables-1];
        SolutionOfX+="x3=y3= "+ ArrayResultSet[2*number_of_variables-1]+"\n";
        for(int i=number_of_variables-1;i>=1;i--) {
            SolutionOfX+="x"+i+"=y"+i+"-(";
            ArrayResultSet[number_of_variables + i - 1] = ArrayResultSet[i - 1] - SumForResultX(i);
            SolutionOfX+=")= "+ArrayResultSet[i - 1]+"-("+SolutionOfB_1+")= "+ArrayResultSet[number_of_variables + i - 1]+"\n";
            SolutionOfB_1="";
        }
    }
}
