/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author ASUS
 */
public class Ammo extends MapObj{
    
    private boolean hit; //if ammo hit something
    private boolean remove; //if hit set remove
    private int temptimer;
    private BufferedImage[] sprites;

    
    public Ammo(TileMap tm,boolean right){
        super(tm);
        facingRight = true;
        movingSpeed = 2;
        if(right){
            dx = movingSpeed;
        }  
        else {
            dx = -movingSpeed;
        }
        width =30; height=30; cheight=15; cwidth=15;
        
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resource/ammoshoot.png"));
            //put in arraylist
            sprites = new BufferedImage[2];
            for(int i = 0;i<2;i++)
            {
                sprites[i] =spritesheet.getSubimage(i*width, 0, width, height);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(10);
    }
    
    
    public void sethitsomething(){
        if(hit) return;
        hit = true;
        dx = 0;
    }
    
    public boolean shouldRemove(){
        return remove;
    }
    
    public void update(){
        CheckTileMapCollision();
        setPosition(xtemp, ytemp);
        
        if(dx==0&&!hit)
        {
            sethitsomething();
        }
        
        animation.update();
        if(hit && animation.hasPlayedOnce())
        {
            remove = true;
        }
    }
    public void draw(Graphics2D g)
    {
        setMapPosition();
        super.draw(g);
    }
}
