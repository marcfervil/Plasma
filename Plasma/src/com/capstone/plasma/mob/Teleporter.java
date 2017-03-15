package com.capstone.plasma.mob;

import java.awt.Color;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.mapmaker.Map;
import com.capstone.plasma.player.Utilities;

public class Teleporter extends Mob {
	
	public Teleporter(int x, int y) {
		super(x, y);
	}
	
	public void paint(){
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, size, size*2, 0, Color.PINK);
	}
	
	
	
	public void tick(){
		if(Utilities.touchPlayer(x, y, size)){
			System.out.println("teleporter away!");
			GameScreen.map=Map.load("level1.ser");
		}
	}

}
