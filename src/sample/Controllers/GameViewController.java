package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;

import javafx.animation.AnimationTimer;
import sample.Board;
import sample.Main;

import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static sample.Board.players;

//Handles events of main game window
//TO DO: create and connect start window and end game window
public class GameViewController {
    @FXML
    Label player1LivesLabel,player2LivesLabel, player1ScoreLabel, player2ScoreLabel;
    @FXML
    VBox menu, player1Box, player2Box;
    @FXML
    HBox enemyBox;
    @FXML
    Canvas canvas;
    public static AnimationTimer timer;
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int TileMeasurement = (int) canvas.getHeight() / 16; //each box is 32x32p2
        Board BattleField = new Board((int) canvas.getWidth() / TileMeasurement, (int)canvas.getHeight() / TileMeasurement, TileMeasurement);
        BattleField.GenerateMap();
        timer =
                new AnimationTimer() {
                    public void handle(long now) {
                        player1LivesLabel.setText("Lives: " + players[0].getLives());
                        player1ScoreLabel.setText("Score: " + players[0].getScore());
                        player2LivesLabel.setText("Lives: " + players[1].getLives());
                        player2ScoreLabel.setText("Score: " + players[1].getScore());
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        BattleField.UpdateBoard(Main.input, gc);
                    }
                };
    }


}

