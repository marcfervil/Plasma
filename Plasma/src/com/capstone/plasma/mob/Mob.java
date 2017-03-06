package com.capstone.plasma.mob;

import java.util.ArrayList;


import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Tile;
//import com.sun.prism.paint.Color;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;

public class Mob implements Serializable {

	//public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	
	public int x;
	public int y;
	public int texture;
	public boolean faceRight = true;
	public int viewRange = 300;
	public int speed;
	public int size=Tile.size;
	public float hp=300;
	public Thread t1;
	public float maxHp=300;
	public boolean onGround = false;
	public int yVelocity = 0;
	public int gravityStrength = 1;
	public int maxGrav = 100;
	
	//public Mob(int texture for texture
	public Mob(int x, int y){
		this.x=x;
		this.y=y;
		//this.speed = speed;
		//this.texture=texture;
	}
	public void run(){
		    t1 = new Thread(new Runnable() {
		         public void run() {
		        	 while(true){
		        		 try {
		 					Thread.sleep(10);
		 				} catch (InterruptedException e) {
		 					e.printStackTrace();
		 				}
		              tick();
		        	 }
		         }
		   });
		   t1.start();
	}
	

	public void death(){
		Player.kills++;
		ParticleHandler.createParticleStream(x, y, Color.RED, 10, 10, true,10);
		GameScreen.map.mobs.remove(GameScreen.map.mobs.indexOf(this));
		t1.stop();
	}
	

	public static class MobTickManager extends Thread{
		public void run(){
			int i = 0;
			while(true){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				GameScreen.map.mobs.add(new Robot(Player.x, Player.y));
				System.out.println("loaded");
		        while(GameScreen.map.mobs.size()>i){
		        	i++;
		        	GameScreen.map.mobs.get(i).run();
		        }
				//for(int i=0;i<mobs.size();i++){
				//	Mob mob= mobs.get(i);
			//		mob.tick();
				
				//}
			}
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+GameScreen.xCam,y+GameScreen.yCam,size,size);
	}
	
	public static void paintMobs(){
		for(int i=0;i<GameScreen.map.mobs.size();i++){
			Mob mob= GameScreen.map.mobs.get(i);
			mob.paint();
		}
	}
	
	public void damage(int dm){
		hp-=dm;
		if(hp<=0){
			death();
		}
		if(y>GameScreen.map.lowest){
			death();
		}
	}
	
	public static boolean touchBounds(int xn,int yn){
		try{
			Rectangle r=  new Rectangle(xn,yn,Tile.size,Tile.size);
			//looping
			for(int i=0;i<GameScreen.map.tiles.size();i++){
				Tile s = GameScreen.map.tiles.get(i);
				if(r.intersects(s.getBounds())  && (s.collide)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkSide(int xn, int yn){
		for(int i =0; i<GameScreen.map.tiles.size(); i++){
			if(GameScreen.map.tiles.get(i).x>xn && GameScreen.map.tiles.get(i).x<xn+Tile.size &&GameScreen.map.tiles.get(i).y>yn && 
					GameScreen.map.tiles.get(i).y<(yn+Tile.size)){
				return true;				
			}
		}
		
		return false;
	}
	
	public void paintHealthBar(){
		float percent = (hp/maxHp);
		float fill =  (float) (40.0f*percent);
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam-20, fill, 5, 0, Color.RED);
		GraphicsHandler.drawEmptyRect(x+GameScreen.xCam, y+GameScreen.yCam-20, 40, 5, 0, Color.BLACK);
	}
	
	public void gravity(){
		try{
			Tile t;
			if((t = Utilities.touchBoundsTile(x,y,0, yVelocity,size)) !=null){
					
				if(yVelocity==0){
					onGround=true;
				}
				if(yVelocity<0 ){
					
					y-=(y-t.y-size);
					
				}
				yVelocity = 0;
			}else{
				if(yVelocity<maxGrav){
					yVelocity +=gravityStrength;
				}
				onGround=false;
			}
	//		System.out.println("falling");
		
			if(!(Utilities.touchBoundsMobs(x,y,0, yVelocity,size,this))){
				y+=yVelocity;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/*
	public static boolean checkCollide(int xn, int yn){
		for(int i = 0; i<GameScreen.map.tiles.size(); i++){
			if()
		}
		
		return false;
	}
*/
	public void tick(){
		
	}
	
	public void seek(){
		/*
		if(faceRight && (Player.x-x)<viewRange &&Player.x>x){
			x+=speed;
		}else if(!(faceRight) && Math.abs(Player.x-x)<viewRange){
			x-=speed;
		}
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		*/

	}
	
	public void paint(){
		//GraphicsHandler.drawRect(280, 40, Tile.size, Tile.size, 0, Color.BLUE);
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.BLUE);	

	}
	
	
}