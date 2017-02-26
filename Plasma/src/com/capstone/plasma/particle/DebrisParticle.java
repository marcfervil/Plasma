package com.capstone.plasma.particle;

import java.awt.Color;

import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Floor;

class DebrisParticle extends Particle{

	public int dx,dy,distance,distanceTraveled,angle;
	
	public DebrisParticle(int x, int y,int angle,int dx,int dy,int distance,Color color){
		super(x,y,color);
		this.dx=dx;
		this.dy=dy;
		this.distance=distance;
		this.angle=angle;
		backgroundTick=true;
	}

	public void tick(){
		
		if(Utilities.touchBoundsTile(x, y, 0, 0, 10) instanceof Floor){
	//		angle=180;
		}
		x += dx * Math.sin(angle);
		y += dy * Math.cos(angle);
		
		 if(distanceTraveled==distance){
			dx--;
			dy--;
			distanceTraveled=0;
		}
		distanceTraveled++;
		
		if(dx==0 && dy==0){
			remove();
		}
		
	}
	
}