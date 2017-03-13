package com.capstone.plasma;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class SpriteSheet {
	
	public int[] sprites;
	public int[] imageCycle;
	public int currentSprite=0;
	public int pixelsPerImage=0;
	public String path;

	public SpriteSheet(String path, int[] imageCycle,int pixelsPerImage){
		this.pixelsPerImage=pixelsPerImage;
		this.path=path;
		this.imageCycle=imageCycle;
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		}catch(IOException e) {
			e.printStackTrace();
		}	
		int textSize=(image.getWidth()/pixelsPerImage);
		
		sprites=new int[textSize*textSize];
		
		int textureLocation=0;
		
		
		for(int iy=0;iy<textSize;iy++){
			for(int ix=0;ix<textSize;ix++){

				int[] pixels = new int[textSize * textSize];

				if(iy*textSize>=image.getHeight()||ix*textSize>=image.getWidth()){
					continue;
				}
		        image.getRGB(ix*textSize, iy*textSize, textSize, textSize, pixels, 0, textSize);

		        ByteBuffer buffer = BufferUtils.createByteBuffer(textSize * textSize * 4);
		        
		        for(int x = 0; x <textSize; x++){
		        	  for(int y = 0; y < textSize; y++){  
		                  int pixel = pixels[y * textSize + x];
		                  buffer.put((byte) ((pixel >> 0) & 0xFF));     // Red component
		                  buffer.put((byte) ((pixel >> 0) & 0xFF));      // Green component
		                  buffer.put((byte) (pixel & 0xFF));               // Blue component
		                  buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
		              }
		          }
		
		          buffer.flip();
		          
		          int textureID = glGenTextures(); //Generate texture ID
		          glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
		          
		          //Setup wrap mode
		          glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		          glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		          //Setup texture scaling filtering
		          glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		          glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		          
		          //Send texel data to OpenGL
		          glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, textSize, textSize, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		          
		          textureLocation++;
		          sprites[textureLocation]=textureID;
			}
		}
	}
	
	public void paint(int x,int y,int width,int height){
		GraphicsHandler.drawImage(sprites[currentSprite], x, y, width, height);
	}
	
	public void advanceFrame(){
		
	}
	
}
