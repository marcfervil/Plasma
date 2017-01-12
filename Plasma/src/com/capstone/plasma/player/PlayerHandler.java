package com.capstone.plasma.player;

import java.awt.Color;

import com.capstone.plasma.ParticleHandler;

public class PlayerHandler extends Thread{
	
	public void run(){

		try {
			while(true){
				//it slept 10
				Thread.sleep(20);
				Player.tick();
				
				//for fun lol, this is not efficent tho
				ParticleHandler.createParticleStream(Player.x, Player.y, 1,Color.CYAN);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
