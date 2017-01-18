package com.capstone.plasma.mapmaker;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.capstone.plasma.player.PlayerHandler;
import com.capstone.plasma.GameScreen;
import com.capstone.plasma.player.Player;
import com.capstone.plasma.tiles.Tile;

import static org.lwjgl.opengl.GL11.*;

public class mapScreen {
	public static int xCam = 0;
	public static int yCam = 0;
	public static int totalXCam = 0;
	public static int totalYCam = 0;
	
	
	public static void moveCam(int x, int y){
		/*System.out.println("xCam: "+xCam+" yCam: "+yCam);
		int newX = xCam;
		int newY = yCam;
		xCam = 0;
		yCam = 0;
		*/
		GameScreen.xCam = totalXCam;
		GameScreen.yCam = totalYCam;
		/*
		for(int i =0; i<MapMaker.tiles.size(); i++){
			//System.out.println("this is another tes");
			MapMaker.tiles.get(i).x+=x;
			MapMaker.tiles.get(i).y+=y;
		}
		*/
		totalXCam +=x;
		totalYCam +=y;
		/*
		newX = 0;
		newY = 0;
		totalXCam += newX;
		totalYCam += newY;
		*/
	}

}
