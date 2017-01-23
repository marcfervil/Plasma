package com.capstone.plasma.inventory;

import com.capstone.plasma.GraphicsHandler;

public class Item {

	public int texture;
	
	public Item(int texture){
		this.texture=texture;
	}
	
	public void paint(int x, int y){
		GraphicsHandler.drawImage(texture, x, y, 30, 30);
	}
	
	public void action(){
		
	}
	
}
