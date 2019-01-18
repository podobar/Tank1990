package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.util.Pair;

import java.io.File;
import java.util.*;


public class Board {
    @SuppressWarnings("WeakerAccess")
    public static Tile[][] Map;
    public static PlayerTank[] players = new PlayerTank[2];
    public static Queue<EnemyTank> EnemiesToAdd = new LinkedList<>();

    public static boolean enemiesKilled=false;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")

    private List<MovingTile> MovingTiles;   // Tanks, enemies and bullets
    private List<Bullet> Bullets;
    private List<PlayerTank> Players;
    private List<EnemyTank> Enemies;
    private Queue<MovingTile> MovingTilesToAdd;
    private Queue<MovingTile> MovingTilesToDel;
    private Queue<AbstractMap.SimpleEntry<Tank, Integer>> ExplodingTanks;
    private int Width;
    private int Height;
    private int TileMeasurement;
    private int EnemiesLimit;
    private int EnemyAddTimer;
    private int EnemiesOverallLimit;
    private int ExplosionTime;
    private Image ExplosionImage;

    private Random r;

    @SuppressWarnings("unused")
    public int getWidth() {
        return Width;
    }

    @SuppressWarnings("unused")
    public int getHeight() {
        return Height;
    }

    public Board(int width, int height, int tileLength) {
        Width = width;
        Height = height;
        TileMeasurement = tileLength;
        ExplosionImage = new Image("/Resources/Explosion1/3.png");
        r = new Random();
        ExplosionTime = 60;
        EnemiesLimit = 6;
        EnemyAddTimer = 180;
        EnemiesOverallLimit = 20;
    }

    private Tile getTile(int i, int j) {
        if(i>=0 && j>=0 &&i<Width && j<Height)
            return Map[i][j];
        else
            return Map[0][0];
    }

    // Add checking whether some MovingTile isn't on Tile (collisions)

    public void GenerateMap() {
        MovingTiles = new LinkedList<>();   // <MovingTile>
        MovingTilesToAdd = new LinkedList<>(); // Queue
        MovingTilesToDel = new LinkedList<>();
        ExplodingTanks = new LinkedList<>();

        List<Pair<Integer, Integer>> spawns = new LinkedList<>();
        spawns.add(new Pair<>(1, 1));
        spawns.add(new Pair<>(11, 1));
        spawns.add(new Pair<>(23, 1));

        //MapTile
        Map = new Tile[Width][Height];

        Bullets = new LinkedList<>();
        Players = new LinkedList<>();
        Enemies = new LinkedList<>();

        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++) {

                if (i == Width / 2 && j == Height - 2) {
                    // Eagle
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image("/Resources/Eagle/eagle4.jpg")},
                            false,
                            true,
                            1
                    );
                }
                else if( (i == 6 || i == 18) && (j==14 || j==13))
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image("/Resources/Terrain/indestructible2.png")},
                            false,
                            false,
                            1
                    );
                else if (((i == Width / 2 - 1 || i == Width / 2 || i == Width / 2 + 1) && j == Height - 3)
                        || ((i == Width / 2 - 1 || i == Width / 2 + 1) && j == Height - 2)) {
                    // Bricks around the eagle
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image("/Resources/Terrain/bricks3.jpg"),
                                    new Image("/Resources/Terrain/bricks2.jpg"),
                                    new Image("/Resources/Terrain/bricks1.jpg")},
                            false,
                            true,
                            3
                    );
                }
                else if (i == 0 || i == Width - 1 || j == 0 || j == Height - 1)
                    if (j == Height - 1 && (i == Width / 2 - 2 || i == Width / 2 + 2)) // Players spawn points
                        Map[i][j] = new PlainTile(
                                i * TileMeasurement, j * TileMeasurement,
                                new Image[]{new Image("/Resources/Terrain/indestructible2.png")},
                                true,
                                false,
                                1
                        );
                    else
                        Map[i][j] = new PlainTile(
                                i * TileMeasurement, j * TileMeasurement,
                                new Image[]{new Image("/Resources/Terrain/bricks3.jpg"),
                                        new Image("/Resources/Terrain/bricks2.jpg"),
                                        new Image("/Resources/Terrain/bricks1.jpg")},
                                false,
                                true,
                                3
                        );
                else if ((j == 2 && i <= 22 && i >= 2 && i!= 15 && i!=9) || (j == 3 && i >= 10 && i <= 14) || ((i == 2 || i == Width - 3) && j >= 4 && j <= 8) || (j == 11 && (i == 4 || i == 5 || i == 19 || i == 20)) || (j==8 && (i==9 || i==15)))
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image("/Resources/Terrain/indestructible2.png")},
                            false,
                            false,
                            1
                    );
                else
                    Map[i][j] = new PlainTile(i * TileMeasurement, j * TileMeasurement);
            }
        //noinspection IntegerDivisionInFloatingPointContext
        players[0] = new PlayerTank(
                Width / 2 - 2, Height - 2,
                (Width / 2 - 2) * TileMeasurement, (Height - 2) * TileMeasurement,
                new Image[]{new Image("/Resources/Tanks/Green/up1.png"),
                        new Image("/Resources/Tanks/Green/up2.png")},
                new Image[]{new Image("/Resources/Tanks/Green/down1.png"),
                        new Image("/Resources/Tanks/Green/down2.png")},
                new Image[]{new Image("/Resources/Tanks/Green/left1.png"),
                        new Image("/Resources/Tanks/Green/left2.png")},
                new Image[]{new Image("/Resources/Tanks/Green/right1.png"),
                        new Image("/Resources/Tanks/Green/right2.png")},
                new String[]{"UP", "DOWN", "LEFT", "RIGHT", "SLASH"},
                3
        );
        //noinspection IntegerDivisionInFloatingPointContext
        players[1] = new PlayerTank(
                Width / 2 + 2, Height - 2,
                (Width / 2 + 2) * TileMeasurement, (Height - 2) * TileMeasurement,
                new Image[]{new Image("/Resources/Tanks/Yellow/up1.png"),
                        new Image("/Resources/Tanks/Yellow/up2.png")},
                new Image[]{new Image("/Resources/Tanks/Yellow/down1.png"),
                        new Image("/Resources/Tanks/Yellow/down2.png")},
                new Image[]{new Image("/Resources/Tanks/Yellow/left1.png"),
                        new Image("/Resources/Tanks/Yellow/left2.png")},
                new Image[]{new Image("/Resources/Tanks/Yellow/right1.png"),
                        new Image("/Resources/Tanks/Yellow/right2.png")},
                new String[]{"W", "S", "A", "D", "G"},
                3
        );

        Players.add(players[0]);
        Players.add(players[1]);

        EnemyTank testEnemy =
                new EnemyTank(1, 1,
                        TileMeasurement, TileMeasurement,
                        new Image[]{new Image("/Resources/Tanks/Red/up1.png"),
                                new Image("/Resources/Tanks/Red/up2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/down1.png"),
                                new Image("/Resources/Tanks/Red/down2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/left1.png"),
                                new Image("/Resources/Tanks/Red/left2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/right1.png"),
                                new Image("/Resources/Tanks/Red/right2.png")},
                        500, 3, 2, 1
                );
        EnemyTank testEnemy2 =
                new EnemyTank(23, 1,
                        TileMeasurement * 23, TileMeasurement,
                        new Image[]{new Image("/Resources/Tanks/Red/up1.png"),
                                new Image("/Resources/Tanks/Red/up2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/down1.png"),
                                new Image("/Resources/Tanks/Red/down2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/left1.png"),
                                new Image("/Resources/Tanks/Red/left2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/right1.png"),
                                new Image("/Resources/Tanks/Red/right2.png")},
                        500, 3, 2, 1
                );
        EnemyTank testEnemy3 =
                new EnemyTank(14, 1,
                        TileMeasurement * 14, TileMeasurement,
                        new Image[]{new Image("/Resources/Tanks/Red/up1.png"),
                                new Image("/Resources/Tanks/Red/up2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/down1.png"),
                                new Image("/Resources/Tanks/Red/down2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/left1.png"),
                                new Image("/Resources/Tanks/Red/left2.png")},
                        new Image[]{new Image("/Resources/Tanks/Red/right1.png"),
                                new Image("/Resources/Tanks/Red/right2.png")},
                        500, 3, 2, 1
                );
        Enemies.add(testEnemy);
        EnemiesOverallLimit--;
        Enemies.add(testEnemy2);
        EnemiesOverallLimit--;
        Enemies.add(testEnemy3);
        EnemiesOverallLimit--;

        Pair<Integer, Integer> spawn = spawns.get(r.nextInt(spawns.size()));
        int iX, iY;

        for (int i = 0; i < EnemiesOverallLimit - 3; i++) {
            spawn = spawns.get(r.nextInt(spawns.size()));
            iX = spawn.getKey();
            iY = spawn.getValue();

            switch (r.nextInt(2)) {
                case 0: // Plain enemy
                    EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                            new Image[]{new Image("/Resources/Tanks/Red/up1.png"),
                                    new Image("/Resources/Tanks/Red/up2.png")},
                            new Image[]{new Image("/Resources/Tanks/Red/down1.png"),
                                    new Image("/Resources/Tanks/Red/down2.png")},
                            new Image[]{new Image("/Resources/Tanks/Red/left1.png"),
                                    new Image("/Resources/Tanks/Red/left2.png")},
                            new Image[]{new Image("/Resources/Tanks/Red/right1.png"),
                                    new Image("/Resources/Tanks/Red/right2.png")},
                            500, 3, 2, 2));
                    break;

                case 1: // Faster movement, normal shooting, low stamina
                    EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                            new Image[]{new Image("/Resources/Tanks/Blue/up1.png"),
                                    new Image("/Resources/Tanks/Blue/up2.png")},
                            new Image[]{new Image("/Resources/Tanks/Blue/down1.png"),
                                    new Image("/Resources/Tanks/Blue/down2.png")},
                            new Image[]{new Image("/Resources/Tanks/Blue/left1.png"),
                                    new Image("/Resources/Tanks/Blue/left2.png")},
                            new Image[]{new Image("/Resources/Tanks/Blue/right1.png"),
                                    new Image("/Resources/Tanks/Blue/right2.png")},
                            350, 6, 2, 1));
                    break;

                case 2: // Slow movement, fast shooting, low stamina
                    EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                            new Image[]{new Image("/Resources/Tanks/Purple/up1.png"),
                                    new Image("/Resources/Tanks/Purple/up2.png")},
                            new Image[]{new Image("/Resources/Tanks/Purple/down1.png"),
                                    new Image("/Resources/Tanks/Purple/down2.png")},
                            new Image[]{new Image("/Resources/Tanks/Purple/left1.png"),
                                    new Image("/Resources/Tanks/Purple/left2.png")},
                            new Image[]{new Image("/Resources/Tanks/Purple/right1.png"),
                                    new Image("/Resources/Tanks/Purple/right2.png")},
                            700, 2, 4, 3));
                    break;

                case 3: // Uber-enemy: fast & furious
                    EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                            new Image[]{new Image("/Resources/Tanks/Soviet/up1.png"),
                                    new Image("/Resources/Tanks/Soviet/up2.png")},
                            new Image[]{new Image("/Resources/Tanks/Soviet/down1.png"),
                                    new Image("/Resources/Tanks/Soviet/down2.png")},
                            new Image[]{new Image("/Resources/Tanks/Soviet/left1.png"),
                                    new Image("/Resources/Tanks/Soviet/left2.png")},
                            new Image[]{new Image("/Resources/Tanks/Soviet/right1.png"),
                                    new Image("/Resources/Tanks/Soviet/right2.png")},
                            500, 3, 5, 5));
                    break;
            }
        }

        spawn = spawns.get(r.nextInt(spawns.size()));
        iX = spawn.getKey();
        iY = spawn.getValue();
        EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                new Image[]{new Image("/Resources/Tanks/Soviet/up1.png"),
                        new Image("/Resources/Tanks/Soviet/up2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/down1.png"),
                        new Image("/Resources/Tanks/Soviet/down2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/left1.png"),
                        new Image("/Resources/Tanks/Soviet/left2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/right1.png"),
                        new Image("/Resources/Tanks/Soviet/right2.png")},
                2000, 4, 5, 5));


        spawn = spawns.get(r.nextInt(spawns.size()));
        iX = spawn.getKey();
        iY = spawn.getValue();
        EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                new Image[]{new Image("/Resources/Tanks/Soviet/up1.png"),
                        new Image("/Resources/Tanks/Soviet/up2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/down1.png"),
                        new Image("/Resources/Tanks/Soviet/down2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/left1.png"),
                        new Image("/Resources/Tanks/Soviet/left2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/right1.png"),
                        new Image("/Resources/Tanks/Soviet/right2.png")},
                2000, 4, 5, 5));


        spawn = spawns.get(r.nextInt(spawns.size()));
        iX = spawn.getKey();
        iY = spawn.getValue();
        EnemiesToAdd.add(new EnemyTank(iX, iY, iX * TileMeasurement, iY * TileMeasurement,
                new Image[]{new Image("/Resources/Tanks/Soviet/up1.png"),
                        new Image("/Resources/Tanks/Soviet/up2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/down1.png"),
                        new Image("/Resources/Tanks/Soviet/down2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/left1.png"),
                        new Image("/Resources/Tanks/Soviet/left2.png")},
                new Image[]{new Image("/Resources/Tanks/Soviet/right1.png"),
                        new Image("/Resources/Tanks/Soviet/right2.png")},
                2000, 4, 5, 5));
        //Eventually enemies on map will be limited to max 6,
    }


    // Add checking MovingTiles
    private boolean IsMovementPossible(int i, int j) {
        if (i >= 0 && i < Width && j >= 0 && j < Height) {
            for (MovingTile mt : MovingTiles
            ) {
                if (mt.IX == i && mt.IY == j && !mt.CanMoveThrough)
                    return false;
            }

            for (AbstractMap.SimpleEntry<Tank, Integer> explodingTank : ExplodingTanks
            ) {
                Tank tank = explodingTank.getKey();
                if (tank.IX == i && tank.IY == j && !tank.CanMoveThrough)
                    return false;
            }

            for (PlayerTank pt : Players) {
                if (pt.IX == i && pt.IY == j && !pt.CanMoveThrough)
                    return false;
            }

            for (EnemyTank et : Enemies) {
                if (et.IX == i && et.IY == j && !et.CanMoveThrough)
                    return false;
            }

            return Map[i][j].CanMoveThrough;
        }
        // Throw exception IndexOutOfBoard?
        return false;
    }


    private void AddMovingTile(MovingTile movingTile) {
        if (movingTile instanceof PlayerTank)
            Players.add((PlayerTank) movingTile);
        else if (movingTile instanceof EnemyTank)
            Enemies.add((EnemyTank) movingTile);
        else if (movingTile instanceof Bullet)
            Bullets.add((Bullet) movingTile);
    }

    private void DelMovingTile(MovingTile movingTile) {
        if (movingTile instanceof PlayerTank)
            Players.remove(movingTile);
        else if (movingTile instanceof EnemyTank)
            Enemies.remove(movingTile);
        else if (movingTile instanceof Bullet)
            Bullets.remove(movingTile);
    }

    private void CheckCollisions() {
        int bulletSize = TileMeasurement / (3 * 2);
        int tankSize = TileMeasurement;
        for (Bullet b : Bullets
        ) {

            if (b.getNoClipTime() <= 0) {
                for (PlayerTank pt : Players) {
                    if (b.getOwnerId() != pt.getId())
                        if (b.CheckCollision(bulletSize, pt.getX(), pt.getY(), tankSize)) {
                            if (--pt.stamina <= 0) {
                                pt.setLives(pt.getLives() - 1);
                                pt.IX = -1;
                                pt.IY = -1;

                                pt.Explode(ExplosionImage);
                                ExplodingTanks.offer(new AbstractMap.SimpleEntry<>(pt, ExplosionTime));
                                MovingTilesToDel.offer(pt);
                            }

                            MovingTilesToDel.offer(b);
                        }
                }

                for (EnemyTank et : Enemies) {
                    if (b.getOwnerId() != et.getId())
                        if (b.CheckCollision(bulletSize, et.getX(), et.getY(), tankSize) && et.IsDestroyed()) {
                            if (--et.stamina <= 0) {
                                for (PlayerTank player : players)
                                    if (player.getId() == b.getOwnerId())
                                        player.addScore(et.getReward());
                                et.Explode(ExplosionImage);
                                ExplodingTanks.offer(new AbstractMap.SimpleEntry<>(et, ExplosionTime));
                                MovingTilesToDel.offer(et);
                            }

                            MovingTilesToDel.offer(b);
                        }
                }

                for (Bullet b2 : Bullets) {
                    if (!b.equals(b2) && b.CheckCollision(bulletSize, b2.getX(), b2.getY(), (double) bulletSize)) {
                        MovingTilesToDel.offer(b);
                        MovingTilesToDel.offer(b2);
                    }
                }

                for (int i = 0; i < Map.length; i++)
                    for (int j = 0; j < Map[i].length; j++) {
                        if (!Map[i][j].CanShotThrough)
//                            Some second version to check collisions with walls, etc.?
                            if (b.CheckCollision(bulletSize, i * TileMeasurement, j * TileMeasurement, TileMeasurement)) {
                                if(Map[i][j].IsDestroyed()) {
                                    if(i == Width / 2 && j == Height - 2){
                                        players[0].setLives(0);
                                        players[1].setLives(0);
                                    }
                                    Map[i][j] = new PlainTile(i * TileMeasurement, j * TileMeasurement);
                                }

                                MovingTilesToDel.offer(b);
                            }
                    }

            } else
                b.setNoClipTime(b.getNoClipTime() - 1);

            // With borders
            if (b.CheckCollision(bulletSize, Width * TileMeasurement, Height * TileMeasurement))
                MovingTilesToDel.offer(b);
        }
        for (MovingTile mt : MovingTilesToDel
        ) {
            DelMovingTile(mt);
        }
        MovingTilesToDel.clear();
    }

    public void UpdateBoard(ArrayList<String> input, GraphicsContext gc) {
        if(Enemies.isEmpty() && EnemiesOverallLimit==0) /*&& Stack<EnemyTank>EnemiesToSpawn.isEmpty()*/{
            enemiesKilled=true;
        }
        CheckCollisions();
        if (EnemyAddTimer <= 0 && Enemies.size() < EnemiesLimit && EnemiesOverallLimit > 0) {
            if(!EnemiesToAdd.isEmpty())
                Enemies.add(EnemiesToAdd.poll());
            EnemyAddTimer = 180;
            EnemiesOverallLimit--;
        } else {
            EnemyAddTimer--;
        }
        for (int i = 0; i < Map.length; i++)
            for (int j = 0; j < Map[i].length; j++) {
                Tile t = Map[i][j];
                if (t.texture != null)
                    gc.drawImage(t.texture, i * TileMeasurement, j * TileMeasurement, TileMeasurement, TileMeasurement);
            }

        for (AbstractMap.SimpleEntry<Tank, Integer> explodingTank : ExplodingTanks) {
            Tank tank = explodingTank.getKey();
            explodingTank.setValue(explodingTank.getValue() - 1);
            gc.drawImage(tank.texture, tank.X, tank.Y, TileMeasurement, TileMeasurement);
        }

        ExplodingTanks.removeIf(explodingTank -> {
            if(explodingTank.getValue() <=0)
                if(explodingTank.getKey() instanceof PlayerTank) {
                    PlayerTank pt = (PlayerTank) explodingTank.getKey();
                    if (pt.getLives() > 0) {
                        if (pt.getId() == players[0].getId()) {
                            pt.IX = 10;
                        } else {
                            pt.IX = 14;
                        }
                        pt.IY = 15;
                        pt.setX(TileMeasurement * pt.IX);
                        pt.setY(TileMeasurement * pt.IY);

                        pt.setShooting(false);
                        pt.IsMoving = false;
                        pt.CanMoveThrough = false;
                        pt.CanShotThrough = false;

                        pt.stamina = 3;

                        pt.texture = pt.TextureUp[0];
                        pt.Direction = "UP";
                        Players.add(pt);
                    }
                }
            return explodingTank.getValue() <= 0;
        });

        for (PlayerTank pt : Players) {
            if (pt.getLives() == 0) {
                DelMovingTile(pt);
                pt.IX = -1;
                pt.IY = -1;
            }
            int TextureChangeTime = (int) (15 / pt.getSpeed());
            if (!pt.IsMoving) {
                // It enforces movement priorities - e.g. when you're pressing Left and then press Up or Down, then next move will be Up or Down. (Up > Down > Left > Right)
                if (input.contains(pt.getControl(0))) {

                    if (IsMovementPossible(pt.IX, pt.IY - 1)) {
                        pt.IY--;
                        pt.IsMoving = true;
                    }

                    pt.Direction = "UP";
                    pt.texture = pt.TextureUp[0];
                } else if (input.contains(pt.getControl(1))) {
                    if (IsMovementPossible(pt.IX, pt.IY + 1)) {
                        pt.IY++;
                        pt.IsMoving = true;
                    }

                    pt.Direction = "DOWN";
                    pt.texture = pt.TextureDown[0];
                } else if (input.contains(pt.getControl(2))) {
                    if (IsMovementPossible(pt.IX - 1, pt.IY)) {
                        pt.IX--;
                        pt.IsMoving = true;
                    }

                    pt.Direction = "LEFT";
                    pt.texture = pt.TextureLeft[0];

                } else if (input.contains(pt.getControl(3))) {
                    if (IsMovementPossible(pt.IX + 1, pt.IY)) {
                        pt.IX++;
                        pt.IsMoving = true;
                    }

                    pt.Direction = "RIGHT";
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

            if (!pt.isShooting())     // Not shooting
            {
                if (input.contains(pt.getControl(4)))     // Add Controls to Tank?
                {
                    pt.setShooting(true);
                    pt.ShotDelay();
                    int x, y;
                    switch (pt.Direction) {
                        case "UP":
                            x = (int) pt.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                            y = (int) pt.Y - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size

                            break;
                        case "DOWN":
                            x = (int) pt.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                            y = (int) pt.Y + TileMeasurement - TileMeasurement / 3; // TileMeasurement/3 = bullet size

                            break;
                        case "LEFT":
                            x = (int) pt.X - TileMeasurement / (2 * 3);
                            y = (int) pt.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size

                            break;
                        case "RIGHT":
                            x = (int) pt.X + TileMeasurement - TileMeasurement / (2 * 3);
                            y = (int) pt.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size

                            break;
                        default:       // Exception - Direction different from U,D,L,R
                            x = 0;
                            y = 0;
                            break;
                    }
//                    MovingTilesToAdd.offer(new Bullet(pt.IX, pt.IY, x, y, pt.Direction, Players.indexOf(pt)+1));
                    MovingTilesToAdd.offer(new Bullet(pt.IX, pt.IY, x, y, pt.Direction, pt.getId()));
                }
            } else { // Shooting
                pt.setShooting(pt.ReduceShotDelay());

            }
            gc.drawImage(pt.texture, pt.X, pt.Y, TileMeasurement, TileMeasurement);
        }

        for (Bullet b : Bullets) {
            int TextureChangeTime = (int) (15 / b.getSpeed());
            if (b.IsMoving) {

                // Bullet should always be moving.
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
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }
                        break;

                    case "LEFT":
                        b.X -= (double) TileMeasurement / 60 * b.getSpeed();

                        if (b.getTextureChangeCounter() % TextureChangeTime == 0) {
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }
                        break;

                    case "RIGHT":
                        b.X += (double) TileMeasurement / 60 * b.getSpeed();

                        if (b.getTextureChangeCounter() % TextureChangeTime == 0) {
                            b.ChangeTexture();

                            b.setTextureChangeCounter(0);
                        }
                        break;

                    default:
                        break;
                }
            }

            //noinspection IntegerDivisionInFloatingPointContext
            gc.drawImage(b.texture, b.X, b.Y, (TileMeasurement / 3), (TileMeasurement / 3));
        }

        for (EnemyTank et : Enemies) {
            int TextureChangeTime = (int) (15 / et.getSpeed());

            boolean worthShooting = et.MakeMove();
            if (!et.IsMoving) {
                switch (et.Direction) {
                    case "UP": {
                        if (IsMovementPossible(et.IX, et.IY - 1)) {
                            et.IY--;
                            et.IsMoving = true;
                        }
                        et.Direction = "UP";
                        et.texture = et.TextureUp[0];
                        break;
                    }
                    case "DOWN": {
                        if (IsMovementPossible(et.IX, et.IY + 1)) {
                            et.IY++;
                            et.IsMoving = true;
                        }

                        et.texture = et.TextureDown[0];
                        break;
                    }
                    case "LEFT": {
                        if (IsMovementPossible(et.IX - 1, et.IY)) {
                            et.IX--;
                            et.IsMoving = true;
                        }

                        et.Direction = "LEFT";
                        et.texture = et.TextureLeft[0];
                        break;
                    }
                    case "RIGHT": {
                        if (IsMovementPossible(et.IX + 1, et.IY)) {
                            et.IX++;
                            et.IsMoving = true;
                        }
                        et.texture = et.TextureRight[0];
                        break;
                    }
                }
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
                if (worthShooting)     // Add Controls to Tank?
                {
                    et.setShooting(true);
                    et.ShotDelay();
                    int x, y;
                    switch (et.Direction) {
                        case "UP":
                            x = (int) et.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                            y = (int) et.Y - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size

                            break;
                        case "DOWN":
                            x = (int) et.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                            y = (int) et.Y + TileMeasurement - TileMeasurement / 3; // TileMeasurement/3 = bullet size

                            break;
                        case "LEFT":
                            x = (int) et.X - TileMeasurement / (2 * 3);
                            y = (int) et.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size

                            break;
                        case "RIGHT":
                            x = (int) et.X + TileMeasurement - TileMeasurement / (2 * 3);
                            y = (int) et.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size

                            break;
                        default:       // Exception - Direction different from U,D,L,R
                            x = 0;
                            y = 0;
                            break;
                    }
                    MovingTilesToAdd.offer(new Bullet(et.IX, et.IY, x, y, et.Direction, et.getId()));

                }

            } else { // Shooting
                et.setShooting(et.ReduceShotDelay());
            }

            gc.drawImage(et.texture, et.X, et.Y, TileMeasurement, TileMeasurement);
        }

        while (!MovingTilesToAdd.isEmpty()) {
            AddMovingTile(MovingTilesToAdd.poll());
        }
    }
}

// Tiles sizes? E.g. bullet = Tile/3;
