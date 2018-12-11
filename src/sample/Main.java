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
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Tank 1990");

        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(800, 512);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        File RoboImageFile = new File("Robo.jpg");
        Image RoboImage = new Image(RoboImageFile.toURI().toString());
        gc.drawImage(RoboImage,0,0,canvas.getWidth(),canvas.getHeight());

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


        final long startNanoTime = System.nanoTime();

        Random random = new Random(13);


        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                double explosionX = 0;
                double explosionY = 0;

                if((currentNanoTime - startNanoTime)%6 == 0)
                {
                    explosionX = random.nextInt((int)canvas.getWidth());
                    explosionY = random.nextInt((int)canvas.getHeight());
                }


                gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
                gc.setGlobalAlpha(0.5);
                gc.drawImage(RoboImage,0,0,canvas.getWidth(),canvas.getHeight());
                gc.setGlobalAlpha(1);
                gc.fillText("Привет, товарищ!", x, y);
                gc.strokeText("Привет, товарищ!", x, y);
                gc.drawImage( ufo.getFrame(t), explosionX, explosionY );
            }
        }.start();

        //primaryStage.setScene(theScene);
        primaryStage.show();
        Canvas c =new Canvas(500,300);

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

