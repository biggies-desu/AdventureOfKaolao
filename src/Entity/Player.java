/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import TileMap.*;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends MapObj{

    
    //player stuff
    private int hp,maxhp;
    public int ammo;
    private int maxammo;
    private boolean dead,hurt;
    private long hurttimer,temptimer;
    
    //gun stuff
    private boolean firing;
    private int ammodmg;
    private ArrayList<Ammo> ammos;
    //sword slash
    private boolean slashing;
    private int slashdmg,slashrange;
    //animate
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames={
        2,6,1,6,3,1
    };
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int SHOOTING = 3;
    private static final int SLASHING = 4;
    private static final int FALLING = 5;
    private boolean isded;
    
    
    
    public Player(TileMap tm){
        super(tm);
        //player properties
        width = 30;height = 30;
        cwidth = 20;cheight = 20;
        facingRight = true;
        movingSpeed = 0.3; maxSpeed = 1.6; stopSpeed = 0.4;
        FallSpeed = 0.15; maxFallSpeed = 4.0;
        jumpStart = -5.5; stopJumpSpeed = 0.1;
        hp = maxhp = 10; ammo = maxammo = 1;
        ammos = new ArrayList<Ammo>();
        ammodmg = 20; slashdmg = 5;slashrange=50;
        
        //load sprite
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resource/kaolao2.png"));
            //put in arraylist
            sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0;i<6;i++)
            {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for(int j=0;j<numFrames[i];j++)
                {   
                    
                    bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                        
                }
                sprites.add(bi);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
        
        
    }
    
    public int getHP(){
        return hp;
    }
    public int getMaxHP(){
        return maxhp;
    }
    public int getAmmo(){
        return ammo;
    }
    public int getMaxAmmo(){
        return maxammo;
    }
    
    //keyboard input
    public void setShooting(){
        firing = true;
    }
    public void setslashing(){
        slashing = true;
    }
    public void hit(int damage)
    {
        if(hurt) return;
        hp = hp-1;
        if(hp < 0) hp = 0;
        if(hp == 0) dead = true;
        hurt = true;
        hurttimer = System.nanoTime();
    }
    public boolean checkded()
    {
        return isded;
    }
    public void setHP(int hp)
    {
        this.hp = hp;
    }
    public void setalive()
    {
        isded = false;
    }
    
    
    
    public void draw(Graphics2D g)
    {
        setMapPosition();
        //draw ammo
        for(int i=0;i<ammos.size();i++)
        {
            ammos.get(i).draw(g);
        }
        //drawplayer
        if(hurt){
            long invis = (System.nanoTime() - hurttimer) / 1000000;
            if(invis / 100 % 2 == 0)
            {
                return;
            }
        }
        super.draw(g);

    }
    
    private void getNextPosition() {
		
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
	else
        {
            if(dx > 0)
            {
                dx -= stopSpeed;
                if(dx < 0)
                {
                    dx = 0;
		}
            }
            else if(dx < 0)
            {
		dx += stopSpeed;
		if(dx > 0)
                {
                    dx = 0;
		}
            }
	}
		
	// jumping
	if(jumping && !falling)
        {
            dy = jumpStart;
            falling = true;	
	}
	// falling
	if(falling)
        {         
            if(dy > 0)
                jumping = false;
                dy +=FallSpeed;
                
            if(dy < 0 && !jumping)
                dy += stopJumpSpeed;
            
            if(dy > maxFallSpeed)
                dy = maxFallSpeed;
	}
        
        
    }
    public void update(){
        //update pos
        getNextPosition();
        CheckTileMapCollision();
        setPosition(xtemp, ytemp);
        
        if(y>tileMap.getHeight() || hp==0)
        {
            isded = true;
            hp = 0;
        }
        
        
        //set direction
        if(currentAction != SHOOTING && currentAction != SLASHING)
        {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }
        
        
        //set animation
        if(slashing){
            if(currentAction != SLASHING){
                currentAction = SLASHING;
                animation.setFrames(sprites.get(SLASHING));
                animation.setDelay(30);
                width = 30;
            }
        }
        else if(firing)
        {
            if(currentAction != SHOOTING){
                currentAction = SHOOTING;
                animation.setFrames(sprites.get(SHOOTING));
                animation.setDelay(10);
                width = 30;
            }
        }
        else if(firing)
        {
            if(currentAction != SHOOTING){
                currentAction = SHOOTING;
                animation.setFrames(sprites.get(SHOOTING));
                animation.setDelay(30);
                width = 30;
            }
        }

        else if(dy<0)
        {
            if(currentAction != JUMPING){
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        }
        else if(dy>0)
        {
            if(currentAction != FALLING)
            {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        }
        
        
        else if(left || right)
        {
            if(currentAction != WALKING){
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(50);
            }
        }
        else
        {
            if(currentAction != IDLE){
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(350);
            }
        }
        
         
        
        //fireball related
        if(ammo > maxammo)
            ammo = maxammo;
        
        if(firing)
        {
            
            if(ammo!=0)
            {
                Ammo am = new Ammo(tileMap, facingRight);
                am.setPosition(x, y);
                ammos.add(am);
                ammo = ammo-1;
            }
        }
        

        for(int i=0;i<ammos.size();i++)
        {
            ammos.get(i).update();
            //check collusion
            if(ammos.get(i).shouldRemove())
            {
                ammos.remove(i);
                i--;
            }
        }
        
        //check that get hit?
        if(hurt)
        {
            long invis = (System.nanoTime() - hurttimer) / 1000000;
            if(invis > 1000)
            {
                hurt = false;
            }
        }
        
        

        
        //check that click once or not prevent loop
        if(currentAction==SHOOTING)
        {
            if(animation.hasPlayedOnce())
            {
                firing = false;
            }
        }
        if(currentAction==SLASHING)
        {
            if(animation.hasPlayedOnce())
            {
                slashing = false;
            }
        }
        
        animation.update();

    }

    public void checkAttack(ArrayList<Enemy> enemies) {
        
        //check every eneimes
        for(int k = 0;k<enemies.size();k++)
        {
            Enemy e = enemies.get(k);
            if(slashing)
            {
                if(facingRight)
                {
                    if(e.getX() > x && e.getX() < x + slashrange && e.getY() > y - height && e.getY() < y + height / 2)
                    {
                        e.hit(slashdmg);
                        System.out.println("Hit");
                    }
                }
                else if(e.getX() < x && e.getX() > x - slashrange && e.getY() > y - height / 2 && e.getY() < y + height / 2)
                {
                    e.hit(slashdmg);
                    System.out.println("Hit");
                }
            }
            if(firing)
            {
                for(int j = 0;j<ammos.size();j++)
                {
                    if(ammos.get(j).intersects(e))
                    {
                        e.hit(ammodmg);
                        ammos.get(j).sethitsomething();
                        System.out.println("Hit");
                        break;
                    }
                }
            
            }
            //check player-enemies
            if(intersects(e))
            {
                hit(e.getDamage());
            }
                    
            
        }
    }     
        
}
