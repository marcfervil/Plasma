package com.capstone.plasma.mob;

import java.awt.Color;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class robot extends Mob {
	
	public static int speed = 2;
	public static int gravityStrength = 1;
	public static int maxGrav = 100;
	public static int yVelocity = 0;
	public static int jumpHeight = 20; //was 20
	public static boolean jump=false;
	public long startTime = 10;
	public long endTime;
	public boolean seeking = false;
	public int viewRange = 200;
	public int action = 30;
	//public int x;
	//public int y;
	
	public robot(int x1, int y1) {
		super(x1, y1);
		//this.x=x1;
		//this.y=y1;
		// TODO Auto-generated constructor stub
	}
	
	
	public void tick(){
		if(faceRight){
			speed = Math.abs(speed);
		}else{
			speed = (-1)*Math.abs(speed);
		}
		
		gravity();
		action();
		//startTime = System.nanoTime();
	}
	
	public void action(){
		endTime = System.nanoTime();
		//System.out.println(startTime-endTime);
		//pasive movement
		//System.out.println(action);
		seek();
		if(!(seeking)){
			if(!(seeking) && Math.abs(startTime-endTime)>900000000){ //1000000000 is 1 second
				action = (int)(Math.random()*100);
				startTime = System.nanoTime();
			}
			if(action<60){
				move();
			}else if(action>85){
				System.out.println("switched direction");
				faceRight ^= true;
				action = (int)(Math.random()*100);
			}
		}else{
			move();
		}
	}
	
	
	//draw robot in different modes.
	public void paint(){
		if(seeking){
			//seeking
			GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.pink);
		}else{
		if(faceRight){
			//facing right
			GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.BLUE);
		}else{
			// facing left
			GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.GREEN);
		}
		}
	}
	
	//this basically sets up the robots field of vision and tells it to move.
	public void seek(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		
		if(seeking){
			if(Player.x<x+viewRange && Player.x>x){
				faceRight = true;
			}else if(Player.x >x-viewRange && Player.x<x){
				faceRight = false;
			}else{
				seeking = false;
			}
		}else{
		
		// i know this is not effecient. I'll fix it.
		if(faceRight && ((Player.x-x))<viewRange &&Player.x>x){
			//x+=speed;
			//move();
			seeking = true;
		}else if(!(faceRight) && Math.abs((Player.x-x))<viewRange && Player.x<x){
			//move();
			seeking = true;
		}else{
			seeking = false;
		}
	}
	}
	//left of with jumping
	public void move(){
		if(touchBounds(x,y)){
			yVelocity-=jumpHeight;
		}
		x+=speed;
	}
	
	
	public void gravity(){
		if(!(touchBounds(x, y))){
			//y+=yVelocity;
			yVelocity+=gravityStrength;
		}else{
			yVelocity = 0;
		}
		y+=yVelocity;
	}
	
}
