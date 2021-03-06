package sample;

import javafx.scene.image.Image;

public class PlainTile extends Tile {

    Image[] StagesOfTile;

    public PlainTile(int x, int y, Image[] stagesOfTile, boolean canShotThrough, boolean canBeDestroyed, int shield) {
        //Texture tile, which can or cannot be destroyed
        IX = x;
        IY = y;
        StagesOfTile = stagesOfTile;
        CanMoveThrough = false;
        CanShotThrough = canShotThrough;
        CanBeDestroyed = canBeDestroyed;
        stamina = shield;
        texture = StagesOfTile[stagesOfTile.length-1];
    }

    @Override
    public boolean IsDestroyed() {
        if(CanBeDestroyed){
            if (stamina > 1) {
                --stamina;
                texture = StagesOfTile[stamina - 1];
                return false;
            } else
                return true;
        }
        else
            return false;

    }

    public PlainTile(int x, int y) {
        IX = x;
        IY = y;
        CanMoveThrough = true;
        CanShotThrough = true;
    }

    public PlainTile() {
        // Add texture initialization
        CanMoveThrough = true;
        CanShotThrough = true;
    }

    @Override
    public double getX() {
        return IX/32;
    }

    @Override
    public double getY() {
        return IY/32;
    }
}
