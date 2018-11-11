package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import sample.lab1.GaussSeidel;
import sample.lab1.MethodChlesky;
import sample.lab2.DihotomyMethodSolver;

public class Controller {
    //Field for input data
    public TextField variableX11;
    public TextField variableX12;
    public TextField variableX13;
    public TextField variableX21;
    public TextField variableX22;
    public TextField variableX23;
    public TextField variableX31;
    public TextField variableX32;
    public TextField variableX33;
    public TextField variableD1;
    public TextField variableD2;
    public TextField variableD3;
    public TextField FunctionField;

    //Radiobuttons for chosen method
    public RadioButton Cholesky;//
    public RadioButton Gauss;
    public RadioButton DichotomyRadio;
    public RadioButton LiovaMethodRadio;
    //Buttons
    public Button SolveButn;//Show the solution
    public Button PrintButn;//Safe the report
    public Button AboutButn;//Show the theory of the methods
    //Thare will be a solution
    public TextArea SolutionAre;
    public TextArea Lab2Area;
    final int variables = 3; //number of variables which user needs to find
    double ArrayA []= new double[variables*variables]; // Array of input data of the coefficients
    double ArrayD []= new double[variables]; // Array of input data
    //
    MethodChlesky methodChlesky;


    @FXML //Action for the button "Solve"
    private void ActionForSolution(){
        if (!isNotNumberAndEmpty()) //Checking a field for is not number and empty
            if(isAllRadioButtonsOff(Cholesky, Gauss)) //Checking a radiobuttons for being selected
                if(Cholesky.isSelected()) //My method is chosen
                {
                    FillArrays();
                    methodChlesky = new MethodChlesky(ArrayA,ArrayD);
                    if(methodChlesky.isZeroDiagonal())
                        Message("Method Chlesky demands no zero from a main diagonal");
                    else
                        if(methodChlesky.isZeroMinor())
                            Message("Method Chlesky demands no zero from the minors of a main diagonal");
                        else{
                            methodChlesky.DoMethod();
                            SolutionAre.setText(FillAreaByChlessky());

                        }
                }else{
                    SolutionAre.setText(solveEquationByGaussSeidel());
                }

    }

    @FXML
    private void SaveAndOpenReport(){
        if (!isNotNumberAndEmpty()) //Checking a field for is not number and empty
            if(isAllRadioButtonsOff(Cholesky, Gauss)) //Checking a radiobuttons for being selected
                    FillArrays();
                    methodChlesky = new MethodChlesky(ArrayA, ArrayD);
                    if (methodChlesky.isZeroDiagonal())
                        Message("Method Chlesky demands no zero from a main diagonal");
                    else if (methodChlesky.isZeroMinor())
                        Message("Method Chlesky demands no zero from the minors of a main diagonal");
                    else {
                        methodChlesky.DoMethod();

                        String Head = "<html>\n" +
                                "<head>\n" +
                                "\t<title>Report</title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "<h1 align=\"center\">Method Cholesky</h1>\n" +
                                "<hr>\n" +
                                "<h2> The converted system of equations in the matrix</h2>" +
                                "<pre>"+FillAreaByChlessky() +"</pre>" +
                            "<h1 align=\"center\">Method Gauss Seidel</h1>\n" +
                            "<hr>\n" +
                            "<h2>Solution</h2>" +
                            "<pre>"+solveEquationByGaussSeidel() +"</pre>"+
                            "</body>\n" +
                            "</html>";

                        FileChooser fileChooser = getFileChooserHtmL("Save Report");
                        File file;
                        if ((file = fileChooser.showSaveDialog(null)) != null) {
                            try {
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                                    bw.write(Head);
                                    bw.close();
                                    file.getCanonicalPath();
                                    try {
                                        Desktop.getDesktop().browse(file.toURI());
                                    } catch (IOException e) {
                                        Message("Sorry, but can't open a theory about Chlesky.\nPlease, try one more time.");
                                    }
                                }
                            } catch (IOException e) {
                                Message("Sorry, but can't save the report. Please, try one more time.");
                            }
                        }
                    }
                }


    public static FileChooser getFileChooserHtmL(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
        fileChooser.setTitle(title);
        return fileChooser;
    }

    @FXML
    public void SolveEquationLab2(ActionEvent actionEvent) {
            if(DichotomyRadio.isSelected())
            {
                DihotomyMethodSolver methodSolver = new DihotomyMethodSolver(FunctionField.getText());

                    Lab2Area.setText(methodSolver.solve()+"");


            }else{
                //SolutionAre.setText(solveEquationByGaussSeidel());
            }
    }

    //Open theory Cholesky
    @FXML
    private void OpenTheoryAboutCholesky(){
        File file = new File("src/sample/lab1/AboutMethodChlesky.htm");
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            Message("Sorry, but can't open a theory about Chlesky.\nPlease, try one more time.");
    }

    }

    @FXML
    private  void OpenTheoryAboutGaussSeidel(){

        File file = new File("src/sample/lab1/AboutGaussSeidel.pdf");
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
            Message("Sorry, but can't open a theory about Gauss Seidel.\nPlease, try one more time.");
        }
    }

    private  String solveEquationByGaussSeidel(){
        FillArrays();
        double [][]M = new double[ArrayD.length][ArrayD.length+1];
        for (int i=0,h=0;i<M.length;i++){
            for (int j= 0;j<M.length;j++){
                M[i][j] = ArrayA[h];
                h++;
            }
            M[i][M.length]=ArrayD[i];
        }
        GaussSeidel gaussSeidel = new GaussSeidel(M);
        gaussSeidel.solve();
        StringBuilder sb = new StringBuilder();
        sb.append(gaussSeidel.getArrayB());
        sb.append(gaussSeidel.getArrayX());
        return sb.toString();
    }

    //Fill Soution area by Chlessky
    private String FillAreaByChlessky(){
        StringBuilder s= new StringBuilder();
        s.append("Matrix A:\n");
        for(int i=0,j=1;i<variables*variables;i++) {
            if((i+1)%(variables*j)==1&&i!=0){
                s.append("\n");
                j++;
            }
            s.append(round(ArrayA[i])).append(" ");
        }
        s.append("\n\nSolve the matrix B\n");
        s.append(methodChlesky.getSolutionOfB());
        s.append("Matrix B:\n");
        for(int i=0,j=1;i<variables*variables;i++) {
            if((i+1)%(variables*j)==1&&i!=0){
                s.append("\n");
                j++;
            }
            s.append(round(methodChlesky.getArrayB()[i])).append(" ");
        }
        s.append("\n\nSolve the matrix C\n");
        s.append(methodChlesky.getSolutionOfC());
        s.append("Matrix C:\n");
        for(int i=0,j=1;i<variables*variables;i++) {
            if((i+1)%(variables*j)==1&&i!=0){
                s.append("\n");
                j++;
            }
            s.append(round(methodChlesky.getArrayC()[i])).append(" ");
        }
        s.append("\n\nFind the all y:\n");
        s.append(methodChlesky.getSolutionOfY());
        s.append("Find the all x:\n");
        s.append(methodChlesky.getSolutionOfX());
        return s.toString();
    }

    //Arrays is fill of input data for method Chlessky
    private void FillArrays(){
        ArrayA[0] = Double.valueOf(variableX11.getText());
        ArrayA[1] = Double.valueOf(variableX12.getText());
        ArrayA[2] = Double.valueOf(variableX13.getText());
        ArrayA[3] = Double.valueOf(variableX21.getText());
        ArrayA[4] = Double.valueOf(variableX22.getText());
        ArrayA[5] = Double.valueOf(variableX23.getText());
        ArrayA[6] = Double.valueOf(variableX31.getText());
        ArrayA[7] = Double.valueOf(variableX32.getText());
        ArrayA[8] = Double.valueOf(variableX33.getText());
        ArrayD[0] = Double.valueOf(variableD1.getText());
        ArrayD[1] = Double.valueOf(variableD2.getText());
        ArrayD[2] = Double.valueOf(variableD3.getText());
    }

    //Method which checks a field for is not number and empty
    private boolean isNotNumberAndEmpty(){
        if(variableX11.getText().isEmpty()||variableX12.getText().isEmpty()||variableX13.getText().isEmpty()) {
            Message("One (or more) fields is empty. Please, fill out them all.");
            return true;
        }
        if(variableX21.getText().isEmpty()||variableX22.getText().isEmpty()||variableX23.getText().isEmpty()) {
            Message("One (or more) fields is empty. Please, fill out them all.");
            return true;
        }
        if(variableX31.getText().isEmpty()||variableX32.getText().isEmpty()||variableX33.getText().isEmpty()) {
            Message("One (or more) fields is empty. Please, fill out them all.");
            return true;
        }
        if(variableD1.getText().isEmpty()||variableD2.getText().isEmpty()||variableD3.getText().isEmpty()){
            Message("One (or more) fields is empty. Please, fill out them all.");
            return true;
        }
        if(!variableX11.getText().matches("-?\\d+\\.?\\d*")||!variableX12.getText().matches("-?\\d+\\.?\\d*")
                ||!variableX13.getText().matches("-?\\d+\\.?\\d*")) {
            Message("One (or more) fields is not number. Please, check them out.");
            return true;
        }
        if(!variableX21.getText().matches("-?\\d+\\.?\\d*")||!variableX22.getText().matches("-?\\d+\\.?\\d*")
                ||!variableX23.getText().matches("-?\\d+\\.?\\d*")) {
            Message("One (or more) fields is not number. Please, check them out.");
            return true;
        }
        if(!variableX31.getText().matches("-?\\d+\\.?\\d*")||!variableX32.getText().matches("-?\\d+\\.?\\d*")
                ||!variableX33.getText().matches("-?\\d+\\.?\\d*")) {
            Message("One (or more) fields is not number. Please, check them out.");
            return true;
        }
        if(!variableD1.getText().matches("-?\\d+\\.?\\d*")||!variableD2.getText().matches("-?\\d+\\.?\\d*")
                ||!variableD3.getText().matches("-?\\d+\\.?\\d*")) {
            Message("One (or more) fields is not number. Please, check them out.");
            return true;
        }
       return false;
    }

    //Method which checks a radiobuttons for not being selected
    private boolean isAllRadioButtonsOff(RadioButton ... button){
        for (RadioButton aButton : button) {
            if (aButton.isSelected()) {
                return true;
            }
        }
        Message("Please, choose the method of solution");
        return false;
    }

    //Dialog which says that user need to do
    private void Message(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please, follow the instructions below!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String round(double d){
        if(d==0.0)
            return String.valueOf(0);
        d=d*100;
        int i = (int)d;
        d=(double)i;
        d=d/100;
        return String.valueOf(d);
    }

}
