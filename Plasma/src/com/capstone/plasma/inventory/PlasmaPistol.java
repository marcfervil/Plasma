package com.capstone.plasma.inventory;

import java.util.concurrent.TimeUnit;

import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.UserInput;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.PlasmaShot;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class PlasmaPistol extends Weapon{
	
	public int damage=100;
	
	public PlasmaPistol(){
		super(GraphicsHandler.plasmaPistol);
	}

	public void action(){
		/*
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		if(UserInput.lastKey =="d"){
			ParticleHandler.particles.add(new PlasmaShot(Player.x+Tile.size,Player.y,damage));
		}else{
			ParticleHandler.particles.add(new PlasmaShot(Player.x-Tile.size,Player.y,damage));
		}
	}
	
}