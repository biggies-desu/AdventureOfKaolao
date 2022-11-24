/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import TileMap.TileMap;


public class Enemy extends MapObj{
    
    protected int health,maxhealth;
    protected boolean dead;
    protected int damage;
    
    
    protected boolean hurt;
    protected long hurttimer;
    
 
    
    public Enemy(TileMap tm){
        super(tm);
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public int getDamage(){
        return damage;
    }
    
    public void hit(int damage)
    {
        if(dead || hurt) return;
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        hurt = true;
        hurttimer = System.nanoTime();
    }
    public void update(){
        
    }
}
