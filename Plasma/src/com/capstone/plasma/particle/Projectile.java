package com.capstone.plasma.particle;

import com.capstone.plasma.UserInput;

public class Projectile extends Particle{

	public boolean right = true;
	
	public Projectile(int x, int y, int damage) {
		super(x, y, null);
		if(UserInput.lastKey =="d"){
			right = true;
		}else{
			right = false;
		}

	}
	


}