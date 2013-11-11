package org.cs347.session;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
	public static void storeSession() throws FileNotFoundException{
		int taskListID = user.getLists().indexOf(activeTaskList);
		int taskID = activeTaskList.getTaskList().indexOf(getCurrentTask());
		Scanner sc = new Scanner(new File("session.sec"));
		String lines = "";
		while (sc.hasNextLine()){
			String nextLine = sc.nextLine();
			lines = (nextLine.startsWith(user.getName()+",")) ? lines : lines + nextLine;
		}
		sc.close();
		PrintWriter pw = new PrintWriter("session.sec");
		pw.println(lines);
		pw.println(user.getName() +","+ taskListID + "," + taskID);
		pw.close();
		user.updateTaskFile();
	}
	
	public static void loadSession(User u) throws FileNotFoundException{
		setUser(u);
		
		Scanner sc = new Scanner(new File("session.sec"));
		while (sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if (nextLine.startsWith(u.getName()+",")){
				String[] vals = nextLine.split(",");
				if (vals.length>=3)
					if (vals[1].length() == 0) break;
				//setActiveTaskList(user.getLists().get(Integer.parseInt(vals[1])));
				//setCurrentTask(activeTaskList.getTask(Integer.parseInt(vals[2])));
			}
			break;
		}
		sc.close();
		if (activeTaskList == null){
			setActiveTaskList(new TaskList("New Task List",u));
		}
	}
	public static void createFiles() throws IOException{
		File curFile = new File("Users.db");
		if (!curFile.isFile()) curFile.createNewFile();
		curFile = new File("session.sec");
		if (!curFile.isFile()) curFile.createNewFile();
		curFile = new File("Tasklists");
		if (!curFile.isFile()) curFile.createNewFile();
	}
}