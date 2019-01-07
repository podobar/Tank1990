package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Queue;

public class Board {
    private List<MovingTile> MovingTiles;   // Tanks, enemies and bullets
    private Queue<MovingTile> MovingTilesToAdd;

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

    // Add checking whether some MovingTile isn't on Tile (collisions)


    public void GenerateMap() {
        MovingTiles = new LinkedList<>();   // <MovingTile>
        MovingTilesToAdd = new LinkedList<>(); // Queue

        Map = new Tile[Width][Height];

        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++) {

                Map[i][j] = new PlainTile(i * TileMeasurement, j * TileMeasurement);

            }
    }


    // Add checking MovingTiles
    public boolean IsMovementPossible(int i, int j) {
        if (i >= 0 && i < Width && j >= 0 && j < Height) {
            for (MovingTile mt : MovingTiles
            ) {
                if (mt.IX == i && mt.IY == j && !mt.CanMoveThrough)
                    return false;
            }

            return Map[i][j].CanMoveThrough;
        }

        // Throw exception IndexOutOfBoard?
        return false;
    }


    public void AddMovingTile(MovingTile movingTile) {
        MovingTiles.add(movingTile);
    }

    public void UpdateBoard(ArrayList<String> input, GraphicsContext gc) {
        for (MovingTile mt : MovingTiles
        ) {

            int TextureChangeTime = (int) (15 / mt.getSpeed());
            if (!mt.IsMoving) {
                // If it isn't PlayerTank (may be bullet or EnemyTank but then logic isn't right, I think) or input contains move control.
                // Works now but I don't know whether casting is correct usage (looking for some C#-like "as").
                // It enforces movement priorities - e.g. when you're pressing Left and then press Up or Down, then next move will be Up or Down. (Up > Down > Left > Right)
                if (!(mt instanceof PlayerTank) || (input.contains(((PlayerTank) mt).getControl(0)))) {
                    if (IsMovementPossible(mt.IX, mt.IY - 1)) {
                        mt.IY--;
                        mt.IsMoving = true;
                        mt.Direction = "UP";
                    }

                    // Always zero?
                    mt.texture = mt.TextureUp[0];
                } else if (mt.getClass() != PlayerTank.class || (input.contains(((PlayerTank) mt).getControl(1)))) {
                    if (IsMovementPossible(mt.IX, mt.IY + 1)) {
                        mt.IY++;
                        mt.IsMoving = true;
                        mt.Direction = "DOWN";
                    }
                    mt.texture = mt.TextureDown[0];
                } else if (mt.getClass() != PlayerTank.class || (input.contains(((PlayerTank) mt).getControl(2)))) {
                    if (IsMovementPossible(mt.IX - 1, mt.IY)) {
                        mt.IX--;
                        mt.IsMoving = true;
                        mt.Direction = "LEFT";
                    }
                    mt.texture = mt.TextureLeft[0];

                } else if (mt.getClass() != PlayerTank.class || (input.contains(((PlayerTank) mt).getControl(3)))) {
                    if (IsMovementPossible(mt.IX + 1, mt.IY)) {
                        mt.IX++;
                        mt.IsMoving = true;
                        mt.Direction = "RIGHT";
                    }
                    mt.texture = mt.TextureRight[0];
                }
            } else {
                Tile TileLocation = getTile(mt.IX, mt.IY);
                mt.setTextureChangeCounter(mt.getTextureChangeCounter() + 1);
                switch (mt.Direction) {
                    case "UP":
                        mt.Y -= (double) TileMeasurement / 60 * mt.getSpeed();

                        // Changing texture if needed, some ChangeTexture() function

                        if (mt.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            mt.ChangeTexture();

                            mt.setTextureChangeCounter(0);
                        }
                        if (mt instanceof Tank && mt.Y <= TileLocation.IY) {
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

                        if (mt instanceof Tank && mt.Y >= TileLocation.IY) {
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

                        if (mt instanceof Tank && mt.X <= TileLocation.IX) {
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

                        if (mt instanceof Tank && mt.X >= TileLocation.IX) {
                            mt.X = TileLocation.IX;
                            mt.IsMoving = false;
                        }
                        break;

                    default:
                        break;
                }
            }

            // Interfaces IShotable, IMoveable?
            if (mt instanceof Tank) {
                if (!((Tank) mt).isShooting())     // Not shooting
                {
                    if (mt instanceof PlayerTank) // Player
                    {
                        if (input.contains(((PlayerTank) mt).getControl(4)))     // Add Controls to Tank?
                        {
                            ((PlayerTank) mt).setShooting(true);
                            ((PlayerTank) mt).ShotDelay();
                            int x, y;
                            if (mt.Direction.equals("UP")) {
                                x = (int) mt.X + TileMeasurement / 2 - TileMeasurement / (2*3);
                                y = (int) mt.Y - TileMeasurement / (2*3); // TileMeasurement/3 - bullet size
                            } else if (mt.Direction.equals("DOWN")) {
                                x = (int) mt.X + TileMeasurement / 2 - TileMeasurement / (2*3);
                                y = (int) mt.Y + TileMeasurement - TileMeasurement / 3; // TileMeasurement/3 - bullet size
                            } else if (mt.Direction.equals("LEFT")) {
                                x = (int) mt.X - TileMeasurement / (2*3);
                                y = (int) mt.Y + TileMeasurement / 2 - TileMeasurement / (2*3); // TileMeasurement/3 - bullet size
                            } else if (mt.Direction.equals("RIGHT")) {
                                x = (int) mt.X + TileMeasurement - TileMeasurement / (2*3);
                                y = (int) mt.Y + TileMeasurement / 2 - TileMeasurement / (2*3); // TileMeasurement/3 - bullet size
                            } else {      // Exception - Direction different from U,D,L,R
                                x = 0;
                                y = 0;
                            }

                            MovingTilesToAdd.offer(new Bullet(mt.IX, mt.IY, x, y, mt.Direction));

                        }
                    } else {                        // Enemy

                    }
                } else { // Shooting
                    ((Tank) mt).setShooting(((Tank) mt).ReduceShotDelay());
                }

            }
            if (mt instanceof Bullet)
                gc.drawImage(mt.texture, mt.X, mt.Y, (int)TileMeasurement / 3, (int)TileMeasurement / 3);
            else
                gc.drawImage(mt.texture, mt.X, mt.Y, TileMeasurement, TileMeasurement);
        }

        while(!MovingTilesToAdd.isEmpty()){
            MovingTiles.add(MovingTilesToAdd.poll());
        }
    }
}

// Tiles sizes? E.g. bullet = Tile/3;

