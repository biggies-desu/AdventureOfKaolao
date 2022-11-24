/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import TileMap.Tile;
import TileMap.TileMap;
import adventureofkaolao.GamePanel;
import java.awt.*;

/**
 *
 * @author ASUS
 */
public abstract class MapObj {
    //tile
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap,ymap;
    
    protected double x,y,dx,dy; //pos
    
    protected int width,height; //dimension
    
    protected int cwidth,cheight; //collision box
    
    protected int currRow,currCol; //collision
    protected double xdest,ydest,xtemp,ytemp;
    protected boolean topLeft,topRight,bottomLeft,bottomRight;
    
    protected Animation animation;//animation
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;
    
    protected boolean left,right,up,down,jumping,falling;//movement
    
    //moving attibute
    protected double movingSpeed,maxSpeed,stopSpeed,FallSpeed,maxFallSpeed,jumpStart,stopJumpSpeed;
    
    //constructor
    public MapObj(TileMap tm){
        tileMap = tm;
        tileSize = tm.getTileSize();
    }
    //check collusion like fireball and player
    public boolean intersects(MapObj o){
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }
    public Rectangle getRectangle(){
        return new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
    }
    public void CheckTileMapCollision(){
        currCol = (int)x/tileSize;
        currRow = (int)y/tileSize;
        xdest = x+dx;
        ydest = y+dy;
        xtemp = x;
        ytemp = y;
        
        calculateCorner(x,ydest);
        if(dy<0){//going upward and hit a block tiles -> stop
            if(topLeft || topRight){
                dy=0;
                ytemp = currRow*tileSize+cheight/2;
            }
            else//otherwise keep going upward
            {
                ytemp += dy;
            }
        }
        if(dy>0){//going downward (failing and hit a tile -> stop failing
            if(bottomLeft || bottomRight){
                dy=0;
                falling = false;
                ytemp = (currRow + 1)*tileSize-cheight/2;
            }
            else//otherwise keep going downward
            {
                ytemp += dy;
            }
        }
        
        calculateCorner(xdest, y);
        if(dx<0){//going left -> hit block -> stop moving
            if(topLeft || bottomLeft){
                dx=0;
                xtemp = currCol * tileSize + cwidth /2;
            }
            else//otherwise keep going left
            {
                xtemp =xtemp+dx;
            }
        }
        if(dx>0){//going right -> hit block -> stop moving
            if(topRight || bottomRight){
                dx=0;
                xtemp = (currCol+1) * tileSize - cwidth /2;
            }
            else//otherwise keep going right
            {
                xtemp =xtemp+dx;
            }
        }
        
        if(!falling){ //falling from tile
            calculateCorner(x, ydest+1); //check one pixel below feet
            if(!bottomLeft && !bottomRight){
                falling = true;
            }
        }  
    }
    
    public int getX(){
        return (int)x;
    }
    public int getY(){
        return (int)y;
    }
    public int getWidth(){
        return width;
    }
    public int getWeight(){
        return height;
    }
    public int getCWidth(){
        return cwidth;
    }
    public int getCWeight(){
        return cheight;
    }
    
    public void setPosition(double x,double y){
        this.x = x;
        this.y = y;
    }
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    public void setMapPosition(){
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }
    
    public void calculateCorner(double x,double y)
    {
        int leftTile = (int)(x-cwidth/2) / tileSize;
        int rightTile = (int)(x+cwidth/2 - 1) / tileSize;
        int topTile = (int)(y-cheight/2) / tileSize;
        int bottomTile = (int)(y+cheight/2 - 1) / tileSize;
        if (topTile >= 0 && bottomTile < this.tileMap.getNumRow() && leftTile >= 0 && rightTile < this.tileMap.getNumCol())
        {
        
        int top_left = tileMap.getType(topTile, leftTile);
        int top_right = tileMap.getType(topTile, rightTile);
        int bottom_left = tileMap.getType(bottomTile, leftTile);
        int bottom_right = tileMap.getType(bottomTile, rightTile);
        
        topLeft = top_left == Tile.BLOCKED;
        topRight = top_right == Tile.BLOCKED;
        bottomLeft = bottom_left == Tile.BLOCKED;
        bottomRight = bottom_right == Tile.BLOCKED;
        }
        else{
            this.topLeft = this.topRight = this.bottomLeft = this.bottomRight = false;
        }
    }
    
    public void setLeft(boolean b){
        left = b;
    }
    public void setRight(boolean b){
        right = b;
    }
    public void setUp(boolean b){
        up = b;
    }
    public void setDown(boolean b){
        down = b;
    }
    public void setJumping(boolean b){
        jumping = b;
    }
    
    public boolean NotonScreen(){ //tell that this object is on screen or not
        return x+xmap+width<0 || x+xmap-width>GamePanel.WIDTH || y+ymap+height<0 || y+ymap-height>GamePanel.HEIGHT;
    }
    
    public void draw(Graphics2D g){
        if(facingRight){
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2),null);
        }
        else{
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2+width), (int)(y+ymap-height/2),-width,height,null);
        }
    }
    
}
