package sample;

import javafx.scene.image.Image;

public abstract class MovingTile extends Tile
{
    protected boolean IsMoving;
    protected double X;
    protected double Y;
    protected String Direction;
    protected double speed; // 1 means that tank moves 1 tile per second

    protected Image TextureUp;
    protected Image TextureDown;
    protected Image TextureLeft;
    protected Image TextureRight;

    public double getSpeed() {
        return speed;
    }

}
