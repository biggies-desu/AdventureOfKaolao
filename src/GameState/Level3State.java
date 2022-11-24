/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameState;


import Entity.*;
import Entity.Enemies.*;
import java.awt.*;
import TileMap.*;
import adventureofkaolao.GamePanel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;



/**
 *
 * @author ASUS
 */
public class Level3State extends GameState{
    
    private TileMap tileMap;
    private Background bg;
    private Player player;
    private int temptimer = 0;
    private UI ui;
    private deadUI deadui;
    private PassUI passui;
    private boolean pass = false;
 
    
    private ArrayList<Enemy> enemies;
    
    
    public Level3State(GameStateManager gsm)
    {
        this.gsm = gsm;
        init();
    }
    @Override
    public void init(){
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Resource/mapsprite1.png");
        tileMap.loadMap("/Resource/level3.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);
        
        bg = new Background("/Resource/bg_3.png", 0.3);
        
        player = new Player(tileMap);
        player.setPosition(50,200);
        
        setenemiespos();
        
        ui = new UI(player);
        deadui = new deadUI(player);
        passui = new PassUI(player);
        
        
    }
    @Override
    public void update() {
       player.update();
       tileMap.setPosition(GamePanel.WIDTH/2-player.getX(), GamePanel.HEIGHT/2-player.getY());
       for(int i=0;i<enemies.size();i++)
       {
           enemies.get(i).update();
       }
       
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tileMap.draw(g);
        player.draw(g);
        ui.draw(g);
        for(int i=0;i<enemies.size();i++)
        {
            enemies.get(i).draw(g);
            if(enemies.get(i).isDead())
            {
                enemies.remove(i);
            }
        }
        player.checkAttack(enemies);
        System.out.println(player.getX()+" "+player.getY());
        
        
        temptimer++;
        if(temptimer==100)
        {
            temptimer = 0;
            player.ammo++; //regen ammo everytime temptimer count to 0;
        }
        
        if(player.checkded() == true)
        {
            deadui.draw(g);
        }
        
        if((player.getX()>=650 && player.getY()<=110))
       {
            System.out.println("pass");
            pass = true;
            passui.draw(g);
       }
        
        
        
        
    }

    @Override
    public void keyPressed(int key) {
        if(player.getHP()!=0)
        {
            if(key == KeyEvent.VK_LEFT)
            {
            player.setLeft(true);
            }
            if(key == KeyEvent.VK_RIGHT)
            {
            player.setRight(true);
            }
            if(key == KeyEvent.VK_UP)
            {
            player.setUp(true);
            }
            if(key == KeyEvent.VK_DOWN)
            {
            player.setDown(true);
            }
            if(key == KeyEvent.VK_UP)
            {
            player.setJumping(true);
            }
            if(key == KeyEvent.VK_E)
            {
            player.setShooting();
            System.out.println(player.getAmmo());
            }
            if(key == KeyEvent.VK_R)
            {
            player.setslashing();
            }
            if(key == KeyEvent.VK_ESCAPE)
            {
            this.gsm.setState(0);
            }
        }
        else if(player.getHP()==0)
        {
            if(key == KeyEvent.VK_ESCAPE)
            {
            player.setPosition(50,200);
            player.setHP(10);
            player.setalive();
            setenemiespos();
            }
        }
        else if(pass = true)
        {
            if(key == KeyEvent.VK_ESCAPE)
            {
            this.gsm.setState(0);
            }
        }
        
    }

    @Override
    public void keyReleased(int key) {
        if(key == KeyEvent.VK_LEFT)
        {
            player.setLeft(false);
        }
        if(key == KeyEvent.VK_RIGHT)
        {
            player.setRight(false);
        }
        if(key == KeyEvent.VK_UP)
        {
            player.setUp(false);
        }
        if(key == KeyEvent.VK_DOWN)
        {
            player.setDown(false);
        }
        if(key == KeyEvent.VK_W)
        {
            player.setJumping(false);
        }
        

    }

    private void setenemiespos() {
        
        enemies = new ArrayList<Enemy>();
        Enemy1 e1 = new Enemy1(tileMap);
        e1.setPosition(300, 100);
        enemies.add(e1);
        
        Enemy1 e2 = new Enemy1(tileMap);
        e2.setPosition(400, 100);
        enemies.add(e2);
        
        Enemy1 e3 = new Enemy1(tileMap);
        e3.setPosition(500, 100);
        enemies.add(e3);
        
        Enemy1 e4 = new Enemy1(tileMap);
        e4.setPosition(600, 100);
        enemies.add(e4);
        
        Enemy2 e5 = new Enemy2(tileMap);
        e5.setPosition(370, 150);
        enemies.add(e5);
        
        Enemy2 e6 = new Enemy2(tileMap);
        e6.setPosition(350, 200);
        enemies.add(e6);
        
        Enemy2 e7 = new Enemy2(tileMap);
        e7.setPosition(350, 200);
        enemies.add(e7);
        Enemy2 e8 = new Enemy2(tileMap);
        e8.setPosition(450, 200);
        enemies.add(e8);
        
        
    }
    
}
