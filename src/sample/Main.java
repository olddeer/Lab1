package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("LevadnyAndSutydinCompany");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
