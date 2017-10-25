package com.ronei.ad;

import java.util.ArrayList;
import java.util.List;

public class Init {
	
	public static void main(String[] args) {
		String entryTimeFile = null;
		List<Team> teams = new ArrayList<Team>();
		EntryListInfo entryTimeListInfo;
		EntryListInfo randomEntryTimeListInfo;
		EntryListInfo randomEntryTaskListInfo;
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
		
		entryTimeListInfo = new EntryListInfo(entryTimeFile);
		
		for(Team team: teams){
			team.setEntryTimeList(entryTimeListInfo);
			team.readEntryTaskFile(team.getName());
			team.populateTaskBuffer(team.getEntryTaskList());
			team.startWorking();
			team.printResults();
		}
		
		
		for(int i = 1; i <= repeat; i++){
			System.out.println("============================================= RANDOM ENTRY TIME " + i + " =============================================");
			randomEntryTimeListInfo = new EntryListInfo(entryTimeListInfo.getMin(), entryTimeListInfo.getMax(), entryTimeListInfo.getEntry().size());
			for(Team team: teams){
				team.setEntryTimeList(randomEntryTimeListInfo);
				team.readEntryTaskFile(team.getName());
				team.populateTaskBuffer(team.getEntryTaskList());
				team.startWorking();
				team.printResults();
			}
		}
		
		for(int i = 1; i <= repeat; i++){
			System.out.println("============================================= RANDOM ENTRY TASK " + i + " =============================================");
			for(Team team: teams){
				randomEntryTaskListInfo = new EntryListInfo(team.getEntryTaskList().getMin(), team.getEntryTaskList().getMax(), team.getEntryTaskList().getEntry().size());
				
				team.setEntryTimeList(entryTimeListInfo);
				team.readEntryTaskFile(team.getName());
				team.populateTaskBuffer(randomEntryTaskListInfo);
				team.startWorking();
				team.printResults();
			}
		}
	}

}
