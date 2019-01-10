package sample;

import javafx.scene.image.Image;


// Should I add classes PlayerTank and EnemyTank?
abstract public class Tank extends MovingTile {

    private double attackSpeed; // bullets per second?

    private int ShotDelay;

    private boolean IsShooting;

    public Tank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight) {
        IX = iX;
        IY = iY;
        X = x;
        Y = y;

        this.texture = texturesUp[0];

        TextureUp = texturesUp.clone();
        TextureDown = texturesDown.clone();
        TextureLeft = texturesLeft.clone();
        TextureRight = texturesRight.clone();

        attackSpeed = 1;
        speed = 3;

        ShotDelay = 0;

        TextureChangeCounter = 0;

        Direction = "UP";

        CanMoveThrough = false;
        CanShotThrough = false;
        CanBeDestroyed = true;


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

    public void Explode(Image ExplosionImage){
        texture = ExplosionImage;
        //IsMoving = false;
    }
}
