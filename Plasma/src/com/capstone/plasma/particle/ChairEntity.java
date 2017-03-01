package com.capstone.plasma.particle;

import java.awt.Color;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler.ParticleStream;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Tile;

public class ChairEntity extends Throwable{

	ParticleStream ps;
	boolean fireworks=false;
	
	public ChairEntity(int x, int y, int damage) {
		super(x,y,damage);
	}

//	int endTime = 10.0;
	
	public void paint(){
		if(fireworks){
			GraphicsHandler.drawText("chair", x+GameScreen.xCam, y+GameScreen.yCam-Tile.size, 25);
		}
		GraphicsHandler.drawImage(GraphicsHandler.chair, x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size);
	}
	
	public void onStoppedMoving(){
		
		
		 Thread t1 = new Thread(new Runnable() {
	         public void run() {
		
				for(int i=0;i<Utilities.randInt(5, 15);i++){
					ParticleHandler.createExplosion(x+Utilities.randInt(5, 7),y,15,6,15,Color.RED);
					ParticleHandler.createExplosion(x,y+Utilities.randInt(5, 9),40,6,15,Color.BLUE);
					ParticleHandler.createExplosion(x+Utilities.randInt(5, 10),y+Utilities.randInt(5, 10),60,6,15,Color.GREEN);
					ParticleHandler.createExplosion(x+Utilities.randInt(5, 40),y,15,6,15,Color.RED);
					ParticleHandler.createExplosion(x,y+Utilities.randInt(5, 30),40,6,15,Color.BLUE);
					ParticleHandler.createExplosion(x+Utilities.randInt(5, 10),y+Utilities.randInt(5, 10),60,6,15,Color.GREEN);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fireworks=true;
				//calledStop=true;
	         }
        
   });
   t1.start();
		
	}
	
}
