package com.capstone.plasma;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;



public class ControllerInput {
	
	public static Controller controller;
	public static  Robot robot;
	//it's the thing -1 for the button #
	
	public static void init(){
		System.out.println("Initializing controller");
		try{
			Controllers.create();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Controllers.poll();

		controller=Controllers.getController(0);
		readInput();
		
		//System.out.println();
		//controller.setRumblerStrength(0, 5);
		//controller.getR
		try{
			robot = new Robot();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void readInput(){
		Thread input = new Thread() {
		    public void run(){
		    	while(true){
			        try {
			            Thread.sleep(10);
			            Controllers.poll();

			            if(GameScreen.gameMode==0){
			            	if(controller.isButtonPressed(14)){
				            	pressKey(KeyEvent.VK_J);
				            	Thread.sleep(500);
				            }
			            	if(controller.getYAxisValue()>=0.7){
			            		pressKey(KeyEvent.VK_S);
			            		Thread.sleep(150);
					        }
			            	if(controller.getYAxisValue()<=-0.7){
			            		pressKey(KeyEvent.VK_W);
			            		Thread.sleep(150);
			            		
					        }
			            }
			            if(GameScreen.gameMode==1){
			            	if(controller.isButtonPressed(11)){
				            	//pressKey(KeyEvent.VK_J);
			            		UserInput.keysDown.add(Keyboard.KEY_J);
	            		    	try {
									Thread.sleep(100);
								} catch (Exception e){
								}
	            		    	UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_J));
				            }
			            
			            	
			            	if(controller.isButtonPressed(9)){
			            		UserInput.keysDown.add(Keyboard.KEY_K);
	            		    	try {
									Thread.sleep(100);
								} catch (Exception e){
								}
	            		    	UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_K));
				            }
			            	
			            	if(controller.getXAxisValue()>=0.7){
			            		robot.keyPress(KeyEvent.VK_D);
					        }
			            	

			            	if(controller.getXAxisValue()<=-0.7){	
			            		robot.keyPress(KeyEvent.VK_A);
					        }
			            	
			            	if(controller.isButtonPressed(14)){
				            	//pressKey(KeyEvent.VK_W);
			            		
			            		Thread stupidThread = new Thread() {
			            		    public void run(){
			            		    	UserInput.keysDown.add(Keyboard.KEY_W);
			            		    	try {
											Thread.sleep(100);
										} catch (Exception e){
										}
			            		    	UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_W));
			            		    }
			            		};
			            		stupidThread.start();
				            }
			            	
			            	
			            	
			            	if(controller.getXAxisValue()==0){
			            		robot.keyRelease(KeyEvent.VK_D);
			            		robot.keyRelease(KeyEvent.VK_W);
			            		robot.keyRelease(KeyEvent.VK_A);
			            		robot.keyRelease(KeyEvent.VK_S);
					        }
			            	
			            	
			            	
			            }

			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
		    	}
		    }  
		};

		input.start();
	}
	
	public static void pressKey(int key){
		try{
			robot.keyPress(key);
			Thread.sleep(5);
			robot.keyRelease(key);
		//	Thread.sleep(500);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
