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
import com.capstone.plasma.mob.Turret;
import com.capstone.plasma.mapmaker.Map;
import com.capstone.plasma.mapmaker.MapHandler;
import com.capstone.plasma.mapmaker.MapMaker;
import com.capstone.plasma.particle.ParticleHandler;
//import com.capstone.plasma.particle.Teleporter;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Chunk;

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
	public static int gameMode=0;
	public static  PlayerHandler ph;
	public static MapHandler mh;

    public static ParticleHandler.ParticleTick pt;
	
	public static void initDisplay(){
		try {
	        Display.setDisplayMode(new DisplayMode(width,height));
	        Display.setTitle("Plasma Demo");
	        Display.setVSyncEnabled(true);
	        Display.setSwapInterval(1);
	        Display.setResizable(true);
	       
	        Display.create();
	        

	        TitleScreen.startStarThread();
	        TitleScreen.init();
	        
	        
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
          
        if(ControllerInput.isControllerConnected()){
        	System.out.println("Controller detected!");
        	ControllerInput.init();
        }
    }
   
    public static void startGame(){
    	
        ph = new PlayerHandler();
        ph.start();
        mh = new MapHandler();
        mh.start();
        
        UserInput.startKeyManager();
        
        pt = new ParticleHandler.ParticleTick();
        pt.start();

        for(int i =0; i<GameScreen.map.mobs.size();i ++){
        	GameScreen.map.mobs.get(i).run();
        }
        
       
    }
    
    public static void shakeCamera(int duration,int intensity){
    	///TEMPERARILY DISABLING CAMERA SHAKING
    	
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
	        	 GameScreen.xCam = 300-Player.x;
	     		GameScreen.yCam = 250-Player.y;
	       // 	xCam=ogXcam;
	 	   // 	yCam=ogYcam;
	         }
	   });
	   t1.start();
   }
    
    public static void paint(){
    	if(UserInput.lastKey == "a"){
    		map.paintMap();
    	}else{
    		map.paintMap2();
    	}
    	Player.paint();
    	Mob.paintMobs();
    	ParticleHandler.paint();
    	Mob.paintMobs();
    	Inventory.paint();
    	
    	GraphicsHandler.drawText("fps:"+fps,20, 20,25);
    	GraphicsHandler.drawText("kills: "+Player.kills,orgWidth-200,20,25);
    	GraphicsHandler.drawText("deaths: "+Player.deaths,orgWidth-200,40,25);
    	
    	UserInput.get();  
    	
    }
    
    public static void stopAll(){
    	
    	//ph.stop();
    	//mh.stop();
    	//pt.stop();
    	
    	if(ph!=null)ph.kill();
    	if(pt!=null)pt.kill();
    	
    	//ph = new PlayerHandler();
        mh = new MapHandler();
        pt = new ParticleHandler.ParticleTick();
        UserInput.stopKeyManager();
        if(map!=null)map.mobs.clear();
          
    }
    
    public static void loadGame(Map map){
 	   GameScreen.map= map;
 	   GameScreen.gameMode=1;
 	   GameScreen.startGame();
 	   Player.respawn();
    }
    
    public static void run(){

    	long lastTime = System.nanoTime();
    	double delta = 0.0;
    	double ns = 1000000000.0 / 60.0;
    	long timer = System.currentTimeMillis();
    	int frames = 0;
    	
    	
        while(!Display.isCloseRequested()) {
    		long now = System.nanoTime();
    		delta += (now - lastTime) / ns;
    		lastTime = now;
    		if (delta >= 1.0) {
    			delta--;
    		}
   	 
        	glClear(GL_COLOR_BUFFER_BIT);
        	if (Display.wasResized()){
        		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        		width=Display.getWidth();
        		height=Display.getHeight();
        	}

        	switch(gameMode){
        		case 0:
        			TitleScreen.paint();
        			break;
        		case 1:
        			paint();
        			break;
        		case 2:
        			MapMaker.paint();
        			break;
        	}

        	Display.update();        	
        	Display.sync(60);
  
        	frames++;
    		if (System.currentTimeMillis() - timer > 1000) {
    			timer += 1000;
    			fps=frames;
    			frames = 0;
    		}
    		UserInput.globalKeyPress();
        }
        try{
        	Display.destroy();
        }catch(Exception e){
        	System.out.println("error thing");
        }
        System.exit(0);
    }   

    public static void start(){
    	initDisplay();
    	initGL();
    	run();
    }

 
    
    public static void main(String[] args){
    	start();
    }
}