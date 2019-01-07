package sample;

import javafx.util.Pair;

import java.awt.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class EnemyTank extends Tank {
    //Enemy tanks (AI) knows where players and eagle are.
    //AI tries to either destroy eagle, or kill players when it's possible
    Tile[][] map;
    PlayerTank[] players;
    Tile eagle;
    int mapHeight, mapWidth;
    int shotDelay;

    private void Strategy() {
        while (true) {
            if (CanShoot())
                Shoot();
            else if (IsMovePossible())
                Move();
            else
                Shoot();
        }
    }

    private boolean CanShoot() {
        //TODO: Check - maybe you also have to decide in which direction shoot
        //If there is no indestructible terrain, friendly tanks between this tank and eagle ( this tank is in row || column of eagle) -> shoot eagle
        if (eagle.IX == IX) {
            for (int i = Math.min(eagle.IY, IY) + 1; i < Math.max(eagle.IY, IY) - 1; ++i)
                if (map[i][IX] instanceof EnemyTank || !map[i][IX].CanShotThrough)
                    return false;
            return true;
        } else if (eagle.IY == IY) {
            for (int i = Math.min(eagle.IX, IX) + 1; i < Math.max(eagle.IX, IX) - 1; ++i)
                if (map[IY][i] instanceof EnemyTank || !map[IY][i].CanShotThrough)
                    return false;
            return true;
        }
        //If closest player is in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
        //If there is another player (not closest one) in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
        if (Math.sqrt(Math.pow(IX - players[0].IX, 2) + Math.pow(IY - players[0].IY, 2)) <= Math.sqrt(Math.pow(IX - players[1].IX, 2) + Math.pow(IY - players[1].IY, 2))) {
            //Distance from P1 is smaller than P2
            if (players[0].IX == IX) {
                for (int i = Math.min(players[0].IY, IY) + 1; i < Math.max(players[0].IY, IY) - 1; ++i)
                    if (map[i][IX] instanceof EnemyTank || !map[i][IX].CanShotThrough)
                        return false;
                return true;
            } else if (players[0].IY == IY) {
                for (int i = Math.min(players[0].IX, IX) + 1; i < Math.max(players[0].IX, IX) - 1; ++i)
                    if (map[IY][i] instanceof EnemyTank || !map[IY][i].CanShotThrough)
                        return false;
                return true;
            } else if (players[1].IX == IX) {
                for (int i = Math.min(players[1].IY, IY) + 1; i < Math.max(players[1].IY, IY) - 1; ++i)
                    if (map[i][IX] instanceof EnemyTank || !map[i][IX].CanShotThrough)
                        return false;
                return true;
            } else if (players[1].IY == IY) {
                for (int i = Math.min(players[1].IX, IX) + 1; i < Math.max(players[1].IX, IX) - 1; ++i)
                    if (map[IY][i] instanceof EnemyTank || !map[IY][i].CanShotThrough)
                        return false;
                return true;
            }
        } else {
            if (players[1].IX == IX) {
                for (int i = Math.min(players[1].IY, IY) + 1; i < Math.max(players[1].IY, IY) - 1; ++i)
                    if (map[i][IX] instanceof EnemyTank || !map[i][IX].CanShotThrough)
                        return false;
                return true;
            } else if (players[1].IY == IY) {
                for (int i = Math.min(players[1].IX, IX) + 1; i < Math.max(players[1].IX, IX) - 1; ++i)
                    if (map[IY][i] instanceof EnemyTank || !map[IY][i].CanShotThrough)
                        return false;
                return true;
            }
            if (players[0].IX == IX) {
                for (int i = Math.min(players[0].IY, IY) + 1; i < Math.max(players[0].IY, IY) - 1; ++i)
                    if (map[i][IX] instanceof EnemyTank || !map[i][IX].CanShotThrough)
                        return false;
                return true;
            } else if (players[0].IY == IY) {
                for (int i = Math.min(players[0].IX, IX) + 1; i < Math.max(players[0].IX, IX) - 1; ++i)
                    if (map[IY][i] instanceof EnemyTank || !map[IY][i].CanShotThrough)
                        return false;
                return true;
            }
        }
        return true;
    }

    private void Shoot() {
        //TODO: shoot a bullet i

    }

    private boolean IsMovePossible() {
        //TODO: If any shots to objectives is not available(player tanks, eagle) and tank can't move (is stuck between terrain tiles, but not between other AI tanks) it shoots to terrain.
        return true;
    }

    private void Move() {
        //TODO: Own implementation of A* algorithm to move through the map
        //What we wave on start:
        // -IX, IY - tile coordinates,
        // -We know where the eagle and players are,
        // -We have a map of Tiles (game map)
        // -We can only move up, down, left or right

        //Assumptions of Tile weights:
        // -Tanks are like indestructible terrain tiles, their weight is Double.POSITIVE_INFINITY;
        // -Destructible tiles have weight which depends on delay between shots multiplied by their stamina (terrain can take certain amount of damage)
        // -Normal tiles have weight = 1

        Double[][] estimations = new Double[mapHeight][mapWidth];
        Double[][] distance = new Double[mapHeight][mapWidth];
        Point[][] previous = new Point[mapHeight][mapWidth];
        for (Tile[] row : map) {
            for (Tile t : row) {
                estimations[t.IY][t.IX] = Math.sqrt(Math.pow(t.IY - eagle.IY, 2) + Math.pow(t.IX - eagle.IX, 2)); //Euclidean distance as approx. heurestic
                distance[t.IY][t.IX] = Double.POSITIVE_INFINITY;
            }
        }
        distance[IX][IY] = 0.0;
        Stack<Point> path = new Stack<>();
        PriorityQueue<Point> open = new PriorityQueue<Point>((p1, p2) -> {
            return (int) (distance[p1.y][p1.x] + estimations[p1.y][p1.x] - distance[p2.y][p2.x] + estimations[p2.y][p2.x]);
        });
        PriorityQueue<Point> close = new PriorityQueue<Point>((p1, p2) -> {
            return (int) (distance[p1.y][p1.x] + estimations[p1.y][p1.x] - distance[p2.y][p2.x] + estimations[p2.y][p2.x]);
        });

        open.add(new Point(IX, IY));

        while (!open.isEmpty()) {
            Point p = open.poll();
            close.add(p);
            if (eagle.IX == p.x && eagle.IY == p.y)
                break;
            //add neighbors (left right up down) if they're not in close
            Point leftNeighbor = new Point(p.x - 1, p.y); //LEFT
            Point rightNeighbor = new Point(p.x + 1, p.y); //RIGHT
            Point upNeighbor = new Point(p.x, p.y - 1); //UP
            Point downNeighbor = new Point(p.x, p.y + 1); //DOWN

            if (p.x - 1 >= 0 && !close.contains(leftNeighbor)) {
                if (!open.contains(leftNeighbor)) {
                    distance[p.y][p.x - 1] = Double.POSITIVE_INFINITY;
                    open.add(leftNeighbor);
                }
                if (distance[p.y][p.x - 1] > distance[p.y][p.x] + EdgeWeight(p, leftNeighbor)) {
                    distance[p.y][p.x - 1] = distance[p.y][p.x] + EdgeWeight(p, leftNeighbor);
                    //update priorities
                    open.remove(leftNeighbor);
                    open.add(leftNeighbor);

                    previous[leftNeighbor.y][leftNeighbor.x] = p;
                }
            }
            if (p.x + 1 >= 0 && !close.contains(rightNeighbor)) {
                if (!open.contains(rightNeighbor)) {
                    distance[p.y][p.x + 1] = Double.POSITIVE_INFINITY;
                    open.add(rightNeighbor);
                }
                if (distance[p.y][p.x + 1] > distance[p.y][p.x] + EdgeWeight(p, rightNeighbor)) {
                    distance[p.y][p.x + 1] = distance[p.y][p.x] + EdgeWeight(p, rightNeighbor);
                    //update priorities
                    open.remove(rightNeighbor);
                    open.add(rightNeighbor);

                    previous[rightNeighbor.y][rightNeighbor.x] = p;
                }
            }
            if (p.y + 1 >= 0 && !close.contains(downNeighbor)) {
                if (!open.contains(downNeighbor)) {
                    distance[p.y + 1][p.x] = Double.POSITIVE_INFINITY;
                    open.add(downNeighbor);
                }
                if (distance[p.y + 1][p.x] > distance[p.y][p.x] + EdgeWeight(p, downNeighbor)) {
                    distance[p.y + 1][p.x] = distance[p.y][p.x] + EdgeWeight(p, downNeighbor);
                    //update priorities
                    open.remove(downNeighbor);
                    open.add(downNeighbor);

                    previous[downNeighbor.y][downNeighbor.x] = p;
                }
            }
            if (p.y - 1 >= 0 && !close.contains(upNeighbor)) {
                if (!open.contains(upNeighbor)) {
                    distance[p.y - 1][p.x] = Double.POSITIVE_INFINITY;
                    open.add(upNeighbor);
                }
                if (distance[p.y - 1][p.x] > distance[p.y][p.x] + EdgeWeight(p, upNeighbor)) {
                    distance[p.y - 1][p.x] = distance[p.y][p.x] + EdgeWeight(p, upNeighbor);
                    //update priorities
                    open.remove(upNeighbor);
                    open.add(upNeighbor);

                    previous[upNeighbor.y][upNeighbor.x] = p;
                }
            }
        }//while
        Point pathNode = previous[eagle.IY][eagle.IX];
        while (pathNode.x != IX && pathNode.y != IY) {
            path.add((Point) pathNode.clone());
            pathNode = previous[pathNode.y][pathNode.x];
        }
        //On top of the stack is the next move;
    }

    private Double EdgeWeight(Point from, Point to) {//From [i,j] to [k,l]
        if (map[to.y][to.x].CanMoveThrough)
            return 1.0;
        else if (map[to.y][to.x].CanShotThrough)
            return map[to.y][to.x].stamina * 1.0;
        else
            return Double.POSITIVE_INFINITY;
    }
}
