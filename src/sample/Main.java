package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    public static PlayerTank[] players = new PlayerTank[2];
    public static List<HBox> views = new ArrayList<HBox>();
    public static StackPane root = new StackPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader startLoader = new FXMLLoader(getClass().getResource("Views/StartView.fxml"));
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("Views/GameView.fxml"));
        FXMLLoader endLoader = new FXMLLoader(getClass().getResource("Views/EndView.fxml"));
        VBox start = startLoader.load();
        HBox game = gameLoader.load();
        HBox  end = endLoader.load();
        views.add(game);
        views.add(end);
        root.getChildren().add(start);
        primaryStage.setTitle("Tank 1990: Rebirth");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        //Necessary global fields:
        //player  1 and 2 (for now: tanks(start, game), score(end) and controls(start -> game)
        //game map
    }


    public static void main(String[] args) {
        launch(args);
    }
}