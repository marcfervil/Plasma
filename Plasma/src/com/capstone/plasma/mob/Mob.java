package com.capstone.plasma.mob;

import java.util.ArrayList;

public class Mob {

	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	
	public static class MobTickManager extends Thread{
		public void run(){
			while(true){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(Mob mob:mobs){
					mob.tick();
				}
			}
		}
	}
	
	
	public static void paintMobs(){
		
	}
	
	public void tick(){
		
	}
	
	public void paint(){
		
	}
	
	
}
