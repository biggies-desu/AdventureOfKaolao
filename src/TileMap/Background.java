/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TileMap;

import adventureofkaolao.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 *
 * @author ASUS
 */
public class Background {
    
    private BufferedImage image;
    
    private double x,y,dx,dy;
    private double moveScale;
    
    public Background(String s,double ms){
        try{
            image = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale = ms;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setPosition(double x, double y){
        this.x = (x*moveScale)%GamePanel.WIDTH;
        this.y = (y*moveScale)%GamePanel.HEIGHT;
    }
    
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    
    public void update(){
        x = x+dx;
        y = y+dy;
    }
    
    public void draw(Graphics2D g){
        
        //scroll left right
        g.drawImage(image,(int)x,(int)y,null);
        if(x<0){
            g.drawImage(image, (int)x+GamePanel.WIDTH, (int)y+GamePanel.HEIGHT, null);
        }
        if(x>0){
            g.drawImage(image, (int)x-GamePanel.WIDTH, (int)y-GamePanel.HEIGHT, null);
        }
    }
    
}
