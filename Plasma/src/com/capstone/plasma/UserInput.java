package com.capstone.plasma;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.inventory.PlasmaPistol;
import com.capstone.plasma.inventory.TNT;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.Projectile;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.tiles.Tile;

public class UserInput {
	public static String lastKey;
	public static int shotTickSpeed = 20;
	public static int shotTick = shotTickSpeed;
	
	static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	
	
	public static void get(){
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				if(!keysDown.contains(Keyboard.getEventKey()))keysDown.add(Keyboard.getEventKey());
				if(Keyboard.getEventKey()==Keyboard.KEY_G){
					PlayerHandler.playerTrail.toggleStream();
				}
				if(Keyboard.getEventKey()==Keyboard.KEY_I){
					Inventory.toggleExpand();
				}
		
				if(Keyboard.getEventKey()==Keyboard.KEY_SPACE){
					ParticleHandler.particles.clear();
				}
				//lastKey=Keyboard.getEventKey();
			}else{
				keysDown.remove(keysDown.indexOf(Keyboard.getEventKey()));
		    }
		}
	}
	
	public static void startKeyManager(){
		new KeyManager().start();
	}
	
	static class KeyManager extends Thread{
		public void run(){
			while(true){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
				//for(int key:keysDown){
				if(keysDown.size()==0){
					Player.jumpTick = 0;
				}
				for(int i=0;i<keysDown.size();i++){
					int key=keysDown.get(i);
			//		System.out.println(Player.x);
					switch(key){
						
						case Keyboard.KEY_RIGHT:
							Player.x+=3;
							if(Player.x+GameScreen.xCam<=100){
								GameScreen.xCam+=3;
								GameScreen.backCam+=2;
							}
							break;
						case Keyboard.KEY_LEFT:
							Player.move();
							Player.faceRight = false;
							break;
						case Keyboard.KEY_UP:
							if(Player.onGround){
								//Player.jump();
							}
							break;
						case Keyboard.KEY_D:
							lastKey = "d";
							Player.faceRight = true;
							//Player.findX(Player.x, Tile.tiles);
							Player.move();
							break;
						case Keyboard.KEY_A:
							lastKey = "a";
							if(!Player.touchBounds(-Player.PlayerSpeed, -1)){
								Player.x-=Player.PlayerSpeed;
								if(Player.x+GameScreen.xCam<=300){ //was 100 i think?
									GameScreen.xCam+=Player.PlayerSpeed;
									GameScreen.backCam+=Player.PlayerSpeed-(Player.PlayerSpeed/3);
									
								}
							}
							break;
						case Keyboard.KEY_W:
					//		System.out.println(Player.jumpTick);
							Player.jumpTick++;
							//Player.jump();
							
							if(Player.onGround){
								Player.jump=true;
							}
							
							break;
							
						case Keyboard.KEY_J:
							//Inventory.activeItems[0] = new TNT();
							if(Inventory.activeItems[0] !=null &&shotTick>=shotTickSpeed){
								shotTick = 0;
								Inventory.activeItems[0].action();
							}else{
								shotTick++;
							}
							break;
						case Keyboard.KEY_K:
							//Inventory.activeItems[0] = new TNT();
							if(Inventory.activeItems[1] !=null &&shotTick>=shotTickSpeed){
								shotTick = 0;
								Inventory.activeItems[1].action();
							}else{
								shotTick++;
							}
							break;
						case Keyboard.KEY_L:
							//Inventory.activeItems[0] = new TNT();
							if(Inventory.activeItems[2] !=null &&shotTick>=shotTickSpeed){
								shotTick = 0;
								Inventory.activeItems[2].action();
							}else{
								shotTick++;
							}
							break;
						
							
						default:
							shotTick =shotTickSpeed;
						
							
				
					}
				}
				//System.out.println(keysDown.size());
				if(keysDown.size()==0 && shotTick !=shotTickSpeed){
					//System.out.println("ran");
					shotTick = shotTickSpeed;
				}
			}
		}
	}
	
}
