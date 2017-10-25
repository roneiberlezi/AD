package com.ronei.ad;

import java.util.ArrayList;
import java.util.List;

public class Init {
	
	public static void main(String[] args) {
		String entryTimeFile = null;
		List<Team> teams = new ArrayList<Team>();
		EntryTimeListInfo entryTimeListInfo;
		EntryTimeListInfo randomEntryTimeListInfo;
		boolean isRepeatable = false;
		int repeat = 1;
		
		int counter = 0;
		for(String arg: args){
			if (counter == 0)
				entryTimeFile = arg;
			else if (counter == 1 && arg.equalsIgnoreCase("--repeat"))
				isRepeatable = true;
			else if (counter == 2 && isRepeatable)
				repeat = Integer.parseInt(arg);
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
		for(int i = 1; i <= repeat; i++){
			System.out.println("============================================= RANDOM ENTRY TIME " + i + " =============================================");
			randomEntryTimeListInfo = new EntryTimeListInfo(entryTimeListInfo.getMin(), entryTimeListInfo.getMax(), entryTimeListInfo.getEntryTime().size());
			for(Team team: teams){
				team.setEntryTimeList(randomEntryTimeListInfo.getEntryTime());
				team.readFromFile(team.getName());
				team.startWorking();
				team.printResults();
			}
		}
	}

}
