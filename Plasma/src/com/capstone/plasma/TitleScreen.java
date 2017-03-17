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

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.capstone.plasma.mapmaker.MapMaker;
import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.player.Utilities;


public class TitleScreen {
	
	public static int width = 900;
	public static int height = 600;
	public static ArrayList<Star> stars= new ArrayList<Star>();
	public static int[] crosshairChords = new int[]{ 250, 310, 390,450,530};
	public static int selected =0 ;
	public static int drift=0;
	public static boolean goingIn=true;
	
	public static void initDisplay(){
		try {
	        Display.setDisplayMode(new DisplayMode(width,height));
	        Display.setTitle("Plasma Demo Tile");
	        Display.setVSyncEnabled(true);
	        Display.setSwapInterval(1);
	        Display.setResizable(true);
	        Display.create();

	    }catch (LWJGLException e){
	    	e.printStackTrace();
	    }
	    Display.update();
	 }
	
	static class Star {
		  public int x;
		  public int y;
		  public int size;
		  public int speed;
		  
		  public Star(int x, int y,int size,int speed){
			  this.x = x;
			  this.y = y;
			  this.speed=speed;
			  this.size=size;
		  }
	}
	
	 
	  public static void initGL(){
	        glMatrixMode(GL_PROJECTION);
	        glLoadIdentity();
	        
	        glOrtho(0, width, height, 0, 1, -1);
	        glMatrixMode(GL_MODELVIEW);
	        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
	        Keyboard.enableRepeatEvents(true);
	        
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GraphicsHandler.loadTextures();
	        
	        
	        
	        class StarThread extends Thread{
	        	
		         public void run() {
		        	 for(int i=0;i<50;i++){
		        		 try {
		 					Thread.sleep(100);
		 				} catch (InterruptedException e) {
		 					e.printStackTrace();
		 				}
		        		if(Utilities.randInt(0, 100)>40){
		        			stars.add( new Star(5,Utilities.randInt(0, height),2,5)) ;
		        		}else{
		        			stars.add( new Star(5,Utilities.randInt(0, height),5,5)) ;
		        		}
		        	 }
		         }
	        }
		   (new StarThread()).start();
	  }
	  
	  

	    public static void run(){   	
	        while(!Display.isCloseRequested()) {

	        	glClear(GL_COLOR_BUFFER_BIT );
	        	if (Display.wasResized()){
	        		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	        	}
	        	
	        	
	        	try{
		        	for(int i=0;i<stars.size();i++){
		        		Star s= stars.get(i);
		        		GraphicsHandler.drawRect(s.x, s.y, s.size, s.size, 0, Color.WHITE);
		        		if(s.size==2){
		        			s.x+=5;
		        		}else if(s.size==5){
		        			s.x+=2;
		        		}
		        		if(s.x>width){
		        			//stars.remove(stars.indexOf(s));
		        			s.x=-10;
		        			s.y=Utilities.randInt(0, height);
		        		}
		        	}
	        	}catch(Exception e){
	        		//e.printStackTrace();
	        		continue;
	        	}
	        	
	        	GraphicsHandler.drawImage(GraphicsHandler.PlasmaTitleLogo, 0, 0, width, height);
	        	

	        	GraphicsHandler.drawImage(GraphicsHandler.SelectionCrosshairRight, 250+drift, crosshairChords[selected], 50, 50);
	        	GraphicsHandler.drawImage(GraphicsHandler.SelectionCrosshairLeft, 670-drift, crosshairChords[selected], 50, 50);
	        	
	        	//GraphicsHandler.drawImage(GraphicsHandler.SelectionCrosshairRight, 250, 310, 50, 50);
	        	//GraphicsHandler.drawImage(GraphicsHandler.SelectionCrosshairLeft, 670, 310, 50, 50);
	        	
	        	
	        	
	        	
	        	
	        	if(drift==30){
	        		goingIn=false;
	        	}
	        	
	        	if(drift==0){
	        		goingIn=true;
	        	}
	        	
	        	if(goingIn){
	        		drift++;
	        	}else{
	        		drift--;
	        	}
	        	
	        	getInput();  
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
	    
	    
	    public static void getInput(){
	    	while(Keyboard.next()){
				if(Keyboard.getEventKeyState()){
					if(Keyboard.getEventKey()==Keyboard.KEY_S || Keyboard.getEventKey()==Keyboard.KEY_DOWN){
						selected++;
						if(selected >= crosshairChords.length){
							selected=0;
						}
					}
					if(Keyboard.getEventKey()==Keyboard.KEY_W || Keyboard.getEventKey()==Keyboard.KEY_UP){
						selected--;
						if(selected == -1){
							selected=crosshairChords.length-1;
						}
					}
					
					if(Keyboard.getEventKey()==Keyboard.KEY_J ||
						Keyboard.getEventKey()==Keyboard.KEY_K ||
						Keyboard.getEventKey()==Keyboard.KEY_L ||
						Keyboard.getEventKey()==Keyboard.KEY_RETURN||
						Keyboard.getEventKey()==Keyboard.KEY_SPACE){
						menuSelect();
					}
				}
	    	}
	    }
	    
	    public static void menuSelect(){
	    	switch(selected){
	    		case 0:
	    			Display.destroy();
	    			//GameScreen.initDisplay();
	    			new GameScreen().start();
	    			break;
	    		case 1:
	    			Display.destroy();
	    			//GameScreen.initDisplay();
	    			new GameScreen().start();
	    			break;
	    		case 2:
	    			Display.destroy();
	    			(new MapMaker()).start();
	    			break;
	    	}
	    }
	    

	    public static void main(String[] args){
	    	initDisplay();
	    	initGL();
	    	run();
	    }
	
}
