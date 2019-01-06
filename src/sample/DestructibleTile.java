package sample;

import sample.Tile;

public class DestructibleTile extends Tile {
    //Terrain which is impassable for MovingTiles (bullets, tanks), but possible to destroy

    public DestructibleTile(int x, int y) {
        IX = x;
        IY = y;

        CanMoveThrough = false;
        CanShotThrough = true;
    }
    public DestructibleTile(){
        // Add texture initialization
        CanMoveThrough = false;
        CanShotThrough = true;
    }
}
