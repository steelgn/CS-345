package org.cs347.session;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.cs347.tasks.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class UserUpdateFrame extends JFrame {
	private boolean isClosed = false;
	private JPanel contentPane;
	private JPasswordField pfPword;
	private JTextField tfPassword;
	private JTextField tfQuestion;
	private JTextField tfAnswer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Session.setUser(new User("ale", "happy"));
					UserUpdateFrame frame = new UserUpdateFrame();
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
	public UserUpdateFrame() {
		setTitle("Update Profile");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPword = new JLabel("Old Password:");
		lblPword.setBounds(22, 8, 120, 14);
		panel.add(lblPword);
		
		pfPword = new JPasswordField();
		pfPword.setBounds(130, 5, 86, 20);
		panel.add(pfPword);
		pfPword.setColumns(20);
		
		JLabel lblPassword = new JLabel("New Password:");
		lblPassword.setBounds(22, 33, 120, 14);
		panel.add(lblPassword);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(130, 30, 86, 20);
		panel.add(tfPassword);
		tfPassword.setColumns(20);
		
		JLabel lblQuestion = new JLabel("Secret Question:");
		lblQuestion.setBounds(22, 58, 120, 14);
		panel.add(lblQuestion);
		
		tfQuestion = new JTextField();
		tfQuestion.setBounds(130, 55, 86, 20);
		panel.add(tfQuestion);
		tfQuestion.setColumns(10);
		
		JLabel lblAnswer = new JLabel("Secret Answer");
		lblAnswer.setBounds(22, 86, 120, 14);
		panel.add(lblAnswer);
		
		tfAnswer = new JTextField();
		tfAnswer.setBounds(130, 83, 86, 20);
		panel.add(tfAnswer);
		tfAnswer.setColumns(10);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateUser();
				dispose();
			}
		});
		btnConfirm.setBounds(127, 204, 89, 23);
		panel.add(btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(218, 204, 89, 23);
		panel.add(btnCancel);
	}
	public boolean getWindowClose(){
		return isClosed;
	}
	void updateUser(){
		
		User u = Session.returnUser();
		System.out.println(new String(pfPword.getPassword()));
		if (u.isPassword(new String(pfPword.getPassword()))){
		u.setPassword(tfPassword.getText());
		u.setQuestion(tfQuestion.getText());
		u.setAnswer(tfAnswer.getText());
		try {
			u.updateFile();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		}
		
		}
}
