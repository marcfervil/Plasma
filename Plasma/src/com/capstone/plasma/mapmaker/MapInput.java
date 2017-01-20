package com.capstone.plasma.mapmaker;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;
import org.lwjgl.input.Keyboard;

public class MapInput {
	public static int scrollSpeed = 5;

	
	static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	
	
	public static void get(){
		//System.out.println("get ran");
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				if(!keysDown.contains(Keyboard.getEventKey()))keysDown.add(Keyboard.getEventKey());
			}else{
				keysDown.remove(keysDown.indexOf(Keyboard.getEventKey()));
		    }
		}
	}
	
	public static void startKeyManager(){
		new KeyManager().start();
		//System.out.println("test");
	}
	
	static class KeyManager extends Thread{
		
		public void run(){
			//System.out.println("test");
			while(true){
				//mapScreen.moveCam();
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
				//for(int key:keysDown){
				for(int i=0;i<keysDown.size();i++){
					int key=keysDown.get(i);
			//		System.out.println(Player.x);
					switch(key){
						
						case Keyboard.KEY_RIGHT:
							//mapScreen.xCam+=1;
							mapScreen.moveCam(scrollSpeed, 0);
							break;
						case Keyboard.KEY_LEFT:
							mapScreen.moveCam(-scrollSpeed, 0);
							//mapScreen.xCam+=1;
								//mapScreen.backCam+=2
							break;
						case Keyboard.KEY_UP:
							mapScreen.moveCam(0, scrollSpeed);
							//mapScreen.yCam+=1;
							break;
						case Keyboard.KEY_D:
							//mapScreen.xCam+=1;
							mapScreen.moveCam(scrollSpeed, 0);
							break;
						case Keyboard.KEY_A:
							mapScreen.moveCam(-scrollSpeed, 0);
							//mapScreen.xCam-=1;
							break;
						case Keyboard.KEY_S:
							mapScreen.moveCam(0, scrollSpeed);
							//mapScreen.yCam+=1;
							break;
						case Keyboard.KEY_W:
							mapScreen.moveCam(0, -scrollSpeed);
							//mapScreen.yCam-=1;
							break;
						case Keyboard.KEY_EQUALS:
							scrollSpeed++;
							break;
						case Keyboard.KEY_MINUS:
							if(scrollSpeed>1){
								scrollSpeed--;
							}
							break;
							
						
					}
				}
			}
		}
	}
}