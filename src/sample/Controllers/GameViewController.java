package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Board;
import sample.Main;

import static sample.Board.players;


public class GameViewController {
    @FXML
    Label player1LivesLabel,player2LivesLabel, player1ScoreLabel, player2ScoreLabel;
    @FXML
    VBox menu, player1Box, player2Box;
    @FXML
    HBox enemyBox;
    @FXML
    Canvas canvas;
    @FXML
    Button endGameButton;

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
                        if(players[0].getLives()==0 && players[1].getLives()==0)
                        {
                            gc.setFill(Color.RED);
                            gc.setFont(new Font("Calibri",50));
                            gc.fillText("GAME OVER",269,200);
                        }
                        else{

                            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            BattleField.UpdateBoard(Main.input, gc);
                        }
                    }
                };
    }
    @FXML
    private void EndGame(MouseEvent e){
        Main.root.getChildren().remove(0);
        timer.stop();
        Main.root.getChildren().add(sample.Main.views.get(1));
    }


}

