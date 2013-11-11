package org.cs347.tasks;
import java.util.*;

public class TaskList {
	public static final int TASK_NAME = 0;
	public static final int TASK_PRIORITY = 1;
	public static final int TASK_DUEDATE = 2;

	private String name;
	private ArrayList<Task> taskList;
	private ArrayList<String> userList;
	private boolean complete;

	
	public TaskList(String title, User creator){
		name = title;
		taskList = new ArrayList<Task>();
		userList = new ArrayList<String>();
		userList.add(creator.getName());
		complete = false;
	}
	public TaskList(String title, User creator, ArrayList<Task> tasks){
		name = title;
		complete = false;
		userList = new ArrayList<String>();
		userList.add(creator.getName());
		taskList = tasks;
	}
	

	public void addTask(Task t){
		taskList.add(t);
	}
	
	public void removeTask(Task t){
		taskList.remove(t);
	}
	
	public Task getTask(int i){
		return taskList.get(i);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	public boolean getComplete(){
		return complete;
	}
	public void addUser(User u){
		if (!hasPermission(u)) userList.add(u.getNameID());
	}
	
	public ArrayList<String> getUsers(){
		return userList;
	}
	
	public boolean hasPermission(User u){
		return userList.contains(u.getNameID());
	}
	
	public void removeUser(User u){
		if (hasPermission(u)) userList.remove(u.getNameID());
	}
	
	public void markComplete(){
		complete = true;
		for (Task t : taskList){
			t.setCompleted(true);
		}
	}
	
	public void sortTasks(int code){
		switch (code) {
		case TASK_NAME: {
			Collections.sort(taskList, new Comparator<Task>() { 
				public int compare(Task a, Task b) {
					return a.compareTo(b);
				} });
			break;
		}
		case TASK_PRIORITY: {
			Collections.sort(taskList, new Comparator<Task>() { 
				public int compare(Task a, Task b) {
					return a.comparePriority(b);
				} });
			break;
		}
		case TASK_DUEDATE: {
			Collections.sort(taskList, new Comparator<Task>() { 
				public int compare(Task a, Task b) {
					return a.compareDueDate(b);
				} });
			break;
		}
		default:
		}
	}
	
	public String toString(){
		return name;
	}
	public ArrayList<Task> getTaskList(){
		return taskList;
	}
	public String toOutputString(){
		String output = "User,"+this.getUsers().get(0)+","+this.getName()+"\n";
		for (Task t: this.getTaskList()){
			output = output + t.toFileString();
		}
		return output;
	}
}