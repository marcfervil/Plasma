package com.capstone.plasma;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;



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
				            	pressKey(KeyEvent.VK_J);
				            }
			            
			            	
			            	if(controller.isButtonPressed(9)){
				            	pressKey(KeyEvent.VK_K);
				            }
			            	
			            	if(controller.getXAxisValue()>=0.7){
			            		robot.keyPress(KeyEvent.VK_D);
					        }
			            	

			            	if(controller.getXAxisValue()<=-0.7){	
			            		robot.keyPress(KeyEvent.VK_A);
					        }
			            	
			            	if(controller.isButtonPressed(14)){
				            	pressKey(KeyEvent.VK_W);
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
