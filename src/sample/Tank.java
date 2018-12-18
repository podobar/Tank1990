package sample;

import javafx.scene.image.Image;

class Tank extends Tile {
    private int hp; // how many shots can tank take
    private int level; //
    private int ammoType;
    private double speed;
    private double attackSpeed;
    /*private*/ Image texture; // depends on level of tank
    private int upgrades; // flags for each upgrade [4 youngest bits]
    //TEMP

    public Tank(Image texture, int x, int y) {
        this.texture = texture;

        IX = x;
        IY = y;

        CanMoveThrough = false;
        CanShotThrough = false;
    }
}
