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
import com.capstone.plasma.particle.Dropable;
import com.capstone.plasma.particle.ParticleHandler;

public class Tile implements Serializable{
	
	public static int size=30;
	public static int chunckSize = 10;
	public static int yChuncks;
	public static int xChuncks;
	public static ArrayList<Tile> backgroundTiles= new ArrayList<Tile>();
	public static ArrayList<Tile> tiles= new ArrayList<Tile>();
	public static ArrayList<ArrayList> collideTiles = new ArrayList<ArrayList>();
	public static ArrayList<Tile> paintTiles = new ArrayList<Tile>();
	public static Tile[] tileIds = {new Floor(0,0),new Wall(0,0),new longtile(0,0),};
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
			tiles.remove(tiles.indexOf(this));
			deathAnimation();
		}
	}
	
	public void deathAnimation(){
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+GameScreen.xCam,y+GameScreen.yCam,size,size);
	}
	
	public void paint(){
		if(this.x+GameScreen.xCam<900 && this.x+GameScreen.xCam>-60){
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
	

	/*	//looping but this is prolly acceptable
	public static void paintMap(){
		for(Tile t:tiles){
			t.paint();
		}
	}
	*/
	public static int randInt(int min, int max) {
		return (new Random()).nextInt((max - min) + 1) + min;
	}

	public static void sortMap(){
		ArrayList<Tile> sorted = new ArrayList<Tile>();
		sorted.add(0,tiles.get(0));
		//System.out.println("FIRST "+tiles.get(0).x);
		for(int i=1;i<tiles.size();i++){
			Tile ct=tiles.get(i);
			for(int j=0;j<sorted.size();j++){
				if(ct.x >= sorted.get(j).x){
					//System.out.println(ct.x);
					sorted.add(j,ct);
					break;
				}
			}
		
		}
		
		tiles = sorted;
	}
	
	public static void CollisionArray(){
		int maxY = 0;
		int minY = 0;
		int maxX = tiles.get(tiles.size()).x;
		for(int i =0; i<tiles.size(); i++){
			if(tiles.get(i).y >maxY){
				maxY = tiles.get(i).y;
			}
			if(tiles.get(i).y<minY){
				minY = tiles.get(i).y;
			}
		} 
		
		yChuncks = ((maxY-minY)/size)/chunckSize;
		xChuncks = (maxX/size)/chunckSize;
		int j =0;
		for(int i =0; i<tiles.size(); i++){
			if(tiles.get(i).x<((maxX/xChuncks)*(i+1))){
				
			}
		}
		
	}
	
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
	
	
	public static void findSpot(Tile t1, Tile t2){
		
	}
	
    public static void load(){
    	try{
        FileInputStream fis = new FileInputStream("map1.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
       //world = (ArrayList) ois.readObject();
        tiles = (ArrayList<Tile>) ois.readObject();
        ois.close();
        fis.close();
    	}catch (Exception e){
    		
    	}
   // 	printAllX();
    	sortMap();
    	System.out.println("");
    	System.out.println("done");
    //	printAllX();

    }
    public static void printAllTexture(){
    	for(int i =0; i<tiles.size();i++){
    		System.out.print(tiles.get(i).texture);
    	}
    	System.out.println();
    }
	
	public static void mapGen(){
		
		for(int i=0;i<500;i++){
			for(int j=0;j<20;j++){
				if(j<=13){
					backgroundTiles.add(new Wall(i*Tile.size,j*Tile.size));
					if(randInt(0,100)>95 && j>5){
						
						if(randInt(0,100)>80){
							ParticleHandler.createParticleStream(i*Tile.size, j*Tile.size,Color.yellow,100,200,true);
						}
						if(randInt(0,100)>95){
							//ParticleHandler.createParticle(i*Tile.size, (j*Tile.size)-Tile.size, color);
							ParticleHandler.particles.add(new Dropable(i*Tile.size, (j*Tile.size)-(Tile.size+20),new PlasmaPistol()));
						}
						
						tiles.add(new longtile(i*Tile.size,j*Tile.size));
			//			tiles.add(new breakable(i*Tile.size,j*Tile.size));
					}
				}else{
					tiles.add(new Floor(i*Tile.size,j*Tile.size));
				}
			}
		}
		
		
		
    	//printAllX();
    	//sortMap();
    	System.out.println("");
    	//System.out.println("done");
    	//printAllX();
    	
	
	}
	
}
