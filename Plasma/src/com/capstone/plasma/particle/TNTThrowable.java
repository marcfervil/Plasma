package com.capstone.plasma.particle;


import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;

import com.capstone.plasma.tiles.Tile;

public class TNTThrowable extends Throwable{

	public TNTThrowable(int x, int y, int damage) {
		super(x,y,damage);
	}

//	int endTime = 10.0;
	
	public void paint(){
	//	GraphicsHandler.drawText(vy+"", x+GameScreen.xCam, y+GameScreen.yCam, 25);
		GraphicsHandler.drawImage(GraphicsHandler.TNT, x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size);
		//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10, 0, Color.CYAN);
	}
	
	public void onStoppedMoving(){
		explode();
	}

}
