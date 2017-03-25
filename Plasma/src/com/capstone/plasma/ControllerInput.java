package com.capstone.plasma;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import net.java.games.input.Rumbler;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;


import com.capstone.plasma.mapmaker.MapInput;
import com.capstone.plasma.mapmaker.MapMaker;
import com.capstone.plasma.mapmaker.MapScreen;



public class ControllerInput {
	
	public static Controller controller;
	public static  Robot robot;
	public static  Rumbler[] rumblers;
	//it's the thing -1 for the button #
	public static boolean isConnected=false;
	
	public static int mapX=450;
	public static int mapY=350;
	
	public static int rightCount=0;
	
	public static boolean isControllerConnected(){
		try {
			Controllers.create();
			for(int i=0;i<Controllers.getControllerCount();i++){				
				if(Controllers.getController(i).getName().equals("PLAYSTATION(R)3 Controller")){
					
					
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static void init(){
		System.out.println("Initializing controller");
		try{
			isConnected=true;
			Controllers.create();
			controller=Controllers.getController(0);
			robot = new Robot();
			readInput();			
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
		
			       //     System.out.println(controller.getRZAxisValue()+","+controller.getRYAxisValue()+","+controller.getRXAxisValue()+","+controller.getPovY()+","+controller.getPovX()+",");
			      //      net.java.games.input.Controller target = null;
			        //    rumblers = target.getRumblers();
			          //  System.out.println(UserInput.keysDown);

		            	if(controller.isButtonPressed(3)){
		            		GameScreen.gameMode=0;
		            		GameScreen.stopAll();
		            	}
            	
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
			            
			            if(GameScreen.gameMode==2){
			            	if(controller.getXAxisValue()>=0.7){
			            		mapX+=4;
					        }
			            	if(controller.getXAxisValue()<=-0.7){
			            		mapX-=4;
					        }
			            	if(controller.getYAxisValue()>=0.7){
			            		mapY-=4;
					        }
			            	if(controller.getYAxisValue()<=-0.7){
			            		mapY+=4;
					        }
			            	
			         
			            	
			            	if(controller.getRZAxisValue()>=0.7){
			            		MapScreen.moveCam(0, MapInput.scrollSpeed);
			            		
					        }
			            	if(controller.getRZAxisValue()<=-0.7){
			            		MapScreen.moveCam(0, -MapInput.scrollSpeed);
					        }
			            	if(controller.getAxisValue(2)>=0.7){
			            		MapScreen.moveCam(MapInput.scrollSpeed, 0);
					        
					        }
			            	if(controller.getAxisValue(2)<=-0.7){
			            		MapScreen.moveCam(-MapInput.scrollSpeed, 0);
					        
					        }
			            	
			            	if(controller.isButtonPressed(0)){
			            		if(rightCount==0){
			            		
			            			MapMaker.testMap();
			            			rightCount=300;
			            		}
			            	}
			            	
			            	if(controller.isButtonPressed(11)){
			            		if(rightCount==0){
			            			MapInput.active++;
			            			rightCount=15;
			            		}
			            	}
			            	
			            	if(controller.isButtonPressed(10)){
			            		if(rightCount==0){
			            			MapInput.active--;
			            			rightCount=15;
			            		}
			            	}
			            		
			            	if(controller.isButtonPressed(14)){
				            	//	System.out.println("ff");
				            		MapMaker.placeBlock();
				            	//	Thread.sleep(400);
				            	}
			            	
			            	if(controller.isButtonPressed(13)){
			            	//	System.out.println("ff");
			            		MapMaker.removeTile();
			            	//	Thread.sleep(400);
			            	}
			            	if(rightCount>0)rightCount--;
			            }
			            
			            
			            if(GameScreen.gameMode==1){
			            	if(controller.isButtonPressed(9)){
				            	//pressKey(KeyEvent.VK_J);
			            		UserInput.keysDown.add(Keyboard.KEY_J);
	            		    	try {
									Thread.sleep(100);
								} catch (Exception e){
								}
	            		    	UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_J));
				            }
			            
			            	
			            	if(controller.isButtonPressed(11)){
			            		UserInput.keysDown.add(Keyboard.KEY_K);
	            		    	try {
									Thread.sleep(100);
								} catch (Exception e){
								}
	            		    	UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_K));
				            }
			         
			            	
			            	if(controller.isButtonPressed(7)){
			            		robot.keyPress(KeyEvent.VK_A);
			            	}
			            	
			            	if(controller.isButtonPressed(5)){
			            		robot.keyPress(KeyEvent.VK_D);
			            		//System.out.println("fffaaa");
			            	}
			            	
			            	
			            	if(controller.getXAxisValue()>=0.7){
			            		robot.keyPress(KeyEvent.VK_D);
			            		//System.out.println("fff");
					        }
			            	

			            	if(controller.getXAxisValue()<=-0.7){	
			            		robot.keyPress(KeyEvent.VK_A);
			            	//	if(UserInput.keysDown.indexOf(Keyboard.KEY_A)==-1){
			            	//		UserInput.keysDown.add(Keyboard.KEY_A);
			            	//	}
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
			            	
			            	
			            	
			            	if(controller.getXAxisValue()>=-0.5 && controller.getXAxisValue()<=0.5){
			            	//if(controller.getXAxisValue()==0){
			            		//System.out.println("CLEAR");
			            		
			            		robot.keyRelease(KeyEvent.VK_D);
			            		robot.keyRelease(KeyEvent.VK_W);
			            		robot.keyRelease(KeyEvent.VK_A);
			            		robot.keyRelease(KeyEvent.VK_S);
			            		
			            		//if(UserInput.keysDown.indexOf(Keyboard.KEY_D)!=-1)UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_D));
			            		//if(UserInput.keysDown.indexOf(Keyboard.KEY_A)!=-1)UserInput.keysDown.remove(UserInput.keysDown.indexOf(Keyboard.KEY_A));
					        }
			            	
			            //	controller.rum
			            	
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
