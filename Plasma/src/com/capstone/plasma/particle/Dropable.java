package com.capstone.plasma.particle;



import java.awt.Rectangle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.inventory.Inventory;
import com.capstone.plasma.inventory.Item;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

public class Dropable extends Particle{

	Item item;
	boolean rise=true;
	int up = 0;
	
	
	public Dropable(int x, int y, Item item){
		super(x, y, null);
		this.item=item;
		onTick=5;
	}
	
	public void action(){
		if(new Rectangle(x+GameScreen.xCam, y+GameScreen.yCam+up, Tile.size, Tile.size).intersects(Player.getBounds(0, 0))){
			remove=true;
			Inventory.items.add(item);
		}
	}
	
	public void paint(){
		GraphicsHandler.drawImage(item.texture, x+GameScreen.xCam, y+GameScreen.yCam+up, Tile.size, Tile.size);
	}
	
	public void tick(){
		if(rise){
			up++;
			if(up==20){
				rise=false;
			}
		}else{
			up--;
			if(up==0){
				rise=true;
			}
		}
		
		action();
	}

}
