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
import com.capstone.plasma.tiles.Wall;
//import com.sun.java.util.jar.pack.Package.Class.Field;
import com.capstone.plasma.tiles.longtile;

import java.util.Scanner;

public class MapMaker {
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static ArrayList<Tile> tiles = new ArrayList<Tile> ();
	public static boolean released = true;
	public static int PlaceMode = 2; //1 is the default
	ArrayList<ArrayList<Integer>> PastActions = new ArrayList<ArrayList<Integer>>();
	public static int selectedTile=0;
	public static final int width = 900;
	public static final int height = 600;
	public static int mouseX = 0;
	public static int mouseY = 0;
	
	 public static void initDisplay(){
	        try {
	            Display.setDisplayMode(new DisplayMode(width,height));
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
        glOrtho(0, width, height, 0, 1, -1);
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
    
    
    public static void getInput(){
    	new Thread( new Runnable() {
    	    public void run() {
    	    	while(true){
    	    		System.out.println("What tile would you like to select? ");
    	    		Scanner s = new Scanner(System.in);
    	    		String tileCode=s.next();
    	    		selectedTile=Integer.parseInt(tileCode);
    	    	}
    	    }
    	}).start();
    }
    
    //will pass on the mouse chords and what mouse was clicked to actions.
    public static void getMouseEvents(){

    	if(Mouse.isButtonDown(0) && released){
    		released = false;
    		mouseX = Mouse.getX();
    		mouseY = Mouse.getY();
    		tiles.add(getTileFromId(Mouse.getX(), Mouse.getY(),selectedTile));
    		return;
    	}
    	if(Mouse.isButtonDown(0) && !released){
    		int i = 0;
    		if(mouseX-Mouse.getX()<(Tile.size*-1)){
        		System.out.println("mousex: "+mouseX+" getMouse: "+Mouse.getX());
    			tiles.add(getTileFromId(Mouse.getX(), mouseY, selectedTile));
    			i++;
    			mouseX=Mouse.getX();

    		}
    	}
    		
    	if(!(Mouse.isButtonDown(0)))released = true;
    	
    	if(Mouse.isButtonDown(1)) undo();
    }
    
    public static Tile getTileFromId(int x, int y, int Id){
    	switch(Id){
    	case 0:
    		return new Floor(x,height-y);
    	case 1:
    		return new Wall(x,height-y);
    	case 2:
    		return new longtile(x,height-y);
    	default:
    		return null;
    		
    	}
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
    			System.out.println ("how many blocks?:"); //this is crude but it was quick, do you have a better idea?
    			int blocks = Integer.parseInt(s.nextLine());
    			tileArrangement(mouseX,mouseY,blocks,"right");
    			break;
	
    	}
    } 
    
    public static void tileArrangement(int mouseX, int mouseY, int numBlocks, String direction){
    	ArrayList<Integer[]> set = new ArrayList<Integer[]>();
    	for(int i =0; i<numBlocks; i++){
    		if(direction == "up" ){
    			tiles.add(new Floor(mouseX,(mouseY)+(i*Tile.size)));
    		//	set.add(mouseX+(mouseY)+(i*Tile.size));
    		}
    		if(direction == "down" ){
    			tiles.add(new Floor(mouseX,(mouseY)-(i*Tile.size)));
    			//record(mouseX,(mouseY)-(i*Tile.size));
    		}
    		
    		if(direction == "right" ){
    			tiles.add(new Floor(mouseX+(i*Tile.size),(mouseY)));
    		//	record(mouseX+(i*Tile.size),(mouseY));
    		}
    		if(direction == "left" ){
    			tiles.add(new Floor(mouseX-(i*Tile.size),(mouseY)));
    		//	record(mouseX-(i*Tile.size),(mouseY));
    		}
    		
    		
    	}
    }
    
    
    public static void record(){
    	
    }
    
    //right click to undo
    public static void undo(){
    	
    }
    
    
    
    public static void main(String[] args){
    	initDisplay();
    	initGL();
    	getInput();
    	run();
    	
    }
}
