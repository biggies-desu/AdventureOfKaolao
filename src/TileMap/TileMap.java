/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TileMap;

import adventureofkaolao.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileMap {
    
    private double x,y; //position
    
    private int xmin,ymin,xmax,ymax; //bounds
    
    private double tween;
    
    private int[][] map;
    private int tileSize,numRow,numCol,width,height; //map
    
    private BufferedImage tileset; //tileset
    private int numTilesAcross;
    private Tile[][] tiles;
    
    private int RowOffset,ColOffset,numRowToDraw,numColToDraw;
    public TileMap(int tileSize){
        this.tileSize = tileSize;
        numRowToDraw = GamePanel.HEIGHT/tileSize+2;
        numColToDraw = GamePanel.WIDTH/tileSize+2;
        tween = 0.07;
    }
    
    public void loadTiles(String s){
        try {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth()/tileSize;
            tiles = new Tile[2][numTilesAcross];
            BufferedImage subimage;
            for(int col = 0;col<numTilesAcross;col++){
                subimage = tileset.getSubimage(col*tileSize, 0, tileSize, tileSize);
                tiles[0][col]=new Tile(subimage,Tile.NORMAL);
                subimage = tileset.getSubimage(col*tileSize, tileSize, tileSize, tileSize);
                tiles[1][col]=new Tile(subimage,Tile.BLOCKED);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadMap(String s){
        try {	
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            numCol = Integer.parseInt(br.readLine());
            numRow = Integer.parseInt(br.readLine());
            map = new int[numRow][numCol];
            width = numCol * tileSize;
            height = numRow * tileSize;
            
            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;
            
            String delims = "\\s+";
            for(int row = 0; row < numRow; row++) {
		String line = br.readLine();
		String[] tokens = line.split(delims);
		for(int col = 0; col < numCol; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                    }
		}	
            }
	catch(Exception e) {
            e.printStackTrace();
	}
    }
    
    public int getTileSize(){
        return tileSize;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    public int getNumRow()
    {
        return numRow;
    }
    public int getNumCol()
    {
        return numCol;
    }
    
    
    public int getType(int row,int col){
        int rc = map[row][col];
        int r = rc/numTilesAcross;
        int c = rc%numTilesAcross;
        return tiles[r][c].getType();
    }
    
    public void setPosition(double x, double y)
    {
        this.x += (x-this.x) * tween;
        this.y += (y-this.y) * tween;
        
        fixBounds();
        
        ColOffset = (int)-this.x / tileSize;
        RowOffset = (int)-this.y / tileSize;
    }
    public void setTween(double d)
    {
        tween = d;
    }
    
    private void fixBounds(){
        if(x<xmin) x=xmin;
        if(y<ymin) y=ymin;
        if(x>xmax) x=xmax;
        if(y>ymax) y=ymax;
    }
    
    public void draw(Graphics2D g) {
		
		for(
			int row = RowOffset; row < RowOffset + numRowToDraw; row++) {
			
			if(row >= numRow) break;
			
			for(int col = ColOffset;col < ColOffset + numColToDraw;col++) {
				if(col >= numCol) break;
				if(map[row][col] == 0) continue;
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize,(int)y + row * tileSize,null
				);
				
			}
			
		}
		
	}
}
