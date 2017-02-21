package com.capstone.plasma.player;

import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.tiles.Tile;

public class Utilities {
	
	
	public static Tile touchBoundsTile(int x, int y,int xn,int yn, int size){
		Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,size,size);
		//looping
		for(int i=0;i<Tile.tiles.size();i++){
			Tile s = Tile.tiles.get(i);
			if((s.collide) && r.intersects(s.getBounds())){
				return s;
			}
		}
		return null;
	}
	
	public static boolean touchMobs(int x, int y,int xn,int yn,int size){
		try{
			Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,size,size);
			//looping
			for(int i=0;i<Mob.mobs.size();i++){
				Tile s = Tile.tiles.get(i);
				if(r.intersects(s.getBounds())  && (s.collide)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean touchBounds(int x, int y,int xn,int yn,int size){
		try{
			Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,size,size);
			//looping
			for(int i=0;i<Tile.tiles.size();i++){
				Tile s = Tile.tiles.get(i);
				if(r.intersects(s.getBounds())  && (s.collide)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean touchBoundsMobs(int x, int y,int xn,int yn,int size,Mob you){
		try{
			Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,size,size);
			//looping
	
			for(int i=0;i<Mob.mobs.size();i++){
				Mob m = Mob.mobs.get(i);
				if(r.intersects(m.getBounds()) &&m!=you){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

}