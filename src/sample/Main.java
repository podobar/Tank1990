package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.animation.AnimationTimer;
import javafx.stage.StageStyle;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

//region Layout
        HBox root = new HBox();
        primaryStage.setTitle("Tank 1990");
        //primaryStage.setResizable(false);

        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(800, 512);
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        HBox enemyBox = new HBox();
        VBox player1Box = new VBox();
        VBox player2Box = new VBox();
        Button newGameButton = new Button("Reset");
        player1Box.getChildren().addAll(new Label("Lives"), new Label("Score"));
        player2Box.getChildren().addAll(new Label("Lives"), new Label("Score"));
        canvas.setStyle(
                "-fx-background-color: black;"
        );
        menu.setStyle(
                "-fx-background-color: grey;" +
                        "-fx-min-height: 512;"
        );
        enemyBox.setStyle(
                "-fx-background-color: pink;" +
                        "-fx-min-width: 180px;" +
                        "-fx-min-height: 150px;"+
                        "-fx-padding: 20px;"
        );
        player1Box.setStyle(
                "-fx-background-color: yellow;" +
                        "-fx-min-width: 180px;" +
                        "-fx-min-height: 100px;"+
                        "-fx-padding: 20px;"
        );
        player2Box.setStyle(
                "-fx-background-color: green;" +
                        "-fx-min-width: 180px;" +
                        "-fx-min-height: 100px;"+
                        "-fx-padding: 20px;"
        );
        newGameButton.setStyle(
                "-fx-min-width: 100;" +
                        "-fx-min-height: 50px;"
        );
        root.getChildren().addAll(canvas,menu);
        menu.getChildren().addAll(enemyBox,player1Box,player2Box,newGameButton);

//endregion
        GraphicsContext gc = canvas.getGraphicsContext2D();

        File RoboImageFile = new File("Robo.jpg");
        Image RoboImage = new Image(RoboImageFile.toURI().toString());
        gc.drawImage(RoboImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.RED);
        gc.fill();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        Font theFont = Font.font("Comic Sans", FontWeight.BOLD, 48);
        gc.setFont(theFont);
//region Loading resources
        File TankImageFile = new File("Resources/Tanks/Green/up1.png");
        Image GreenUp1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/up2.png");
        Image GreenUp2Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/down1.png");
        Image GreenDown1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/down2.png");
        Image GreenDown2Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/left1.png");
        Image GreenLeft1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/left2.png");
        Image GreenLeft2Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/right1.png");
        Image GreenRight1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Green/right2.png");
        Image GreenRight2Image = new Image(TankImageFile.toURI().toString());


        TankImageFile = new File("Resources/Tanks/Blue/up1.png");
        Image BlueUp1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/up2.png");
        Image BlueUp2Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/down1.png");
        Image BlueDown1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/down2.png");
        Image BlueDown2Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/left1.png");
        Image BlueLeft1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/left2.png");
        Image BlueLeft2Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/right1.png");
        Image BlueRight1Image = new Image(TankImageFile.toURI().toString());

        TankImageFile = new File("Resources/Tanks/Blue/right2.png");
        Image BlueRight2Image = new Image(TankImageFile.toURI().toString());
//endregion
//region Board and players generation
        int n = 16;
        int TileMeasurement = (int) canvas.getHeight() / n;
        Board BattleField = new Board(n, n, TileMeasurement);

        BattleField.GenerateMap();
        Tank tank = new PlayerTank(
                0, 0,
                BattleField.getTile(0, 0).IX, BattleField.getTile(0, 0).IY,
                new Image[]{GreenUp1Image, GreenUp2Image},
                new Image[]{GreenDown1Image, GreenDown2Image},
                new Image[]{GreenLeft1Image, GreenLeft2Image},
                new Image[]{GreenRight1Image, GreenRight2Image},
                new String[]{"UP", "DOWN", "LEFT", "RIGHT"}
        );

        Tank tank2 = new PlayerTank(
                BattleField.getWidth() - 1, BattleField.getHeight() - 1,
                BattleField.getTile(BattleField.getWidth() - 1, BattleField.getHeight() - 1).IX, BattleField.getTile(BattleField.getWidth() - 1, BattleField.getHeight() - 1).IY,
                new Image[]{BlueUp1Image, BlueUp2Image},
                new Image[]{BlueDown1Image, BlueDown2Image},
                new Image[]{BlueLeft1Image, BlueLeft2Image},
                new Image[]{BlueRight1Image, BlueRight2Image},
                new String[]{"W", "S", "A", "D"}
        );

        BattleField.AddMovingTile(tank);
        BattleField.AddMovingTile(tank2);

        final long startNanoTime = System.nanoTime();

        ArrayList<String> input = new ArrayList<>();

        theScene.setOnKeyPressed(
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
                    }
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    if (input.contains(code))
                        input.remove(code);
                });
//endregion
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
        // Animated explosion (named UFO :P)
        File file;
        AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[5];
        for (int i = 1; i < 6; i++) {
            file = new File("Resources/Explosion1/" + i + ".png");
            imageArray[i - 1] = new Image(file.toURI().toString());
        }
        ufo.frames = imageArray;
        ufo.duration = 0.750;


        final long startNanoTime = System.nanoTime();

        Random random = new Random(13);


        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                gc.setGlobalAlpha(0.5);
                gc.drawImage(RoboImage, 0, 0, canvas.getWidth(), canvas.getHeight());

                gc.setGlobalAlpha(0.75);
                gc.fillText("Привет, товарищ!", x, y);
                gc.strokeText("Привет, товарищ!", x, y);

                gc.setGlobalAlpha(1);

                BattleField.UpdateBoard(input, gc);
            }
        }.start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}