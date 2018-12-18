package sample;

public class PlainTile extends Tile{

    public PlainTile(int x, int y) {
        IX = x;
        IY = y;

        CanMoveThrough = true;
        CanShotThrough = true;
    }

    public PlainTile()
    {
        CanMoveThrough = true;
        CanShotThrough = true;
    }
}
