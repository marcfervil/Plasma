package com.capstone.plasma.particle;



import com.capstone.plasma.GameScreen;
import com.capstone.plasma.GraphicsHandler;
import com.capstone.plasma.inventory.Item;

public class Dropable extends Particle{

	Item item;
	boolean rise=true;
	int up = 0;
	
	
	public Dropable(int x, int y, Item item){
		super(x, y, null);
		this.item=item;
		onTick=5;
	}
	
	public void paint(){
		//GraphicsHandler.drawRect(x+GameScreen.xCam, y+GameScreen.yCam, 10, 10, 0, ParticleHandler.getColorAlpha(color,alpha));
		GraphicsHandler.drawImage(item.texture, x+GameScreen.xCam, y+GameScreen.yCam+up, 30, 30);
	}
	
	public void tick(){
		if(rise){
			up++;
			if(up==20){
			
				rise=false;
				return;
			}
		}else{
			up--;
			if(up==0){
				rise=true;
				return;
			}
		}
	}

}
