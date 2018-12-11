package sample;

import javafx.scene.image.Image;

class Tank {
    private int hp; // how many shots can tank take
    private int level; //
    private int ammoType;
    private double speed;
    private double attackSpeed;
    private Image texture; // depends on level of tank
    private int upgrades; // flags for each upgrade [4 youngest bits]
}
