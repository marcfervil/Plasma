package com.capstone.plasma.tiles;
import java.awt.Color;

import com.capstone.plasma.*;
import com.capstone.plasma.particle.ParticleHandler;

public class Floor extends Tile{
	//public static int maxHp = 1200;

	public Floor(int x,int y){
		super(GraphicsHandler.floor,x,y);
		breakable=true;
		maxHp = 900;
		hp = 900;
		//increase();
	}
	
	public void deathAnimation(){
		//ParticleHandler.createParticleStream(x, y, Color.YELLOW, 30, 40, true,20);
		ParticleHandler.createExplosion(x,y,15,6,10,Color.LIGHT_GRAY);
		
	}
	/*yeah yeah i know this is terrible code, the other way was causing an error so i gave up
	public void increase(){
		maxHp = 1200;
		hp = 1200;
	}
	//ok idk why this code breaks eveyrthign
	 * 
	*/
	
	
}
