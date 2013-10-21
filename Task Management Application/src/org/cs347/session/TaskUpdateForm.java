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
import org.cs347.tasks.*;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class TaskUpdateForm extends JFrame {
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy, h:mm a");
	private JPanel contentPane;
	private JTextField tfTaskName;
	private Task task;

	/**
	 * Create the frame.
	 */
	public TaskUpdateForm(Task t) {
		setResizable(false);
		task = t;
		
		setTitle("Update A Task");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 1, 444, 90);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblTaskName = new JLabel("Task Name:");
		panel.add(lblTaskName);
		
		tfTaskName = new JTextField(task.getName());
		panel.add(tfTaskName);
		tfTaskName.setColumns(30);
		
		JLabel lblDueDate = new JLabel("Due Date:");
		panel.add(lblDueDate);
		
		
		final JFormattedTextField ftfDueDate = new JFormattedTextField(sdf);
		ftfDueDate.setText(sdf.format(task.getDueDate()));
		panel.add(ftfDueDate);
		
		JLabel lblTaskPriority = new JLabel("Task Priority");
		panel.add(lblTaskPriority);
		
		String[] boxItems = {"1", "2", "3", "4", "5"};
		final JComboBox comboBox = new JComboBox(boxItems);
		comboBox.setSelectedIndex(task.getPriority() - 1);
		panel.add(comboBox);
		
		JLabel lblDescription = new JLabel("Description:");
		panel.add(lblDescription);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 91, 444, 148);
		contentPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		final JTextArea txtrDescriptionGoesHere = new JTextArea();
		panel_1.add(txtrDescriptionGoesHere);
		txtrDescriptionGoesHere.setBorder(border);
		txtrDescriptionGoesHere.setLineWrap(true);
		txtrDescriptionGoesHere.setText(task.getDescription());
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 238, 444, 33);
		contentPane.add(panel_2);
		
		JButton btnOk = new JButton("Update");
		panel_2.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				task.setName(tfTaskName.getText());
				try {
				task.setDueDate(sdf.parse(ftfDueDate.getText()));
				}
				catch (Exception e) {
					task.setDueDate(new Date());
				}
				task.setDescription(txtrDescriptionGoesHere.getText());
				task.setPriority(comboBox.getSelectedIndex() + 1);
				
				dispose();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

}
