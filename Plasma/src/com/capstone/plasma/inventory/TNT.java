package com.capstone.plasma.inventory;

import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.PlasmaShot;
import com.capstone.plasma.particle.TNTThrowable;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class TNT extends Weapon{
	public int energy = 100;

	public TNT(){
		super(GraphicsHandler.TNT);
	}
	
	
	public void action(){
		if(Player.plasma>=energy){
		Player.plasma -=energy;
		ParticleHandler.particles.add(new TNTThrowable(Player.x+Tile.size,Player.y,100));
		}
	}
}
