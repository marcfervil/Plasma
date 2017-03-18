package com.capstone.plasma.particle;

import java.awt.Color;
import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.Settings;
import com.capstone.plasma.mapmaker.Map;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class Teleporter extends Particle{

	public Teleporter(int x, int y) {
		super(x, y, null);
		// TODO Auto-generated constructor stub
	}
	
	public void paint(){
		GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, Tile.size, Tile.size*2, 0, Color.PINK);
		//GraphicsHandler.drawRect(x, y, size, size*2, 0, Color.PINK);
	}
	
	public void tick(){
		if(new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam, 20, 10).intersects(Player.getBounds(0,0))){
			System.out.println("teleporter away!");
			//GameScreen.map=Map.load("map1.ser");
			Player.level++;
			String map = Settings.world+"/level"+Player.level;
			//System.out.println()
			GameScreen.map=Map.load(map+".ser");
			Player.x = GameScreen.map.spawnX;
			Player.y = GameScreen.map.spawnY;
			GameScreen.xCam = 0;
			GameScreen.yCam = 105;
	        for(int i =0; i<GameScreen.map.mobs.size();i ++){
	        	GameScreen.map.mobs.get(i).run();
	        }
		}
	}
	
}
