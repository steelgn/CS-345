package org.cs347.tasks;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class UserTester {
	
	
	
	
	public static void main(String[] args)throws IOException, ParseException {
		
		User w = new User("abcs","123124124");
		w.updateFile();
		User u = User.loadUser("username");
		u.setAnswer("happy");
		u.updateFile();
		org.cs347.session.Login login = new org.cs347.session.Login();
		//login.createUser();
		login.resetPassword("username", "happy", "pw2");
		System.out.println("Validate false 1: "+ login.validateLogin("username", "password"));
		System.out.println("Validate true 1:"+ login.validateLogin("username", "pw2"));
		File taskFile = new File("Tasklists");
		if (!taskFile.isFile()) taskFile.createNewFile();
		System.out.println(u.isPassword("password"));
		u.setPassword("newP");
		System.out.println(u.isPassword("newP"));
		u.updateFile();
		
		TaskList tl = new TaskList("tl1", u);
		
		Task ntask = new Task("taskName", "hi", new Date(), 2);
		Task subA = new Task("SubA", "hi", new Date(), 2);
		Task subsubA = new Task("subsubA", "hi", new Date(), 2);
		Task subB = new Task("SubB", "hi", new Date(), 2);
		Task subC = new Task("SubSub", "hi", new Date(), 2);
		Task subsubsubC = new Task("subsubsubC", "hi", new Date(), 2);
		subC.addSubTask(subsubsubC);
		subA.addSubTask(subsubA);
		subB.addSubTask(subC);
		ntask.addSubTask(subA);
		ntask.addSubTask(subB);
		tl.addTask(ntask);
		tl.addTask(new Task());
		tl.addTask(new Task("name1", "hi", new Date(), 3));
		tl.addTask(new Task("name2", "hfs", new Date(), 1));
		
		String out = ntask.toFileString();
		
		
		PrintWriter pw = new PrintWriter(taskFile);
		pw.print(out);
		pw.close();
		
		Scanner sc = new Scanner(taskFile);
		String section = "";
		while (sc.hasNextLine()){
			section = section + sc.nextLine()+"\n";
		}
		sc.close();
		//ArrayList<Task> taskList = createListFromFile(section);
		//for (Task t: taskList){
		//	System.out.println("Task: " + t.toFileString());
		//}
		
	
	}
}
