package com.capstone.plasma.tiles;
import com.capstone.plasma.*;

public class breakable extends Tile{
	
	public int hp = 300;

	
	public breakable(int x,int y){
		super(GraphicsHandler.breakable,x,y);
	}
	
	public void paint(){
		GraphicsHandler.drawImage(GraphicsHandler.breakable,this.x+GameScreen.xCam,this.y+GameScreen.yCam,size,size);
	}
	
	public void damage(int damage){
		hp -=damage;
	}
	
	
	
}
