package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Board {
    private List<MovingTile> MovingTiles;   // Tanks, enemies and bullets
    private List<Bullet> Bullets;
    private List<PlayerTank> Players;
    private List<EnemyTank> Enemies;
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

        Bullets = new LinkedList<>();
        Players = new LinkedList<>();
        Enemies = new LinkedList<>();

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
        if(movingTile instanceof PlayerTank)
            Players.add((PlayerTank)movingTile);
        else if(movingTile instanceof EnemyTank)
            Enemies.add((EnemyTank) movingTile);
        else if(movingTile instanceof Bullet)
            Bullets.add((Bullet)movingTile);
    }

    // TODO: Bullets isEnemy property - to disable friendly fire within teams
    public void CheckCollisions() {
        int bulletSize = TileMeasurement / 3;
        int tankSize = TileMeasurement;
        for (Bullet b : Bullets
        ) {
            for (PlayerTank pt : Players) {
                if (b.CheckCollision(bulletSize, pt.getX(), pt.getY(), tankSize))
                // TODO: after collision, Explode() is a placeholder.
                    Explode();
            }
        }
    }

    public void Explode() {
        // Placeholder
    }

    public void UpdateBoard(ArrayList<String> input, GraphicsContext gc) {
        CheckCollisions();
        for (PlayerTank pt : Players)
        {
            int TextureChangeTime = (int) (15 / pt.getSpeed());
            if (!pt.IsMoving) {
                // If it isn't PlayerTank (may be bullet or EnemyTank but then logic isn't right, I think) or input contains move control.
                // Works now but I don't know whether casting is correct usage (looking for some C#-like "as").
                // It enforces movement priorities - e.g. when you're pressing Left and then press Up or Down, then next move will be Up or Down. (Up > Down > Left > Right)
                if (input.contains(pt.getControl(0))) {
                    if (IsMovementPossible(pt.IX, pt.IY - 1)) {
                        pt.IY--;
                        pt.IsMoving = true;
                        pt.Direction = "UP";
                    }

                    // Always zero?
                    pt.texture = pt.TextureUp[0];
                } else if (input.contains(pt.getControl(1))) {
                    if (IsMovementPossible(pt.IX, pt.IY + 1)) {
                        pt.IY++;
                        pt.IsMoving = true;
                        pt.Direction = "DOWN";
                    }
                    pt.texture = pt.TextureDown[0];
                } else if (input.contains(pt.getControl(2))) {
                    if (IsMovementPossible(pt.IX - 1, pt.IY)) {
                        pt.IX--;
                        pt.IsMoving = true;
                        pt.Direction = "LEFT";
                    }
                    pt.texture = pt.TextureLeft[0];

                } else if (input.contains(pt.getControl(3))) {
                    if (IsMovementPossible(pt.IX + 1, pt.IY)) {
                        pt.IX++;
                        pt.IsMoving = true;
                        pt.Direction = "RIGHT";
                    }
                    pt.texture = pt.TextureRight[0];
                }
            } else {
                Tile TileLocation = getTile(pt.IX, pt.IY);
                pt.setTextureChangeCounter(pt.getTextureChangeCounter() + 1);
                switch (pt.Direction) {
                    case "UP":
                        pt.Y -= (double) TileMeasurement / 60 * pt.getSpeed();

                        // Changing texture if needed, some ChangeTexture() function

                        if (pt.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            pt.ChangeTexture();

                            pt.setTextureChangeCounter(0);
                        }
                        if (pt.Y <= TileLocation.IY) {
                            pt.Y = TileLocation.IY;
                            pt.IsMoving = false;
                        }
                        break;

                    case "DOWN":
                        pt.Y += (double) TileMeasurement / 60 * pt.getSpeed();

                        if (pt.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            pt.ChangeTexture();

                            pt.setTextureChangeCounter(0);
                        }

                        if (pt.Y >= TileLocation.IY) {
                            pt.Y = TileLocation.IY;
                            pt.IsMoving = false;
                        }
                        break;

                    case "LEFT":
                        pt.X -= (double) TileMeasurement / 60 * pt.getSpeed();

                        if (pt.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            pt.ChangeTexture();

                            pt.setTextureChangeCounter(0);
                        }

                        if (pt.X <= TileLocation.IX) {
                            pt.X = TileLocation.IX;
                            pt.IsMoving = false;
                        }
                        break;

                    case "RIGHT":
                        pt.X += (double) TileMeasurement / 60 * pt.getSpeed();

                        if (pt.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            pt.ChangeTexture();

                            pt.setTextureChangeCounter(0);
                        }

                        if (pt.X >= TileLocation.IX) {
                            pt.X = TileLocation.IX;
                            pt.IsMoving = false;
                        }
                        break;

                    default:
                        break;
                }
            }

            // Interfaces IShotable, IMoveable?

                if (!pt.isShooting())     // Not shooting
                {
                        if (input.contains(pt.getControl(4)))     // Add Controls to Tank?
                        {
                            pt.setShooting(true);
                            pt.ShotDelay();
                            int x, y;
                            if (pt.Direction.equals("UP")) {
                                x = (int) pt.X + TileMeasurement / 2 - TileMeasurement / (2*3);
                                y = (int) pt.Y - TileMeasurement / (2*3); // TileMeasurement/3 - bullet size
                            } else if (pt.Direction.equals("DOWN")) {
                                x = (int) pt.X + TileMeasurement / 2 - TileMeasurement / (2*3);
                                y = (int) pt.Y + TileMeasurement - TileMeasurement / 3; // TileMeasurement/3 - bullet size
                            } else if (pt.Direction.equals("LEFT")) {
                                x = (int) pt.X - TileMeasurement / (2*3);
                                y = (int) pt.Y + TileMeasurement / 2 - TileMeasurement / (2*3); // TileMeasurement/3 - bullet size
                            } else if (pt.Direction.equals("RIGHT")) {
                                x = (int) pt.X + TileMeasurement - TileMeasurement / (2*3);
                                y = (int) pt.Y + TileMeasurement / 2 - TileMeasurement / (2*3); // TileMeasurement/3 - bullet size
                            } else {      // Exception - Direction different from U,D,L,R
                                x = 0;
                                y = 0;
                            }
                            MovingTilesToAdd.offer(new Bullet(pt.IX, pt.IY, x, y, pt.Direction));

                        }
                } else { // Shooting
                    pt.setShooting(pt.ReduceShotDelay());
                }
        }

        for (Bullet b : Bullets ) {
            int TextureChangeTime = (int) (15 / b.getSpeed());
            if (!b.IsMoving) {
                // If it isn't PlayerTank (may be bullet or EnemyTank but then logic isn't right, I think) or input contains move control.
                // Works now but I don't know whether casting is correct usage (looking for some C#-like "as").
                // It enforces movement priorities - e.g. when you're pressing Left and then press Up or Down, then next move will be Up or Down. (Up > Down > Left > Right)

                // Bullet should always be moving.

//                if (!(b instanceof PlayerTank) || (input.contains(((PlayerTank) b).getControl(0)))) {
//                    if (IsMovementPossible(b.IX, b.IY - 1)) {
//                        b.IY--;
//                        b.IsMoving = true;
//                        b.Direction = "UP";
//                    }
//
//                    // Always zero?
//                    b.texture = b.TextureUp[0];
//                } else if (b.getClass() != PlayerTank.class || (input.contains(((PlayerTank) b).getControl(1)))) {
//                    if (IsMovementPossible(b.IX, b.IY + 1)) {
//                        b.IY++;
//                        b.IsMoving = true;
//                        b.Direction = "DOWN";
//                    }
//                    b.texture = b.TextureDown[0];
//                } else if (b.getClass() != PlayerTank.class || (input.contains(((PlayerTank) b).getControl(2)))) {
//                    if (IsMovementPossible(b.IX - 1, b.IY)) {
//                        b.IX--;
//                        b.IsMoving = true;
//                        b.Direction = "LEFT";
//                    }
//                    b.texture = b.TextureLeft[0];
//
//                } else if (b.getClass() != PlayerTank.class || (input.contains(((PlayerTank) b).getControl(3)))) {
//                    if (IsMovementPossible(b.IX + 1, b.IY)) {
//                        b.IX++;
//                        b.IsMoving = true;
//                        b.Direction = "RIGHT";
//                    }
//                    b.texture = b.TextureRight[0];
//                }
            } else {
                Tile TileLocation = getTile(b.IX, b.IY);
                b.setTextureChangeCounter(b.getTextureChangeCounter() + 1);
                switch (b.Direction) {
                    case "UP":
                        b.Y -= (double) TileMeasurement / 60 * b.getSpeed();

                        // Changing texture if needed, some ChangeTexture() function

                        if (b.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }

                        break;

                    case "DOWN":
                        b.Y += (double) TileMeasurement / 60 * b.getSpeed();

                        if (b.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }

                        break;

                    case "LEFT":
                        b.X -= (double) TileMeasurement / 60 * b.getSpeed();

                        if (b.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }

                        break;

                    case "RIGHT":
                        b.X += (double) TileMeasurement / 60 * b.getSpeed();

                        if (b.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }

                        break;

                    default:
                        break;
                }
            }

            //if (b instanceof Bullet)
            gc.drawImage(b.texture, b.X, b.Y, (int) (TileMeasurement / 3), (int) (TileMeasurement / 3));

        }

        for (EnemyTank et : Enemies
        ) {
            int TextureChangeTime = (int) (15 / et.getSpeed());

            // TODO: Enemies movement

            if (!et.IsMoving) {
                // If it isn't PlayerTank (may be bullet or EnemyTank but then logic isn't right, I think) or input contains move control.
                // Works now but I don't know whether casting is correct usage (looking for some C#-like "as").
                // It enforces movement priorities - e.g. when you're pressing Left and then press Up or Down, then next move will be Up or Down. (Up > Down > Left > Right)
//                if (!(et instanceof PlayerTank) || (input.contains(((PlayerTank) et).getControl(0)))) {
//                    if (IsMovementPossible(et.IX, et.IY - 1)) {
//                        et.IY--;
//                        et.IsMoving = true;
//                        et.Direction = "UP";
//                    }
//
//                    // Always zero?
//                    et.texture = et.TextureUp[0];
//                } else if (et.getClass() != PlayerTank.class || (input.contains(((PlayerTank) et).getControl(1)))) {
//                    if (IsMovementPossible(et.IX, et.IY + 1)) {
//                        et.IY++;
//                        et.IsMoving = true;
//                        et.Direction = "DOWN";
//                    }
//                    et.texture = et.TextureDown[0];
//                } else if (et.getClass() != PlayerTank.class || (input.contains(((PlayerTank) et).getControl(2)))) {
//                    if (IsMovementPossible(et.IX - 1, et.IY)) {
//                        et.IX--;
//                        et.IsMoving = true;
//                        et.Direction = "LEFT";
//                    }
//                    et.texture = et.TextureLeft[0];
//
//                } else if (et.getClass() != PlayerTank.class || (input.contains(((PlayerTank) et).getControl(3)))) {
//                    if (IsMovementPossible(et.IX + 1, et.IY)) {
//                        et.IX++;
//                        et.IsMoving = true;
//                        et.Direction = "RIGHT";
//                    }
//                    et.texture = et.TextureRight[0];
//                }
            } else {
                Tile TileLocation = getTile(et.IX, et.IY);
                et.setTextureChangeCounter(et.getTextureChangeCounter() + 1);
                switch (et.Direction) {
                    case "UP":
                        et.Y -= (double) TileMeasurement / 60 * et.getSpeed();

                        // Changing texture if needed, some ChangeTexture() function

                        if (et.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            et.ChangeTexture();

                            et.setTextureChangeCounter(0);
                        }
                        if (et.Y <= TileLocation.IY) {
                            et.Y = TileLocation.IY;
                            et.IsMoving = false;
                        }
                        break;

                    case "DOWN":
                        et.Y += (double) TileMeasurement / 60 * et.getSpeed();

                        if (et.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            et.ChangeTexture();

                            et.setTextureChangeCounter(0);
                        }

                        if (et.Y >= TileLocation.IY) {
                            et.Y = TileLocation.IY;
                            et.IsMoving = false;
                        }
                        break;

                    case "LEFT":
                        et.X -= (double) TileMeasurement / 60 * et.getSpeed();

                        if (et.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            et.ChangeTexture();

                            et.setTextureChangeCounter(0);
                        }

                        if (et.X <= TileLocation.IX) {
                            et.X = TileLocation.IX;
                            et.IsMoving = false;
                        }
                        break;

                    case "RIGHT":
                        et.X += (double) TileMeasurement / 60 * et.getSpeed();

                        if (et.getTextureChangeCounter() % TextureChangeTime == 0) {
                            //Something like ChangeTexture, if earlier tank loaded two of them
                            et.ChangeTexture();

                            et.setTextureChangeCounter(0);
                        }

                        if (et.X >= TileLocation.IX) {
                            et.X = TileLocation.IX;
                            et.IsMoving = false;
                        }
                        break;

                    default:
                        break;
                }
            }

                if (!et.isShooting())     // Not shooting
                {
                    // TODO: Enemy shooting

                } else { // Shooting
                    et.setShooting( et.ReduceShotDelay());
                }

                gc.drawImage(et.texture, et.X, et.Y, TileMeasurement, TileMeasurement);
        }

        while(!MovingTilesToAdd.isEmpty()){

            // TODO: Change adding with Board.AddMovingTile(MovingTilesToAdd.poll()) ?
            MovingTile mt = MovingTilesToAdd.poll();
            if(mt instanceof Bullet)
                Bullets.add((Bullet)mt);
            else if (mt instanceof EnemyTank)
                Enemies.add((EnemyTank)mt);

            // TODO: Will there be any other MovingTiles?
        }
    }
}

// Tiles sizes? E.g. bullet = Tile/3;