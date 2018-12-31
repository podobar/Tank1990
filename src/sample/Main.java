package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMap.fxml"));
        Parent game = loader.load();
        primaryStage.setTitle("Tank 1990: Rebirth");
        primaryStage.setScene(new Scene(game));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}