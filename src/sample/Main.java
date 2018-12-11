package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tank 1990");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        Canvas c =new Canvas(500,300);
        Image img = new Image(300,200);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
