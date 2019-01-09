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
    //TODO: Should score be in Tank.java instead of PlayerTank? When enemy tank is destroyed and player's bullet did it -> he'll receive EnemyTank.getScore() for taking him down
    //private int score;
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
