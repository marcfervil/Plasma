package com.capstone.plasma.particle;

import com.capstone.plasma.GameScreen;
import com.capstone.plasma.mob.Mob;
import com.capstone.plasma.player.Utilities;
import com.capstone.plasma.tiles.Tile;

public class Throwable  extends Projectile{

	int ay = -2;
	int ax = 1;
	int angle = 45;
	int speed=Tile.size*2;
	int vx;
	int vy;
	boolean calledStop=false;
	
	public Throwable(int x, int y, int damage) {
		super(x, y-(Tile.size),damage);
		onTick=6;
		backgroundTick=true;
		speed=15;
		
		if(!right){
			vx = (int) -(speed*Math.cos(angle*(Math.PI/180.0)));
			ax=-1;
		}else{
			vx = (int) (speed*Math.cos(angle*(Math.PI/180.0)));
			ax=1;
		}
		
		vy = (int) (speed*Math.sin(angle*(Math.PI/180.0)));
		
	
	}
	
	public void tick(){

		


		x += vx;
		vx += ax;
		
		if(( Utilities.touchBounds(x, y, vx, -Tile.size/2, Tile.size) )){
			//vx*=-1;
			speed*=-1;
		}
		
		
		if(vy<-Tile.size){
			vy=-Tile.size;
		}
		
		Tile t;
		if(!( (t=Utilities.touchBoundsTile(x, y, 0, -vy, Tile.size))!=null )){
			vy += ay;
			
			
			y -= vy;
		}else{
			
			y=t.y-Tile.size;
			
			
			if(speed>=1){
				speed--;
			}else if(speed<=1){
				speed++;
			}
		}
		
		
		
		
		if(!right){
			vx = (int) -(speed*Math.cos(angle*(Math.PI/180.0)));
			ax=-1;
		}else{
			vx = (int) (speed*Math.cos(angle*(Math.PI/180.0)));
			ax=1;
		}

		if(speed==0){
			
			
			if(!calledStop)onStoppedMoving();
			calledStop=true;
		}
		
	}
	

	public void onStoppedMoving(){
		
	}
	
	
	//*old explode method
	public void explode(){
		for(int i=0;i<10;i++){
			int xr=Utilities.randInt(-100, 100);
			int yr=Utilities.randInt(-100, 100);
			Tile h=Utilities.touchBoundsTile(x+xr, y+yr, vx, speed, Tile.size);
			if(h!=null)h.damage(1000);
			
			Mob p=Utilities.touchBoundReturnMobs(x+xr, y+yr, vx, speed, Tile.size,null);
			if(p!=null)p.damage(Utilities.randInt(0, 300));
			
			p=Utilities.touchBoundReturnMobs(x-xr, y-yr, vx, speed, Tile.size,null);
			if(p!=null)p.damage(Utilities.randInt(0, 300));
			
			p=Utilities.touchBoundReturnMobs(x-xr, y, vx, speed, Tile.size,null);
			if(p!=null)p.damage(Utilities.randInt(0, 300));
			
			Tile z=Utilities.touchBoundsTile(x+xr, (y+yr)+(Tile.size), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			z=Utilities.touchBoundsTile(x+xr, (y+yr)-(Tile.size), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			z=Utilities.touchBoundsTile((x+xr)-(Tile.size), (y+yr), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			z=Utilities.touchBoundsTile((x+xr)+(Tile.size), (y+yr), vx, speed, Tile.size);
			if(z!=null)z.damage(Utilities.randInt(0, 300));
			
			
			GameScreen.shakeCamera(15,5);
			
		}
		remove();
	}
	//*/
	
}
