package com.capstone.plasma;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.inventory.PlasmaPistol;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

import static org.lwjgl.opengl.GL11.*;



public class GameScreen{
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static final int width = 900;
	public static final int height = 600;
	
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
        Inventory.items.add(new PlasmaPistol());
        Inventory.items.add(new PlasmaPistol());
    }
   
    
    public static void run(){
        while(!Display.isCloseRequested()) {
        	Display.update();
        	glClear(GL_COLOR_BUFFER_BIT);
        	for(Tile t:Tile.backgroundTiles){
    			t.paint();
    		}
        	for(Tile t:Tile.tiles){
    			t.paint();
    		}
        	Player.paint();
        	ParticleHandler.paint();
        	Inventory.paint();
        	UserInput.get();     	
        }
        Display.destroy();
        System.exit(0);
    }   
    

    public static void main(String[] args){
    	initDisplay();
    	initGL();
    	run();
    }
}