package com.capstone.plasma.tiles;
import java.awt.Color;

import com.capstone.plasma.*;
import com.capstone.plasma.particle.ParticleHandler;

public class Floor extends Tile{


	public Floor(int x,int y){
		super(GraphicsHandler.floor,x,y);
		breakable=true;
	}
	
	public void deathAnimation(){
		//ParticleHandler.createParticleStream(x, y, Color.YELLOW, 30, 40, true,20);
		ParticleHandler.createExplosion(x,y,15,6,15,Color.LIGHT_GRAY);
		
	}
	
	
}
