package com.capstone.plasma;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;

public class UserInput {

	
	static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	
	
	public static void get(){
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				if(!keysDown.contains(Keyboard.getEventKey()))keysDown.add(Keyboard.getEventKey());
				if(Keyboard.getEventKey()==Keyboard.KEY_G){
					PlayerHandler.playerTrail.toggleStream();
				}
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
							Player.x-=3;
							if(Player.x+GameScreen.xCam<=100){
								GameScreen.xCam+=3;
								GameScreen.backCam+=2;
							}
							break;
						case Keyboard.KEY_UP:
							if(Player.onGround){
								//Player.jump();
							}
							break;
						case Keyboard.KEY_D:
							if(!Player.touchBounds(3, -1)){
								Player.x+=3;
								if(Player.x+GameScreen.xCam>=400){
									GameScreen.xCam-=3;
									GameScreen.backCam-=2;
								}
							}else{
								//ParticleHandler.createParticleStream(Player.x, Player.y, 5,Color.CYAN);
							}
							break;
						case Keyboard.KEY_A:
							if(!Player.touchBounds(-3, -1)){
								Player.x-=3;
								if(Player.x+GameScreen.xCam<=100){
									GameScreen.xCam+=3;
									GameScreen.backCam+=2;
								}
							}
							break;
						case Keyboard.KEY_W:
							
							if(Player.onGround){
								Player.jump=true;
							}
							break;
						
					}
				}
			}
		}
	}
	
}