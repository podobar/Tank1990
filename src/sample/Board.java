package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Controllers.GameViewController;

import java.io.File;
import java.util.*;


public class Board {
    public static Tile[][] Map;
    public static PlayerTank[] players = new PlayerTank[2];
    public static boolean enemiesKilled=false;
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
    private int ExplosionTime;
    private Image ExplosionImage;

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
        ExplosionImage = new Image(new File("Resources/Explosion1/3.png").toURI().toString());
        // TODO: to think about how long does explosion stay
        ExplosionTime = 60;
    }

    public Tile getTile(int i, int j) {
        return Map[i][j];
    }

    // Add checking whether some MovingTile isn't on Tile (collisions)

    public void GenerateMap() {
       MovingTiles = new LinkedList<>();   // <MovingTile>
        MovingTilesToAdd = new LinkedList<>(); // Queue
        MovingTilesToDel = new LinkedList<>();
        ExplodingTanks = new LinkedList<>();

        //MapTile
        Map = new Tile[Width][Height];

        Bullets = new LinkedList<>();
        Players = new LinkedList<>();
        Enemies = new LinkedList<>();

        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++) {


                if (i == Width / 2 && j == Height - 2) {
                    //Eagle
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image(new File("Resources/Eagle/eagle.jpg").toURI().toString())},
                            false,
                            true,
                            1
                    );
                }
                else if (((i == Width / 2 - 1 || i == Width / 2 || i == Width / 2 + 1) && j == Height - 3)
                        || ((i == Width / 2 - 1 || i == Width / 2 + 1) && j == Height - 2)) {
                    //Bricks around the eagle
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image(new File("Resources/Terrain/bricks3.jpg").toURI().toString()),
                                        new Image(new File("Resources/Terrain/bricks2.jpg").toURI().toString()),
                                        new Image(new File("Resources/Terrain/bricks1.jpg").toURI().toString())},
                            false,
                            true,
                            3
                    );
                }
                else if (i == 0 || i == Map.length - 1 || j == 0 || j == Map[i].length - 1)
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image(new File("Resources/Terrain/bricks3.jpg").toURI().toString()),
                                        new Image(new File("Resources/Terrain/bricks2.jpg").toURI().toString()),
                                        new Image(new File("Resources/Terrain/bricks1.jpg").toURI().toString())},
                            false,
                            true,
                            3
                    );
                else if(j==3 && i>=10 && i<=14){
                    Map[i][j] = new PlainTile(
                            i * TileMeasurement, j * TileMeasurement,
                            new Image[]{new Image(new File("Resources/Terrain/indestructible.png").toURI().toString())},
                            false,
                            false,
                            1
                    );
                }
                else
                    Map[i][j] = new PlainTile(i * TileMeasurement, j * TileMeasurement);
            }
        players[0] = new PlayerTank(
                Width / 2 - 2, Height - 2,
                (Width / 2 - 2) * TileMeasurement, (Height - 2) * TileMeasurement,
                new Image[]{new Image(new File("Resources/Tanks/Green/up1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Green/up2.png").toURI().toString())},
                new Image[]{new Image(new File("Resources/Tanks/Green/down1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Green/down2.png").toURI().toString())},
                new Image[]{new Image(new File("Resources/Tanks/Green/left1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Green/left2.png").toURI().toString())},
                new Image[]{new Image(new File("Resources/Tanks/Green/right1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Green/right2.png").toURI().toString())},
                new String[]{"UP", "DOWN", "LEFT", "RIGHT", "SLASH"},
                1
        );
        players[1] = new PlayerTank(
                Width / 2 + 2, Height - 2,
                (Width / 2 + 2) * TileMeasurement, (Height - 2) * TileMeasurement,
                new Image[]{new Image(new File("Resources/Tanks/Blue/up1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Blue/up2.png").toURI().toString())},
                new Image[]{new Image(new File("Resources/Tanks/Blue/down1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Blue/down2.png").toURI().toString())},
                new Image[]{new Image(new File("Resources/Tanks/Blue/left1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Blue/left2.png").toURI().toString())},
                new Image[]{new Image(new File("Resources/Tanks/Blue/right1.png").toURI().toString()),
                        new Image(new File("Resources/Tanks/Blue/right2.png").toURI().toString())},
                new String[]{"W", "S", "A", "D", "G"},
                1
        );

        Players.add(players[0]);
        Players.add(players[1]);

        EnemyTank testEnemy =
                new EnemyTank(1,1,
                        TileMeasurement,TileMeasurement,
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/up1.png").toURI().toString()),
                                    new Image(new File("Resources/Tanks/Soviet/up2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/down1.png").toURI().toString()),
                                    new Image(new File("Resources/Tanks/Soviet/down2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/left1.png").toURI().toString()),
                                    new Image(new File("Resources/Tanks/Soviet/left2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/right1.png").toURI().toString()),
                                    new Image(new File("Resources/Tanks/Soviet/right2.png").toURI().toString()),},
                        500
                        );
        EnemyTank testEnemy2 =
                new EnemyTank(23,1,
                        TileMeasurement*23,TileMeasurement*1,
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/up1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/up2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/down1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/down2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/left1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/left2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/right1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/right2.png").toURI().toString())},
                        500
                );
        EnemyTank testEnemy3 =
                new EnemyTank(14,1,
                        TileMeasurement*14,TileMeasurement*1,
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/up1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/up2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/down1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/down2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/left1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/left2.png").toURI().toString())},
                        new Image[]{new Image(new File("Resources/Tanks/Soviet/right1.png").toURI().toString()),
                                new Image(new File("Resources/Tanks/Soviet/right2.png").toURI().toString())},
                        500
                );
        Enemies.add(testEnemy);
        Enemies.add(testEnemy2);
        Enemies.add(testEnemy3);
        //Eventually enemies on map will be limited to max 6,
    }


    // Add checking MovingTiles
    public boolean IsMovementPossible(int i, int j) {
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

            for(PlayerTank pt: Players){
                if (pt.IX == i && pt.IY == j && !pt.CanMoveThrough)
                    return false;
            }

            for(EnemyTank et: Enemies){
                if (et.IX == i && et.IY == j && !et.CanMoveThrough)
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

    public void DelMovingTile(MovingTile movingTile){
        if(movingTile instanceof PlayerTank)
            Players.remove(movingTile);
        else if(movingTile instanceof EnemyTank)
            Enemies.remove(movingTile);
        else if(movingTile instanceof Bullet)
            Bullets.remove(movingTile);
    }

    public void CheckCollisions() {
        int bulletSize = TileMeasurement / (3 * 2);
        int tankSize = TileMeasurement;
        for (Bullet b : Bullets
        ) {

            if (b.getNoClipTime() <= 0) {
                for (PlayerTank pt : Players) {
                    // TODO: Change to bottom if, that one lets shooting between players
                    if(b.getOwnerId() != Players.indexOf(pt) + 1)
//                    // TODO: Change 1 and 2 to some (global) variables?
//                    if (b.getOwnerId() != 1 && b.getOwnerId() != 2)
                        if (b.CheckCollision(bulletSize, pt.getX(), pt.getY(), tankSize)) {
                            // TODO: reacting correctly to being shot (e.g. updating hp), it's something to talk about
                            // TODO: BUG when one player is destroyed, enemy is shooting into the wall (but he wants to kill us) but then phew! Lots of exceptions
                            if(pt.stamina==0){
                                pt.setLives(pt.getLives()-1);
                                pt.IX=-1; pt.IY=-1;
                            }
                            else{
                                //TODO: Respawn (checking if respawn field is empty, what if isn't?)
                            }

                            pt.Explode(ExplosionImage);
                            MovingTilesToDel.offer(b);
                            ExplodingTanks.offer(new AbstractMap.SimpleEntry<>(pt, ExplosionTime));
                            MovingTilesToDel.offer(pt);
                        }
                }

                for (EnemyTank et : Enemies) {
                    if (b.getOwnerId() != 0)
                        if (b.CheckCollision(bulletSize, et.getX(), et.getY(), tankSize) && et.IsDestroyed()) {
                            players[b.getOwnerId()-1].addScore(500);
                            et.Explode(ExplosionImage);
                            MovingTilesToDel.offer(b);
                            ExplodingTanks.offer(new AbstractMap.SimpleEntry<>(et,ExplosionTime));
                            MovingTilesToDel.offer(et);
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
                            if (b.CheckCollision(bulletSize, i*TileMeasurement, j*TileMeasurement, TileMeasurement)) {
                                // TODO: after collision, what happens with tile.
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
        for (MovingTile mt: MovingTilesToDel
             ) {
            DelMovingTile(mt);
        }
    }

    public void UpdateBoard(ArrayList<String> input, GraphicsContext gc) {
        if(Enemies.isEmpty()) /*&& Stack<EnemyTank>EnemiesToSpawn.isEmpty()*/{
            enemiesKilled=true;
        }
        CheckCollisions();
        for(int i = 0; i < Map.length; i++)
            for(int j = 0; j < Map[i].length; j++) {
                Tile t = Map[i][j];
                if (t.texture != null)
                    gc.drawImage(t.texture, i * TileMeasurement, j * TileMeasurement, TileMeasurement, TileMeasurement);
            }

            for(AbstractMap.SimpleEntry<Tank, Integer> explodingTank: ExplodingTanks) {
                Tank tank = explodingTank.getKey();
                explodingTank.setValue(explodingTank.getValue() - 1);
                gc.drawImage(tank.texture, tank.X, tank.Y, TileMeasurement, TileMeasurement);
            }

            ExplodingTanks.removeIf(explodingTank -> {
                // TODO: it's probably place to implement player tanks respawn
                if (explodingTank.getValue() <= 0)
                    return true;
                return false;
            });

        for (PlayerTank pt : Players) {
            if(pt.getLives()==0){
                DelMovingTile(pt);
                pt.IX=-1;
                pt.IY=-1;
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
                    if (pt.Direction.equals("UP")) {
                        x = (int) pt.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                        y = (int) pt.Y - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size
                    } else if (pt.Direction.equals("DOWN")) {
                        x = (int) pt.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                        y = (int) pt.Y + TileMeasurement - TileMeasurement / 3; // TileMeasurement/3 = bullet size
                    } else if (pt.Direction.equals("LEFT")) {
                        x = (int) pt.X - TileMeasurement / (2 * 3);
                        y = (int) pt.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size
                    } else if (pt.Direction.equals("RIGHT")) {
                        x = (int) pt.X + TileMeasurement - TileMeasurement / (2 * 3);
                        y = (int) pt.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size
                    } else {      // Exception - Direction different from U,D,L,R
                        x = 0;
                        y = 0;
                    }
                    MovingTilesToAdd.offer(new Bullet(pt.IX, pt.IY, x, y, pt.Direction, Players.indexOf(pt)+1));

                }
            } else { // Shooting
                pt.setShooting(pt.ReduceShotDelay());
            }
            gc.drawImage(pt.texture, pt.X, pt.Y, TileMeasurement, TileMeasurement);
        }

        for (Bullet b : Bullets ) {
            int TextureChangeTime = (int) (15 / b.getSpeed());
            if (b.IsMoving) {

                // Bullet should always be moving.
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

            gc.drawImage(b.texture, b.X, b.Y, (int) (TileMeasurement / 3), (int) (TileMeasurement / 3));
        }

        for (EnemyTank et : Enemies) {
            int TextureChangeTime = (int) (15 / et.getSpeed());

            // TODO: Enemies movement
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
            }
            else {
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
                    if (worthShooting)     // Add Controls to Tank?
                    {
                        et.setShooting(true);
                        et.ShotDelay();
                        int x, y;
                        if (et.Direction.equals("UP")) {
                            x = (int) et.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                            y = (int) et.Y - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size
                        } else if (et.Direction.equals("DOWN")) {
                            x = (int) et.X + TileMeasurement / 2 - TileMeasurement / (2 * 3);
                            y = (int) et.Y + TileMeasurement - TileMeasurement / 3; // TileMeasurement/3 = bullet size
                        } else if (et.Direction.equals("LEFT")) {
                            x = (int) et.X - TileMeasurement / (2 * 3);
                            y = (int) et.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size
                        } else if (et.Direction.equals("RIGHT")) {
                            x = (int) et.X + TileMeasurement - TileMeasurement / (2 * 3);
                            y = (int) et.Y + TileMeasurement / 2 - TileMeasurement / (2 * 3); // TileMeasurement/3 = bullet size
                        } else {      // Exception - Direction different from U,D,L,R
                            x = 0;
                            y = 0;
                        }
                        MovingTilesToAdd.offer(new Bullet(et.IX, et.IY, x, y, et.Direction, 0));

                    }

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
