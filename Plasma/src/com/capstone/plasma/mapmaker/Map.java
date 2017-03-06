package com.capstone.plasma.mapmaker;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.inventory.PlasmaPistol;
import com.capstone.plasma.inventory.TNT;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.particle.Dropable;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Floor;
import com.capstone.plasma.tiles.GlowTile;
import com.capstone.plasma.tiles.Tile;
import com.capstone.plasma.tiles.Wall;

public class Map implements Serializable{
	public ArrayList<Tile> backgroundTiles= new ArrayList<Tile>();
	public ArrayList<Tile> tiles= new ArrayList<Tile>();
	public ArrayList<Tile> revtiles = new ArrayList<Tile>();
	public ArrayList<Mob> mobs = new ArrayList<Mob>();
	public int lowest = 1000;
	public static int spawnX = 250;
	public static int spawnY = 40;
	
	/*
	public Map(ArrayList<Tile> t, ArrayList<Tile> b){
		tiles = t;
		backgroundTiles = b;
		
	}
	*/
	//load
	public Map(String action){
		load(action);
	}
	//random
	public Map(){
		mapGen();
	}
	
	public Map(ArrayList<Tile> t,ArrayList<Mob> m){
		tiles = t;
		mobs = m;
	}
	
	public void sortMap(){
		ArrayList<Tile> sorted = new ArrayList<Tile>();
		sorted.add(0,tiles.get(0));
		for(int i=1;i<tiles.size();i++){
			Tile ct=tiles.get(i);
			for(int j=0;j<sorted.size();j++){
				if(ct.x >= sorted.get(j).x){
					sorted.add(j,ct);
					break;
					}
				}
			}
			
			tiles = sorted;
		}
	
	public static int randInt(int min, int max) {
		return (new Random()).nextInt((max - min) + 1) + min;
	}
		
		public ArrayList<Tile> sortY(ArrayList<Tile> t){
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
		
	    public void printAllTexture(){
	    	for(int i =0; i<tiles.size();i++){
	    		System.out.print(tiles.get(i).texture);
	    	}
	    	System.out.println();
	    }
		
		//looping but this is prolly acceptable
	public void paintMap(){
		try{
			for(Tile t:tiles){
				t.paint();
			}
		}catch(Exception e){
			System.out.println("some error with painting tiles");
		}
	}

	public void paintMap2(){
		for(int i =tiles.size()-1; i>=0; i--){
			tiles.get(i).paint();
		}
	}//i noticed i am in the tile class so i don't need to do Tile.tiles but I am 2 lazy to change

	public void paintBackground(){
		for(Tile t: backgroundTiles){
			t.paint();
		}
	}

	public void paintbackground2(){
		for(int i = backgroundTiles.size()-1;i>0;i--){
			backgroundTiles.get(i).paint();
		}
	}
	
    public static Map load(String level){
    	Map m = null;
    	try{
        FileInputStream fis = new FileInputStream(level);
        ObjectInputStream ois = new ObjectInputStream(fis);
        m = (Map) ois.readObject();
        System.out.println("loaded game from map");
        ois.close();
        fis.close();
    	}catch (Exception e){
    		e.printStackTrace();    		
    	}
    	System.out.println("");
    	System.out.println("Map sorted");
    	return m;

    }

	public void mapGen(){
		
		for(int i=0;i<500;i++){
			for(int j=0;j<35;j++){
				if(j<=13){
					backgroundTiles.add(new Wall(i*Tile.size,j*Tile.size));
					if(randInt(0,100)>95 && j>5){
						if(randInt(0,100)>95){
							ParticleHandler.particles.add(new Dropable(i*Tile.size, (j*Tile.size)-(Tile.size+20),new PlasmaPistol()));
						}else if(randInt(0,100)>95){
							ParticleHandler.particles.add(new Dropable(i*Tile.size, (j*Tile.size)-(Tile.size+20),new TNT()));
						}
						
						tiles.add(new GlowTile(i*Tile.size,j*Tile.size));
					}
				}else{
					tiles.add(new Floor(i*Tile.size,j*Tile.size));
				}
			}
		}
		revtiles.addAll(tiles);
    	sortMap();
	}
	
	public void tick() {
		//System.out.println("maphand?");
		//System.out.println(Player.x +" "+ GameScreen.xCam+" "+Player.x+GameScreen.xCam);
		//System.out.println("test");
		
		if(Player.x+GameScreen.xCam>=400){
			GameScreen.xCam-=Player.x+GameScreen.xCam-400;
			GameScreen.backCam-=2;
		}
		
	}

}
