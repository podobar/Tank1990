package sample;

import javafx.scene.image.Image;

import java.io.File;

public class Bullet extends MovingTile {
    public Bullet(int iXStarting, int iYStarting, double x, double y, String direction) {

        IX = iXStarting;    // These two can be used as a destination.
        IY = iYStarting;

        X = x;
        Y = y;

        Direction = direction;
        speed = 5;

        CanMoveThrough = true;
        CanShotThrough = true;
        IsMoving = true;

//        texture = new Image((new File("Explosion1/1.png")).toURI().toString());
        texture = new Image(new File("Resources/Explosion1/1.png").toURI().toString());
        TextureUp = TextureDown = TextureLeft = TextureRight = new Image[]{texture};
    }
}
