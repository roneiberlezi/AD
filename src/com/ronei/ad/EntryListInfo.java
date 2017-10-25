package com.ronei.ad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntryListInfo {
	private List<Float> entry;
	private float min;
	private float max;
	
	public EntryListInfo(String file){
		entry = new ArrayList<Float>();
		max = 0;
		min = 999;
		
		readFromFile(file, entry);
	}
	
	
	public EntryListInfo(float min, float max, int numberOfEntrys){
		entry = new ArrayList<Float>();
		this.min = min;
		this.max = max;
		
		generateRandomentry(min, max, numberOfEntrys);
	}
	
	private void generateRandomentry(float min, float max, int numberOfEntrys){
		Random random = new Random();
		
		for (int i = 0; i < numberOfEntrys; i++){
			float f = min + random.nextFloat() * (max - min);
			entry.add(f);
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


	public List<Float> getEntry() {
		return entry;
	}

	
}
