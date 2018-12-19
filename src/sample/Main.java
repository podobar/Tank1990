package sample;

import javafx.application.Application;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Tank 1990");

        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(800, 512);
        root.getChildren().add(canvas);

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

        // Board generation
        int n = 16;
        int TileMeasurement = (int) canvas.getHeight() / n;
        Board BattleField = new Board(n, n, TileMeasurement);

        BattleField.GenerateMap();

//        Tile[][] Board = new Tile[n][n];
//
//        for(int i = 0; i < Board.length; i++)
//            for(int j = 0; j < Board[i].length; j++)
//            {
//                Board[i][j] = new PlainTile(i * FieldWidth, j * FieldWidth);
//            }


        Tank tank = new Tank(GreenRight1Image, 0, 0, GreenUp1Image, GreenDown1Image, GreenLeft1Image, GreenRight1Image);
        BattleField.AddMovingTile(tank);

        final long startNanoTime = System.nanoTime();

//        Random random = new Random(13);

        ArrayList<String> input = new ArrayList<String>();

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

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

//                Tile P1Location;

//                int tmpTextureChangeTime = 30;

                // Add some delay after each update
                // Change conditions of move to some IfPossible(x, y) function
                // Movement by changing some IDs in Board [BattleField]?

//                if (input.contains("UP")) {
//                    if (BattleField.IsMovementPossible(tank.IX, tank.IY - 1)) {
//                        tank.IY--;
//                    }
//                    tank.texture = GreenUpImage;
//                } else if (input.contains("DOWN")) {
//                    if (BattleField.IsMovementPossible(tank.IX, tank.IY + 1)) {
//                        tank.IY++;
//                    }
//                    tank.texture = GreenDownImage;
//                }
//                if (input.contains("LEFT")) {
//                    if (BattleField.IsMovementPossible(tank.IX - 1, tank.IY)) {
//                        tank.IX--;
//                    }
//                    tank.texture = GreenLeftImage;
//
//                } else if (input.contains("RIGHT")) {
//                    if (BattleField.IsMovementPossible(tank.IX + 1, tank.IY)) {
//                        tank.IX++;
//                    }
//                    tank.texture = GreenRightImage;
//                }
//
//                if(!tank.IsMoving) {
//                    if (input.contains("UP")) {
//                        if (BattleField.IsMovementPossible(tank.IX, tank.IY - 1)) {
//                            tank.IY--;
//                            tank.IsMoving = true;
//                            tank.Direction = "UP";
//                        }
//                        tank.texture = GreenUp1Image;
//                    } else if (input.contains("DOWN")) {
//                        if (BattleField.IsMovementPossible(tank.IX, tank.IY + 1)) {
//                            tank.IY++;
//                            tank.IsMoving = true;
//                            tank.Direction = "DOWN";
//                        }
//                        tank.texture = GreenDown1Image;
//                    }
//                    if (input.contains("LEFT")) {
//                        if (BattleField.IsMovementPossible(tank.IX - 1, tank.IY)) {
//                            tank.IX--;
//                            tank.IsMoving = true;
//                            tank.Direction = "LEFT";
//                        }
//                        tank.texture = GreenLeft1Image;
//
//                    } else if (input.contains("RIGHT")) {
//                        if (BattleField.IsMovementPossible(tank.IX + 1, tank.IY)) {
//                            tank.IX++;
//                            tank.IsMoving = true;
//                            tank.Direction = "RIGHT";
//                        }
//                        tank.texture = GreenRight1Image;
//                    }
//                }
//                else{
//                    P1Location = BattleField.getTile(tank.IX, tank.IY);
//                    switch(tank.Direction) {
//                        case "UP":
//                            tank.Y -= (double) TileMeasurement / 60 * tank.getSpeed();
//                            if(tank.tmpTextureChangeCounter++ % tmpTextureChangeTime == 0)
//                            {
//                                //Something like ChangeTexture, if earlier tank loaded two of them
//                                if(tank.texture == GreenUp1Image)
//                                    tank.texture = GreenUp2Image;
//                                else
//                                    tank.texture = GreenUp1Image;
//
//                                tank.tmpTextureChangeCounter = 0;
//                            }
//                            if(tank.Y <= P1Location.IY) {
//                                tank.Y = P1Location.IY;
//                                tank.IsMoving = false;
//                            }
//                            break;
//
//                        case "DOWN":
//                            tank.Y += (double) TileMeasurement / 60 * tank.getSpeed();
//
//                            if(tank.tmpTextureChangeCounter++ % tmpTextureChangeTime == 0)
//                            {
//                                //Something like ChangeTexture, if earlier tank loaded two of them
//                                if(tank.texture == GreenDown1Image)
//                                    tank.texture = GreenDown2Image;
//                                else
//                                    tank.texture = GreenDown1Image;
//
//                                tank.tmpTextureChangeCounter = 0;
//                            }
//
//                            if(tank.Y >= P1Location.IY) {
//                                tank.Y = P1Location.IY;
//                                tank.IsMoving = false;
//                            }
//                            break;
//
//                        case "LEFT":
//                            tank.X -= (double) TileMeasurement / 60 * tank.getSpeed();
//
//                            if(tank.tmpTextureChangeCounter++ % tmpTextureChangeTime == 0)
//                            {
//                                //Something like ChangeTexture, if earlier tank loaded two of them
//                                if(tank.texture == GreenLeft1Image)
//                                    tank.texture = GreenLeft2Image;
//                                else
//                                    tank.texture = GreenLeft1Image;
//
//                                tank.tmpTextureChangeCounter = 0;
//                            }
//
//                            if(tank.X <= P1Location.IX) {
//                                tank.X = P1Location.IX;
//                                tank.IsMoving = false;
//                            }
//                            break;
//
//                        case "RIGHT":
//                            tank.X += (double) TileMeasurement / 60 * tank.getSpeed();
//
//                            if(tank.tmpTextureChangeCounter++ % tmpTextureChangeTime == 0)
//                            {
//                                //Something like ChangeTexture, if earlier tank loaded two of them
//                                if(tank.texture == GreenRight1Image)
//                                    tank.texture = GreenRight2Image;
//                                else
//                                    tank.texture = GreenRight1Image;
//
//                                tank.tmpTextureChangeCounter = 0;
//                            }
//
//                            if(tank.X >= P1Location.IX) {
//                                tank.X = P1Location.IX;
//                                tank.IsMoving = false;
//                            }
//                            break;
//
//                        default:
//                            break;
//                    }
//                }


//                P1Location = BattleField.getTile(tank.IX,tank.IY);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                gc.setGlobalAlpha(0.5);
                gc.drawImage(RoboImage, 0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setGlobalAlpha(1);

                gc.fillText("Привет, товарищ!", x, y);
                gc.strokeText("Привет, товарищ!", x, y);

                BattleField.UpdateBoard(input, gc);

//                gc.drawImage(tank.texture, tank.X, tank.Y, TileMeasurement, TileMeasurement);
                //gc.drawImage( ufo.getFrame(t), explosionX, explosionY );
            }
        }.start();

        //primaryStage.setScene(theScene);
        primaryStage.show();
        //Canvas c =new Canvas(500,300);

//        primaryStage.setTitle( "AnimatedImage Example" );
//
//        Group root = new Group();
//        Scene theScene = new Scene( root );
//        primaryStage.setScene( theScene );
//
//        Canvas canvas = new Canvas( 512, 512 );
//        root.getChildren().add( canvas );
//
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//
//        AnimatedImage ufo = new AnimatedImage();
//        Image[] imageArray = new Image[6];
//        for (int i = 0; i < 6; i++)
//            imageArray[i] = new Image( "ufo_" + i + ".png" );
//        ufo.frames = imageArray;
//        ufo.duration = 0.100;
//
//        final long startNanoTime = System.nanoTime();
//
//        new AnimationTimer()
//        {
//            public void handle(long currentNanoTime)
//            {
//                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
//
//                double x = 232 + 128 * Math.cos(t);
//                double y = 232 + 128 * Math.sin(t);
//
//                gc.drawImage( ufo.getFrame(t), 450, 25 );
//            }
//        }.start();
//
//        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

