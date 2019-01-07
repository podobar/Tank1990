package sample;

import javafx.scene.image.Image;

public abstract class Tile
{
    boolean CanMoveThrough;
    boolean CanShotThrough;
    boolean CanBeDestroyed;
    int IX;
    int IY;
    int stamina;
    Image texture;
}

