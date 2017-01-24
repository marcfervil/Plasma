package com.capstone.plasma.particle;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.tiles.Tile;
import com.capstone.plasma.tiles.breakable;

public class PlasmaShot extends Projectile{

	public int maxRange = 500;
	public int initX = 0;
	public int speed = 1;
	public int damage = 100;
	
	
	public PlasmaShot(int x, int y, int damage) {
		super(x, y,damage);
		onTick=3;
		initX=x;
	}
	
	public void paint(){
		//GraphicsHandler.drawImage(item.texture, x+GameScreen.xCam, y+GameScreen.yCam+up, Tile.size, Tile.size);
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10, 0, Color.CYAN);
	}
	
	
	
	public void tick(){
		if(Math.abs(initX-x)<maxRange){
			x+=speed;
			speed++;
		}else{
			remove=true;
		}
		
		for(int i =0; i<Tile.tiles.size(); i++){
			Tile t=Tile.tiles.get(i);
			if(t.breakable && new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(t.getBounds())){
				//System.out.println("hit");
				Tile.tiles.get(i).damage(100);
				remove=true;
			}
			
		}
		
	}

}
