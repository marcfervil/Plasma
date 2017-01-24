package com.capstone.plasma.tiles;
import java.awt.Color;

import com.capstone.plasma.*;
import com.capstone.plasma.particle.ParticleHandler;
public class longtile extends Tile{

	
	public longtile(int x,int y){
		super(GraphicsHandler.longtile,x,y);
		breakable=true;
	}

	public void deathAnimation(){
		ParticleHandler.createParticleStream(x, y, Color.YELLOW, 30, 40, true,20);
	}
	
}
