package sample;

import javafx.util.Pair;

import java.awt.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class EnemyTank extends Tank{
    //Enemy tanks (AI) knows where players and eagle are.
    //AI tries to either destroy eagle, or kill players when it's possible
    Tile[][] map;
    PlayerTank[] players;
    Tile eagle;
    int mapHeight,mapWidth;
    int shotDelay;
    Stack<Point> path;
    String Direction;

    private void Strategy(){
        while(true){
            if(CanShootToObjective())
                Main.input.add("SHOOT");
            else {
                Move();
                if(!IsMovePossible(path.pop()))//When we want to move into destructible terrain
                    Main.input.add("SHOOT");
                else
                    Main.input.add(Direction); //??
            }
        }
    }
    private boolean CanShootToObjective(){
        //TODO: Check - maybe you also have to decide in which direction shoot
        //If there is no indestructible terrain, friendly tanks between this tank and eagle ( this tank is in row || column of eagle) -> shoot eagle
        if(eagle.IX == IX){
            for(int i = IY-1;i>eagle.IY;--i)
                if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                    return false;
            Direction="DOWN";
            return true;
        }
        else if(eagle.IY == IY){
            if(eagle.IX < IX)
            {
                for(int i =IX-1;i>eagle.IX;--i)
                    if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                        return false;
                Direction="LEFT";
                return true;
            }
            else //eagle.IX > IX
            {
                for(int i =IX+1;i<eagle.IX;++i)
                    if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                        return false;
                Direction="RIGHT";
                return true;
            }
        }
        //If closest player is in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
        //If there is another player (not closest one) in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
        if(Math.sqrt(Math.pow(IX-players[0].IX,2) + Math.pow(IY-players[0].IY,2))<= Math.sqrt(Math.pow(IX-players[1].IX,2) + Math.pow(IY-players[1].IY,2))){
            //Distance from P1 is smaller than from P2
            if(players[0].IX == IX){
                if(players[0].IY < IY){ //player lower than enemy
                    for(int i = IY-1;i>players[0].IY;--i)
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="DOWN";
                    return true;
                }
                else{ //player higher than enemy
                    for(int i = IY+1; i< players[0].IY;++i )
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="UP";
                    return true;
                }
            }
            else if(players[0].IY == IY){
                if(players[0].IX < IX){ //player is on the left of the enemy
                    for(int i = IX-1;i>players[0].IX;--i)
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="LEFT";
                    return true;
                }
                else{ //player is on the right of the enemy
                    for(int i = IX+1; i< players[0].IX;++i )
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="RIGHT";
                    return true;
                }
            }
            if(players[1].IX == IX){
                if(players[1].IY < IY){ //player lower than enemy
                    for(int i = IY-1;i>players[1].IY;--i)
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="DOWN";
                    return true;
                }
                else{ //player higher than enemy
                    for(int i = IY+1; i< players[1].IY;++i )
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="UP";
                    return true;
                }
            }
            else if(players[1].IY == IY){
                if(players[1].IX < IX){ //player is on the left of the enemy
                    for(int i = IX-1;i>players[1].IX;--i)
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="LEFT";
                    return true;
                }
                else{ //player is on the right of the enemy
                    for(int i = IX+1; i< players[1].IX;++i )
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="RIGHT";
                    return true;
                }
            }
        }
        else //Distance from P1 is higher than from P2
        {
            if(players[1].IX == IX){
                if(players[1].IY < IY){ //player lower than enemy
                    for(int i = IY-1;i>players[1].IY;--i)
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="DOWN";
                    return true;
                }
                else{ //player higher than enemy
                    for(int i = IY+1; i< players[1].IY;++i )
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="UP";
                    return true;
                }
            }
            else if(players[1].IY == IY){
                if(players[1].IX < IX){ //player is on the left of the enemy
                    for(int i = IX-1;i>players[1].IX;--i)
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="LEFT";
                    return true;
                }
                else{ //player is on the right of the enemy
                    for(int i = IX+1; i< players[1].IX;++i )
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="RIGHT";
                    return true;
                }
            }
            if(players[0].IX == IX){
                if(players[0].IY < IY){ //player lower than enemy
                    for(int i = IY-1;i>players[0].IY;--i)
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="DOWN";
                    return true;
                }
                else{ //player higher than enemy
                    for(int i = IY+1; i< players[0].IY;++i )
                        if(map[i][IX] instanceof EnemyTank || !map[i][IX].CanBeDestroyed)
                            return false;
                    Direction="UP";
                    return true;
                }
            }
            else if(players[0].IY == IY){
                if(players[0].IX < IX){ //player is on the left of the enemy
                    for(int i = IX-1;i>players[0].IX;--i)
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="LEFT";
                    return true;
                }
                else{ //player is on the right of the enemy
                    for(int i = IX+1; i< players[0].IX;++i )
                        if(map[IY][i] instanceof EnemyTank || !map[IY][i].CanBeDestroyed)
                            return false;
                    Direction="RIGHT";
                    return true;
                }
            }
        }
        return true;
    }
    //    private void Shoot(){
//        //TODO: shoot a bullet i
//
//    }
    private boolean IsMovePossible(Point p){
        //TODO: If any shots to objectives is not available(player tanks, eagle) and tank can't move (is stuck between terrain tiles, but not between other AI tanks) it shoots to terrain.
        if(map[p.y][p.x].CanMoveThrough)
            return true;
        return false;
    }
    private Point Move(){
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
        for (Tile[] row: map) {
            for(Tile t: row){
                estimations[t.IY][t.IX] = Math.sqrt(Math.pow(t.IY-eagle.IY,2) + Math.pow(t.IX-eagle.IX,2)); //Euclidean distance as approx. heurestic
                distance[t.IY][t.IX] = Double.POSITIVE_INFINITY;
            }
        }
        distance[IX][IY]=0.0;
        path = new Stack<>();
        PriorityQueue<Point> open = new PriorityQueue<Point>((p1, p2) -> {
            return (int)(distance[p1.y][p1.x] + estimations[p1.y][p1.x] - distance[p2.y][p2.x] + estimations[p2.y][p2.x]);
        });
        PriorityQueue<Point> close =  new PriorityQueue<Point>((p1, p2) -> {
            return (int)(distance[p1.y][p1.x] + estimations[p1.y][p1.x] - distance[p2.y][p2.x] + estimations[p2.y][p2.x]);
        });

        open.add(new Point(IX,IY));

        while(!open.isEmpty()){
            Point p = open.poll();
            close.add(p);
            if(eagle.IX ==p.x && eagle.IY==p.y)
                break;
            //add neighbors (left right up down) if they're not in close
            Point leftNeighbor = new Point(p.x-1,p.y); //LEFT
            Point rightNeighbor = new Point(p.x+1,p.y); //RIGHT
            Point upNeighbor = new Point(p.x,p.y-1); //UP
            Point downNeighbor = new Point(p.x,p.y+1); //DOWN

            if(p.x-1>=0 && !close.contains(leftNeighbor)){
                if(!open.contains(leftNeighbor)){
                    distance[p.y][p.x-1]=Double.POSITIVE_INFINITY;
                    open.add(leftNeighbor);
                }
                if(distance[p.y][p.x-1] > distance[p.y][p.x] + EdgeWeight(p,leftNeighbor)){
                    distance[p.y][p.x-1] = distance[p.y][p.x] + EdgeWeight(p,leftNeighbor);
                    //update priorities
                    open.remove(leftNeighbor);
                    open.add(leftNeighbor);

                    previous[leftNeighbor.y][leftNeighbor.x]=p;
                }
            }
            if(p.x+1>=0 && !close.contains(rightNeighbor)){
                if(!open.contains(rightNeighbor)){
                    distance[p.y][p.x+1]=Double.POSITIVE_INFINITY;
                    open.add(rightNeighbor);
                }
                if(distance[p.y][p.x+1] > distance[p.y][p.x] + EdgeWeight(p,rightNeighbor)){
                    distance[p.y][p.x+1] = distance[p.y][p.x] + EdgeWeight(p,rightNeighbor);
                    //update priorities
                    open.remove(rightNeighbor);
                    open.add(rightNeighbor);

                    previous[rightNeighbor.y][rightNeighbor.x]=p;
                }
            }
            if(p.y+1>=0 && !close.contains(downNeighbor)){
                if(!open.contains(downNeighbor)){
                    distance[p.y+1][p.x]=Double.POSITIVE_INFINITY;
                    open.add(downNeighbor);
                }
                if(distance[p.y+1][p.x] > distance[p.y][p.x] + EdgeWeight(p,downNeighbor)){
                    distance[p.y+1][p.x] = distance[p.y][p.x] + EdgeWeight(p,downNeighbor);
                    //update priorities
                    open.remove(downNeighbor);
                    open.add(downNeighbor);

                    previous[downNeighbor.y][downNeighbor.x]=p;
                }
            }
            if(p.y-1>=0 && !close.contains(upNeighbor)){
                if(!open.contains(upNeighbor)){
                    distance[p.y-1][p.x]=Double.POSITIVE_INFINITY;
                    open.add(upNeighbor);
                }
                if(distance[p.y-1][p.x] > distance[p.y][p.x] + EdgeWeight(p,upNeighbor)){
                    distance[p.y-1][p.x] = distance[p.y][p.x] + EdgeWeight(p,upNeighbor);
                    //update priorities
                    open.remove(upNeighbor);
                    open.add(upNeighbor);

                    previous[upNeighbor.y][upNeighbor.x]=p;
                }
            }
        }//while
        Point pathNode = previous[eagle.IY][eagle.IX];
        while(pathNode.x!=IX && pathNode.y!=IY){
            path.add((Point)pathNode.clone());
            pathNode=previous[pathNode.y][pathNode.x];
        }
        //On top of the stack is the next move;
        return path.pop();
    }
    private Double EdgeWeight(Point from, Point to)
    {//From [i,j] to [k,l]
        if(map[to.y][to.x].CanMoveThrough)
            return 1.0;
        else if(map[to.y][to.x].CanBeDestroyed)
            return map[to.y][to.x].stamina * 1.0;
        else
            return Double.POSITIVE_INFINITY;
    }
}
