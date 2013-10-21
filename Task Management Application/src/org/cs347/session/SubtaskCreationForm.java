package org.cs347.session;
import org.cs347.tasks.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

	public class SubtaskCreationForm extends JFrame {
		private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy, h:mm a");
		private JPanel contentPane;
		private JTextField tfTaskName;
		private Task task;

		/**
		 * Create the frame.
		 */
		public SubtaskCreationForm(final Task parent) {
			setResizable(false);
			task = new Task();
			
			setTitle("Create New Task");
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
			
			tfTaskName = new JTextField();
			panel.add(tfTaskName);
			tfTaskName.setColumns(30);
			
			JLabel lblDueDate = new JLabel("Due Date:");
			panel.add(lblDueDate);
			
			/*JSpinner.DateEditor spinner = new JSpinner.DateEditor(
					new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH)));
			panel.add(spinner);
			*/
			
			final JFormattedTextField ftfDueDate = new JFormattedTextField(sdf);
			ftfDueDate.setText("12-31-2000, 8:30 AM");
			panel.add(ftfDueDate);
			
			JLabel lblTaskPriority = new JLabel("Task Priority");
			panel.add(lblTaskPriority);
			
			String[] boxItems = {"1", "2", "3", "4", "5"};
			final JComboBox comboBox = new JComboBox(boxItems);
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
			txtrDescriptionGoesHere.setText("Description Goes Here");
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 238, 444, 33);
			contentPane.add(panel_2);
			
			JButton btnOk = new JButton("Ok");
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
					parent.addSubTask(task);
					
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
