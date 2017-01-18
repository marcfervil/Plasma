package com.capstone.plasma.player;
import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.*;
import com.capstone.plasma.tiles.Tile;


public class Player {

	public static int x=250;
	public static int y=40;

	public static int gravityStrength = 1;
	public static int yVelocity = 0;
	public static int jumpHeight = 20; //was 20
	public static boolean jump=false;
	public static int gravCount = 0;
	public static int jumpTick = 0;
	
	public static boolean onGround = false;
	
	
	public static void paint(){
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.RED);		
		//GraphicsHandler.drawRect(getBounds(0,yVelocity).x, getBounds(0,yVelocity).y, getBounds(0,yVelocity).width, getBounds(0,(yVelocity)).height, 0, Color.BLUE);
	}
	
	public static Rectangle getBounds(int xn,int yn){
		return new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
	}
	
	
	public static boolean touchBounds(int xn,int yn){
		Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
		for(Tile s:Tile.tiles){
			if(r.intersects(s.getBounds())  && (s.collide)){
				return true;
			}
		}
		return false;
	}

	
	public static Tile touchBoundsTile(int xn,int yn){
		Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
		for(Tile s:Tile.tiles){
			if((s.collide) && r.intersects(s.getBounds())){
				return s;
			}
		}
		return null;
	}


	
	public static void tick(){
		
		if (jump && onGround){
			yVelocity-=jumpHeight;
		//	yVelocity -=jumpTick;
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
			//if(!onGround)gravCount++;
			gravCount++;
		//	if(gravCount+4>3){
			yVelocity +=gravityStrength;
			onGround=false;
			gravCount=0;
		//	}
		}
		y+=yVelocity;
	}

}
