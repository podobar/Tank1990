package sample;

import javafx.scene.image.Image;

import java.io.File;

public class Bullet extends MovingTile {
    private int noClipTime;

    public int getNoClipTime() {
        return noClipTime;
    }

    public void setNoClipTime(int noClipTime) {
        this.noClipTime = noClipTime;
    }

    public Bullet(int iXStarting, int iYStarting, double x, double y, String direction) {

        noClipTime = 60; // in 1/60 s

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

    public double getX(){
        return X;
    }

    public double getY(){
        return Y;
    }

    public boolean CheckCollision(double r, double x, double y, int size) {
        double midX = X + r;
        double midY = Y + r;

        if (midX >= x && midX <= x + size && midY >= y && midY <= y + size)
            return true;

        // From left or right
        if ((Math.abs(x - midX) <= r || Math.abs(midX - (x + size)) <= r) && (y <= midY && midY <= y + size))
            return true;

        // From up or down
        if ((Math.abs(y - midY) <= r || Math.abs(midY - (y + size)) <= r) && (x <= midX && midX <= x + size))
            return true;

//        if ((x - midX) * (x - midX) + (y - midY) * (y - midY) < r * r)
//            return true;

        return false;
    }
}
