package com.capstone.plasma.mapmaker;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.player.Player;
import com.capstone.plasma.player.PlayerHandler;
import org.lwjgl.input.Keyboard;

public class MapInput {
	public static int scrollSpeed = 5;
	public static boolean save = true;
	public static boolean load = true;
	public static boolean r = true;
	public static int active = 0;

	
	static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	
	public static void get(){
		//System.out.println("get ran");
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				if(!keysDown.contains(Keyboard.getEventKey()))keysDown.add(Keyboard.getEventKey());
				if(Keyboard.getEventKey() == Keyboard.KEY_R){
					if(MapMaker.color =="red"){
						MapMaker.layer(true);
						MapMaker.color = "blue";
					}else{
						MapMaker.color = "red";
						MapMaker.layer(false);
					}
				}
			}else{
				if(keysDown.indexOf(Keyboard.getEventKey())!=-1)
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
				if(keysDown.size()==0){
					//if no keys down
					save = true;
					load = true;
					r = true;
					
				}
				for(int i=0;i<keysDown.size();i++){
					int key=keysDown.get(i);
			//		System.out.println(Player.x);
					switch(key){
						
						case Keyboard.KEY_RIGHT:
							//mapScreen.xCam+=1;
							MapScreen.moveCam(scrollSpeed, 0);
							break;
						case Keyboard.KEY_LEFT:
							MapScreen.moveCam(-scrollSpeed, 0);
							//mapScreen.xCam+=1;
								//mapScreen.backCam+=2
							break;
						case Keyboard.KEY_UP:
							MapScreen.moveCam(0, scrollSpeed);
							//mapScreen.yCam+=1;
							break;
						case Keyboard.KEY_D:
							//mapScreen.xCam+=1;
							MapScreen.moveCam(scrollSpeed, 0);
							//System.out.println("d pressed");
							break;
						case Keyboard.KEY_A:
							MapScreen.moveCam(-scrollSpeed, 0);
							//mapScreen.xCam-=1;
							break;
						case Keyboard.KEY_S:
							MapScreen.moveCam(0, scrollSpeed);
							//mapScreen.yCam+=1;
							break;
						case Keyboard.KEY_W:
							MapScreen.moveCam(0, -scrollSpeed);
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
						case Keyboard.KEY_K:
							if(save){
								save = false;
								MapMaker.save();
							}
							break;
						case Keyboard.KEY_L:
							if(load){
								load = false;
								MapMaker.load();
							}
							break;
						case Keyboard.KEY_1:
							active = 0;
							break;
						case Keyboard.KEY_2:
							active = 1;
							break;
						case Keyboard.KEY_3:
							active =2;
							break;
						case Keyboard.KEY_4:
							active=3;
							break;
						case Keyboard.KEY_5:
							active =4;
							break;
						case Keyboard.KEY_6:
							active=5;
							break;
						case Keyboard.KEY_7:
							active=6;
							break;
							
						default:
							save = true;
							load = true;
							r = true;
						
					}
				}
			}
		}
	}
}