package com.capstone.plasma.particle;



import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.inventory.Item;

public class Dropable extends Particle{

	Item item;
	int size=30;
	boolean shrink=true;
	
	
	public Dropable(int x, int y, Item item){
		super(x, y, null);
		this.item=item;
	}
	
	public void paint(){
		//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, 10, 10, 0, ParticleHandler.getColorAlpha(color,alpha));
		GraphicsHandler.drawImage(item.texture, x+GameScreen.xCam, y+GameScreen.yCam, size, size);
	}
	
	public void tick(){
		if(shrink){
			size--;
			if(size==5){
				shrink=false;
				return;
			}
		}else{
			size++;
			if(size==30){
				shrink=true;
				return;
			}
		}
	}

}
