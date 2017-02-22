package com.capstone.plasma.player;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.capstone.plasma.*;
import com.capstone.plasma.tiles.Chunk;
import com.capstone.plasma.tiles.Tile;


public class Player {

	public static int x=250;
	public static int y=40;

	public static int gravityStrength = 1;
	public static int maxGrav = 100;
	public static int yVelocity = 0;
	public static int jumpHeight = 20; //was 20
	public static boolean jump=false;
	public static int gravCount = 0;
	public static int jumpTick = 0;
	public static int PlayerSpeed = 3;
	public static boolean onGround = false;
	public static int hp = 300;
	
	
	public static void paint(){
		
		int fps=10;
		GraphicsHandler.drawText("B: 0123456789",20, 20,25);
		
		
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.RED);	
		
		
		
		//GraphicsHandler.drawRect(getBounds(0,yVelocity).x, getBounds(0,yVeloc
	}
	
	public static Rectangle getBounds(int xn,int yn){
		return new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
	}
	
	public static void damage(int dm){
		hp-=dm;
		
	}

	
	public static boolean touchBounds(int xn,int yn){
		try{
			Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
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
	
	
	public static Tile touchBoundsTile(int xn,int yn){
		Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
		//looping
		for(int i=0;i<Tile.tiles.size();i++){
			Tile s = Tile.tiles.get(i);
			if((s.collide) && r.intersects(s.getBounds())){
				return s;
			}
		}
		return null;
	}

	public static void findX(int playerX,ArrayList<Tile> s){
		int numTiles = s.size();
		if(numTiles>1){
			if(s.get(numTiles/2).x>playerX){
				findX(playerX, (ArrayList<Tile>)(s.subList(0,(numTiles/2) )));
			}else{
				findX(playerX, (ArrayList<Tile>)(s.subList((numTiles/2),numTiles )));
			}
		}else{
			System.out.println("that worked");
		}
	}




	
	public static void tick(){
		
		if (jump && onGround){
				yVelocity-=jumpHeight;

			jump=false;
		}
		Tile t;
		if(( t = touchBoundsTile(0,yVelocity))!=null){
				
			if(yVelocity>0){
			onGround=true;
			}
			if(yVelocity<0){
			y-=(y-t.y-Tile.size);
			}
			yVelocity = 0;
		}else{

			if(yVelocity<maxGrav){
			yVelocity +=gravityStrength;}
			onGround=false;
		//	}
		}
		//if(yVelocity<maxGrav){
			y+=yVelocity;
		//}
	}

}