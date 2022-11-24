package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
    private Background bg;
	
    private int currentChoice = 0;
    private String[] options =
    {
        "Start Level 1","Start Level 2","Start Level 3","Quit game"
    };
	
    private Color titleColor;
    private Font titleFont;
    private Font font;
	
    public MenuState(GameStateManager gsm)
    {	
        this.gsm = gsm;
        try 
        {
            bg = new Background("/Resource/bg.png", 1);
            bg.setVector(-0.1, 0);	
            titleColor = new Color(255, 128, 30);
            titleFont = new Font("Comic Sans MS",Font.PLAIN,18);
            font = new Font("Calibri", Font.PLAIN, 12);	
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void init() 
    {  
        
    }
    @Override
    public void update()
    {
        bg.update();
    }
	
    @Override
    public void draw(Graphics2D g)
    {
        // draw bg
        bg.draw(g);
        // draw title screen
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("> ADVENTURE OF KAOLAO <", 40, 60);
        // draw options
        g.setFont(font);
        for(int i = 0; i < options.length; i++)
        {
            if(i == currentChoice)
            {
               g.setColor(Color.DARK_GRAY);
            }
            else
            {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.drawString(options[i], 120, 140 + i * 15);
        }	
    }
    
    private void select() {
        if(currentChoice == 0)
        {
            // start
            System.out.println("Strat Level 1");
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if(currentChoice == 1)
        {
            System.out.println("Strat Level 2");
            gsm.setState(GameStateManager.LEVEL2STATE);
        }
        if(currentChoice == 2)
        {
            
            System.out.println("Strat Level 3");
            gsm.setState(GameStateManager.LEVEL3STATE);
        }
        if(currentChoice == 3)
        {
            // exit game
            System.exit(0);
        }
    }
	
    @Override
    public void keyPressed(int key)
    {
        if(key == KeyEvent.VK_ENTER)
        {
            select();
        }
        if(key == KeyEvent.VK_UP)
        {
            currentChoice--;
            if(currentChoice == -1) //if option was top but press up, select buttom one
            {
                currentChoice = options.length - 1;
            }
        }
        if(key == KeyEvent.VK_DOWN)
        {
            currentChoice++;
            if(currentChoice == options.length)
            {
                currentChoice = 0;
            }
        }
    }
    @Override
    public void keyReleased(int key) {
           
    }	
}










