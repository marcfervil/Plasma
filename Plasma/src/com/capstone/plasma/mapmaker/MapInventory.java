package com.capstone.plasma.mapmaker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;

import org.lwjgl.input.Mouse;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.inventory.Chair;
import com.capstone.plasma.inventory.Item;
import com.capstone.plasma.inventory.PlasmaPistol;
import com.capstone.plasma.inventory.TNT;
import com.capstone.plasma.inventory.Weapon;
import com.capstone.plasma.tiles.Tile;

public class MapInventory {
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static Integer[] activeItems = new Integer[6];
	public static boolean isInventoryExpanded=false;
	//public static int active = 0;
	//public static boolean 

	public static void paint(){
		activeItems[0]=0;//floor
		activeItems[1]=1;//glowtile change later
		activeItems[2]=2;//breakable
		activeItems[3]=3;//robot
		activeItems[4]=4;//turret
		activeItems[5]=5;//player
		//activeItems[2]=new Chair(); //breakable was removed D: yell at marco later
		for(int i=0;i<activeItems.length;i++){
			int item= activeItems[i];
			int holderX=30+(i*(60+Tile.size/2));
			//int holderY=GameScreen.height-90;
			int holderY=GameScreen.orgHeight-80;
			if(MapInput.active == i){
				GraphicsHandler.drawImage(GraphicsHandler.selecteditemHolder, holderX, holderY, 60, 60);
			}else{
				GraphicsHandler.drawImage(GraphicsHandler.itemHolder, holderX,holderY, 60, 60);
			}
			if(item!=10){
				//item.paint(holderX+(30/2), holderY+(30/2));
				if(activeItems[i] ==0){
					GraphicsHandler.drawImage(GraphicsHandler.floor,holderX+(30/2),holderY+(30/2),Tile.size,Tile.size);
				}else if(activeItems[i] ==1){
					GraphicsHandler.drawImage(GraphicsHandler.GlowTile,holderX+(30/2),holderY+(30/2),Tile.size,Tile.size);
				}else if(activeItems[i] ==2){
					GraphicsHandler.drawImage(GraphicsHandler.breakable,holderX+(30/2),holderY+(30/2),Tile.size,Tile.size);
				}else if(activeItems[i] ==3){
					GraphicsHandler.drawImage(GraphicsHandler.robotRight, holderX+(30/2),holderY+(30/2),Tile.size,Tile.size);
				}else if(activeItems[i]==4){
					GraphicsHandler.drawImage(GraphicsHandler.turret,holderX+(30/2),holderY+(30/2),Tile.size,Tile.size);
				}else{
					GraphicsHandler.drawRect(holderX+(30/2),holderY+(30/2),Tile.size,Tile.size, 0, Color.RED);
				}
			}
		}
		/*
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
		*/
	}
}
