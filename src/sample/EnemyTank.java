package sample;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.awt.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class EnemyTank extends Tank{
    //Enemy tanks (AI) knows where Board.players and eagle are.
    //AI tries to either destroy eagle, or kill Board.players when it's possible
    //TODO: change w,h into board.Width, board.Height -> avoid hardcoding
    int w =25;
    int h =16;
    Tile eagle = new PlainTile(12,14);
    //int Board.MapHeight,Board.MapWidth;
    int shotDelay;
    private Stack<Point> path;
    //Direction is already here, deleted.

    public EnemyTank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight) {
        super(iX, iY, x, y, texturesUp, texturesDown, texturesLeft, texturesRight);
    }

    public boolean MakeMove(){

//        if(CanShootToObjective())
//            shouldShoot=true;
//        else {
//            Move();
//            if(!IsMovePossible(path.pop()))//When we want to move into destructible terrain
//                Main.input.add("SHOOT");
//            else
//                Main.input.add(Direction); //??
//        }
//        return shouldShoot;
        if(IsMoving)
            return false;
        if(CanShootToObjective())
        {
            return true;
        }
        Direction ="RIGHT";
        return false;
        //return CanShootToObjective();
    }
    private boolean CanShootToObjective(){
        //TODO: Check - maybe you also have to decide in which direction shoot
        //If there is no indestructible terrain, friendly tanks between this tank and eagle ( this tank is in row || column of eagle) -> shoot eagle
        if(eagle.IX == IX){
//            for(int i = IY+1;i<eagle.IY;++i)
//                if(/*Board.Map[IX][i] instanceof EnemyTank ||*/ !Board.Map[IX][i].CanBeDestroyed)
//                    return false;
            Direction="DOWN";
            return true;
        }
//        else if(eagle.IY == IY){
//            if(eagle.IX < IX)
//            {
//                for(int i =IX-1;i>eagle.IX;--i)
//                    if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                        return false;
//                Direction="LEFT";
//                return true;
//            }
//            else //eagle.IX > IX
//            {
//                for(int i =IX+1;i<eagle.IX;++i)
//                    if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                        return false;
//                Direction="RIGHT";
//                return true;
//            }
//        }
//        //If closest player is in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
//        //If there is another player (not closest one) in column or row of enemy tank and there are no obstacles (indestructible terrain, friendly tanks) -> shoot this player
//        if(Math.sqrt(Math.pow(IX-Board.players[0].IX,2) + Math.pow(IY-Board.players[0].IY,2))<= Math.sqrt(Math.pow(IX-Board.players[1].IX,2) + Math.pow(IY-Board.players[1].IY,2))){
//            //Distance from P1 is smaller than from P2
//            if(Board.players[0].IX == IX){
//                if(Board.players[0].IY < IY){ //player lower than enemy
//                    for(int i = IY-1;i>Board.players[0].IY;--i)
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="DOWN";
//                    return true;
//                }
//                else{ //player higher than enemy
//                    for(int i = IY+1; i< Board.players[0].IY;++i )
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="UP";
//                    return true;
//                }
//            }
//            else if(Board.players[0].IY == IY){
//                if(Board.players[0].IX < IX){ //player is on the left of the enemy
//                    for(int i = IX-1;i>Board.players[0].IX;--i)
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="LEFT";
//                    return true;
//                }
//                else{ //player is on the right of the enemy
//                    for(int i = IX+1; i< Board.players[0].IX;++i )
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="RIGHT";
//                    return true;
//                }
//            }
//            if(Board.players[1].IX == IX){
//                if(Board.players[1].IY < IY){ //player lower than enemy
//                    for(int i = IY-1;i>Board.players[1].IY;--i)
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="DOWN";
//                    return true;
//                }
//                else{ //player higher than enemy
//                    for(int i = IY+1; i< Board.players[1].IY;++i )
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="UP";
//                    return true;
//                }
//            }
//            else if(Board.players[1].IY == IY){
//                if(Board.players[1].IX < IX){ //player is on the left of the enemy
//                    for(int i = IX-1;i>Board.players[1].IX;--i)
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="LEFT";
//                    return true;
//                }
//                else{ //player is on the right of the enemy
//                    for(int i = IX+1; i< Board.players[1].IX;++i )
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="RIGHT";
//                    return true;
//                }
//            }
//        }
//        else //Distance from P1 is higher than from P2
//        {
//            if(Board.players[1].IX == IX){
//                if(Board.players[1].IY < IY){ //player lower than enemy
//                    for(int i = IY-1;i>Board.players[1].IY;--i)
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="DOWN";
//                    return true;
//                }
//                else{ //player higher than enemy
//                    for(int i = IY+1; i< Board.players[1].IY;++i )
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="UP";
//                    return true;
//                }
//            }
//            else if(Board.players[1].IY == IY){
//                if(Board.players[1].IX < IX){ //player is on the left of the enemy
//                    for(int i = IX-1;i>Board.players[1].IX;--i)
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="LEFT";
//                    return true;
//                }
//                else{ //player is on the right of the enemy
//                    for(int i = IX+1; i< Board.players[1].IX;++i )
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="RIGHT";
//                    return true;
//                }
//            }
//            if(Board.players[0].IX == IX){
//                if(Board.players[0].IY < IY){ //player lower than enemy
//                    for(int i = IY-1;i>Board.players[0].IY;--i)
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="DOWN";
//                    return true;
//                }
//                else{ //player higher than enemy
//                    for(int i = IY+1; i< Board.players[0].IY;++i )
//                        if(Board.Map[IX][i] instanceof EnemyTank || !Board.Map[IX][i].CanBeDestroyed)
//                            return false;
//                    Direction="UP";
//                    return true;
//                }
//            }
//            else if(Board.players[0].IY == IY){
//                if(Board.players[0].IX < IX){ //player is on the left of the enemy
//                    for(int i = IX-1;i>Board.players[0].IX;--i)
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="LEFT";
//                    return true;
//                }
//                else{ //player is on the right of the enemy
//                    for(int i = IX+1; i< Board.players[0].IX;++i )
//                        if(Board.Map[i][IY] instanceof EnemyTank || !Board.Map[i][IY].CanBeDestroyed)
//                            return false;
//                    Direction="RIGHT";
//                    return true;
//                }
//            }
//        }
        return false;
    }
    //    private void Shoot(){
//        //TODO: shoot a bullet i
//
//    }
    private boolean IsMovePossible(Point p){
        //TODO: If any shots to objectives is not available(player tanks, eagle) and tank can't move (is stuck between terrain tiles, but not between other AI tanks) it shoots to terrain.
        if(Board.Map[p.y][p.x].CanMoveThrough)
            return true;
        return false;
    }
    private Point Move(){
        //TODO: Own implementation of A* algorithm to move through the Board.Map
        //What we wave on start:
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
        for (Tile[] row: Board.Map) {
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
        if(Board.Map[to.y][to.x].CanMoveThrough)
            return 1.0;
        else if(Board.Map[to.y][to.x].CanBeDestroyed)
            return Board.Map[to.y][to.x].stamina * 1.0;
        else
            return Double.POSITIVE_INFINITY;
    }
}
