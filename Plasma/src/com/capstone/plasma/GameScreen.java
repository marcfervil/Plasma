package com.capstone.plasma;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.mob.Robot;
import com.capstone.plasma.mapmaker.Map;
import com.capstone.plasma.mapmaker.MapHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Chunk;
import com.capstone.plasma.tiles.Tile;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;



public class GameScreen{
	
	public static int xCam=0;
	public static int backCam=0;
	public static int yCam=105;
	public static int width = 900;
	public static int height = 600;
	public static int orgHeight = height;
	public static int orgWidth = width;
	public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	public static int fps=0;
	public static Map map; 

	
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
        map=Map.load("map1.ser");
        //map = new Map();
        for(int i =0; i<3; i++){
			Mob.mobs.add(new Robot(350+i*(50),40,i));
		}
      //  Tile.load();
        //Tile.createChunks();
        
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        PlayerHandler ph = new PlayerHandler();
        ph.start();
        MapHandler mh = new MapHandler();
        mh.start();
        
        UserInput.startKeyManager();
        
        ParticleHandler.ParticleTick pt = new ParticleHandler.ParticleTick();
        pt.start();
        
  //      Mob.MobTickManager mm = new Mob.MobTickManager();
//        mm.start();
        for(int i =0; i<Mob.mobs.size();i ++){
        	Mob.mobs.get(i).run();
        }
        
 /*
        Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        	 while(true){
	        		 try {
	 					Thread.sleep(500);
	 				} catch (InterruptedException e) {
	 					e.printStackTrace();
	 				}
	        		Robot r=  new Robot(Utilities.randInt(0, 500),40,4);
	        		 Mob.mobs.add(r);
	        		r.run();
	        	 }
	         }
	   });
	   t1.start();*/
    }
   
    /*
    public static void shakeCamera(int duration,int intensity){
    	
    	
    	 Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        	int ogXcam=xCam;
	         	int ogYcam=yCam;
	        	 
	        	 int count = duration;
	        	 while(count > 0){
	        		 try {
	 					Thread.sleep(10);
	 				} catch (InterruptedException e) {
	 					e.printStackTrace();
	 				}
	        	    xCam+=Utilities.randInt(-intensity, intensity);
	        	    yCam+=Utilities.randInt(-intensity, intensity);	
	        		count--;
	        	}
	        	xCam=ogXcam;
	 	    	yCam=ogYcam;
	         }
	   });
	   t1.start();
    }
    */
    
    public static void shakeCamera(int duration,int intensity){
    	
    	
   	 Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        //	int ogXcam=xCam;
	        // 	int ogYcam=yCam;
	        	 
	        	 int count = duration;
	        	 while(count > 0){
	        		 
	        		 int xOffset=Utilities.randInt(-intensity, intensity);
		        	 int yOffset=Utilities.randInt(-intensity, intensity);	
		        	    
		        	 xCam+=xOffset;
		        	 yCam+=yOffset;
	        		 
		       // 	 backCam+=xOffset;
		        	
	        		 try {
	 					Thread.sleep(10);
	 				} catch (InterruptedException e) {
	 					e.printStackTrace();
	 				}
	        		 
	        		 xCam-=xOffset;
		        	 yCam-=yOffset;
	        	    
		     //   	 backCam-=xOffset;
		        	 
	        		count--;
	        	}
	       // 	xCam=ogXcam;
	 	   // 	yCam=ogYcam;
	         }
	   });
	   t1.start();
   }
    
    public static void run(){

    	long lastTime = System.nanoTime();
    	double delta = 0.0;
    	double ns = 1000000000.0 / 60.0;
    	long timer = System.currentTimeMillis();
    	int updates = 0;
    	int frames = 0;
    	
    	
        while(!Display.isCloseRequested()) {
        	
        	//double lastTime = System.currentTimeMillis();
        	//int nbFrames = 0;
    		long now = System.nanoTime();
    		delta += (now - lastTime) / ns;
    		lastTime = now;
    		if (delta >= 1.0) {
    		//	update();
    			updates++;
    			delta--;
    		}
   	 
        	glClear(GL_COLOR_BUFFER_BIT );
        	if (Display.wasResized()){
        		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        		width=Display.getWidth();
        		height=Display.getHeight();
        	}
        	//loop
        	
        	if(UserInput.lastKey == "a"){
        		//Tile.paintBackground();
        		/*
            	for(int i=map.backgroundTiles.size()-1;i>=0;i--){
            		Tile b = map.backgroundTiles.get(i);
            		b.paint();
            		
            	}*/
        		map.paintMap();
        	}else{
        		//Tile.paintbackground2();
        		/*
            	for(int i=0;i< map.backgroundTiles.size();i++){
            		Tile b = map.backgroundTiles.get(i);
            		b.paint();
            		
            	}*/
        		map.paintMap2();
        	}
        	//Tile.paintMap();
        	
        
        	Player.paint();
        	Mob.paintMobs();
        	ParticleHandler.paint();
        	Mob.paintMobs();
        	Inventory.paint();
    
        	
        	GraphicsHandler.drawText("fps:"+fps,20, 20,25);
        	GraphicsHandler.drawText("kills: "+Player.kills,orgWidth-200,20,25);
        	GraphicsHandler.drawText("deaths: "+Player.deaths,orgWidth-200,40,25);
        	
        
    	
    		
        	UserInput.get();  
        
        	Display.update();
        	
        	
        	
        	Display.sync(60);
  
      
        	frames++;
    		if (System.currentTimeMillis() - timer > 1000) {
    			timer += 1000;
    			fps=frames;
    			updates = 0;
    			frames = 0;
    		}
         
    		
        	
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