package com.capstone.plasma.mob;

import java.util.ArrayList;


import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;
//import com.sun.prism.paint.Color;
import java.awt.Color;
import java.awt.Rectangle;

public class Mob {

	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	
	public int x;
	public int y;
	public int texture;
	public boolean faceRight = true;
	public int viewRange = 300;
	public int speed;
	public int size=Tile.size;
	public float hp;
	public Thread t1;
	public float maxHp;
	
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
	


	public static class MobTickManager extends Thread{
		public void run(){
			int i = 0;
			while(true){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Mob.mobs.add(new Robot(Player.x, Player.y));
				System.out.println("loaded");
		        while(Mob.mobs.size()>i){
		        	i++;
		        	Mob.mobs.get(i).run();
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
		for(int i=0;i<mobs.size();i++){
			Mob mob= mobs.get(i);
			mob.paint();
		}
	}
	
	public void damage(int dm){
		hp-=dm;
		System.out.println(hp);
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
			if(GameScreen.map.tiles.get(i).x>xn && GameScreen.map.tiles.get(i).x<xn+Tile.size &&GameScreen.map.tiles.get(i).y>yn && GameScreen.map.tiles.get(i).y<(yn+Tile.size)){
				return true;				
			}
		}
		
		return false;
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