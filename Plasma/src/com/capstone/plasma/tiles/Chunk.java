package com.capstone.plasma.tiles;

import java.util.ArrayList;

public class Chunk {

	public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public int minX,maxX,minY,maxY;	
	
	public Chunk(int minX, int maxX,int minY,int maxY){
		this.minX=minX;
		this.maxX=maxX;
		this.minY=minY;
		this.maxY=maxY;
	}
	
	public boolean inChunk(int x,int y){
		return (x>= minX && x <= maxX && y>= minY && y <= maxY);
	}
	
	public void addTile(Tile t){
		tiles.add(t);
	}
	
	public String toString(){
		return "{"+minX+","+maxX+","+minY+","+maxY+"}";
	}
	
}
