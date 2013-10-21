package org.cs347.tasks;
//Created by Chris Rorvig
//Last Edited 10/20/2013

import java.util.ArrayList;

public class User {
	private String name;
	private String password;
	private ArrayList<TaskList> tasks;
	private String question;
	private String answer;
	private long userID;
	
	public User(String username, String pass){
		name = username;
		password = pass;
		tasks = new ArrayList<TaskList>();
		question = "";
		answer = "";
		userID = (long)(Math.random()*1000000);
	}
	
	public User(String username, String pass, ArrayList<TaskList> tasklists, String qs, String answr, long id){
		name = username;
		password = pass;
		tasks = tasklists;
		question = qs;
		answer = answr;
		userID = id;
	}
	
	public void setPassword(String pass){
		password = pass;
	}
	
	public boolean isPassword(String pass){
		return (pass.equals(password));
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
		answer = a;
	}
	
	public boolean isAnswer(String a){
		return (a.equals(answer));
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
	
}