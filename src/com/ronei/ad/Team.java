package com.ronei.ad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Team {
	private String name;
	private float timeLine;
	private boolean isIdle;
	private int maxNumberOfWaitingTasks;

	private int minNumberOfWaitingTasks;
	private int totalNumberOfWaitingTasks;
	
	private Task currentTask;
	private List<Task> taskQueue;
	private List<Task> executedTasks;
	private List<Task> taskBuffer;
	private List<Float> entryTimeList = null;
	
	public Team(String name){
		this.name = name;
	}
	
	private void init(){
		executedTasks = new ArrayList<Task>();
		taskQueue = new ArrayList<Task>();
		taskBuffer = new ArrayList<Task>();
		timeLine = 0;
		currentTask = null;
		isIdle = true;
		maxNumberOfWaitingTasks = 0;
		minNumberOfWaitingTasks = 0;
		totalNumberOfWaitingTasks = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumberOfTasks(){
		return executedTasks.size();
	}
	
	public float getSpentTime(){
		float timeSpent = 0;
		for(Task t: executedTasks){
			timeSpent += t.getExecutionTime();
		}
		
		return timeSpent;
	}
	
	public void setEntryTimeList(List<Float> timeList){
		entryTimeList = new ArrayList<Float>(timeList);
	}
	
	public void readFromFile(String durationFile) {
		//TODO - if entryTimeList is empty, throw an exception to initialize variable
		
		//Make sure everything is clear to start processing again
		init();
		
		String durationLine = null;
		
		try {
			FileReader durationFileReader = new FileReader(durationFile);
			BufferedReader durationBufferedReader = new BufferedReader(durationFileReader);
			
			Iterator<Float> entryTimeIterator = entryTimeList.iterator();
			float entryTime; 
					
			Task task;
			float timeLine = 0;
			while((durationLine = durationBufferedReader.readLine()) != null && entryTimeIterator.hasNext()) {
	            entryTime = entryTimeIterator.next();
	            
				task = new Task(durationLine, entryTime);
	            timeLine += task.getDiffTime();
	            task.setEntryTime(timeLine);
	            
	            //TODO - Make a TaskListInfo, similar to the EntryTimeListInfo
	            taskBuffer.add(task);
	        }
			
			durationBufferedReader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startWorking(){
		//Try to execute all tasks inside taskBuffer
		for(Task task: taskBuffer){
		   this.taskEntry(task);
		   totalNumberOfWaitingTasks += taskQueue.size();
		}
		//Execute the last task
		endTask(currentTask);
		
		//Execute pending tasks
		Iterator<Task> i = taskQueue.iterator();
		while (i.hasNext()) {
		   Task task = i.next();
		   i.remove();
		   startTask(task);
		   endTask(task);
		   totalNumberOfWaitingTasks += taskQueue.size();
		}
	}
	
	
	public void queueTask(Task task){
		task.setOnQueue(true);
		this.taskQueue.add(task);
	}
	
	public void taskEntry(Task task){
		
		if (currentTask != null){
			if (currentTask.getStartTime() + currentTask.getExecutionTime() <= task.geEntryTime()){
				endTask(currentTask);
				
				if (!taskQueue.isEmpty())
					this.queueTask(task);
			}
		}
		
		if (isIdle){
			if (!taskQueue.isEmpty()){
				this.startTask(taskQueue.get(0));
				taskQueue.remove(0);
			}else{
				this.startTask(task);
			}
		}
        else
        	this.queueTask(task);
	}
	
	public void startTask(Task task){		
		if (taskQueue.size() > maxNumberOfWaitingTasks){
			maxNumberOfWaitingTasks = taskQueue.size();
		}
		
		if (timeLine < task.geEntryTime())
			this.setTime(task.geEntryTime());
		
		currentTask = task;
		currentTask.setExecuting(true);
		currentTask.setStartTime(timeLine);
		isIdle = false;
	}
	
	public void endTask(Task task){
		currentTask = null;
		task.setDone(true);
		task.setExecuting(false);
		task.setEndTime(task.getStartTime() + task.getExecutionTime());
		isIdle = true;
		
		executedTasks.add(task);
		
		this.increaseTimeBy(task.getExecutionTime());
	}
	
	private void increaseTimeBy(float time){
		timeLine += time;
	}
	
	private void setTime(float time){
		timeLine = time;
	}
	
	public float getAverageNumberOfWaitingTasks(){
		return (float) totalNumberOfWaitingTasks/executedTasks.size();
	}
	
	public float getMinimumWaitingTime(){
		float min = 9999;
		for(Task task: executedTasks)
			if (task.getWaitingTime() > 0 && task.getWaitingTime() < min)
				min = task.getWaitingTime();
		
		if (min == 9999) return 0;
		else return min;
	}
	
	public float getMaximumWaitingTime(){
		float max = 0;
		for(Task task: executedTasks)
			if (task.getWaitingTime() > 0 && task.getWaitingTime() > max)
				max = task.getWaitingTime();
		
		return max;
	}
	
	public float getAverageWaitingTimeOnQueue(){
		int numberOfQueuedTasks = 0;
		int totalWaitingTime = 0;
		for(Task task: executedTasks){
			if(task.getWaitingTime() > 0){
				numberOfQueuedTasks++;
				totalWaitingTime += task.getWaitingTime();
			}
		}
		
		if (numberOfQueuedTasks > 0)
			return totalWaitingTime/numberOfQueuedTasks;
		else
			return 0;
	}
	
	public int getMaxNumberOfWaitingTasks() {
		return maxNumberOfWaitingTasks;
	}

	public int getMinNumberOfWaitingTasks() {
		return minNumberOfWaitingTasks;
	}
	
	public float getAverageExecutionTime(){
		int totalExecutionTime = 0;
		for(Task task: executedTasks){
			totalExecutionTime += task.getExecutionTime();
		}
		
		return totalExecutionTime/executedTasks.size();
	}
	
	public float getMinimuExecutionTime(){
		float min = executedTasks.get(0).getExecutionTime();
		for(Task task: executedTasks){
			if (task.getExecutionTime() < min)
				min = task.getExecutionTime();
		}
		
		return min;
	}
	
	public float getMaximuExecutionTime(){
		float max = 0;
		for(Task task: executedTasks){
			if (task.getExecutionTime() > max)
				max = task.getExecutionTime();
		}
		
		return max;
	}
	
	public float getAverageResponseTime(){
		float totalWaitingAndExecutionTime = 0;
		
		for(Task task: executedTasks){
			totalWaitingAndExecutionTime += task.getResponseTime();
		}
		
		return totalWaitingAndExecutionTime/executedTasks.size();
	}
	
	public float getMinimumResponseTime(){
		float min = executedTasks.get(0).getResponseTime();
		for(Task task: executedTasks){
			if (task.getResponseTime() < min)
				min = task.getResponseTime();
		}
		
		return min;
	}
	
	public float getMaximumResponseTime(){
		float max = executedTasks.get(0).getResponseTime();
		for(Task task: executedTasks){
			if (task.getResponseTime() > max)
				max = task.getResponseTime();
		}
		
		return max;
	}
	
	 
	
	public void printResults(){
		System.out.println("=============== Results " + this.getName());
		
		System.out.format("  Spent time: %.2f\n", this.getSpentTime());
		System.out.println("  QUEUE TIME");
		System.out.format("Average:  %.2f\n", this.getAverageWaitingTimeOnQueue());
		System.out.format("Minimum:  %.2f\n", this.getMinimumWaitingTime());
		System.out.format("Maximium: %.2f\n", this.getMaximumWaitingTime());
		System.out.println("  WAITING TASKS");
		System.out.format("Average:  %.2f\n", this.getAverageNumberOfWaitingTasks());
		System.out.format("Minimum:  %.2f\n", (float) this.getMinNumberOfWaitingTasks());
		System.out.format("Maximum:  %.2f\n", (float) this.getMaxNumberOfWaitingTasks());
		System.out.println("  EXECUTION TIME");
		System.out.format("Average:  %.2f\n", this.getAverageExecutionTime());
		System.out.format("Minimum:  %.2f\n", this.getMinimuExecutionTime());
		System.out.format("Maximum:  %.2f\n", this.getMaximuExecutionTime());
		System.out.println("  RESPONSE TIME");
		System.out.format("Average:  %.2f\n", this.getAverageResponseTime());
		System.out.format("Minimum:  %.2f\n", this.getMinimumResponseTime());
		System.out.format("Maximum:  %.2f\n", this.getMaximumResponseTime());
		System.out.println("\n");
	}

	@Override
	public String toString() {
		return executedTasks.toString();
	}
	
	
	
}
