package com.capstone.plasma.mob;

import java.util.ArrayList;

public class Mob {

	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	
	public int x;
	public int y;
	public int texture;
	
	public static class MobTickManager extends Thread{
		public void run(){
			while(true){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int i=0;i<mobs.size();i++){
					Mob mob= mobs.get(i);
					mob.tick();
				}
			}
		}
	}
	
	
	public static void paintMobs(){
		for(int i=0;i<mobs.size();i++){
			Mob mob= mobs.get(i);
			mob.paint();
		}
	}

	public Mob(int texture,int x, int y){
		this.x=x;
		this.y=y;
		this.texture=texture;
	}

	public void tick(){
		
	}
	
	public void paint(){
		
	}
	
	
}
