package com.capstone.plasma.mob;

import java.awt.Color;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Tile;

public class Robot extends Mob {
	
	public static int lowSpeed = 2;
	public static int highSpeed = 3;
	public static int speed = lowSpeed;
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
	public boolean onGround = false;
	public int hp = 300;
	//public int x;
	//public int y;
	
	public Robot(int x, int y) {
		super(x, y);
		//this.x=x1;
		//this.y=y1;
		// TODO Auto-generated constructor stub
	}
	
	
	public void tick(){
		if(faceRight){
			speed = Math.abs(speed);
			lowSpeed = Math.abs(lowSpeed);
			highSpeed = Math.abs(highSpeed);
		}else{
			speed = (-1)*Math.abs(speed);
			lowSpeed = (-1)*Math.abs(lowSpeed);
			highSpeed = (-1)*Math.abs(highSpeed);
		}
		
		
		gravity();
		action();
		//startTime = System.nanoTime();
	}
	
	public void damage(int dm){
		hp-=dm;
	}
	
	public void action(){
		endTime = System.nanoTime();
		//System.out.println(startTime-endTime);
		//pasive movement
		//System.out.println(action);
		seek();
		if(!(seeking)){
			speed = lowSpeed;
			
			if(!(seeking) && Math.abs(startTime-endTime)>900000000){ //1000000000 is 1 second
				action = (int)(Math.random()*100);
				startTime = System.nanoTime();
				y-=4;
				
			}
			if(action<60){
				move();
			}else if(action>85){
				
				faceRight ^= true;
				action = (int)(Math.random()*100);
			}
		}else{
			speed = highSpeed;
			move();
		}
	}
	
	
	//draw robot in different modes.
	public void paint(){
		if(seeking){
			//seeking
			GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, size, size, 0, Color.pink);
			//GraphicsHandler.drawImage(GraphicsHandler.robotRight, x+GameScreen.xCam, y+GameScreen.yCam, size, size);
		}else{
			if(faceRight){
				//facing right
				GraphicsHandler.drawImage(GraphicsHandler.robotRight, x+GameScreen.xCam, y+GameScreen.yCam, size, size);
				
			}else{
				// facing left
				GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, size, size, 0, Color.GREEN);
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
		x+=speed;
		if(checkSide(x,y)){
			jump();
		}
	}
	
	public void jump(){
		yVelocity-=jumpHeight;
		//System.out.println("jump!");
	}
	public void gravity(){
		if (jump && onGround){
			//if(yVelocity>maxGrav){
				yVelocity-=jumpHeight;
			//}
			
		//	yVelocity -=jumpTick;
			jump=false;
		}
		
		Tile t;
		if((t = Utilities.touchBoundsTile(x,y,0, yVelocity)) !=null){
			//y+=yVelocity/3;
			//System.out.println(yVelocity);
			//System.out.println(touchBoundsNum(0,yVelocity));
				
			if(yVelocity>0){
			onGround=true;
			}
			if(yVelocity<0){
			y-=(y-t.y-Tile.size);
			}
			yVelocity = 0;
		}else{
			if(yVelocity<maxGrav){
			yVelocity +=gravityStrength;}
			onGround=false;
		}
			y+=yVelocity;
	}
	
	/*old grav
	public void gravity(){
		if(!(touchBounds(x, y))){
			//y+=yVelocity;
			yVelocity+=gravityStrength;
		}else{
			yVelocity = 0;
		}
		
		
		
		y+=yVelocity;
	}*/
	
}
