package com.capstone.plasma.mob;



import java.awt.Color;
import java.io.Serializable;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.PlasmaShot;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class Turret extends Mob{

	public ShotTick st;
	public boolean open=false;
	public int range = 10*Tile.size;
	public boolean first = true;//plz find a better way to do this
	
	public Turret(int x, int y) {
		super(x, y);
		size=Tile.size;
		st= new ShotTick();
		st.start();
	}
	
	public void paint(){
		paintHealthBar();
		if(open){
			GraphicsHandler.drawImage(GraphicsHandler.turret, x+GameScreen.xCam, y+GameScreen.yCam, size,size);
		}else{
			GraphicsHandler.drawImage(GraphicsHandler.turretClosed, x+GameScreen.xCam, y+GameScreen.yCam, size,size);
		}
	}
	
	public void tick(){
		if(first){
			//System.out.println("tick?");
			st= new ShotTick();
			st.start();
			first = false;
			//System.out.println("printed?");
		}
		if(Math.abs(x-Player.x)<=range){
			open=true;
		}else{
			open=false;
		}
		gravity();

	}
	
	public void damage(int dm){
		if(open){
			hp-=dm;
			if(hp<=0){
				death();
			}
			if(y>GameScreen.map.lowest){
				death();
			}
		}
	}

	//this is stupid but I couldn't think of away to get 'this' inside of a thread w/o refferencing the thread
	public Object getThis(){
		return this;
	}
	
	
	class ShotTick extends Thread implements Serializable{
		public void run(){
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(open){
					int shotX=x;
					int shotY=y;
					
					int playerX=Player.x;
					int playerY=Player.y+(Tile.size/2);
					
					final double deltaY = (playerY - shotY);
					final double deltaX = (playerX - shotX);
					final double result = Math.toDegrees(Math.atan2(deltaY, deltaX)); 
					ParticleHandler.particles.add(new PlasmaShot(shotX,shotY,15,(int) result,getThis(),new Color(255, 100, 205)));
				}
			}
		}
	}
	
	public void death(){
		Player.kills++;
		ParticleHandler.createParticleStream(x, y, Color.RED, 10, 10, true,10);
		GameScreen.map.mobs.remove(GameScreen.map.mobs.indexOf(this));
		st.stop();
		t1.stop();
		System.out.println("stopped");
	}
	
}
