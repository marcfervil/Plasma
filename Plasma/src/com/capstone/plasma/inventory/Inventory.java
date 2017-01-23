package com.capstone.plasma.inventory;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.tiles.Tile;

public class Inventory {

	public static ArrayList<Item> items = new ArrayList<Item>();
	public static Item[] activeItems = new Item[3];
	public static boolean isInventoryExpanded=false;
	
	//activeItems 0 is weapon
	//activeItems 1 is armorac
	//activeItems 2 is quick weapon
	
	public static void paint(){
		for(int i=0;i<activeItems.length;i++){
			Item item= activeItems[i];
			int holderX=30+(i*(60+Tile.size/2));
			int holderY=GameScreen.height-90;
			GraphicsHandler.drawImage(GraphicsHandler.itemHolder, holderX,holderY, 60, 60);
			if(item!=null){
				item.paint(holderX+(30/2), holderY+(30/2));
			}
		}
		if(isInventoryExpanded){

			int y=0;
			int x=0;
			GraphicsHandler.drawImage(GraphicsHandler.InventoryBackground, 50, 50, GameScreen.width-100, GameScreen.height-100);

			for(int i=0;i<activeItems.length;i++){
				Item item= activeItems[i];			
				GraphicsHandler.drawImage(GraphicsHandler.itemHolder, 300+x,100, 80, 80);
				if(item!=null){
					GraphicsHandler.drawImage(item.texture, (300+x)+10,(100)+10, 60, 60);
				}
				
				x+=((90+Tile.size/2));
			}
			
			y=0;
			x=0;
			
			for(int i=0;i<items.size();i++){
				Item item= items.get(i);
				if(100+x>(GameScreen.width-100)){
					y+=80;
					x=0;
				}
				GraphicsHandler.drawImage(GraphicsHandler.itemHolder, 100+x,200+y, 60, 60);
				//GraphicsHandler.drawRect(Mouse.getX(),GameScreen.height-Mouse.getY(),15,15, 0, Color.RED);
				if(Mouse.isButtonDown(0)){
					//System.out.println("feijfeijfeiw");
					if(new Rectangle(100+x,200+y, 60, 60).intersects(new Rectangle(Mouse.getX(),GameScreen.height-Mouse.getY(),15,15))){
					//	System.out.println("clicked square");
						if(item instanceof Weapon){
							activeItems[0]=item;
						}
					}
				}
				
				item.paint(100+x+(30/2), 200+y+(30/2));
				x+=((60+Tile.size/2));
				
			}
		}
	}
	
	public static void toggleExpand(){
		if(isInventoryExpanded){
			isInventoryExpanded=false;
		}else{
			isInventoryExpanded=true;
		}
	}
	
}
