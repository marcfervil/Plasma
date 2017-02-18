package com.capstone.plasma.mob;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Tile;

public class Robot extends Mob {
	
	public int lowSpeed = 2;
	public int highSpeed = 3;
	public int speed = lowSpeed;
	public int gravityStrength = 1;
	public int maxGrav = 100;
	public int yVelocity = 0;
	public int jumpHeight = 20; //was 20
	public boolean jump=false;
	public long startTime = 10;
	public long endTime;
	public boolean seeking = false;
	public int viewRange = 200;
	public int action = 30;
	public boolean onGround = false;
	public int hp = 300;
	public int size = Tile.size;
	public int paintSize = size+10;
	public boolean aniStage = true;
	//public int x;
	//public int y;
	
	
	public Robot(int x, int y) {
		super(x, y);
	}
	
	
	public void tick(){
		attack();
		if(hp<=0){
			ParticleHandler.createParticleStream(x, y, Color.BLACK, 10, 10, true,10);
			
		//	System.out.println(mobs.indexOf(this));
			
			Mob.mobs.remove(mobs.indexOf(this));
			t1.stop();
			
			//deathAnimation();
		}
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
		seek();
		if(!(seeking)){
			speed = lowSpeed;
			
			if(!(seeking) && Math.abs(startTime-endTime)>900000000){ //1000000000 is 1 second
				action = (int)(Math.random()*100);
				startTime = System.nanoTime();
				
				
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
	
	public void attack(){
		if((x-GameScreen.xCam)>=Player.x && (x-GameScreen.xCam)<=Player.x+Tile.size){
			System.out.println("true");
		}
		//if(x-GameScreen.xCam)
	}
	
	//this basically sets up the robots field of vision and tells it to move.
	public void seek(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		
		if(seeking){
			if(Player.x<x+viewRange && Player.x >x){
				faceRight = true;
			}else if(Player.x >x-viewRange && Player.x<x){
				faceRight = false;
			}else{
				seeking = false;
			}
		}else{
		
		// i know this is not effecient. I'll fix it.
			
		if(faceRight && ((Player.x-x))<viewRange &&Player.x>x){
			seeking = true;
		}else if(!(faceRight) && Math.abs((Player.x-x))<viewRange && Player.x<x){
			seeking = true;
		}else{
			seeking = false;
		}
	}
	}
	//left of with jumping
	public void move(){
		if(!(Utilities.touchBounds(x, y, speed, -1,size)) && !(Utilities.touchBoundsMobs(x, y, speed, -1,size,this))){
			
				
				if( !(Player.x-Tile.size < x && Player.x+Tile.size > x) ){
					x+=speed;
				}
			
		}else{
			jump();
		}

	}
	
	public void jump(){
		if(onGround){
			yVelocity-=jumpHeight;
		}
		//System.out.println("jump!");
	}
	public void gravity(){
		if (jump && onGround){
			//if(yVelocity>maxGrav){
				yVelocity-=jumpHeight;

			jump=false;
		}
		
		Tile t;
		if((t = Utilities.touchBoundsTile(x,y,0, yVelocity,size)) !=null){
				
			if(yVelocity>0){
				onGround=true;
			}
			if(yVelocity<0 ){
				
				y-=(y-t.y-Tile.size);
				
			}
			yVelocity = 0;
		}else{
			if(yVelocity<maxGrav){
				yVelocity +=gravityStrength;
			}
			onGround=false;
		}
//		System.out.println("falling");
	
		if(!(Utilities.touchBoundsMobs(x,y,0, yVelocity,size,this))){
			y+=yVelocity;
		}
	}
	
	public void paint(){
		if(seeking){
			//seeking
			//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, size, size, 0, Color.pink);
			if(aniStage){
				GraphicsHandler.drawImage(GraphicsHandler.angryRobotRight, x+GameScreen.xCam, y+GameScreen.yCam+(size-paintSize), paintSize, paintSize);
			}else{
				GraphicsHandler.drawImage(GraphicsHandler.angryRobotLeft, x+GameScreen.xCam, y+GameScreen.yCam+(size-paintSize), paintSize, paintSize);
			}
			//aniStage ^= true;
		}else{
			if(faceRight){
				//facing right
				GraphicsHandler.drawImage(GraphicsHandler.robotRight, x+GameScreen.xCam, y+GameScreen.yCam+(size-paintSize), paintSize, paintSize);
				
			}else{
				// facing left
				//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, size, size, 0, Color.GREEN);
				GraphicsHandler.drawImage(GraphicsHandler.robotRight, x+GameScreen.xCam, y+GameScreen.yCam+(size-paintSize), paintSize, paintSize);
			}
		}
	}
	

	
}