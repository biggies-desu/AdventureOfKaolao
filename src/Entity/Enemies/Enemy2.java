/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author ASUS
 */
public class Enemy2 extends Enemy{
    
    private BufferedImage[] sprites;
    
    public Enemy2(TileMap tm){
        super(tm);
        
        movingSpeed = 0.1; maxSpeed = 0.1;
        
        width = 30; height=30;
        cwidth = 15; cheight=15;
        FallSpeed = 0.3; maxFallSpeed=10;
        
        health=maxhealth=35;
        damage = 2;
        
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resource/enemy2.png"));
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
        animation.setDelay(1000);
        
        right = true;
        facingRight = true;
        
        
    }
    
    private void getNextPosition(){
        // movement
	if(left)
        {
            dx -= movingSpeed;
            if(dx < -maxSpeed)
            {
		dx = -maxSpeed;
            }
	}
	else if(right)
        {
            dx += movingSpeed;
            if(dx > maxSpeed)
            {
		dx = maxSpeed;
            }
	}
	if(falling)
        {         
            dy +=FallSpeed;
            if(dy > maxFallSpeed)
            {
               dy = maxFallSpeed;
            }
            
	}
    }
    
    @Override
    public void update(){
        //update enemy position
        getNextPosition();
        CheckTileMapCollision();
        setPosition(xtemp, ytemp);
        
        if(hurt)
        {
            long elapsed = (System.nanoTime()-hurttimer)/1000000;
            if(elapsed>500){
                hurt = false;
            }
        }
        
        if(dx == 0 && right)
        {
            right = false; left = true;
            facingRight = false;
        }
        else if(dx == 0 && left)
        {
            right = true; left = false;
            facingRight = true;
        }
        
        animation.update();
    }
    
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }
}

