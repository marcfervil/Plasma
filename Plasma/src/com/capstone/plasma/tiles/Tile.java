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
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.mob.Robot;
import com.capstone.plasma.particle.Dropable;
import com.capstone.plasma.particle.ParticleHandler;

public class Tile implements Serializable{
	
	public static int size=30;
	public static int chunkSize = 10;
	public static int ychunks;
	public static int xchunks;
	public static ArrayList<Tile> backgroundTiles= new ArrayList<Tile>();
	public static ArrayList<Tile> tiles= new ArrayList<Tile>();
	public static ArrayList<Tile> revtiles = new ArrayList<Tile>();
	public static ArrayList<ArrayList> chunks = new ArrayList<ArrayList>();
	public static ArrayList<Tile> paintTiles = new ArrayList<Tile>();
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
			tiles.remove(tiles.indexOf(this));
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
	

		//looping but this is prolly acceptable
	public static void paintMap(){
		for(Tile t:tiles){
			t.paint();
		}
	}
	
	public static void paintMap2(){
		for(int i =Tile.tiles.size()-1; i>0; i--){
			Tile.tiles.get(i).paint();
		}
	}//i noticed i am in the tile class so i don't need to do Tile.tiles but I am 2 lazy to change
	
	public static void paintBackground(){
		for(Tile t: Tile.backgroundTiles){
			t.paint();
		}
	}
	
	public static void paintbackground2(){
		for(int i = Tile.backgroundTiles.size()-1;i>0;i--){
			Tile.backgroundTiles.get(i).paint();
		}
	}
	
	public static int randInt(int min, int max) {
		return (new Random()).nextInt((max - min) + 1) + min;
	}
	
	
	public static ArrayList<Tile> sortY(ArrayList<Tile> t){
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
		return sorted;
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
		/*
		for(int i =0; i<20; i++){
			Mob.mobs.add(new Robot(350+i*(120),40));
			//Mob.mobs.add(m);
		}
		*/
		
		for(int i=0;i<500;i++){
			for(int j=0;j<20;j++){
				if(j<=13){
					backgroundTiles.add(new Wall(i*Tile.size,j*Tile.size));
					if(randInt(0,100)>95 && j>5){
						
						
						if(randInt(0,100)>95){
							//ParticleHandler.createParticle(i*Tile.size, (j*Tile.size)-Tile.size, color);
							ParticleHandler.particles.add(new Dropable(i*Tile.size, (j*Tile.size)-(Tile.size+20),new PlasmaPistol()));
						}
						
						tiles.add(new GlowTile(i*Tile.size,j*Tile.size));
			//			tiles.add(new breakable(i*Tile.size,j*Tile.size));
					}
				}else{
					tiles.add(new Floor(i*Tile.size,j*Tile.size));//used to be floor
				}
			}
		}
		
		
		
    	//printAllX();
		revtiles.addAll(tiles);
    	sortMap();
    //	System.out.println("");
    	//System.out.println("done");
    	//printAllX();
    	
	
	}
	
}
