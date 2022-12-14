/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adventureofkaolao;

/**
 *
 * @author ASUS
 */

import GameState.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable,KeyListener{
    //dimension
    
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    
    //Game Thread
    
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000/FPS;
    
    // image
    private BufferedImage image;
    private Graphics2D g;
    
    //gamestate
    private GameStateManager gsm;
    
    public GamePanel()
    {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        
    }
    
    @Override
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
    
    private void init(){
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        
        gsm = new GameStateManager();
    }

    //key listenner
    @Override
    public void run() {
        
        init();
        //timer
        long start;
        long elapsed;
        long wait;
        
        //game loop
        while(running)
        {
            start = System.nanoTime();
            
            update(); 
            draw();
            drawToScreen();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            
            if(wait<0)
            {
                wait = 10;
            }
            
            try{
                Thread.sleep(wait);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    private void update(){
        gsm.update();
    }
    
    private void draw(){
        gsm.draw(g);
    }
    
    private void drawToScreen(){
            Graphics g2 = getGraphics();
            g2.drawImage(image, 0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
            g2.dispose();
            
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }
    
    
}
