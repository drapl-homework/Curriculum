package mygui;

import curriculum.Semester;
import curriculum.Course;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import curriculum.Task;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

public class TaskInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cb_course;
	private JComboBox cb_type;
	private JComboBox cb_status;
	private JTextPane txt_intro;
	private JTextField txt_time;
	
	private Task task;
	
	/**
	 * Create the dialog.
	 */
	public TaskInfo(Task current_task, Course current_course, Semester current_sms) {
		if (current_task!=null) task=current_task;
		List<Course> courseList = current_sms.getAllCourses();
		Vector<String> courseName = new Vector<String>();
		for(Course course:courseList){
			courseName.add(course.getName());
		}
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		contentPanel.setLayout(null);
		contentPanel.setVisible(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		

		
		{
			JLabel lb_crs = new JLabel("所属课程");
			lb_crs.setBounds(23, 10, 54, 15);
			contentPanel.add(lb_crs);
		}
		{
			JLabel lb_type = new JLabel("类型");
			lb_type.setBounds(23, 45, 54, 15);
			contentPanel.add(lb_type);
		}
		{
			JLabel lb_time = new JLabel("截止时间");
			lb_time.setBounds(23, 78, 54, 15);
			contentPanel.add(lb_time);
		}
		{
			JLabel lb_flag = new JLabel("状态");
			lb_flag.setBounds(23, 122, 54, 15);
			contentPanel.add(lb_flag);
		}
		{
			JLabel lb_intro = new JLabel("简介");
			lb_intro.setBounds(23, 155, 54, 15);
			contentPanel.add(lb_intro);
		}
		/*{
			txt_name = new JTextField();
			txt_name.setEditable(false);
			txt_name.setBounds(89, 7, 87, 21);
			contentPanel.add(txt_name);
			txt_name.setColumns(10);
		}*/


		{
			cb_course = new JComboBox();
			cb_course.setModel(new DefaultComboBoxModel(courseName));
			if (current_task!=null) {
				current_course =  current_task.getCourse();
				
			}
			if(current_course!=null){
				String default_course = current_course.getName();
				cb_course.setSelectedItem(default_course);
			}
			cb_course.setBounds(89,7,113,21);
			contentPanel.add(cb_course);
		}
		
		{
			cb_type = new JComboBox();
			cb_type.setModel(new DefaultComboBoxModel(new String[] {"作业", "考试", "论文", "讨论", "其他"}));
			if (current_task!=null) {
				cb_type.setSelectedItem(current_task.getName());
			}
			cb_type.setBounds(88, 42, 113, 21);
			contentPanel.add(cb_type);
		}
		{
			txt_time = new JTextField();
			txt_time.setBounds(88, 78, 113, 15);
			if (current_task!=null) {
				txt_time.setText(current_task.getEndTime());
			}
			contentPanel.add(txt_time);
		}
		{
			cb_status = new JComboBox();
			cb_status.setModel(new DefaultComboBoxModel(new String[] {"未完成", "已完成"}));
			if (current_task!=null) {
				cb_status.setSelectedItem(current_task.getStatus());
			}
			cb_status.setBounds(88, 120, 113, 21);
			contentPanel.add(cb_status);
		}
		{
			txt_intro = new JTextPane();
			txt_intro.setBounds(88, 150, 321, 68);
			if (current_task!=null) {
				txt_intro.setText(current_task.getContent());
			}
			contentPanel.add(txt_intro);
		}
		

		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						Course course = courseList.get(cb_course.getSelectedIndex());
						//////////revise//////////////
						if (current_task==null) { //add new task
							task = course.addTask(UUID.randomUUID().toString(), cb_type.getSelectedItem().toString(), 
								txt_intro.getText(), txt_time.getText(), cb_status.getSelectedIndex());
						}
						else { //edit task
							task.setCourse(course);
							task.setName(cb_type.getSelectedItem().toString());
							task.setContent(txt_intro.getText());
							task.setEndTime(txt_time.getText());
							task.setStatus(cb_status.getSelectedIndex());
						}
						
						TaskInfo.this.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			
			
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////////revise//////////////
						
						TaskInfo.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
				
			}

		}
	}

}
