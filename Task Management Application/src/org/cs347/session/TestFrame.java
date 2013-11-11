package org.cs347.session;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.cs347.tasks.TaskList;
import org.cs347.tasks.User;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestFrame extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	static public JList list;
	private ListCellRenderer lcr;
	TaskList[] tlArray;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//lcr = new ListCellRenderer();
		User u = new User("User", "root");
		u.addList(new TaskList("tasklist 1", u));
		u.addList(new TaskList("tl2", u));
		u.addList(new TaskList("tla2", u));
		u.addList(new TaskList("tl22", u));
		u.addList(new TaskList("tl4232", u));
		Session.setActiveTaskList(new TaskList("new Tasks", u));
		ArrayList<TaskList> al = u.getLists();
		list = new JList(al.toArray());
		list.setSelectedIndex(1);
		TaskList tl = (TaskList)list.getSelectedValue();
		System.out.println(tl.getUsers().isEmpty() + tl.getName() + tl.getUsers().get(0));
		
		System.out.println(u.getNameID());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.getContentPane().add(list);
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
	public TestFrame() {
		ArrayList<String> arr = new ArrayList<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		final JCheckBox chckbxCheckbox = new JCheckBox("checkbox 1");
		
		contentPane.add(chckbxCheckbox, BorderLayout.NORTH);
		
		
		int i = 0;
		JCheckBox[] boxes = new JCheckBox[4];
		for (String s : arr){
			boxes[i] = new JCheckBox(s);
			i++;
		}
		//list = new JList(boxes);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(list, BorderLayout.CENTER);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		contentPane.add(table, BorderLayout.WEST);
	}

	public void popup(MouseEvent e){
		if (e.isPopupTrigger()){
			System.out.println("hi");
		}
	}
}
class TaskListRenderer extends JLabel implements ListCellRenderer{
	
	public TaskListRenderer(){
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object tl,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		int selectedIndex = index;
		return this;
	}
}
