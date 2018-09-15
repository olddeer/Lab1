package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    //Radiobuttons for chosen method
    public RadioButton Cholesky;//
    public RadioButton Gauss;
    //Buttons
    public Button SolveButn;//Show the solution
    public Button PrintButn;//Safe the report
    public Button AboutButn;//Show the theory of the methods
    //Thare will be a solution
    public TextArea SolutionAre;
    final int variables = 3; //number of variables which user needs to find
    double ArrayA []= new double[variables*variables]; // Array of input data of the coefficients
    double ArrayD []= new double[variables]; // Array of input data
    //
    MethodChlesky methodChlesky;


    @FXML //Action for the button "Solve"
    private void ActionForSolution(){
        if (!isNotNumberAndEmpty()) //Checking a field for is not number and empty
            if(!isAllRadioButtonsOff(Cholesky,Gauss)) //Checking a radiobuttons for being selected
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
                }else{ //Your method is chosen

                }

    }

    //Fill Soution area by Chlessky
    private String FillAreaByChlessky(){
        String s="";
        s+="Matrix A:\n";
        for(int i=0;i<variables*variables;i++) {
            s += ArrayA[i]+" ";
            if((i+1)%variables==1)
                s+='\n';
        }
        s+="Solve the matrix B\n";
        s+=methodChlesky.getSolutionOfB();
        s+="Matrix B:\n";
        for(int i=0;i<variables*variables;i++) {
            s += methodChlesky.getArrayB()[i]+" ";
            if((i+1)%variables==1)
                s+='\n';
        }
        s+="Solve the matrix C\n";
        s+=methodChlesky.getSolutionOfC();
        s+="Matrix C:\n";
        for(int i=0;i<variables*variables;i++) {
            s += methodChlesky.getArrayC()[i]+" ";
            if((i+1)%variables==1)
                s+='\n';
        }
        s+="Find the all y:\n";
        s+=methodChlesky.getSolutionOfY();
        s+="Find the all x:\n";
        s+=methodChlesky.getSolutionOfX();
        return s;
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
        for(int i=0;i<button.length;i++){
            if(button[i].isSelected())
                return false;
        }
        Message("Please, choose the method of solution");
        return true;
    }

    //Dialog which says that user need to do
    private void Message(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please, follow the instructions below!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
