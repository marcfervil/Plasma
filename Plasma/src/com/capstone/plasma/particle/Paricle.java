package com.capstone.plasma.particle;

import java.awt.Color;

import com.capstone.plasma.tiles.Tile;

class Particle{
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
		
//		int darkTint=Tile.randInt(0, 1);
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