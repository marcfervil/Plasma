package com.capstone.plasma;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.tiles.Floor;
import com.capstone.plasma.tiles.Tile;

public class MapMaker {
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static ArrayList<Tile> tiles = new ArrayList<Tile> ();
	public static boolean mouse = true;
	
	 public static void initDisplay(){
	        try {
	            Display.setDisplayMode(new DisplayMode(900,600));
	            Display.setTitle("Plasma Map Maker");
	            Display.create();
	        }catch (LWJGLException e){
	            e.printStackTrace();
	        }
	        Display.update();
	 }
	 

	
    public static void initGL(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 900, 600, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        Keyboard.enableRepeatEvents(true);
        
        GraphicsHandler.loadTextures();
        Tile.mapGen();
       
  //      UserInput.startKeyManager();
    }
    
    
    public static void run() {
    	
        while(!Display.isCloseRequested()) {
        	Display.update();
        	glClear(GL_COLOR_BUFFER_BIT);

        	for(Tile t:tiles){
    			t.paint();
    		}

        	getMouseEvents();    	
        }
        Display.destroy();
        System.exit(0);
    }   
    
    public static void getMouseEvents(){
//    	System.out.println(Mouse.getX());

    	if(Mouse.isButtonDown(0) && mouse){
    		mouse = false;
    		System.out.println("one tile");
    		tiles.add(new Floor(Mouse.getX(),600-Mouse.getY()));
    		//tiles.add(new Floor(100,30));
    		return;
    	}
    	if(!(Mouse.isButtonDown(0)))mouse = true;
    	
    //	if(Mouse.isButto)
    }
    
    
    
    
    public static void main(String[] args){
    	initDisplay();
    	initGL();
    	run();
    }
}
