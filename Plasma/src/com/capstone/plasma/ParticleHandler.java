package com.capstone.plasma;

import java.awt.Color;
import java.util.ArrayList;

import com.capstone.plasma.tiles.Tile;

public class ParticleHandler {

	public static ArrayList<Particle> particles = new ArrayList <Particle>();
	
	public static void createParticleStream(int x,int y,int duration,Color color){
		
	
		new Thread( new Runnable() {
		    public void run() {
		    	int dCount=0;
		    	while(true){
		    		try {
		    			Thread.sleep(Tile.randInt(50, 500));
		    		} catch (InterruptedException e) {
		    			e.printStackTrace();
		    		}
		    		particles.add(new Particle(x,y,color));
		    		dCount++;
		    		if(dCount==duration){
		    			return;
		    		}
		    	}
		    }
		}).start();
	}

	
	public static Color getColorAlpha(Color c,int alpha){
		return new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha);
	}
	
	public static void paint(){
		try{
			for(int i=0;i<particles.size();i++){
				Particle p=particles.get(i);
				if(p.alpha>-1){
					GraphicsHandler.drawRect(p.x+GameScreen.xCam, p.y+GameScreen.yCam, 10, 10, 0, getColorAlpha(p.color,p.alpha));
				}
				p.check();
				
			}
			for(int i=0;i<particles.size();i++){
				Particle p=particles.get(i);
				if(p.remove){
					particles.remove(i);
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
