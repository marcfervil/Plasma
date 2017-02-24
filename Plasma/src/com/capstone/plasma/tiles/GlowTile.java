package com.capstone.plasma.tiles;
import java.awt.Color;

import com.capstone.plasma.*;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.ParticleHandler.ParticleStream;
public class GlowTile extends Tile{

	ParticleStream ps;
	
	
	public GlowTile(int x,int y){
		super(GraphicsHandler.longtile,x,y);
		breakable=true;
		//20% chance of GlowTile glowing 
		if(randInt(0,100)>80){
			ps=ParticleHandler.createParticleStream(x, y,Color.yellow,100,200,true);
		}
	}

	public void deathAnimation(){
		//ParticleHandler.createParticleStream(x, y, Color.YELLOW, 30, 40, true,20);
		ParticleHandler.createExplosion(x,y,15,5,15,Color.YELLOW);
		if(ps!=null){
			ps.stop();
		}
	}
	
}
