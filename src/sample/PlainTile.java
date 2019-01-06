package sample;

import javafx.scene.image.Image;

public class PlainTile extends Tile{


//    public PlainTile(int x, int y, Image img) {
//        IX = x;
//        IY = y;
//        texture = img;
//        CanMoveThrough = true;
//        CanShotThrough = true;
//    }
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
}
