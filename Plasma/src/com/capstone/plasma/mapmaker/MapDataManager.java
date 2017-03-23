package com.capstone.plasma.mapmaker;


import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class MapDataManager {
	
	
	public static void saveMap(String name,Map m){
		try{
		     Gson gson = new Gson();
			 PrintWriter writer = new PrintWriter(name, "UTF-8");
			 writer.print(gson.toJson(m));
			 writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Map loadMap(String name){
		Map m = null;
		try{
			byte[] encoded = Files.readAllBytes(Paths.get(name));
			Gson gson = new Gson();
			m = gson.fromJson(new String(encoded, StandardCharsets.UTF_8), Map.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
	
}
