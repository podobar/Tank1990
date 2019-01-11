package sample;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.awt.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class EnemyTank extends Tank {
    //Enemy tanks (AI) knows where Board.players and eagle are.
    //AI tries to either destroy eagle, or kill Board.players when it's possible
    //TODO: change w,h into board.getWidth(), board.getHeight() -> avoid hardcoding
    int w = 25;
    int h = 16;
    Tile eagle = new PlainTile(12, 14);
    int shotDelay;
    private Stack<Point> path;
    private int reward;

    public int getReward() {
        return reward;
    }

    public EnemyTank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight, int reward) {
        super(iX, iY, x, y, texturesUp, texturesDown, texturesLeft, texturesRight);
        CreatePath();
    }

    public boolean MakeMove() {
        if (!IsMoving) {
            if (CanShootToObjective())
                return true;

            if (path.isEmpty()) {
                CreatePath();
                if (path.isEmpty())
                    return false; //You reached your destination
            } else {
                Point nextTile = path.pop();
                if (Math.abs(nextTile.x - IX) + Math.abs(nextTile.y - IY) > 1) {
                    //Next move can't be performed, because it is "corner" move instead of "plus" move => Create new path
                    CreatePath();
                    if (path.isEmpty())
                        return false;
                    else
                        nextTile = path.pop();

                }
                //TODO: Perhaps some shooting when moving into destructible wall?
                if (nextTile.x == IX + 1) {
                    Direction = "RIGHT";
                    texture = TextureRight[0];
                } else if (nextTile.x == IX - 1) {
                    Direction = "LEFT";
                    texture = TextureLeft[0];
                } else if (nextTile.y == IY - 1) {
                    Direction = "UP";
                    texture = TextureUp[0];
                } else if (nextTile.y == IY + 1) {
                    Direction = "DOWN";
                    texture = TextureDown[0];
                }
            }
        }
        return false;
    }

    private boolean CanShootToObjective() {
        //TODO: Change texture when direction is changed
        //If there is no indestructible terrain, friendly tanks between this tank and eagle ( this tank is in row || column of eagle) -> shoot eagle
        if (Board.Map[eagle.IX][eagle.IY].stamina > 0) {
            if (eagle.IX == IX) {
                for (int i = IY + 1; i < eagle.IY; ++i) {
                    if (Board.Map[IX][i].CanBeDestroyed == false)
                        return false;
                }
                texture = TextureDown[0];
                Direction = "DOWN";
                return true;
            } else if (eagle.IY == IY) {
                if (eagle.IX < IX) {
                    for (int i = IX - 1; i > eagle.IX; --i) {
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    }
                    texture = TextureLeft[0];
                    Direction = "LEFT";
                    return true;
                } else //eagle.IX > IX
                {
                    for (int i = IX + 1; i < eagle.IX; ++i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureRight[0];
                    Direction = "RIGHT";
                    return true;
                }
            }
        }
        //If closest player is in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
        //If there is another player (not closest one) in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
        if (Math.sqrt(Math.pow(IX - Board.players[0].IX, 2) + Math.pow(IY - Board.players[0].IY, 2)) <= Math.sqrt(Math.pow(IX - Board.players[1].IX, 2) + Math.pow(IY - Board.players[1].IY, 2))) {
            //Distance from P1 is smaller than from P2
            if (Board.players[0].IX == IX) {
                if (Board.players[0].IY > IY) { //player lower than enemy
                    for (int i = IY + 1; i < Board.players[0].IY; ++i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureDown[0];
                    Direction = "DOWN";
                    return true;
                } else { //player higher than enemy
                    for (int i = IY - 1; i > Board.players[0].IY; --i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureUp[0];
                    Direction = "UP";
                    return true;
                }
            } else if (Board.players[0].IY == IY) {
                if (Board.players[0].IX < IX) { //player is on the left of the enemy
                    for (int i = IX - 1; i > Board.players[0].IX; --i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureLeft[0];
                    Direction = "LEFT";
                    return true;
                } else { //player is on the right of the enemy
                    for (int i = IX + 1; i < Board.players[0].IX; ++i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureRight[0];
                    Direction = "RIGHT";
                    return true;
                }
            }
            if (Board.players[1].IX == IX) {
                if (Board.players[1].IY > IY) { //player lower than enemy
                    for (int i = IY + 1; i < Board.players[1].IY; ++i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureDown[0];
                    Direction = "DOWN";
                    return true;
                } else { //player higher than enemy
                    for (int i = IY - 1; i > Board.players[1].IY; --i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureUp[0];
                    Direction = "UP";
                    return true;
                }
            } else if (Board.players[1].IY == IY) {
                if (Board.players[1].IX < IX) { //player is on the left of the enemy
                    for (int i = IX - 1; i > Board.players[1].IX; --i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureLeft[0];
                    Direction = "LEFT";
                    return true;
                } else { //player is on the right of the enemy
                    for (int i = IX + 1; i < Board.players[1].IX; ++i)
                        if (Board.Map[i][IY].CanBeDestroyed)
                            return false;
                    texture = TextureRight[0];
                    Direction = "RIGHT";
                    return true;
                }
            }
        } else //Distance from P1 is higher than from P2
        {
            if (Board.players[1].IX == IX) {
                if (Board.players[1].IY > IY) { //player lower than enemy
                    for (int i = IY + 1; i < Board.players[0].IY; ++i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureDown[0];
                    Direction = "DOWN";
                    return true;
                } else { //player higher than enemy
                    for (int i = IY - 1; i > Board.players[1].IY; --i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureUp[0];
                    Direction = "UP";
                    return true;
                }
            } else if (Board.players[1].IY == IY) {
                if (Board.players[1].IX < IX) { //player is on the left of the enemy
                    for (int i = IX - 1; i > Board.players[1].IX; --i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureLeft[0];
                    Direction = "LEFT";
                    return true;
                } else { //player is on the right of the enemy
                    for (int i = IX + 1; i < Board.players[1].IX; ++i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureRight[0];
                    Direction = "RIGHT";
                    return true;
                }
            }
            if (Board.players[0].IX == IX) {
                if (Board.players[0].IY > IY) { //player lower than enemy
                    for (int i = IY + 1; i < Board.players[0].IY; ++i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureDown[0];
                    Direction = "DOWN";
                    return true;
                } else { //player higher than enemy
                    for (int i = IY - 1; i > Board.players[0].IY; --i)
                        if (Board.Map[IX][i].CanBeDestroyed == false)
                            return false;
                    texture = TextureUp[0];
                    Direction = "UP";
                    return true;
                }
            } else if (Board.players[0].IY == IY) {
                if (Board.players[0].IX < IX) { //player is on the left of the enemy
                    for (int i = IX - 1; i > Board.players[0].IX; --i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureLeft[0];
                    Direction = "LEFT";
                    return true;
                } else { //player is on the right of the enemy
                    for (int i = IX + 1; i < Board.players[0].IX; ++i)
                        if (Board.Map[i][IY].CanBeDestroyed == false)
                            return false;
                    texture = TextureRight[0];
                    Direction = "RIGHT";
                    return true;
                }
            }
        }
        return false;
    }

    private void CreatePath() {
        //TODO: Check why second enemy(right upper corner doesn't do what he's supposed to do (except for not changing texture when changing direction)) suddenly stops
        //Own implementation of A* algorithm, on start we have:
        // -IX, IY - tile coordinates,
        // -We know where the eagle and Board.players are,
        // -We have a Board.Map of Tiles (game Board.Map)
        // -We can only move up, down, left or right

        //Assumptions of Tile weights:
        // -Tanks are like indestructible terrain tiles, their weight is Double.POSITIVE_INFINITY;
        // -Destructible tiles have weight which depends on delay between shots multiplied by their stamina (terrain can take certain amount of damage)
        // -Normal tiles have weight = 1
        Double[][] estimations = new Double[w][h];
        Double[][] distance = new Double[w][h];
        Point[][] previous = new Point[w][h];
        for (int i = 0; i < w; ++i)
            for (int j = 0; j < h; ++j) {
                estimations[i][j] = Math.sqrt(Math.pow(j - eagle.IY, 2) + Math.pow(i - eagle.IX, 2)); //Euclidean distance as approx. heurestic
                distance[i][j] = Double.POSITIVE_INFINITY;
            }
        distance[IX][IY] = 0.0;
        path = new Stack<>();
        PriorityQueue<Point> open = new PriorityQueue<Point>((p1, p2) -> {
            return (int) (distance[p1.x][p1.y] + estimations[p1.x][p1.y] - distance[p2.x][p2.y] + estimations[p2.x][p2.y]);
        });
        PriorityQueue<Point> close = new PriorityQueue<Point>((p1, p2) -> {
            return (int) (distance[p1.x][p1.y] + estimations[p1.x][p1.y] - distance[p2.x][p2.y] + estimations[p2.x][p2.y]);
        });

        open.add(new Point(IX, IY));

        while (!open.isEmpty()) {
            Point p = open.poll();
            close.add(p);
            if (eagle.IX == p.x && eagle.IY == p.y)
                break;
            Point leftNeighbor = new Point(p.x - 1, p.y); //LEFT
            Point rightNeighbor = new Point(p.x + 1, p.y); //RIGHT
            Point upNeighbor = new Point(p.x, p.y - 1); //UP
            Point downNeighbor = new Point(p.x, p.y + 1); //DOWN

            if (p.x - 1 >= 0 && !close.contains(leftNeighbor)) {
                if (!open.contains(leftNeighbor)) {
                    distance[p.x - 1][p.y] = Double.POSITIVE_INFINITY;
                    open.add(leftNeighbor);
                }
                if (distance[p.x - 1][p.y] > distance[p.x][p.y] + EdgeWeight(p, leftNeighbor)) {
                    distance[p.x - 1][p.y] = distance[p.x][p.y] + EdgeWeight(p, leftNeighbor);
                    //update priorities
                    open.remove(leftNeighbor);
                    open.add(leftNeighbor);

                    previous[leftNeighbor.x][leftNeighbor.y] = p;
                }
            }
            if (p.x + 1 <= w - 1 && !close.contains(rightNeighbor)) {
                if (!open.contains(rightNeighbor)) {
                    distance[p.x + 1][p.y] = Double.POSITIVE_INFINITY;
                    open.add(rightNeighbor);
                }
                if (distance[p.x + 1][p.y] > distance[p.x][p.y] + EdgeWeight(p, rightNeighbor)) {
                    distance[p.x + 1][p.y] = distance[p.x][p.y] + EdgeWeight(p, rightNeighbor);
                    //update priorities
                    open.remove(rightNeighbor);
                    open.add(rightNeighbor);

                    previous[rightNeighbor.x][rightNeighbor.y] = p;
                }
            }
            if (p.y + 1 <= h - 1 && !close.contains(downNeighbor)) {
                if (!open.contains(downNeighbor)) {
                    distance[p.x][p.y + 1] = Double.POSITIVE_INFINITY;
                    open.add(downNeighbor);
                }
                if (distance[p.x][p.y + 1] > distance[p.x][p.y] + EdgeWeight(p, downNeighbor)) {
                    distance[p.x][p.y + 1] = distance[p.x][p.y] + EdgeWeight(p, downNeighbor);
                    //update priorities
                    open.remove(downNeighbor);
                    open.add(downNeighbor);

                    previous[downNeighbor.x][downNeighbor.y] = p;
                }
            }
            if (p.y - 1 >= 0 && !close.contains(upNeighbor)) {
                if (!open.contains(upNeighbor)) {
                    distance[p.x][p.y - 1] = Double.POSITIVE_INFINITY;
                    open.add(upNeighbor);
                }
                if (distance[p.x][p.y - 1] > distance[p.x][p.y] + EdgeWeight(p, upNeighbor)) {
                    distance[p.x][p.y - 1] = distance[p.x][p.y] + EdgeWeight(p, upNeighbor);
                    //update priorities
                    open.remove(upNeighbor);
                    open.add(upNeighbor);

                    previous[upNeighbor.x][upNeighbor.y] = p;
                }
            }
        }//while
        if (previous[eagle.IX][eagle.IY] != null) {
            Point pathNode = previous[eagle.IX][eagle.IY];
            while (pathNode.x != IX || pathNode.y != IY) {
                path.add((Point) pathNode.clone());
                pathNode = previous[pathNode.x][pathNode.y];
            }
        }
    }

    private Double EdgeWeight(Point from, Point to) {//From [i,j] to [k,l]
        if (Board.Map[to.x][to.y].CanMoveThrough)
            return 1.0;
        else if (Board.Map[to.x][to.y].CanBeDestroyed)
            return Board.Map[to.x][to.y].stamina * 1.0;
        else
            return Double.POSITIVE_INFINITY;
    }
}
