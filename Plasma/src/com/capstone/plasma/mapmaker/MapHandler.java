package com.capstone.plasma.mapmaker;

import java.awt.Color;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;

public class MapHandler extends Thread {
	public void run(){
		//try{
			while(true){
				try{
				Thread.sleep(10);
				GameScreen.map.tick();
				}catch(InterruptedException e){
					
				}
			}

	}

}
