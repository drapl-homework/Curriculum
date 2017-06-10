package mygui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

public class TaskInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_name;
	private JTextField txt_email;
	private JTextField txt_office;
	
	public String showtxt;
	
	/**
	 * Create the dialog.
	 */
	public TaskInfo() {
		showtxt=null;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
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
			JLabel lb_time = new JLabel("截止日期");
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
		{
			txt_name = new JTextField();
			txt_name.setEditable(false);
			txt_name.setBounds(89, 7, 87, 21);
			contentPanel.add(txt_name);
			txt_name.setColumns(10);
		}
		{
			txt_email = new JTextField();
			txt_email.setBounds(87, 75, 178, 21);
			contentPanel.add(txt_email);
			txt_email.setColumns(10);
		}
		{
			txt_office = new JTextField();
			txt_office.setBackground(new Color(0, 255, 0));
			txt_office.setText("进行中");
			txt_office.setEditable(false);
			txt_office.setBounds(87, 119, 89, 21);
			contentPanel.add(txt_office);
			txt_office.setColumns(10);
		}
		
		JTextPane txt_intro = new JTextPane();
		txt_intro.setBounds(63, 150, 321, 68);
		contentPanel.add(txt_intro);
		{
			JComboBox cb_type = new JComboBox();
			cb_type.setModel(new DefaultComboBoxModel(new String[] {"作业", "考试", "论文", "讨论", "其他"}));
			cb_type.setBounds(88, 42, 113, 21);
			contentPanel.add(cb_type);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////////revise//////////////
						showtxt="3月20日18：00";
						
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
