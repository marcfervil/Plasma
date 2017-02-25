package com.capstone.plasma.particle;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.inventory.PlasmaPistol;
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
		super(x, y-(Tile.size),damage);
		onTick=6;
		backgroundTick=true;
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

		
	//	Inventory.activeItems[0]=new PlasmaPistol();
	
		/*
		if(!( Utilities.touchBounds(x, y, vx, speed, Tile.size) )){
			
			y -= vy;
			x += vx;
			
			vy += ay;
			vx += ax;
			
		}else{
			explode();
		}
		*/

		x += vx;
		vx += ax;
		
		if(!( Utilities.touchBounds(x, y, 0, -vy, Tile.size) )){
			vy += ay;
			y -= vy;
		}else{
			if(speed!=0)speed--;
		}
		
		
		
		if(!right){
			vx = (int) -(speed*Math.cos(angle*(Math.PI/180.0)));
			ax=-1;
		}else{
			vx = (int) (speed*Math.cos(angle*(Math.PI/180.0)));
			ax=1;
		}
		
	//	vy = (int) (speed*Math.sin(angle*(Math.PI/180.0)));
	
		if(speed==0){
			explode();
		}
		
	}
	
	
	public void explode(){
		for(int i=0;i<25;i++){
			int xr=Utilities.randInt(-100, 100);
			int yr=Utilities.randInt(-100, 100);
			Tile h=Utilities.touchBoundsTile(x+xr, y+yr, vx, speed, Tile.size);
			if(h!=null)h.damage(1000);
			
			Tile z=Utilities.touchBoundsTile(x+xr, (y+yr)+(Tile.size), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			z=Utilities.touchBoundsTile(x+xr, (y+yr)-(Tile.size), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			z=Utilities.touchBoundsTile((x+xr)-(Tile.size), (y+yr), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			z=Utilities.touchBoundsTile((x+xr)+(Tile.size), (y+yr), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
		}
		remove();
	}
	
}
