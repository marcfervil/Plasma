package com.capstone.plasma.inventory;

import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.Projectile;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class PlasmaPistol extends Weapon{

	public PlasmaPistol(){
		super(GraphicsHandler.plasmaPistol);
	}

	public void action(){
		ParticleHandler.particles.add(new Projectile(Player.x+Tile.size,Player.y));
	}
	
}
