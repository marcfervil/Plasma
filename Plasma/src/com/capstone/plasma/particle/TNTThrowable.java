package com.capstone.plasma.particle;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Floor;
import com.capstone.plasma.tiles.Tile;

public class TNTThrowable extends Projectile{



//	int endTime = 10.0;
	int ay = -2;
	int ax = 1;
	int angle = 45;
	int speed=60;
	int vx;
	int vy;
	
	public TNTThrowable(int x, int y, int damage) {
		super(x, y-Tile.size,damage);
		onTick=6;
		
		angle = 45;
		speed=15;
		
		if(!right){
			vx = (int) -(speed*Math.cos(angle*(Math.PI/180.0)));
			ax=-1;
		}else{
			vx = (int) (speed*Math.cos(angle*(Math.PI/180.0)));
			ax=1;
		}
		
		vy = (int) (speed*Math.sin(angle*(Math.PI/180.0)));
	}
	public void paint(){
		GraphicsHandler.drawImage(GraphicsHandler.TNT, x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size);
		//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10, 0, Color.CYAN);
	}
	
	public void tick(){

		if(!( Utilities.touchBounds(x, y, vx, speed, Tile.size) )){
			
			y -= vy;
			x += vx;
			
			vy += ay;
			vx += ax;
			
		}else{
			explode();
		}
		
		
		
		
	}
	
	
	public void explode(){
		for(int i=0;i<100;i++){
			Tile h=Utilities.touchBoundsTile(x+Utilities.randInt(-100, 100), y+Utilities.randInt(-100, 100), vx, speed, Tile.size);
			if(h!=null)h.damage(1000);
			
		}
		remove();
	}
	
}
