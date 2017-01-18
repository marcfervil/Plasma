package com.capstone.plasma.inventory;

import java.util.ArrayList;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.tiles.Tile;

public class Inventory {

	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public static void paint(){
		for(int i=0;i<items.size();i++){
			Item item= items.get(i);
			int holderX=30+(i*(60+Tile.size/2));
			int holderY=GameScreen.height-90;
			GraphicsHandler.drawImage(GraphicsHandler.itemHolder, holderX,holderY, 60, 60);
			item.paint(holderX+(30/2), holderY+(30/2));
		}
	}
	
}
