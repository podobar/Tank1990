package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    public static List<HBox> views = new ArrayList<HBox>();
    public static StackPane root = new StackPane();
    public static ArrayList<String> input = new ArrayList<>();
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
        Scene theScene = new Scene(root);

        primaryStage.setScene(theScene);
        game.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            if (input.contains(code))
                input.remove(code);
        });
        game.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();

                    // Prevent duplicates
                    if (!input.contains(code)) {
                        input.add(code);

                        if (code == "UP" && input.contains("DOWN"))
                            input.remove("DOWN");

                        else if (code == "DOWN" && input.contains("UP"))
                            input.remove("UP");

                        else if (code == "LEFT" && input.contains("RIGHT"))
                            input.remove("RIGHT");

                        else if (code == "RIGHT" && input.contains("LEFT"))
                            input.remove("LEFT");
                    }});

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}