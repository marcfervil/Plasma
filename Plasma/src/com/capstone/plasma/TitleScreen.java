package com.capstone.plasma;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.mapmaker.Map;
import com.capstone.plasma.mapmaker.MapInput;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Tile;


public class TitleScreen {
	
	public static int width = 900;
	public static int height = 600;
	public static ArrayList<Star> stars= new ArrayList<Star>();
	public static int[] crosshairChords = new int[]{ 250, 310, 390,450,530};
	public static int selected =0 ;
	public static int drift=0;
	public static boolean goingIn=true;
	public static int playerX,playerY,playerRot,playerAngle,vx,vy;
	public static boolean playerVisible=false;
	
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
	
	public static void init(){
		AudioManager.startMusic();
		stars.clear();
		Thread t1 = new Thread(new Runnable() {
	         public void run() {
	        	 while(true){
	        		 try {
						//Thread.sleep(Utilities.randInt(10000, 15000));
	        			 Thread.sleep(Utilities.randInt(1000, 1500));
	        		 }catch (InterruptedException e) {
						e.printStackTrace();
	        		 }
	        		 
	        		 if(!playerVisible){
	        		///	 System.out.println("NEW DUDDE");
	        			 playerX=0;
	        			 playerY=Utilities.randInt(0, height);
	        			 vx=Utilities.randInt(0, 1);
	        			 vy=Utilities.randInt(-1, 1);
	        			 if(vy==0)vy=2;
	        			 playerVisible=true;
	        		 }
	        		 
	        	 }
	         }
		});
		t1.start();
	}
	
	  static class StarThread extends Thread{
	         public void run() {
	        	 for(int i=0;i<50;i++){
	        		 try {
	 					Thread.sleep(100);
	 				} catch (InterruptedException e) {
	 					e.printStackTrace();
	 				}
	        		if(Utilities.randInt(0, 100)>40){
	        			stars.add(new Star(5,Utilities.randInt(0, height),2,5));
	        		}else{
	        			stars.add(new Star(5,Utilities.randInt(0, height),5,5));
	        		}
	        	 }
	         }
     }
	  
	  public static void startStarThread(){ 
		   (new StarThread()).start();
	  }
	  
	  public static void paint(){
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
 
    	}
    	
      	if(playerVisible){
      		playerRot++;
      		GraphicsHandler.playerSheet.paint(playerX, playerY, Tile.size, Tile.size,playerRot);
      		playerX+=vx;
      		playerY+=vy;
      	}
      	if(playerY>height+Tile.size || playerX>width+Tile.size ||playerX<-Tile.size || playerY<-Tile.size){
			 playerVisible=false;
		 }
      	
    	GraphicsHandler.drawImage(GraphicsHandler.PlasmaTitleLogo, 0, 0, width, height);
    	GraphicsHandler.drawImage(GraphicsHandler.SelectionCrosshairRight, 250+drift, crosshairChords[selected], 50, 50);
    	GraphicsHandler.drawImage(GraphicsHandler.SelectionCrosshairLeft, 670-drift, crosshairChords[selected], 50, 50);
    	
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
					if(Keyboard.getEventKey()==Keyboard.KEY_BACK){
						ControllerInput.init();
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
	    			GameScreen.map= Map.load("map1.ser");
	    			GameScreen.gameMode=1;
	    			GameScreen.startGame();
	    			Player.respawn();
	    			break;
	    		case 1:
	    			//I will change this later 
	    			GameScreen.map= new Map();
	    			GameScreen.gameMode=1;
	    			GameScreen.startGame();
	    			Player.respawn();
	    			break;
	    		case 2:
	    			MapInput.startKeyManager();	
	    			GameScreen.gameMode=2;
	    			break;
	    		case 3:
	    			
	    			break;
	    		case 4:
	    			System.exit(0);
	    			break;
	    	}
	    }
	    

	
}
