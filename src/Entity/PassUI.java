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
public class PassUI {
    private Player player;
    private Font font,font2;
    
    public PassUI(Player p)
    {
        player = p;
        try{
            font = new Font("Comic Sans MS",Font.PLAIN,30);
            font2 = new Font("Comic Sans MS",Font.PLAIN,12);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g){
        g.setFont(font);
        g.setColor(Color.GREEN);
        g.drawString("YOU ARE PASS", 40, 130);
        g.setFont(font2);
        g.drawString("Press Esc to go menu", 80, 150);   
    }
     
}