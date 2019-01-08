package sample;

import javafx.scene.image.Image;

public abstract class Tile
{
    public boolean IsDestroyed() {
        if (stamina > 1) {
            --stamina;
            return false;
        } else
            return true;
    }
    boolean CanMoveThrough;
    boolean CanShotThrough;
    boolean CanBeDestroyed;
    int stamina;
    int IX;
    int IY;

    public int getIX() {
        return IX;
    }

    public int getIY() {
        return IY;
    }

    public abstract double getX();
    public abstract double getY();

    Image texture;
}

