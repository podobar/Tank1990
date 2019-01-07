package sample;

import javafx.scene.image.Image;

public class PlainTile extends MapTile{
  
    Image[] StagesOfTile;

    public PlainTile(int x, int y, Image[] stagesOfTile, boolean canShotThrough, boolean canBeDestroyed, int shield) {
        //Texture tile, which can or cannot be destroyed
        IX = x;
        IY = y;
        StagesOfTile=stagesOfTile;
        CanMoveThrough = false;
        CanShotThrough = canShotThrough;
        CanBeDestroyed = canBeDestroyed;
        stamina=shield;
        texture = StagesOfTile[0];
    }
    @Override
    public boolean IsDestroyed(){
        if(stamina>1){
            --stamina;
            texture=StagesOfTile[stamina-1];
            return false;
        }
        else
            return true;
    }
    public PlainTile(int x, int y) {
        IX = x;
        IY = y;
        CanMoveThrough = true;
        CanShotThrough = true;
    }
    public PlainTile()
    {
        // Add texture initialization
        CanMoveThrough = true;
        CanShotThrough = true;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }
}
