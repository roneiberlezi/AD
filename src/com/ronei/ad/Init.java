package com.ronei.ad;

import java.util.ArrayList;
import java.util.List;

public class Init {
	
	public static void main(String[] args) {
		String entryTimeFile = null;
		List<Team> teams = new ArrayList<Team>();
		EntryTimeListInfo entryTimeListInfo;
		EntryTimeListInfo randomEntryTimeListInfo;
		
		int counter = 0;
		for(String arg: args){
			if (counter == 0)
				entryTimeFile = arg;
			//TODO - if counter == 1 and parameter == -repeat
			//TODO - if counter == 2 and isRepeat then convert to int and set the number of replications
			else
				teams.add(new Team(arg));
			
			counter++;
		}
		
		entryTimeListInfo = new EntryTimeListInfo(entryTimeFile);
		
		for(Team team: teams){
			team.setEntryTimeList(entryTimeListInfo.getEntryTime());
			team.readFromFile(team.getName());
			team.startWorking();
			team.printResults();
		}
		
		
		//TODO - Create a loop to replicate random n times
		System.out.println("============================================= RANDOM ENTRY TIME =============================================");
		randomEntryTimeListInfo = new EntryTimeListInfo(entryTimeListInfo.getMin(), entryTimeListInfo.getMax(), entryTimeListInfo.getEntryTime().size());
		for(Team team: teams){
			team.setEntryTimeList(randomEntryTimeListInfo.getEntryTime());
			team.readFromFile(team.getName());
			team.startWorking();
			team.printResults();
		}
	}

}
