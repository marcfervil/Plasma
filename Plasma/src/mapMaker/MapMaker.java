package mapMaker;

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
	//ArrayList<ArrayList<Integer>> PastActions = new ArrayList<ArrayList<Integer>>();
	public static int selectedTile=0;
	public static final int width = 900;
	public static final int height = 600;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static boolean line = false;
	public static int crosshairX = Mouse.getX()-(Tile.size/2);
	public static int crosshairY = Mouse.getY()+(Tile.size/2);
	
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
        	
        	crosshairX = Mouse.getX()-(Tile.size/2);
        	crosshairY = Mouse.getY()+(Tile.size/2);
    		
        	
    		//this locks it to grid
    		crosshairX=round2(crosshairX,Tile.size);
    		crosshairY=round2(crosshairY,Tile.size);
    		
    		
        	 GraphicsHandler.drawEmptyRect(crosshairX, height-crosshairY, Tile.size,  Tile.size, 0, Color.RED);

        	getMouseEvents();   
        	//MapInput m = new MapInput();
        	//m.startKeyManager();
        	
        	MapInput.startKeyManager();
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
    
    public static int round2(int val,int round){
    	return ((val+round)/round)*round;
    }
    
    public static int round(int val, int round){
    
    	    return (val+ round-1) / round * round;
    	
    }
    
    //will pass on the mouse chords and what mouse was clicked to actions.
    public static void getMouseEvents(){
    	
    	
    	//delete thing
    	if(Mouse.isButtonDown(1)){

    		for(int i =0; i<tiles.size(); i++){
    			System.out.println("tile x: "+round(tiles.get(i).x, Tile.size)+" mouse x "+round(Mouse.getX(),Tile.size));
    			//if(tiles.get(i).x+Tile.size ==round(mouseX, Tile.size) &&tiles.get(i).y+Tile.size ==height-round(mouseY, Tile.size)){
    			if(tiles.get(i).x == crosshairX &&tiles.get(i).y == height-crosshairY){
    				tiles.remove(i);
    				System.out.println("it removed");
    			}
    		}
    	}
    	
    	int totalDistanceX = 0;
    	int totalDistanceY = 0;
    	if(Mouse.isButtonDown(0) && released){
    		released = false;
    		mouseX = Mouse.getX()-(Tile.size/2);
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
        
    
    
    public static void main(String[] args){
    	initDisplay();
    	initGL();
    	getInput();/*
    	MapInput m = new MapInput();
    	m.startKeyManager();
    	
    	m.startKeyManager();
    	m.get();   
    	*/
    	run(); 	
    }
}
