package com.capstone.plasma.particle;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class PlasmaShot extends Projectile{

	public int maxRange = 500;
	public int initX = 0;
	public int speed = 1;
	public int damage = 100;
	public int width = 10;
	public int height = 20;
	public int angle = 0;
	public Object creator;
	
	public PlasmaShot(int x, int y, int damage,int angle,Object creator) {
		super(x, y,damage);
		this.angle=angle;
		this.creator=creator;
		onTick=3;
		initX=x;
	}
	
	public void paint(){
		//GraphicsHandler.drawImage(item.texture, x+GameScreen.xCam, y+GameScreen.yCam+up, Tile.size, Tile.size);

		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, 20, 5, angle, Color.CYAN);

	}
	
	
	
	public void tick(){
		//Tile.backgroundTiles
		if(Math.abs(x-initX)<maxRange){
			
			/*
			if(right){
				x+=speed;
			}else{
				x-=speed;
			}*/
			x += (int) (speed*Math.cos(angle*(Math.PI/180.0)));
			y += (int) (speed*Math.sin(angle*(Math.PI/180.0)));
			
			speed++;
			
			
		}else{
			remove=true;
		}
		
		if(new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(Player.getBounds(0,0))){
			if(creator!=null){
				Player.damage(100);
				remove=true;
			}
		}
		
		//looping
		for(int i=0;i<GameScreen.map.tiles.size();i++){
			Tile t=GameScreen.map.tiles.get(i);
			if(new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(t.getBounds())){
				if(t.breakable){
					GameScreen.map.tiles.get(i).damage(damage);
				}
				remove=true;
			}
			
		}
		
		for(int i=0;i<Mob.mobs.size();i++){
			Mob t=Mob.mobs.get(i);
		//	System.out.println(t + "===" + creator);
			if(t!=creator){
				if(new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(t.getBounds())){
		//			System.out.println("HIT MOB");
					t.damage(damage);
					remove=true;
				}
			}
			
		}
		
	}

}
