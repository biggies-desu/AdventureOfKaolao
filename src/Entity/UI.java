/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author ASUS
 */
public class UI {
    private Player player;
    private Font font;
    
    public UI(Player p)
    {
        player = p;
        try{
            font = new Font("Comic Sans MS",Font.PLAIN,18);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g){
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("HP : "+player.getHP()+"/"+player.getMaxHP(), 0, 20);
        g.setColor(Color.YELLOW);
        g.drawString("AMMO : "+player.getAmmo()+"/"+player.getMaxAmmo(), 0, 50);
        
    }
}
