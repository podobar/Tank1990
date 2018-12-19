package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board{
    private List<MovingTile> MovingTiles;   // Tanks, enemies and bullets
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
        return Map[i][j];
    }

    public Tile getOriginalTile(int i, int j){
        return Map[i][j];
    }

    public void GenerateMap(){
        MovingTiles = new LinkedList<>();   // <MovingTile>
        Map = new Tile[Width][Height];

        for(int i = 0; i < Width; i++)
            for(int j = 0; j < Height; j++)
            {
                Map[i][j] = new PlainTile(i * TileMeasurement, j * TileMeasurement);

            }
    }

    public boolean IsMovementPossible(int i, int j) {
        if (i >= 0 && i < Width && j >= 0 && j < Height)
            return Map[i][j].CanMoveThrough;

        // Throw exception IndexOutOfBoard?
        return false;
    }

    public void AddMovingTile(MovingTile movingTile)
    {
        MovingTiles.add(movingTile);
    }

    public void UpdateBoard(ArrayList<String> input, GraphicsContext gc){
        for (MovingTile mt : MovingTiles
             ) {

                int TextureChangeTime = (int) (15 / mt.getSpeed());
                if(!mt.IsMoving)
                {
                    // Remap ints in getControl?
                    if (input.contains(mt.getControl(0))) {
                        if (IsMovementPossible(mt.IX, mt.IY - 1)) {
                            mt.IY--;
                            mt.IsMoving = true;
                            mt.Direction = "UP";
                        }

                        // Always zero?
                        mt.texture = mt.TextureUp[0];
                    } else if (input.contains(mt.getControl(1))) {
                        if (IsMovementPossible(mt.IX, mt.IY + 1)) {
                            mt.IY++;
                            mt.IsMoving = true;
                            mt.Direction = "DOWN";
                        }
                        mt.texture = mt.TextureDown[0];
                    }
                    if (input.contains(mt.getControl(2))) {
                        if (IsMovementPossible(mt.IX - 1, mt.IY)) {
                            mt.IX--;
                            mt.IsMoving = true;
                            mt.Direction = "LEFT";
                        }
                        mt.texture = mt.TextureLeft[0];

                    } else if (input.contains(mt.getControl(3))) {
                        if (IsMovementPossible(mt.IX + 1, mt.IY)) {
                            mt.IX++;
                            mt.IsMoving = true;
                            mt.Direction = "RIGHT";
                        }
                        mt.texture = mt.TextureRight[0];
                    }
                }
                else {
                    Tile TileLocation = getTile(mt.IX, mt.IY);
                    mt.setTextureChangeCounter(mt.getTextureChangeCounter()+1);
                    switch (mt.Direction) {
                        case "UP":
                            mt.Y -= (double) TileMeasurement / 60 * mt.getSpeed();

                            // Changing texture if needed, some ChangeTexture() function

                            if(mt.getTextureChangeCounter() % TextureChangeTime == 0)
                            {
                                //Something like ChangeTexture, if earlier tank loaded two of them
                                mt.ChangeTexture();

                                mt.setTextureChangeCounter(0);
                            }
                            if (mt.Y <= TileLocation.IY) {
                                mt.Y = TileLocation.IY;
                                mt.IsMoving = false;
                            }
                            break;

                        case "DOWN":
                            mt.Y += (double) TileMeasurement / 60 * mt.getSpeed();

                            if (mt.getTextureChangeCounter() % TextureChangeTime == 0) {
                                //Something like ChangeTexture, if earlier tank loaded two of them
                                mt.ChangeTexture();

                                mt.setTextureChangeCounter(0);
                            }

                            if (mt.Y >= TileLocation.IY) {
                                mt.Y = TileLocation.IY;
                                mt.IsMoving = false;
                            }
                            break;

                        case "LEFT":
                            mt.X -= (double) TileMeasurement / 60 * mt.getSpeed();

                            if (mt.getTextureChangeCounter() % TextureChangeTime == 0) {
                                //Something like ChangeTexture, if earlier tank loaded two of them
                                mt.ChangeTexture();

                                mt.setTextureChangeCounter(0);
                            }

                            if (mt.X <= TileLocation.IX) {
                                mt.X = TileLocation.IX;
                                mt.IsMoving = false;
                            }
                            break;

                        case "RIGHT":
                            mt.X += (double) TileMeasurement / 60 * mt.getSpeed();

                            if (mt.getTextureChangeCounter() % TextureChangeTime == 0) {
                                //Something like ChangeTexture, if earlier tank loaded two of them
                                mt.ChangeTexture();

                                mt.setTextureChangeCounter(0);
                            }

                            if (mt.X >= TileLocation.IX) {
                                mt.X = TileLocation.IX;
                                mt.IsMoving = false;
                            }
                            break;

                        default:
                            break;
                    }
                }
            gc.drawImage(mt.texture, mt.X, mt.Y, TileMeasurement, TileMeasurement);
        }
    }
}
