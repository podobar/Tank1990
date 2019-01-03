package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
    public static PlayerTank[] players = new PlayerTank[2];
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/GameView.fxml"));
        Parent game = loader.load();
        primaryStage.setTitle("Tank 1990: Rebirth");
        primaryStage.setScene(new Scene(game));
        primaryStage.show();

        //Necessary global fields:
        //player  1 and 2 (for now: tanks(start, game), score(end) and controls(start -> game)
        //game map
        //

    }


    public static void main(String[] args) {
        launch(args);
    }
}