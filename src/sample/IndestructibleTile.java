package sample;

public class IndestructibleTile extends Tile {
    //Terrain which is impassable for MovingTiles (bullets, tanks) and impossible to destroy
    public IndestructibleTile(int x, int y) {
        IX = x;
        IY = y;

        CanMoveThrough = false;
        CanShotThrough = false;
    }
    public IndestructibleTile(){
        // Add texture initialization
        CanMoveThrough = false;
        CanShotThrough = false;
    }
}
