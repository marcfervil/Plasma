package com.capstone.plasma.tiles;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import com.capstone.plasma.*;
import com.capstone.plasma.inventory.PlasmaPistol;
import com.capstone.plasma.inventory.TNT;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.mob.Robot;
import com.capstone.plasma.particle.Dropable;
import com.capstone.plasma.particle.ParticleHandler;

public class Tile implements Serializable{
	
	public static int size=22;
	public static int chunkSize = 10;
	public static int ychunks;
	public static int xchunks;
	//public static ArrayList<Tile> backgroundTiles= new ArrayList<Tile>();
	//public static ArrayList<Tile> tiles= new ArrayList<Tile>();
	//public static ArrayList<Tile> revtiles = new ArrayList<Tile>();
	//public static ArrayList<ArrayList> chunks = new ArrayList<ArrayList>();
	//public static ArrayList<Tile> paintTiles = new ArrayList<Tile>();
	public static Tile[] tileIds = {new Floor(0,0),new Wall(0,0),new GlowTile(0,0),};
	public static boolean breakableSkins=true;
	
	public int x;
	public int y;
	public int texture; 
	public boolean collide=true;
	public boolean breakable=false;
	public int maxHp = 300;
	public int hp=maxHp;
	
	public Tile(int texture,int x,int y){
		this.x=x;
		this.y=y;
		this.texture=texture;
	}
	
	public void damage(int damage){
		hp-=damage;
		if(hp<1){
			GameScreen.map.tiles.remove(GameScreen.map.tiles.indexOf(this));
			deathAnimation();
		}
	}
	
	public void deathAnimation(){
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+GameScreen.xCam,y+GameScreen.yCam,size,size);
	}
	
	public Rectangle getBoundsMob(){
		return new Rectangle(x,y,size,size);
	}
	
	public void paint(){
		if(this.x+GameScreen.xCam<900 && this.x+GameScreen.xCam>-90){//was -60
			GraphicsHandler.drawImage(texture,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
			
			if(breakableSkins&&breakable){
				if(hp<(maxHp/3)){
					GraphicsHandler.drawImage(GraphicsHandler.crack3,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
				}else if(hp<(maxHp/3)*2){
					GraphicsHandler.drawImage(GraphicsHandler.crack2,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
				}else if(hp<maxHp){
					GraphicsHandler.drawImage(GraphicsHandler.crack1,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
				}
			}
			
		}
	}

	
	
	public void paintOp(){
		if(this.x+GameScreen.xCam<900 && this.x+GameScreen.xCam>-60){
			
			GraphicsHandler.drawImageOp(this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
			
			if(breakableSkins&&breakable){
			//	int ogTexture=texture;
				if(hp<(maxHp/3)){
					GraphicsHandler.drawImage(GraphicsHandler.crack3,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
				}else if(hp<(maxHp/3)*2){
					GraphicsHandler.drawImage(GraphicsHandler.crack2,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
				}else if(hp<maxHp){
					GraphicsHandler.drawImage(GraphicsHandler.crack1,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
				}
			}
			
		}
	}
	
	
	/*
	public static void createChunks(){		
		//Gained 300ish tiles somewhere????		
		//reading 3,541 tiles worst case
		//{0,300,0,300}
		//{300,600,0,300}		
		int worldWidth=0;
		int worldHeight=0;
		for(int i=0;i<tiles.size();i++){
			if(tiles.get(i).x>worldWidth){
				worldWidth=tiles.get(i).x;
			}
			if(tiles.get(i).y>worldHeight){
				worldHeight=tiles.get(i).y;
			}
		}
		int c=0;
		System.out.println(worldWidth+","+worldHeight);
		for(int x=0;x<worldWidth;x+=Tile.size*chunkSize){	
			for(int y=0;y<worldWidth;y+=Tile.size*chunkSize){
				
				Chunk s=new Chunk(x,x+(Tile.size*chunkSize),y,y+(Tile.size*chunkSize));	
				//System.out.println(s);
				for(int i=0;i<tiles.size();i++){
					Tile t=tiles.get(i);
					if(s.inChunk(t.x, t.y)){
						s.addTile(t);
					}
				}
				if(!(s.tiles.size()==0)){
					Chunk.chunks.add(s);
				}
			}
		}
		
	//	System.out.println(Chunk.chunks.size());
	}*/
	/*
	public static void sortTextures(){
		ArrayList<Tile> sorted = new ArrayList<Tile>();
		sorted.add(0,tiles.get(0));
		for(int i=1;i<tiles.size();i++){
			Tile ct=tiles.get(i);
			for(int j=0;j<sorted.size();j++){
				if(ct.texture >= sorted.get(j).texture){
					sorted.add(j,ct);
					break;
				}
			}
		}
		tiles = sorted;
	}
	*/
	
	
}
