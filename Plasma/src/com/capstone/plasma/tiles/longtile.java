package com.capstone.plasma.tiles;
import com.capstone.plasma.*;
public class longtile extends Tile{

	
	public longtile(int x,int y){
		super(GraphicsHandler.longtile,x,y);
	}
	
	public void paint(){
		GraphicsHandler.drawImage(GraphicsHandler.longtile,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
	}
	
	
	
}
