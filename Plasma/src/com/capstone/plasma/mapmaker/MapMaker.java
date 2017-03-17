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
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.mob.Robot;
import com.capstone.plasma.mob.Teleporter;
import com.capstone.plasma.mob.Turret;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Floor;
import com.capstone.plasma.tiles.Tile;
import com.capstone.plasma.tiles.Wall;
import com.capstone.plasma.tiles.breakable;
//import com.capstone.plasma.tiles.breakable;
//import com.sun.java.util.jar.pack.Package.Class.Field;
import com.capstone.plasma.tiles.GlowTile;
import com.capstone.plasma.GameScreen;

import java.util.Scanner;

public class MapMaker {
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=0;
	public static ArrayList<Tile> tiles = new ArrayList<Tile> ();
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
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
	public static int spawnX;
	public static int spawnY;
	public static String name = "map1";
	
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
        	for(Mob m:mobs){
        		m.paint();
        	}
        	GraphicsHandler.drawRect(spawnX+GameScreen.xCam,spawnY+GameScreen.yCam,Tile.size,Tile.size, 0, Color.RED);
        	MapInventory.paint();
        	selectedTile = MapInput.active;
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
    	    		System.out.println("what would you like this world to be called?");
    	    		Scanner s = new Scanner(System.in);
    	    		String tileCode=s.next();
    	    		name = tileCode;
    	    		//selectedTile=Integer.parseInt(tileCode);
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
    		
    		placeBlock(mouseX, mouseY,selectedTile);
    		return;
    	}
    	
    	if(Mouse.isButtonDown(0) && !released){
    		line = true;
    	}else if(!Mouse.isButtonDown(0) && released &&line){
    		totalDistanceY= mouseY-Mouse.getY();
    		totalDistanceX= mouseX-Mouse.getX();
    		if(Math.abs(totalDistanceX)>=Math.abs(totalDistanceY)){//this is for horizontal
    		if(totalDistanceX>0){
	    		for(int i =1; i<=(totalDistanceX/Tile.size); i++){
	    			placeBlock(mouseX-(Tile.size*i), mouseY,selectedTile);
	    		}
    		}
    		//I (Marc) wrote everything in this else statement, going right didn't work w/o it
    		else if(totalDistanceX<0){
    			for(int i =Math.abs(totalDistanceX/Tile.size); i>=1; i--){
    				int v=i*-1;
	    			placeBlock(mouseX-(Tile.size*v), mouseY,selectedTile);
	    		}
    		}
    	}else{//this is for verticle
    		System.out.println(totalDistanceY);
    		if(totalDistanceY>60){
	    		for(int i =1; i<=(totalDistanceY/Tile.size); i++){
	    			placeBlock(mouseX, mouseY-(Tile.size*i),selectedTile);
	    		}
    		}
    		
    		else if(totalDistanceY<Tile.size){
    			for(int i =Math.abs(totalDistanceY/Tile.size); i>=1; i--){
    				int v=i*-1;
	    			placeBlock(mouseX, mouseY-(Tile.size*v),selectedTile);
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
    		layerX=mouseX;    		
    		layerY=mouseY;
    	}else{
    		for(int i = 0; i<(Math.abs(layerX-mouseX));i+=Tile.size){
    			for(int j = 0; j<(Math.abs(layerY-mouseY)); j+=Tile.size){
    				placeBlock(layerX+i, layerY+j,selectedTile);
    			}
    		}
    	}
    	
    }
    
    public static void placeBlock(int x, int y, int Id){
    	x = x-GameScreen.xCam+(GameScreen.xCam%Tile.size);
    	y = y+GameScreen.yCam-(GameScreen.yCam%Tile.size);
    	//System.out.println(Id);
    	switch(Id){
    	case 0:
    		tiles.add(new Floor(x,height-y));
    		//System.out.println("floor");
    		break;
    	case 1:
    		tiles.add(new GlowTile(x,height-y));
    		break;
    	case 2:
    		tiles.add(new breakable(x,height-y));
    		break;
    	case 3:
    		mobs.add(new Robot(x,height-y));
    		break;
    	case 4:
    		mobs.add(new Turret(x,height-y));
    		break;
    	case 5:
    		spawnX = x;
    		spawnY=height-y;
    		break;
    	case 6:
    		mobs.add(new Teleporter(x,height-y));
    	//case 3;
    		
    		
 //   	default:
    		

    		
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
    		return new GlowTile(x,height-y);
    	case 2:
    		return new breakable(x,height-y);
    	default:
    		return null;
    		
    	}
    }
    /*
	public static void sortMap(){
		ArrayList<Tile> sorted = new ArrayList<Tile>();
		sorted.add(0,tiles.get(0));
		for(int i=1;i<tiles.size();i++){
			Tile ct=tiles.get(i);
			for(int j=0;j<sorted.size();j++){
				if(ct.x >= sorted.get(j).x){
					sorted.add(j,ct);
					break;
					}
				}
			}
			
			tiles = sorted;
			System.out.println(tiles);
		
		}
    */
    
    
    public static void sortMap(){
    	ArrayList<Tile> sorted = new ArrayList<Tile>();
    	sorted.add(0,tiles.get(0));
    	boolean smallest = true;
    	for(int i=1; i<tiles.size();i++){
    		smallest = true;
    		Tile ct=tiles.get(i);
    		for(int j =0; j<sorted.size();j++){
    			if(ct.x>=sorted.get(j).x){
    				smallest =false;
    				sorted.add(j,ct);
    			//	System.out.println("before the break");
    				break;
    			}
    			//continue;
    		}
    		if(smallest){
    			sorted.add(ct);
    		}
    		//System.out.println("outer loop");
    		//sorted.add(ct);
    	}


    	tiles = sorted;
    	
    }
    
    public static void save(){
    	try{
		  FileOutputStream fileOut =new FileOutputStream(name+".ser");
		  ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 // String s = "test";
		  //System.out.println("before sort "+tiles.size());
		  sortMap();
		  //System.out.println("after sort "+tiles.size());
		  Map m = new Map(tiles,mobs,spawnX,spawnY);
		  for(int i =0; i<m.tiles.size();i++){
			  for(int j=0; i<m.tiles.size();i++){
				  if(m.tiles.get(i) == m.tiles.get(j)){
					 // System.out.println("match");
				  }
			  }
		  }
		  out.writeObject(m);
		  System.out.println("saved");
		//a  out.writeObject()
		  out.close();
		  fileOut.close();
    	}catch (Exception e){
			  e.printStackTrace();
		  }

    }
    
    
    public static void load(){
    	try{
    		FileInputStream fis = new FileInputStream(name+".ser");
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		//world = (ArrayList) ois.readObject();
    		GameScreen.map = (Map) ois.readObject();
    		tiles = GameScreen.map.tiles;
    		mobs = GameScreen.map.mobs;
    		spawnX=GameScreen.map.spawnX;
    		spawnY=GameScreen.map.spawnY;
    		System.out.println("loaded spawnx: "+spawnX+" spawnY: "+spawnY);
    		
        	System.out.println("loaded");
        	ois.close();
        	fis.close();
    	}catch (Exception e){
    		
    	}
    }
    
    public static void start(){
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
    
    public static void main(String[] args){
    	start();
    }
}
