package com.capstone.plasma;

import java.awt.Color;
import java.util.ArrayList;

import com.capstone.plasma.tiles.Tile;

public class ParticleHandler {

	public static ArrayList<Particle> particles = new ArrayList <Particle>();
	
	public static ParticleStream createParticleStream(int x,int y,Color color,int minSpeed,int maxSpeed,boolean running){
		ParticleStream p= new ParticleStream(x,y,color,minSpeed,maxSpeed,running);
		p.start();
		return p;
	}
	
	public void createParticle(int x,int y,Color color){
		particles.add(new Particle(x,y,color));
	}
	
	public static class ParticleStream extends Thread{
		public int x,y,duration,minSpeed,maxSpeed;
		public Color color;
		boolean running=true;
		boolean rendering=true;
		public ParticleStream(int x,int y,Color color,int minSpeed,int maxSpeed,boolean running){
			this.x=x;
			this.y=y;
			this.color=color;
			this.minSpeed=minSpeed;
			this.maxSpeed=maxSpeed;
			this.rendering=running;
		}
		public void run() {
		    	while(running){
		    		try {
		    			Thread.sleep(Tile.randInt(minSpeed, maxSpeed));
		    		} catch (InterruptedException e) {
		    			e.printStackTrace();
		    		}
		    		if(rendering)particles.add(new Particle(x,y,color));
		
		    	}
		    }
		public void endStream(){
			running=false;
		}
		public void toggleStream(){
			if(rendering){
				rendering=false;
			}else{
				rendering=true;
			}
		}
	}

	
	public static Color getColorAlpha(Color c,int alpha){
		return new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha);
	}
	
	public static void paint(){
		try{
			for(int i=0;i<particles.size();i++){
				Particle p=particles.get(i);
				
				if((!(p==null)) ){
					if(p.alpha>-1 && p.x+GameScreen.xCam<900 && p.x+GameScreen.xCam>-60){
						GraphicsHandler.drawRect(p.x+GameScreen.xCam, p.y+GameScreen.yCam, 10, 10, 0, getColorAlpha(p.color,p.alpha));
					}
					p.check();
				}else{
					//continue;
				}
				
			}
			for(int i=0;i<particles.size();i++){
				Particle p=particles.get(i);
				if(!(p==null)){
					if(p.remove){
						particles.remove(i);
					}
				}
			}
		}catch(Exception e){
		//	System.out.println("particle error");
			//return;
			e.printStackTrace();
		}
	}
	
	
	static class Particle{
		int x;
		int y;
		int duration;
		int alpha=255;
		Color color;
		boolean goingRight=true;
		boolean remove=false;
		int change;
		int cCount;
		public Particle(int x, int y,Color color){
			this.x=x+Tile.randInt(-30, 30);
			this.y=y;
			
//			int darkTint=Tile.randInt(0, 1);
			this.color=color;
		//	this.color=new Color(this.color.getRed()+1,this.color.getGreen()+1,this.color.getBlue()+1,color.getAlpha());
			
			change=30;
		}
		public void check(){
			y--;
			
			if(goingRight){
				x++;
			}else{
				x--;
			}
			
			cCount++;
			if(cCount==change){
				if(goingRight){
					goingRight=false;
				}else{
					goingRight=true;
				}
				cCount=0;
			}
			
			alpha--;
			if(alpha==0){
				remove=true;
				return;
			}
		}
	}
	
	static class EarthParticle extends Particle{

		public EarthParticle(int x, int y,Color color){
			super(x,y,color);
		}
		public void check(){
			
			//y=x^2
			//y = a(x – h)^2 + k 
			y=(int) (-1*Math.pow((x-x+10),2)+y+10);
	
			
		//	alpha--;
			if(alpha==0){
				remove=true;
				return;
			}
		}
	}
	
	
	
}
