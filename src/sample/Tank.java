package sample;

import javafx.scene.image.Image;


// Should I add classes PlayerTank and EnemyTank?
abstract public class Tank extends MovingTile {
    private int id;


    public int getId() {
        return id;
    }

    private static int idCounter = 0;
    protected double attackSpeed; // bullets per second?
    //TODO: Should score be in Tank.java instead of PlayerTank? When enemy tank is destroyed and player's bullet did it -> he'll receive EnemyTank.getScore() for taking him down
    //TEMP


    private int ShotDelay;

    private boolean IsShooting;

    public Tank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight) {
        id = idCounter++;

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

    public void ShotDelay() {
        ShotDelay = (int) (120 / attackSpeed);
    }

    public Image getDefaultTexture(){
        return TextureUp[0];
    }

    // False when delay has ended.
    public boolean ReduceShotDelay() {
        ShotDelay--;
        if (ShotDelay <= 0)
            return false;
        return true;
    }

    public void Explode(Image ExplosionImage) {
        texture = ExplosionImage;
        CanMoveThrough = true;
        CanShotThrough = true;
        //IsMoving = false;
    }
}
