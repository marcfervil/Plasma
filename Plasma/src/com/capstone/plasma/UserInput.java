package com.capstone.plasma;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;

public class UserInput {
	public static String lastKey;
	public static int shotTickSpeed = 20;
	public static int shotTick = shotTickSpeed;
	public static boolean input1 = true;
	
	public static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	
	
	public static void get(){
		try{
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
				if(Keyboard.getEventKey() == 17){
					Player.jumpTick = 0;
				}
				keysDown.remove(keysDown.indexOf(Keyboard.getEventKey()));
		    }
		}
		}catch(Exception e){
			System.out.println("keyboard error");
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
					//if no keys down
					//Player.jumpTick = 0;
					shotTick =shotTickSpeed;
				}
				for(int i=0;i<keysDown.size();i++){
					int key=keysDown.get(i);
			//		System.out.println(Player.x);
					if(input1){
					switch(key){
						case Keyboard.KEY_D:
							lastKey = "d";
							Player.faceRight = true;
							//Player.findX(Player.x, Tile.tiles);
							Player.move();
							GraphicsHandler.playerSheet.setCycle(new int[]{1,2,3,4});
							break;
						case Keyboard.KEY_Q:
					    	for(int  b=0; b<GameScreen.map.mobs.size();b++){
					    		GameScreen.map.mobs.get(b).death();
					    		//Player.kills--;
					    	}
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
							//	System.out.println(Player.jumpTick);
							//Player.jump();
							if(Player.jumpTick<8){
								//Player.jumpTick++;
								//Player.yVelocity-=3;
							}

							if(Player.onGround){
								Player.jump=true;
							}
							
							break;
							
						case Keyboard.KEY_S:
						//	continue;
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
							//shotTick =shotTickSpeed;
						
							
				
					}
					}
					//else{
						switch(key){
						case Keyboard.KEY_RIGHT:
							lastKey = "d";
							Player.faceRight = true;
							//Player.findX(Player.x, Tile.tiles);
							Player.move();
							break;
						case Keyboard.KEY_LEFT:
							lastKey = "a";
							if(!Player.touchBounds(-Player.PlayerSpeed, -1)){
								Player.x-=Player.PlayerSpeed;
								if(Player.x+GameScreen.xCam<=300){ //was 100 i think?
									GameScreen.xCam+=Player.PlayerSpeed;
									GameScreen.backCam+=Player.PlayerSpeed-(Player.PlayerSpeed/3);
									
								}
							}
							break;
						case Keyboard.KEY_UP:
					//		System.out.println(Player.jumpTick);
							Player.jumpTick++;
							//Player.jump();
							
							if(Player.onGround){
								Player.jump=true;
							}
							
							break;
							
						case Keyboard.KEY_DOWN:
						//	continue;
							break;
							
						case Keyboard.KEY_C:
							//Inventory.activeItems[0] = new TNT();
							if(Inventory.activeItems[0] !=null &&shotTick>=shotTickSpeed){
								shotTick = 0;
								Inventory.activeItems[0].action();
							}else{
								shotTick++;
							}
							break;
						case Keyboard.KEY_X:
							//Inventory.activeItems[0] = new TNT();
							if(Inventory.activeItems[1] !=null &&shotTick>=shotTickSpeed){
								shotTick = 0;
								Inventory.activeItems[1].action();
							}else{
								shotTick++;
							}
							break;
						case Keyboard.KEY_Z:
							//Inventory.activeItems[0] = new TNT();
							if(Inventory.activeItems[2] !=null &&shotTick>=shotTickSpeed){
								shotTick = 0;
								Inventory.activeItems[2].action();
							}else{
								shotTick++;
							}
							break;
						
							
						default:
							//shotTick =shotTickSpeed;
						
							
				
					//}
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
