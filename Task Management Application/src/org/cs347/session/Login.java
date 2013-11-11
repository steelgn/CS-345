package org.cs347.session;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.cs347.tasks.User;

public class Login {
	public static boolean validateLogin(String username, String password) throws IOException{
		User u = User.loadUser(username);
		return u.isPassword(password);
	}
	
	public void resetPassword(String username, String secretAnswer, String newPassword) throws IOException{
		User u = User.loadUser(username);
		if (u.isAnswer(secretAnswer)){
			u.setPassword(newPassword);
			JOptionPane.showMessageDialog(null, "Password Succesfully Changed");
			u.updateFile();
		}
	}
	
	public static void login(String username) throws IOException{
		User u = User.loadUser(username);
		
		Session.loadSession(u);
		System.out.println(Session.returnUser().getName());
		//setPanel(mainwindow);
	}

	public static boolean createUser(){
		UserCreationFrame ucf = new UserCreationFrame();
		ucf.setVisible(true);
		return true;
	}
}
