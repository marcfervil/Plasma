package com.capstone.plasma.mapmaker;

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

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.UserInput;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.tiles.Floor;
import com.capstone.plasma.tiles.Tile;
import com.capstone.plasma.tiles.Wall;
import com.capstone.plasma.tiles.breakable;
//import com.sun.java.util.jar.pack.Package.Class.Field;
import com.capstone.plasma.tiles.GlowTile;
import com.capstone.plasma.GameScreen;

import java.util.Scanner;

public class MapMaker {
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static ArrayList<Tile> tiles = new ArrayList<Tile> ();
	public static boolean released = true;
	public static int PlaceMode = 2; //1 is the default
	//ArrayList<ArrayList<Integer>> PastActions = new ArrayList<ArrayList<Integer>>();
	public static int selectedTile=0;
	public static final int width = 900;
	public static final int height = 600;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static boolean line = false;
	public static int crosshairX = Mouse.getX()-(Tile.size/2);
	public static int crosshairY = Mouse.getY()+(Tile.size/2);
	public static String color = "red";
	public static int layerX=0;
	public static int layerY=0;
	
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
        //GameScreen.map.mapGen(); idk why this started to error after implementing the map object
       
       
  //      UserInput.startKeyManager();
    }
    
    
    public static void run() {
    	
        while(!Display.isCloseRequested()) {
        	Display.update();
        	glClear(GL_COLOR_BUFFER_BIT);

        	for(Tile t:tiles){
    			t.paint();
    			
    		}
        	//System.out.println(round(,Tile.size));
        	
        	crosshairX = Mouse.getX()-(Tile.size/2);
        	crosshairY = Mouse.getY()+(Tile.size/2);
    		
        	
    		//this locks it to grid
    		crosshairX=round(crosshairX,Tile.size);
    		crosshairY=round(crosshairY,Tile.size);
    		
    		crosshairX = crosshairX + GameScreen.xCam%Tile.size;
    		crosshairY = crosshairY - GameScreen.yCam%Tile.size;
    		if(color =="red"){
    			GraphicsHandler.drawEmptyRect(crosshairX, height-crosshairY, Tile.size,  Tile.size, 0, Color.RED);
    		}else{
    			GraphicsHandler.drawEmptyRect(crosshairX, height-crosshairY, Tile.size, Tile.size, 0, Color.CYAN);
    		}

        	getMouseEvents();   
        	
        	MapInput.get();  
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
     
    public static int round(int val, int round){
    
    	    return (val+ round-1) / round * round;
    }
    
    //will pass on the mouse chords and what mouse was clicked to actions.
    public static void getMouseEvents(){
    	
    	//delete thing
    	if(Mouse.isButtonDown(1)){

    		for(int i =0; i<tiles.size(); i++){
    			if(tiles.get(i).x == crosshairX-GameScreen.xCam &&tiles.get(i).y == height-(crosshairY+GameScreen.yCam)){
    			//if(tiles.get(i).x == mouseX-GameScreen.xCam+(GameScreen.xCam%Tile.size) &&tiles.get(i).y == mouseY+GameScreen.yCam-(GameScreen.yCam%Tile.size)){
    				tiles.remove(i);
    				System.out.println("it removed");
    			}
    		}
    	}
    	
    	int totalDistanceX = 0;
    	int totalDistanceY = 0;
    	if(Mouse.isButtonDown(0) && released){
    		released = false;
    		mouseX = Mouse.getX()-(Tile.size/2); //centers the mouse on the red cross
    		mouseY = Mouse.getY()+(Tile.size/2);
    		
    		//this locks it to grid
    		mouseX=round(mouseX,Tile.size);
    		mouseY=round(mouseY,Tile.size);
    		
    		tiles.add(getTileFromId(mouseX, mouseY,selectedTile));
    		return;
    	}
    	
    	if(Mouse.isButtonDown(0) && !released){
    		line = true;
    	}else if(!Mouse.isButtonDown(0) && released &&line){
    		totalDistanceY= mouseY-Mouse.getY();
    		totalDistanceX= mouseX-Mouse.getX();
    		if(Math.abs(totalDistanceX)>=Math.abs(totalDistanceY)){//this is for horizontal
    		if(totalDistanceX>0){
	    		for(int i =1; i<=(totalDistanceX/30); i++){
	    			tiles.add(getTileFromId(mouseX-(30*i), mouseY,selectedTile));
	    		}
    		}
    		//I (Marc) wrote everything in this else statement, going right didn't work w/o it
    		else if(totalDistanceX<0){
    			for(int i =Math.abs(totalDistanceX/30); i>=1; i--){
    				int v=i*-1;
	    			tiles.add(getTileFromId(mouseX-(30*v), mouseY,selectedTile));
	    		}
    		}
    	}else{//this is for verticle
    		System.out.println(totalDistanceY);
    		if(totalDistanceY>60){
    			System.out.println("dis");
	    		for(int i =1; i<=(totalDistanceY/30); i++){
	    			tiles.add(getTileFromId(mouseX, mouseY-(30*i),selectedTile));
	    		}
    		}
    		
    		else if(totalDistanceY<30){
    			System.out.println("dis2");
    			for(int i =Math.abs(totalDistanceY/30); i>=1; i--){
    				int v=i*-1;
	    			tiles.add(getTileFromId(mouseX, mouseY-(30*v),selectedTile));
	    		}
    		}
    	}
    		line =false;
    	}
    	
    	if(!(Mouse.isButtonDown(0)))released = true;
    	//if(!(Mouse.isButtonDown(0)))released = true;
    }
    	
    public static void layer(boolean first){
    	if(first){
    		System.out.println("first");
    		layerX=mouseX;    		
    		layerY=mouseY;
    	}else{
    		System.out.println("second");
    		for(int i = 0; i<(Math.abs(layerX-mouseX));i+=Tile.size){
    			for(int j = 0; j<(Math.abs(layerY-mouseY)); j+=Tile.size){
    				tiles.add(getTileFromId(layerX+i, layerY+j,selectedTile));
    			}
    		}
    	}
    	
    }
    
    
    public static Tile getTileFromId(int x, int y, int Id){
    	//x = x-GameScreen.xCam;
    	x = x-GameScreen.xCam+(GameScreen.xCam%Tile.size);
    	y = y+GameScreen.yCam-(GameScreen.yCam%Tile.size);
    	switch(Id){
    	case 0:
    		return new Floor(x,height-y);
    	case 1:
    		return new Wall(x,height-y);
    	case 2:
    		return new GlowTile(x,height-y);
    	case 3:
    		return new breakable(x,height-y);
    	default:
    		return null;
    		
    	}
    }
    
    public static void save(){
    	try{
		  FileOutputStream fileOut =new FileOutputStream("map1.ser");
		  ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 // String s = "test";
		  out.writeObject(GameScreen.map);
		//a  out.writeObject()
		  out.close();
		  fileOut.close();
    	}catch (Exception e){
			  
		  }

    }
    
    public static void load(){
    	try{
        FileInputStream fis = new FileInputStream("map1.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
       //world = (ArrayList) ois.readObject();
        GameScreen.map = (Map) ois.readObject();
        ois.close();
        fis.close();
    	}catch (Exception e){
    		
    	}
    }
    
    
    public static void main(String[] args){
    	//save();
    	//load();
    	initDisplay();
    	initGL();
    	getInput();/*
    	MapInput m = new MapInput();
    	m.startKeyManager();
    	
    	m.startKeyManager();
    	m.get();   
    	*/
    	MapInput.startKeyManager();
    	run(); 	
    }
}
