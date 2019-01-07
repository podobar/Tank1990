package sample;

import javafx.scene.image.Image;


// Should I add classes PlayerTank and EnemyTank?
abstract public class Tank extends MovingTile {
    private int hp; // how many shots can tank take
    private int level; //
    private int ammoType;
    //private double speed; // 1 means that tank moves 1 tile per second // Moved to MovingTile class

    private double attackSpeed; // bullets per second?
//    /*private*/ Image texture; // depends on level of tank // Moved to Tile class
    private int upgrades; // flags for each upgrade [4 youngest bits]

    //TEMP

    private int ShotDelay;

    private boolean IsShooting;

    public Tank(){

    }

    public Tank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight) {
        IX = iX;
        IY = iY;
        X = x;
        Y = y;

        this.texture = texturesRight[0];

        TextureUp = texturesUp.clone();
        TextureDown = texturesDown.clone();
        TextureLeft = texturesLeft.clone();
        TextureRight = texturesRight.clone();

        attackSpeed = 1;
        speed = 3;

        ShotDelay = 0;

        TextureChangeCounter = 0;

        Direction = "RIGHT";

        CanMoveThrough = false;
        CanShotThrough = false;


        IsMoving = false;
        IsShooting = false;
    }

    public boolean isShooting() {
        return IsShooting;
    }

    public void setShooting(boolean shooting) {
        IsShooting = shooting;
    }

    public void ShotDelay(){
        ShotDelay =(int) (60 / attackSpeed);
    }

    // False when delay has ended.
    public boolean ReduceShotDelay() {
        ShotDelay--;
        if (ShotDelay <= 0)
            return false;
        return true;
    }
}

