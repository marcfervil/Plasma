package com.capstone.plasma;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

import static org.lwjgl.opengl.GL11.*;



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
	        Display.setVSyncEnabled(true);
	        Display.setSwapInterval(1);
	        Display.setResizable(true);
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
        GL11.glDisable(GL11.GL_LIGHTING);
   //     glEnable(GL_DEPTH_TEST);
        //Tile.load();
        
        Tile.mapGen();
        
        Tile.sortTextures();
        
        
        
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
        	glClear(GL_COLOR_BUFFER_BIT );
        	if (Display.wasResized()){
        		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        		width=Display.getWidth();
        		height=Display.getHeight();
        	}
        	//loop
        	for(int i=0;i< Tile.backgroundTiles.size();i++){
        		Tile b = Tile.backgroundTiles.get(i);
        		b.paint();
        		
        	}
        	
        	/*
        	for(int i=0;i< Tile.tiles.size();i++){
        		Tile b = Tile.tiles.get(i);
        		b.paint();
        		
        	}
        	*/
        	
        	int currentTexture=0;
        	for(int i=0;i<Tile.tiles.size();i++){
        		Tile b = Tile.tiles.get(i);
        		if(b.texture!=currentTexture){
        			currentTexture=b.texture;
        			GraphicsHandler.setTexture(b.texture);
        		}
        		b.paintOp();
        	}   
        	
        	
        	Player.paint();
        	ParticleHandler.paint();
        	Mob.paintMobs();
        	Inventory.paint();
    
        	UserInput.get();  
        	
        	Display.update();
        	Display.sync(60);
	
        }
        try{
        	Display.destroy();
        }catch(Exception e){
        	System.out.println("error thing");
        }
        System.exit(0);
    }   
    

    public static void main(String[] args){
    	initDisplay();
    	initGL();
    	run();
    }
}