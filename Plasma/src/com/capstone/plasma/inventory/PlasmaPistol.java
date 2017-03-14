package com.capstone.plasma.inventory;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.UserInput;
import com.capstone.plasma.particle.ParticleHandler;
import com.capstone.plasma.particle.PlasmaShot;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class PlasmaPistol extends Weapon{
	
	public int damage=100;
	public int energy = 5;
	
	public PlasmaPistol(){
		super(GraphicsHandler.plasmaPistol);
	}

	public void action(){
		if(Player.plasma>=energy){
		Player.plasma -=energy;
		//System.out.println(Player.plasma);
		if(UserInput.keysDown.contains(Keyboard.KEY_S)){
			System.out.println("down");
			ParticleHandler.particles.add(new PlasmaShot(Player.x+(Tile.size/2),Player.y,damage,-270,null,Color.CYAN));
			return;
		}
		
		if(UserInput.lastKey =="d"){
			//ParticleHandler.particles.add(new PlasmaShot(Player.x+(Tile.size),Player.y,damage,0,null,Color.CYAN));
			ParticleHandler.particles.add(new PlasmaShot(Player.x+Tile.size,Player.y,damage,0,null,Color.CYAN));
			
		}else{
			ParticleHandler.particles.add(new PlasmaShot(Player.x+(Tile.size/2),Player.y+(Tile.size/2),damage,180,null,Color.CYAN));
		}
	}
	}
	
}