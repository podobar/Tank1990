package sample;

import javafx.scene.image.Image;

class Tank extends MovingTile {
    private int hp; // how many shots can tank take
    private int level; //
    private int ammoType;
    //private double speed; // 1 means that tank moves 1 tile per second
    private double attackSpeed;
//    /*private*/ Image texture; // depends on level of tank
    private int upgrades; // flags for each upgrade [4 youngest bits]

    //TEMP

    public int tmpTextureChangeCounter;

//    public double getSpeed() {
//        return speed;
//    }

    public Tank(Image texture, int x, int y, Image textureUp, Image textureDown, Image textureLeft, Image textureRight) {
        this.texture = texture;

        TextureUp = textureUp;
        TextureDown = textureDown;
        TextureLeft = textureLeft;
        TextureRight = textureRight;

        IX = x;
        IY = y;
        X = 0;
        Y = 0;

        speed = 1;

        tmpTextureChangeCounter = 0;

        CanMoveThrough = false;
        CanShotThrough = false;

        IsMoving = false;
    }
}
