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
    public void initialize() {
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

        Board BattleField = new Board((int) canvas.getWidth() / TileMeasurement, n, TileMeasurement);

        BattleField.GenerateMap();
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i == 1 && (j == 1)) {
                    //Eagle
                    PlainTile pt = new PlainTile(
                            BattleField.getWidth() / 2, BattleField.getHeight() - 1,
                            new Image[]{new Image(new File("Resources/Eagle/eagle.jpg").toURI().toString())},
                            false,
                            true,
                            1
                    );
                } else {
                    //Bricks around the eagle
                    PlainTile pt = new PlainTile(
                            BattleField.getWidth() / 2 - 1 + j, BattleField.getHeight() - 2 + i,
                            new Image[]{new Image(new File("Resources/Terrain/bricks.jpg").toURI().toString()),
                                    new Image(new File("Resources/Terrain/bricks.jpg").toURI().toString()),
                                    new Image(new File("Resources/Terrain/bricks.jpg").toURI().toString())},
                            false,
                            true,
                            3
                    );
                }
            }
        }
        Tank tank = new PlayerTank(
                BattleField.getWidth() / 2 - 2, BattleField.getHeight() - 2,
                (BattleField.getWidth() / 2 - 2) * TileMeasurement, (BattleField.getHeight() - 2) * TileMeasurement,
                new Image[]{GreenUp1Image, GreenUp2Image},
                new Image[]{GreenDown1Image, GreenDown2Image},
                new Image[]{GreenLeft1Image, GreenLeft2Image},
                new Image[]{GreenRight1Image, GreenRight2Image},
                new String[]{"UP", "DOWN", "LEFT", "RIGHT", "SLASH"}
        );

        Tank tank2 = new PlayerTank(
                BattleField.getWidth() / 2 + 2, BattleField.getHeight() - 2,
                (BattleField.getWidth() / 2 + 2) * TileMeasurement, (BattleField.getHeight() - 2) * TileMeasurement,
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

