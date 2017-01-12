package com.capstone.plasma.tiles;
import com.capstone.plasma.*;

public class BackgroundTile extends Tile{

	public BackgroundTile(int texture, int x, int y) {
		super(texture, x, y);
		collide=false;
	}
	
	public void paint(){
		//make this fit screen
		if(this.x+GameScreen.backCam<900 && this.x+GameScreen.backCam>-60){
			GraphicsHandler.drawImage(texture,this.x+GameScreen.backCam,this.y+GameScreen.yCam,size,size);
		}
	}

}
