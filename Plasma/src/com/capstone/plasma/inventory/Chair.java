package com.capstone.plasma.inventory;


import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.particle.ChairEntity;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class Chair  extends Weapon{
	
	
	
	public Chair(){
		super(GraphicsHandler.chair);
	}
	
	
	public void action(){
		ParticleHandler.particles.add(new ChairEntity(Player.x+Tile.size,Player.y,100));
	}
	
	
	
}
