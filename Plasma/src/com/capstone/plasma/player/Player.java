package com.capstone.plasma.player;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.capstone.plasma.*;
import com.capstone.plasma.particle.ParticleHandler;

import com.capstone.plasma.particle.Projectile;
import com.capstone.plasma.tiles.Chunk;

import com.capstone.plasma.tiles.Tile;


public class Player {
	
	//public static int spawnX = 250;
	//public static int spawnY = 40;
	public static int x=GameScreen.map.spawnX;
	public static int y=GameScreen.map.spawnY;

	public static int gravityStrength = 1;
	public static int maxGrav = 100;
	public static int yVelocity = 0;
	public static int jumpHeight = 20; //was 20
	public static boolean jump=false;
	public static int gravCount = 0;
	public static int jumpTick = 0;
	public static int PlayerSpeed = 3;
	public static boolean onGround = false;
	public static int maxHp = 300;
	public static int hp = maxHp;
	public static int size = Tile.size;
	public static int deaths = 0;
	public static int kills = 0;
	public static boolean stun = false;
	public static boolean faceRight = true;
	public static float maxPlasma = 1000;
	public static float plasma = maxPlasma;
	public static int rechargeRate = 1;
	public static int recharge = rechargeRate;
	//public static Thread t1;
	
	public static void paint(){
		//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size, 0, Color.RED);
		if(UserInput.lastKey =="d"){
			GraphicsHandler.drawImage(GraphicsHandler.player,x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size);
		}else{
			GraphicsHandler.drawImage(GraphicsHandler.player2,x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size);
		}
		
		//health bar doesn't work well
		float percent = ((float)hp/maxHp);
		float fill =  (float) (40.0f*percent);
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam-20, fill, 5, 0, Color.RED);
		GraphicsHandler.drawEmptyRect(x+GameScreen.xCam, y+GameScreen.yCam-20, 40, 5, 0, Color.BLACK);
		
		//energy system
		float percentPlasma = (plasma/maxPlasma);
		float fillPlasma = (float) (300*percentPlasma);
		GraphicsHandler.drawRect(60, 400, 30, fillPlasma, 180, new Color(205, 0, 255));
		GraphicsHandler.drawEmptyRect(30,100,30,300,0,Color.BLUE);
		
		///GraphicsHandler.drawEmptyRect((x+5)+GameScreen.xCam, y+GameScreen.yCam, Tile.size-10, Tile.size, 0, Color.BLACK);	
		
		//GraphicsHandler.drawRect(getBounds(0,yVelocity).x, getBounds(0,yVeloc
	}
	
	public static Rectangle getBounds(int xn,int yn){
		return new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
	}
	
	public static void damage(int dm){
		hp-=dm;
		
	}
	
	public static void move(){

		if(!stun){
		if(!touchBounds(PlayerSpeed, -1)){
			if(faceRight = true){
				x+=PlayerSpeed;
				if(Player.x+GameScreen.xCam>=400){
					GameScreen.xCam-=3;
					GameScreen.backCam-=2;
			
			}
			}else{
				Player.x-=3;
				if(Player.x+GameScreen.xCam<=100){
					GameScreen.xCam+=3;
					GameScreen.backCam+=2;
				}
			}
		}
		}
	}
	
	public static void throwBack(int knock,int knockup, boolean imRight){
		if(!stun){
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				int xVelocity = knock;
				yVelocity -= knockup;
				stun = true;
				if(!imRight){
					while(Math.abs(xVelocity) > 0){
						x+=xVelocity;
						if(touchBounds(xVelocity,-1)){
						x-=2*xVelocity;
						}
						if(Player.x+GameScreen.xCam>=400){
						GameScreen.xCam-=xVelocity;
						}
						xVelocity-=1;
				        try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
					while(Math.abs(xVelocity) > 0){
						x-=xVelocity;
						if(touchBounds(xVelocity,-1)){
						x+=2*xVelocity;
						}
						if(Player.x+GameScreen.xCam<=400){
						GameScreen.xCam+=xVelocity;
						}
						xVelocity-=1;
				        try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
				
				stun = false;
				
			}
		});
		t1.start();
		}
	}
	
	public static boolean touchBounds(int xn,int yn){
		try{
			Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
			//looping
			for(int i=0;i<GameScreen.map.tiles.size();i++){
				Tile s = GameScreen.map.tiles.get(i);
				if(r.intersects(s.getBounds())  && (s.collide)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static Tile touchBoundsTile(int xn,int yn){
		Rectangle r=  new Rectangle(x+GameScreen.xCam+xn,y+GameScreen.yCam+yn,Tile.size,Tile.size);
		//looping
		for(int i=0;i<GameScreen.map.tiles.size();i++){
			Tile s = GameScreen.map.tiles.get(i);
			
			if(s!=null && (s.collide) && r.intersects(s.getBounds())){
				return s;
			}
		}
		return null;
	}

	public static void findX(int playerX,ArrayList<Tile> s){
		int numTiles = s.size();
		if(numTiles>1){
			if(s.get(numTiles/2).x>playerX){
				findX(playerX, (ArrayList<Tile>)(s.subList(0,(numTiles/2) )));
			}else{
				findX(playerX, (ArrayList<Tile>)(s.subList((numTiles/2),numTiles )));
			}
		}else{
			System.out.println("that worked");
		}
	}


	public static void respawn(){
		x=GameScreen.map.spawnX;
		y=GameScreen.map.spawnY;
		hp = maxHp;
		GameScreen.xCam = 0;
		GameScreen.yCam = 105;
		GameScreen.backCam = 0;
		
		
		ParticleHandler.createExplosion(x,y,15,6,15,Color.RED);
		

		deaths++;
	}
	
	public static void recharge(){
		//recharge -=1;
		//System.out.println(recharge);
		if(plasma<maxPlasma){
			//recharge = System.nanoTime();
			plasma+=.5;
			//System.out.println("plasma"+plasma);
		}
		
	}

	
	public static void tick(){
		recharge();
		/*
		if(yVelocity>10){ //fall damage code
			damage(yVelocity-20);
			System.out.println(yVelocity-20);
		}
		*/
		if(hp<=0){
			respawn();
		}
		if(y>GameScreen.map.lowest){
			respawn();
		}
		//System.out.println(y);
		if (jump && onGround){
				yVelocity-=jumpHeight;
				
			jump=false;
		}
		Tile t;

		//if(( t = touchBoundsTile(0,yVelocity))!=null){
		
		if(( t = Utilities.touchBoundsTile(x+2,y,0,yVelocity,size-4,size))!=null){	
		
			if(yVelocity>0){
			onGround=true;
			}
			if(yVelocity<0){
				y-=(y-t.y-Tile.size);
				
				
				
			}
			yVelocity = 0;
		}else{

			if(yVelocity<maxGrav){
			yVelocity +=gravityStrength;
			}
			onGround=false;
		//	}
		}
		//System.out.println(yVelocity);
		//if(yVelocity<maxGrav){
			y+=yVelocity;
			//GameScreen.yCam-=yVelocity;
			//System.out.println(y+GameScreen.yCam);//316, //116
			if(y+GameScreen.yCam>416 || y+GameScreen.yCam<86){
				GameScreen.yCam-=yVelocity;//-390 top, 180 bot -105 mid
			}//15 lower -225
	}
}