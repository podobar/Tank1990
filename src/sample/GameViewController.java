package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

//Handles events of main game window
//TO DO: create and connect start window and end game window
public class GameViewController {
    @FXML
    VBox menu, player1Box, player2Box;
    @FXML
    HBox enemyBox;
    @FXML
    Canvas canvas;
    public static AnimationTimer timer;
    public void initialize()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
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
        int n = 16;
        int TileMeasurement = (int) canvas.getHeight() / n;

        //Main.BattleField = new Board(n, n, TileMeasurement);
        Board BattleField = new Board(n, n, TileMeasurement);

        //Main.BattleField.GenerateMap();
        BattleField.GenerateMap();
        Tank tank = new PlayerTank(
                0, 0,
                BattleField.getTile(0, 0).IX, BattleField.getTile(0, 0).IY,
                new Image[]{GreenUp1Image, GreenUp2Image},
                new Image[]{GreenDown1Image, GreenDown2Image},
                new Image[]{GreenLeft1Image, GreenLeft2Image},
                new Image[]{GreenRight1Image, GreenRight2Image},
                new String[]{"UP", "DOWN", "LEFT", "RIGHT", "SLASH"}
        );

        Tank tank2 = new PlayerTank(
                BattleField.getWidth() - 1, BattleField.getHeight() - 1,
                BattleField.getTile(BattleField.getWidth() - 1, BattleField.getHeight() - 1).IX, BattleField.getTile(BattleField.getWidth() - 1, BattleField.getHeight() - 1).IY,
                new Image[]{BlueUp1Image, BlueUp2Image},
                new Image[]{BlueDown1Image, BlueDown2Image},
                new Image[]{BlueLeft1Image, BlueLeft2Image},
                new Image[]{BlueRight1Image, BlueRight2Image},
                new String[]{"W", "S", "A", "D", "G"}
        );

        BattleField.AddMovingTile(tank);
        BattleField.AddMovingTile(tank2);

        timer =
            new AnimationTimer() {
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                BattleField.UpdateBoard(Main.input, gc);
            }
        };
    }


}

