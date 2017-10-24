package com.ronei.ad;

public class Task {
	private float executionTime;
	private float entryTime;
	private float diffTime;
	private boolean done = false;
	private float endTime;
	private float startTime;
	private boolean onQueue = false;
	private boolean executing = false;

	public Task(float executionTime, float diffTime){
		this.executionTime = executionTime;
		this.diffTime = diffTime;
	}
	
	public Task(String executionTime, String diffTime){
		this.executionTime = Utils.convertToFloat(executionTime);
		this.diffTime = Utils.convertToFloat(diffTime);
	}
	
	public Task(String executionTime, float diffTime){
		this.executionTime = Utils.convertToFloat(executionTime);
		this.diffTime = diffTime;
	}
	
	public float getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(float executionTime) {
		this.executionTime = executionTime;
	}
	public float getDiffTime() {
		return diffTime;
	}
	public void setDiffTime(int diffTime) {
		this.diffTime = diffTime;
	}
	
	public float geEntryTime() {
		return entryTime;
	}

	public void setEntryTime(float entryTime) {
		this.entryTime = entryTime;
	}

	
	public float getWaitingTime() {
		return startTime - entryTime;
	}
	
	public float getResponseTime() {
		return this.getWaitingTime() + this.getExecutionTime();
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public float getEndTime() {
		return endTime;
	}

	public void setEndTime(float endTime) {
		this.endTime = endTime;
	}
	
	public float getStartTime() {
		return startTime;
	}

	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}
	
	public boolean isOnQueue() {
		return onQueue;
	}

	public void setOnQueue(boolean onQueue) {
		this.onQueue = onQueue;
	}
	
	public boolean isExecuting() {
		return executing;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

	@Override
	public String toString() {
		return "Task [executionTime=" + executionTime + ", endTime=" + endTime + ", startTime=" + startTime + ", entryTime=" + entryTime + "]\n";
	}

	
}
