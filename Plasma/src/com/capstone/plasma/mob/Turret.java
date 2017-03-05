package com.capstone.plasma.mob;



import java.awt.Color;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.PlasmaShot;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class Turret extends Mob{

	ShotTick st;
	
	public Turret(int x, int y) {
		super(x, y);
		size=Tile.size;
		st= new ShotTick();
		st.start();
	}
	
	public void paint(){
		paintHealthBar();
		GraphicsHandler.drawImage(GraphicsHandler.turret, x+GameScreen.xCam, y+GameScreen.yCam, size,size);
	}
	
	public void tick(){
		gravity();

	}

	//this is stupid but I couldn't think of away to get 'this' inside of a thread w/o refferencing the thread
	public Object getThis(){
		return this;
	}
	
	
	class ShotTick extends Thread{
		public void run(){
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				int shotX=x;
				int shotY=y;
				
				int playerX=Player.x;
				int playerY=Player.y+(Tile.size/2);
				
				final double deltaY = (playerY - shotY);
				final double deltaX = (playerX - shotX);
				final double result = Math.toDegrees(Math.atan2(deltaY, deltaX)); 
				ParticleHandler.particles.add(new PlasmaShot(shotX,shotY,15,(int) result,getThis()));
			}
		}
	}
	
	public void death(){
		Player.kills++;
		ParticleHandler.createParticleStream(x, y, Color.RED, 10, 10, true,10);
		Mob.mobs.remove(mobs.indexOf(this));
		t1.stop();
		st.stop();
	}
	
}
