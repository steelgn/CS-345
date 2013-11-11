package org.cs347.tasks;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Task {
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy, h:mm a");
	private boolean status;
	private Date dueDate;	
	private String name;
	private String desc;
	private LinkedList<Task> subTasks;
	private int priority;
	
	public Task(){
		name = "";
		desc = "";
		subTasks = new LinkedList<Task>();
		status = false;
		dueDate = new Date();
		priority = 0;
	}
	
	public Task(String title, String dsc, Date due, int pri){
		name = title;
		desc = dsc;
		dueDate = due;
		subTasks = new LinkedList<Task>();
		status = false;
		priority = pri;
	}
	
	public Task(String title, boolean st, Date due, int pri, String dsc, LinkedList<Task> subs){
		name = title;
		desc = dsc;
		subTasks = subs;
		status = st;
		dueDate = due;
		priority = pri;
	}
	
	public boolean isCompleted(){
		return status;
	}
	
	public void setCompleted(boolean state){
		status = state;
		for (Task t : subTasks) t.setCompleted(state);
	}
	
	public int getPriority(){
		return priority;
	}
	
	public void setPriority(int pri){
		priority = pri;
	}
	
	public Date getDueDate(){
		return dueDate;
	}
	
	public void setDueDate(Date due){
		dueDate = due;
	}
	
	public void addSubTask(Task t) {
		subTasks.add(t);
	}
	
	public void removeSubTask(Task t) {
		subTasks.remove(t);
	}
	
	public boolean hasSubTask(){
		return (subTasks.size() > 0);
	}
	
	public LinkedList<Task> getSubTasks(){
		return subTasks;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public void setDescription(String s){
		desc = s;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	
	public int compareTo(Task t){
		return name.compareTo(t.getName());
	}
	
	public int comparePriority(Task t){
		return (priority > t.getPriority()) ? 1: 
			(priority == t.getPriority()) ? 0: -1;
	}
	
	public int compareDueDate(Task t){
		return dueDate.compareTo(t.getDueDate());
	}
	
	public String toXML(){
		String xml = "<Task><Name>" + name + "</Name>" +
					"<DueDate>" + dueDate.toString() + "</DueDate>" +
					"<Priority>" + priority + "</Priority>" +
					"<Description>" + desc + "</Description>" + 
					"<SubTasks>";
		for (Task t : subTasks) {
			xml = xml + t.toXML();
		}
		xml = xml + "</SubTasks></Task>";
		return xml;
	}
	public String toFileString(){
		return toFileString("");
	}
	public String toFileString(String pre){
		String prefix = pre;
		String out = prefix + "Task,," + name + ",," + status + ",," +sdf.format(dueDate) + ",," + priority + ",,"
				+ desc+ "\n";
		for (Task t : subTasks){
			out = out + t.toFileString("\t"+prefix);
		}
		return out;
	}
	
	public String toString(){
		return (getName() + ((status) ? " (Completed)" : ""));
	}
}	