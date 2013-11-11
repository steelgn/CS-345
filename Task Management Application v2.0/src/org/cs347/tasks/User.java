package org.cs347.tasks;
//Created by Chris Rorvig
//Last Edited 10/20/2013

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.cs347.session.Login;

import crypto.Hasher;

public class User {
	private String name;
	private String password;
	private ArrayList<TaskList> tasks;
	private String question;
	private String answer;
	private long userID;
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy, h:mm a");
	
	public User(String username, String pass){
		name = username;
		password = Hasher.hashString(pass);
		tasks = new ArrayList<TaskList>();
		question = "";
		answer = "";
		userID = (long)(Math.random()*1000000);
	}
	
	public User(String username, String pass, String qs, String answr, long id, ArrayList<TaskList> tasklists){
		name = username;
		password = pass;
		tasks = tasklists;
		question = qs;
		answer = answr;
		userID = id;
	}
	
	public void setPassword(String pass){
		password = Hasher.hashString(pass);
	}
	
	public boolean isPassword(String pass){
		return (Hasher.hashString(pass).equals(password));
	}
	
	public void setName(String n){
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public String getNameID(){
		return name+userID;
	}
	
	public void setQuestion(String q){
		question = q;
	}
	
	public String getQuestion(){
		return question;
	}
	
	public void setAnswer(String a){
		answer = Hasher.hashString(a);
	}
	
	public boolean isAnswer(String a){
		return (Hasher.hashString(a).equals(answer));
	}
	
	public void addList(TaskList t){
		tasks.add(t);
	}
	
	public void removeList(TaskList t){
		tasks.remove(t);
	}
	
	public void setLists(ArrayList<TaskList> list){
		tasks = list;
	}
	
	public ArrayList<TaskList> getLists(){
		return tasks;
	}
	
	public static User loadUser(String username)throws IOException {
		File file = new File("Users.db");
		if (!file.isFile()){
			file.createNewFile();
		}
		Scanner sc = new Scanner(new File("Users.db"));
		User u = null;
		while (sc.hasNextLine()){
			String currentLine = sc.nextLine();
			if (currentLine.startsWith(username + ",")){
				String[] fields = currentLine.split(",");
				u = new User(fields[0], fields[1], fields[2], fields[3], Long.parseLong(fields[4]), new ArrayList<TaskList>());
			}
		}
		sc.close();
		try {
			u.setLists(listsFromFile(u));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(u.getLists().size());
		if (u == null){
			u = new User("","");
			int create = JOptionPane.showConfirmDialog(null, "User not found. Create new user?", "User not found",
					JOptionPane.YES_NO_OPTION);
			if (create == 1){
				while(Login.createUser());
			}
		}
		
		return u;
	}
	
	public void updateFile() throws IOException{
		File db = new File("Users.db");
		if (!db.isFile()){
			db.createNewFile();
		}
		Scanner sc = new Scanner(db);
		ArrayList<String> strList = new ArrayList<String>();
		while (sc.hasNextLine()){
			String s = sc.nextLine();
			if (!s.startsWith(name+ ",")){
				strList.add(s);
			}
		}
		strList.add(name+","+password+","+question+","+answer+","+userID);
		sc.close();
		PrintWriter pw = new PrintWriter(db);
		for (String s : strList){
			pw.println(s);
		}
		pw.close();
	}
	
	public boolean nameInUse(String username) throws FileNotFoundException{
		Scanner sc = new Scanner(new File("Users.db"));
		while (sc.hasNextLine()){
			String currentLine = sc.nextLine();
			if (currentLine.startsWith(username + ",")){
				sc.close();
				return true;
			}
		}
		sc.close();
		return false;
	}
	public String listsToOutputString(){
		String output = "";
		for (TaskList t: tasks){
			output = output + t.toOutputString();
		}
		return output;
	}
	public void updateTaskFile() throws FileNotFoundException{
		File taskLists = new File("Tasklists");
		Scanner scan = new Scanner(taskLists);
		String output = "";
		boolean write = true;
		while (scan.hasNextLine()){
			String nextLine = scan.nextLine();
			if (nextLine.startsWith("User,"+name+",")){
				write = false;
				nextLine = scan.nextLine();
			}
			if (nextLine.startsWith("User,")&& !nextLine.startsWith("User,"+name+",")){
				write = true;
			}
			output = (write) ? output+nextLine: output;
		}
		scan.close();
		PrintWriter pw = new PrintWriter(taskLists);
		pw.println(output);
		pw.println(listsToOutputString());
		pw.close();
	}
	public static Object[] fromFileString(String str) throws ParseException{
		Task t = new Task();
		int count = 0;
		String[] values = str.split(",,");
			if (values.length > 0){
				if (values.length < 6){
					System.out.println("Error, invalid data type");
					return null;
				}
				count = values[0].length() - values[0].replaceAll("\t", "").length();
				t.setName(values[1]);
				t.setCompleted(Boolean.parseBoolean(values[2]));
				t.setDueDate(sdf.parse(values[3]));
				t.setPriority(Integer.parseInt(values[4]));
				t.setDescription(values[5]);
			}
		Object[] objs = {t, count};
		return objs;
	}	
	public static ArrayList<Task> createListFromFile(String section) throws IOException, ParseException{
		Scanner sc = new Scanner(section);
		ArrayList<Object[]> tasks= new ArrayList<Object[]>();
		while (sc.hasNextLine()){
			String readThis = sc.nextLine();
			tasks.add(fromFileString(readThis));
		}
		sc.close();
		int size = tasks.size();
		ArrayList<Task> taskList = new ArrayList<Task>();
		for (int i = size-1; i>=0; i--){
			int level = (int)tasks.get(i)[1];
			if (level == 0){
				taskList.add((Task)tasks.get(i)[0]);
			}
			else {
				for (int j = i-1; j>=0; j--){
					if ((int)tasks.get(j)[1] == level-1){
						Task t =(Task)tasks.get(j)[0];				
						t.addSubTask((Task)tasks.get(i)[0]);
						Object[] newRow = {t, (int)tasks.get(j)[1]};
						tasks.set(j, newRow);
						break;
					}
				}
			}
		}
		return taskList;
	}
	
	static String toOutputString(TaskList tl){
		String output = "User,"+tl.getUsers().get(0)+","+tl.getName()+"\n";
		for (Task t: tl.getTaskList()){
			output = output + t.toFileString();
		}
		return output;
	}
	
	public static ArrayList<TaskList> listsFromFile(User u) throws IOException, ParseException{
		File file = new File("Tasklists");
		Scanner sc = new Scanner(file);
		boolean discardData = true;
		String currentTLHeader = "";
		String lines = "";
		String next = "";
		TaskList activeTasks;
		ArrayList<Task> tlTasks = new ArrayList<Task>();
		ArrayList<TaskList> lists = new ArrayList<TaskList>();
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if (nextLine.startsWith("User,"+u.getName()+",")){
				System.out.println("data found");
				discardData = false;
				currentTLHeader = nextLine;
			}
			while(!discardData){
				if ((next=sc.nextLine()).startsWith("User,") || !sc.hasNextLine()){
					break;
				}
				lines = lines + next;
			}
			if (!discardData){
				tlTasks = createListFromFile(lines);
				String[] split = currentTLHeader.split(",");
				activeTasks = new TaskList(split[2], u, tlTasks);
				lists.add(activeTasks);
				discardData = true;
			}
		}
		sc.close();
		
		for (TaskList tl : lists){
			System.out.println(tl.getName());
		}
		return lists;
	}
}