package org.cs347.session;
import org.cs347.tasks.*;
public class Session {
	private static User user;
	private static TaskList activeTaskList;
	private static Task currentTask;
	
	public static void setCurrentTask(Task t){
		currentTask = t;
	}
	
	public static Task getCurrentTask(){
		return currentTask;
	}
	
	public static User returnUser(){
		return user;
	}
	
	public static TaskList getActiveTaskList(){
		return activeTaskList;
	}
	
	public static void setActiveTaskList(TaskList t){
		activeTaskList = t;
	}
	
	public static void setUser(User u){
		user = u;
	}
}
