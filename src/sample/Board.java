package sample;

public class Board{
    private Tile[][] ActualMap;
    private Tile[][] Map;
    private int Width;
    private int Height;
    private int TileMeasurement;

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

    public Board(int width, int height, int tileLength) {
        Width = width;
        Height = height;
        TileMeasurement = tileLength;
    }

    public Tile getTile(int i, int j) {
        return ActualMap[i][j];
    }

    public Tile getOriginalTile(int i, int j){
        return Map[i][j];
    }

    public void GenerateMap(){
        ActualMap = new Tile[Width][Height];
        Map = new Tile[Width][Height];

        for(int i = 0; i < Width; i++)
            for(int j = 0; j < Height; j++)
            {
                ActualMap[i][j] = new PlainTile(i * TileMeasurement, j * TileMeasurement);
                // Will it be copying? Or should I use some Clone()?
                Map[i][j] = ActualMap[i][j];
            }
    }

    public boolean IsMovementPossible(int i, int j) {
        if (i >= 0 && i < Width && j >= 0 && j < Height)
            if (ActualMap[i][j].CanMoveThrough)
                return true;
        return false;
    }
}
