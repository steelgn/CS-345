package org.cs347.session;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cs347.tasks.User;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserRecoveryFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfNewPassword;
	private JPasswordField pfAnswer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException{
		Session.createFiles();
		Session.setUser(new User("ale", "alan"));
		Session.returnUser().setQuestion("What is the airspeed velocity of an unladen Swallow?");
		Session.returnUser().setAnswer("42");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRecoveryFrame frame = new UserRecoveryFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserRecoveryFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblQuestion = new JLabel(Session.returnUser().getQuestion());
		panel.add(lblQuestion);
		
		pfAnswer = new JPasswordField();
		panel.add(pfAnswer);
		
		JLabel lblNewPassword = new JLabel("New Password:");
		panel.add(lblNewPassword);
		
		tfNewPassword = new JTextField();
		panel.add(tfNewPassword);
		tfNewPassword.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Session.returnUser().isAnswer(new String(pfAnswer.getPassword()))){
				Session.returnUser().setPassword(tfNewPassword.getText());
				try{
				Session.returnUser().updateFile();
				}
				catch(IOException e){
					e.printStackTrace();
				}
				}
				dispose();
			}
		});
		panel_1.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_1.add(btnCancel);
	}
	

}
