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

        noClipTime = 2; // in 1/60 s

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

    // Circle with square
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

        return false;
    }

    // Circle with circle
    public boolean CheckCollision(double r, double x, double y, double r2) {
        if (Math.abs(x - X) * Math.abs(x - X) + Math.abs(y - Y) * Math.abs(y - Y) <= (r + r2) * (r + r2))
            return true;
        return false;
    }

    // With border
    public boolean CheckCollision(double r, int borderX, int borderY) {
        double midX = X + r;
        double midY = Y + r;

//        if (midX * midX + midY * midY <= r * r ||
//            Math.abs(midX - borderX) * Math.abs(midX - borderX) + midY * midY <= r * r ||
//            midX * midX + Math.abs(midY - borderY) * Math.abs(midY - borderY) < r * r ||
//            Math.abs(midX - borderX) * Math.abs(midX - borderX) + Math.abs(midY - borderY) * Math.abs(midY - borderY) < r * r)
        if(midX <= r || midX >= borderX - r || midY <= r || midY >= borderY - r)
            return true;

        return false;
    }

}
