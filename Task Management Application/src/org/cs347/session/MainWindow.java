package org.cs347.session;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JLabel;

import org.cs347.tasks.Task;
import org.cs347.tasks.TaskList;
import org.cs347.tasks.User;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class MainWindow implements MouseListener{
	final DefaultListModel<TaskList> model = new DefaultListModel<TaskList>();
	final DefaultListModel<Task> taskModel = new DefaultListModel<Task>();
	private JFrame frmTaskManagementApp;
	JMenuBar menuBar;
	private JPanel wrapperPanel;
	private JLabel lblList, lblDueDate, lblTaskName, lblStatus;
	private JTextArea textArea;
	public JList<TaskList> taskLists;
	public JList<Task> tasks;
	private int listIndex = -1;
	boolean taskListsEnabled, tasksEnabled;
	JMenuItem mntmNewTaskList, mntmRenameSelectedList, mntmDeleteSelectedList;
	JMenuItem mntmCreateNewTask, mntmEditSelected, mntmDeleteSelected, mntmViewSubtasks, mntmAddSubtask;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Session.createFiles();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmTaskManagementApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmTaskManagementApp = new JFrame();
		frmTaskManagementApp.setTitle("Task Management App");
		frmTaskManagementApp.setBounds(100, 100, 800, 600);
		frmTaskManagementApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		wrapperPanel = new JPanel();
		wrapperPanel.setLayout(new BorderLayout(0, 0));
		frmTaskManagementApp.getContentPane().add(loginWindow());
		frmTaskManagementApp.setJMenuBar(menuBar);
	
	}
	
	private void createChangePanel(JPanel p){
		frmTaskManagementApp.getContentPane().removeAll();
		frmTaskManagementApp.getContentPane().add(p);
		frmTaskManagementApp.setJMenuBar(menuBar);
		frmTaskManagementApp.validate();
		frmTaskManagementApp.setVisible(true);
		
	}
	
	public JPanel loginWindow(){
		JPanel parentPanel = new JPanel(new BorderLayout(0, 0));
		JPanel entryPanel = new JPanel();
		parentPanel.add(entryPanel, BorderLayout.NORTH);
		entryPanel.setLayout(new GridLayout(2, 2, 1, 0));
		
		entryPanel.add(new JLabel("Username: "));
		final JTextField userNameField = new JTextField();
		entryPanel.add(userNameField);
		entryPanel.add(new JLabel("Password: "));
		final JPasswordField passwordField = new JPasswordField();
		entryPanel.add(passwordField);
		
		JPanel buttonSection = new JPanel(new GridLayout(2, 2, 1, 1));
		parentPanel.add(buttonSection, BorderLayout.CENTER);
		
		
		final JButton loginButton = new JButton("Login");
		buttonSection.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				try {
					if (Login.validateLogin(userNameField.getText(), new String(passwordField.getPassword())))
					{
						try {
							Login.login(userNameField.getText());
							createChangePanel(initEditorWindow());
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace(); }
					}
					else {
						JOptionPane.showMessageDialog(loginButton, "Invalid Username/Password Combination.", 
								"Invalid Credentials", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (HeadlessException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
			
		
		
			
		
		JButton newUserButton = new JButton("New User");
		buttonSection.add(newUserButton);
		newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				UserCreationFrame ucf = new UserCreationFrame();
				ucf.setVisible(true);
			}
		});
		
		JButton recoverPwordBtn = new JButton("Lost Password?");
		buttonSection.add(recoverPwordBtn);
		recoverPwordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				UserRecoveryFrame urf = new UserRecoveryFrame();
				urf.setVisible(true);
			}
		});
		
		return parentPanel;
	}
	
	
	
	public JPanel initEditorWindow(){
		JPanel parentPanel = new JPanel(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		frmTaskManagementApp.setJMenuBar(menuBar);
		frmTaskManagementApp.add(menuBar);
		
		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);
		
			JMenuItem mnumSignIn = new JMenuItem("Sign Out");
			mnumSignIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Session.storeSession();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					createChangePanel(loginWindow());
				}
			});
			mnUser.add(mnumSignIn);
			
			JMenuItem mnumUpdateProf =  new JMenuItem("Update Profile...");
			mnumUpdateProf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UserUpdateFrame uuf = new UserUpdateFrame();
					uuf.setVisible(true);
				}
			});
			mnumUpdateProf.add(menuBar);
			
		
		JMenu mnTaskList = new JMenu("Task List");
		menuBar.add(mnTaskList);
		frmTaskManagementApp.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel listPanel = new JPanel();
		parentPanel.add(listPanel, BorderLayout.WEST);
		listPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		taskLists = new JList<TaskList>(model);
		taskLists.setPreferredSize(new Dimension(240, 480));
		listPanel.add(taskLists);
		taskLists.addMouseListener(this);
		taskLists.setBorder(new LineBorder(new Color(0, 0, 0)));
		taskLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		for (TaskList tl : Session.returnUser().getLists()) {
			model.addElement(tl);
		}
		mntmNewTaskList = new JMenuItem("New Task List...");
		mntmNewTaskList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewList();
			}
		});
		mnTaskList.add(mntmNewTaskList);
		
		mntmRenameSelectedList = new JMenuItem("Rename Selected List...");
		mntmRenameSelectedList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renameList();
			}
		});
		mnTaskList.add(mntmRenameSelectedList);
		
		mntmDeleteSelectedList = new JMenuItem("Delete Selected List...");
		mntmDeleteSelectedList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteList();
			}
		});
		mnTaskList.add(mntmDeleteSelectedList);
		
		JMenu mnTask = new JMenu("Task");
		menuBar.add(mnTask);
		

		
		mntmCreateNewTask = new JMenuItem("Create New Task...");
		mnTask.add(mntmCreateNewTask);
		mntmCreateNewTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewTask();
			}
		});
		
		mntmEditSelected = new JMenuItem("Edit Selected Task...");
		mntmEditSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskUpdateForm tuf = new TaskUpdateForm(tasks.getSelectedValue());
				tuf.setVisible(true);
			}
		});
		
		mntmAddSubtask = new JMenuItem("Add Subtask...");
		mntmAddSubtask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubtaskCreationForm stcf = new SubtaskCreationForm(tasks.getSelectedValue());
				stcf.setVisible(true);
			}
		});
		mnTask.add(mntmAddSubtask);
		mnTask.add(mntmEditSelected);
		
		mntmDeleteSelected = new JMenuItem("Delete Selected Task");
		mntmDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteTask();
			}
		});
		
		mntmViewSubtasks = new JMenuItem("View Subtasks");
		mntmViewSubtasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubtaskWindow stw = new SubtaskWindow(tasks.getSelectedValue());
				stw.setVisible(true);
			}
		});
		mnTask.add(mntmViewSubtasks);
		mnTask.add(mntmDeleteSelected);
		JPanel detailPanel = new JPanel();
		tasks = new JList<Task>(taskModel);
		updateTasks();
		tasks.setPreferredSize(new Dimension(240, 480));
		
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getButton() == 1) {
		           updateFields();
		         }
		    }
		};
		tasks.addMouseListener(mouseListener);
		
		detailPanel.add(tasks);
		parentPanel.add(detailPanel, BorderLayout.CENTER);
		
		
		
		JPanel textPanel = new JPanel();
		parentPanel.add(textPanel, BorderLayout.EAST);
		textPanel.setLayout(new GridLayout(6, 2, 0, 0));
		
		lblTaskName = new JLabel("Task Name: ");
		textPanel.add(lblTaskName);
		
		lblDueDate = new JLabel("Due Date:");
		textPanel.add(lblDueDate);
		
		JLabel lblDescription = new JLabel("Description:");
		textPanel.add(lblDescription);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setRows(8);
		textArea.setColumns(20);
		textArea.setEditable(false);
		textPanel.add(textArea);
		
		lblStatus = new JLabel("Status:");
		textPanel.add(lblStatus);
		
		JButton btnToggleStatus = new JButton("Toggle Status");
		btnToggleStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tasks.getSelectedValue() == null) return;
				Task t = tasks.getSelectedValue();
				t.setCompleted(!t.isCompleted());
				updateFields();
			}
		});
		textPanel.add(btnToggleStatus);
		
		JPanel panelNorth = new JPanel();
		parentPanel.add(panelNorth, BorderLayout.NORTH);
		
		lblList = new JLabel("Active List: " + Session.getActiveTaskList().getName());
		panelNorth.add(lblList);
		frmTaskManagementApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return parentPanel;
	}
	
	public void update(){
		lblList.setText("Active Task: " + Session.getActiveTaskList().getName());
		
		updateTasks();
		updateFields();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == e.BUTTON1){
			if (taskLists.getSelectedValue() != null) {
			Session.setActiveTaskList(taskLists.getSelectedValue());
			update();
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void renameList(){
		String newName = JOptionPane.showInputDialog("Enter New Name:");
		if (newName != null) Session.getActiveTaskList().setName(newName);
		for (Task t : Session.getActiveTaskList().getTaskList()) {
			System.out.println(t.getName() + " " + t.getDescription());
		}
		update();
	}
	
	public void deleteList(){
		int delete = JOptionPane.showConfirmDialog(null, "Really delete?");
		if (delete == JOptionPane.OK_OPTION) {
			Session.returnUser().removeList(Session.getActiveTaskList());
			model.removeElement(Session.getActiveTaskList());
			Session.setActiveTaskList(null);
		}
	}
	public void deleteTask(){
		int delete = JOptionPane.showConfirmDialog(null, "Really delete?");
		if (delete == JOptionPane.OK_OPTION) {
			Session.getActiveTaskList().removeTask(tasks.getSelectedValue());
			taskModel.removeElement(tasks.getSelectedValue());
		}
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
	public void updateTasks(){
		taskModel.clear();
		for (Task t : Session.getActiveTaskList().getTaskList()) {
			taskModel.addElement(t);
		}
		tasks.setSelectedIndex(listIndex);
	}
	public void updateFields(){
		if (tasks.getSelectedValue() == null) return;
		Task t = tasks.getSelectedValue();
		lblTaskName.setText("Name: " + t.getName());
		lblDueDate.setText("Due Date" + t.getDueDate().toString());
		textArea.setText(t.getDescription());
		lblStatus.setText("Status: " + ((t.isCompleted()) ? "Completed" : "Not Completed"));
	}
	public void createNewList(){
		String title = JOptionPane.showInputDialog(null, "Title:");
		if (title != null){
			TaskList tl = new TaskList(title, Session.returnUser());
			Session.returnUser().addList(tl);
			Session.setActiveTaskList(tl);
			lblList.setText("Active Task: " + Session.getActiveTaskList().getName());
			model.addElement(tl);
			taskLists.setSelectedValue(tl, true);
		}
	}
	public void createNewTask(){
		TaskCreationForm tcf = new TaskCreationForm();
		tcf.setVisible(true);
		update();
	}
	
	//not implemented yet
	public void updateMenus(boolean tlEnabled, boolean tEnabled){
		mntmRenameSelectedList.setEnabled(tlEnabled);
		mntmDeleteSelectedList.setEnabled(tlEnabled);
		mntmCreateNewTask.setEnabled(tlEnabled);
		mntmEditSelected.setEnabled(tEnabled);
		mntmAddSubtask.setEnabled(tEnabled);
		mntmDeleteSelected.setEnabled(tEnabled);
		mntmViewSubtasks.setEnabled(tEnabled);
		
	}
}
