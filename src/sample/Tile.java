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
    boolean CanBeDestroyed = true;
    int stamina;
    int IX;
    int IY;

    public int getIX() {
        return IX/32;
    }

    public int getIY() {
        return IY/32;
    }

    public abstract double getX();
    public abstract double getY();

    Image texture;
}

