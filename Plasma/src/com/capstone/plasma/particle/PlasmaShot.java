package com.capstone.plasma.particle;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.tiles.Tile;

public class PlasmaShot extends Projectile{

	public int maxRange = 500;
	public int initX = 0;
	public int speed = 1;
	public int damage = 100;
	public int width = 10;
	public int height = 20;
	
	
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
		//Tile.backgroundTiles
		if(Math.abs(x-initX)<maxRange){
			if(right){
				x+=speed;
			}else{
				x-=speed;
			}
			speed++;
		}else{
			remove=true;
		}
		//looping
		for(int i=0;i<Tile.tiles.size();i++){
			Tile t=Tile.tiles.get(i);
			if(t.breakable && new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(t.getBounds())){
				Tile.tiles.get(i).damage(damage);
				remove=true;
			}
			
		}
		
		for(int i=0;i<Mob.mobs.size();i++){
			Mob t=Mob.mobs.get(i);
			if(new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(t.getBounds())){
				Tile.tiles.get(i).damage(damage);
				remove=true;
			}
			
		}
		
	}

}
