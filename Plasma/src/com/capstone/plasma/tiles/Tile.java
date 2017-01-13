package com.capstone.plasma.tiles;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import com.capstone.plasma.*;

public class Tile {
	public static int size=30;
	public static ArrayList<Tile> tiles= new ArrayList<Tile>();
	
	public int x;
	public int y;
	public int texture; 
	public boolean collide=true;
	
	public Tile(int texture,int x,int y){
		this.x=x;
		this.y=y;
		this.texture=texture;
	}
	

	
	public Rectangle getBounds(){
		return new Rectangle(x+GameScreen.xCam,y+GameScreen.yCam,size,size);
	}
	
	public void paint(){
		if(this.x+GameScreen.xCam<900 && this.x+GameScreen.xCam>-60){
			GraphicsHandler.drawImage(texture,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
		}
	}
	

	public static void paintMap(){
		for(Tile t:tiles){
			t.paint();
		}
	}
	
	public static int randInt(int min, int max) {
		return (new Random()).nextInt((max - min) + 1) + min;
	}
	
	public static void mapGen(){
		
		for(int i=0;i<500;i++){
			for(int j=0;j<20;j++){
				if(j<=13){
					tiles.add(new Wall(i*Tile.size,j*Tile.size));
					if(randInt(0,100)>95 && j>5){
						
						if(randInt(0,100)>80){
							ParticleHandler.createParticleStream(i*Tile.size, j*Tile.size,Color.yellow,100,200,true);
						}
							
						tiles.add(new longtile(i*Tile.size,j*Tile.size));
					}
				}else{
					tiles.add(new Floor(i*Tile.size,j*Tile.size));
				}
			}
		}
	
	}
	
}
