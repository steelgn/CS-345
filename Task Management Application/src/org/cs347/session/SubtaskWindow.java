package org.cs347.session;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.cs347.tasks.*;

import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import java.awt.FlowLayout;

import javax.swing.SwingConstants;

public class SubtaskWindow extends JFrame implements MouseListener {
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy, h:mm a");
	private JPanel contentPane;
	private Task task;
	final DefaultListModel<Task> model = new DefaultListModel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	JList<Task> list;

	/**
	 * Create the frame.
	 */
	public SubtaskWindow(Task tsk) {
		task = tsk;
		
		setResizable(false);
		
		setTitle("Subtasks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(222, 0, 222, 272);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Subtask Name");
		lblNewLabel.setBounds(0, 0, 111, 25);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(111, 0, 111, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Due Date");
		lblNewLabel_1.setBounds(0, 25, 111, 25);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(111, 25, 111, 25);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Priority");
		lblNewLabel_2.setBounds(0, 49, 111, 25);
		panel.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(111, 49, 111, 25);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Description");
		lblNewLabel_3.setBounds(0, 73, 111, 25);
		panel.add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(0, 109, 222, 163);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 222, 272);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		list = new JList<Task>(model);
		for (Task t : task.getSubTasks()){
			model.addElement(t);
		}
		panel_1.add(list);
		list.addMouseListener(this);
		
		String[] boxItems = {"1", "2", "3", "4", "5"};
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	}
	
	void update() {
		if (list.getSelectedValue() != null) {
			Task t = list.getSelectedValue();
			textField.setText(t.getName());
			textField_1.setText(sdf.format(task.getDueDate()));
			textField_2.setText(String.valueOf(t.getPriority()));
			textField_3.setText(t.getDescription());
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == e.BUTTON1) {
			update();
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
