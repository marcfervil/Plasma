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

import java.util.Scanner;

public class MapMaker {
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static ArrayList<Tile> tiles = new ArrayList<Tile> ();
	public static boolean mouse = true;
	public static int PlaceMode = 2; //1 is the default
	ArrayList<Integer> PastActions = new ArrayList<Integer>();
	
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
    
    //will pass on the mouse coords and what mouse was clicked to actions.
    public static void getMouseEvents(){
    	if(Mouse.isButtonDown(0) && mouse){
    		mouse = false;
    		action(PlaceMode,Mouse.getX(),600-Mouse.getY());
    		return;
    	}
    	if(!(Mouse.isButtonDown(0)))mouse = true;

    }
    
    
    //decides what to do next.
    public static void action(int mode,int mouseX,int mouseY){
    	Scanner s = new Scanner(System.in);
    	switch (mode){
    		case 1: mode = 1;//place a single block
    			//tiles.add(new Floor(Mouse.getX(),600-Mouse.getY()));
    			tileArrangement(mouseX,mouseY,1,"right");
    			break;
    		case 2 : mode = 2;//place a row of blocks horizontally
    			System.out.println ("how many blocks?:");
    			int blocks = Integer.parseInt(s.nextLine());
    			tileArrangement(mouseX,mouseY,blocks,"right");
    			break;
	
    	}
    } 
    

    public static void tileArrangement(int mouseX, int mouseY, int numBlocks, String direction){
    	for(int i =0; i<numBlocks; i++){
    		if(direction == "up" ) tiles.add(new Floor(mouseX,(mouseY)+(i*Tile.size)));
    		if(direction == "down" ) tiles.add(new Floor(mouseX,(mouseY)-(i*Tile.size)));
    		
    		if(direction == "right" ) tiles.add(new Floor(mouseX+(i*Tile.size),(mouseY)));
    		if(direction == "left" ) tiles.add(new Floor(mouseX-(i*Tile.size),(mouseY)));
    		
    		
    	}
    }
    
    
    
    public static void main(String[] args){
    	initDisplay();
    	initGL();
    	run();
    }
}
