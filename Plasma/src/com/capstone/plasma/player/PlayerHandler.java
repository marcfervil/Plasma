package com.capstone.plasma.player;

import java.awt.Color;

import com.capstone.plasma.particle.ParticleHandler;

public class PlayerHandler extends Thread{
	
	public static ParticleHandler.ParticleStream playerTrail;
	
	public void run(){

		playerTrail= ParticleHandler.createParticleStream(Player.x, Player.y,Color.RED,5,10,false);
		try {
			while(true){
				Thread.sleep(20);
				Player.tick();
				playerTrail.x=Player.x;
				playerTrail.y=Player.y;
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
