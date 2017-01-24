package com.capstone.plasma;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.inventory.PlasmaPistol;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;



public class GameScreen{
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static int width = 900;
	public static int height = 600;
	
	public static void initDisplay(){
		try {
	        Display.setDisplayMode(new DisplayMode(width,height));
	        Display.setTitle("Plasma Demo");
	        Display.create();
	    }catch (LWJGLException e){
	    	e.printStackTrace();
	    }
	    Display.update();
	 }
	 
	
    public static void initGL(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        //comment wat dis do exactaly
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        Keyboard.enableRepeatEvents(true);
        GraphicsHandler.loadTextures();
        Tile.mapGen();
       
        PlayerHandler ph = new PlayerHandler();
        ph.start();
        
        UserInput.startKeyManager();
        
        ParticleHandler.ParticleTick pt = new ParticleHandler.ParticleTick();
        pt.start();
        
        Mob.MobTickManager mm = new Mob.MobTickManager();
        mm.start();
        
    }
   
    
    public static void run(){
        while(!Display.isCloseRequested()) {
        	Display.update();
        	glClear(GL_COLOR_BUFFER_BIT);
        	//looping
        	for(Tile t:Tile.backgroundTiles){
    			t.paint();
    		}
        	for(Tile t:Tile.tiles){
    			t.paint();
    		}
        	Player.paint();
        	ParticleHandler.paint();
        	Mob.paintMobs();
        	Inventory.paint();
        	
        	//GraphicsHandler.drawRect(50, 50, width-100, height-100, 0, Color.RED);
        	
        	UserInput.get();     	
        }
        try{
        	Display.destroy();
        }catch(Exception e){
        	System.out.println("error thing");
        }
        System.exit(0);
    }   
    

    public static void main(String[] args){
 //   	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
 //   	width = (int) screenSize.getWidth();
//		height = (int) screenSize.getHeight();
		
    	initDisplay();
    	initGL();
    	run();
    }
}