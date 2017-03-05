package com.capstone.plasma.particle;

import java.awt.Color;

import com.capstone.plasma.GameScreen;
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
		
		/*
		 Thread t1 = new Thread(new Runnable() {
	         public void run(){
	        	 while(true){
	        		 try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		 expand();
	        	 }
	         }
	      });
		 t1.start();
		 */
	}

	public static class DebrisParticleTick extends Thread{ 
		public void run(){
			while(true){
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				for(int i=0;i<ParticleHandler.particles.size();i++){
					Particle p=ParticleHandler.particles.get(i);
					
					if((!(p==null)) && p instanceof DebrisParticle){
						if(p.alpha>-1 && p.x+GameScreen.xCam<900 && p.x+GameScreen.xCam>-60 || p.backgroundTick){
							if(p.tickCount==p.onTick){
								p.tick();
								p.tickCount=0;
							}	
							p.tickCount++;
						}
						
					}
					
					
				}
				for(int i=0;i<ParticleHandler.particles.size();i++){
					Particle p=ParticleHandler.particles.get(i);
					if(!(p==null)){
						if(p.remove){
					//		System.out.println("rem");
							ParticleHandler.particles.remove(i);
						}
					}
				}
				
				
			}
		}
	}
	
	public void expand(){
	
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
	
	
	public void tick(){
		expand();
	}
	
}