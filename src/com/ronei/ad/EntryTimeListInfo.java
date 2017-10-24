package com.ronei.ad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntryTimeListInfo {
	private List<Float> entryTime;
	private float min;
	private float max;
	
	public EntryTimeListInfo(String file){
		entryTime = new ArrayList<Float>();
		max = 0;
		min = 999;
		
		readFromFile(file, entryTime);
	}
	
	
	public EntryTimeListInfo(float min, float max, int numberOfEntrys){
		entryTime = new ArrayList<Float>();
		this.min = min;
		this.max = max;
		
		generateRandomEntryTime(min, max, numberOfEntrys);
	}
	
	private void generateRandomEntryTime(float min, float max, int numberOfEntrys){
		Random random = new Random();
		
		for (int i = 0; i < numberOfEntrys; i++){
			float f = min + random.nextFloat() * (max - min);
			entryTime.add(f);
		}
	}
	
	
	private void readFromFile(String file, List<Float> dest){		
		try {
			String entryLine = null;
			float number;
			
			FileReader timeFileReader = new FileReader(file);
			BufferedReader timeBufferedReader = new BufferedReader(timeFileReader);
			
			while((entryLine = timeBufferedReader.readLine()) != null) {
				number = Utils.convertToFloat(entryLine);
	            dest.add(number);
	            
	            if (number < min)
	            	min = number;
	            if (number > max)
	            	max = number;
	        }
			
			timeBufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public float getMin() {
		return min;
	}


	public float getMax() {
		return max;
	}


	public List<Float> getEntryTime() {
		return entryTime;
	}

	
}
