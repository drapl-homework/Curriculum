package mygui;

import java.util.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CourseAdd extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnl_tl;
	private int time_cnt;

	/**
	 * Create the dialog.
	 */
	public CourseAdd() {
		setBounds(100, 100, 537, 339);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lb_title = new JLabel("课程名");
		lb_title.setBounds(30, 10, 54, 15);
		contentPanel.add(lb_title);
		
		JTextField txt_title = new JTextField();
		txt_title.setBounds(97, 8, 117, 18);
		contentPanel.add(txt_title);
		txt_title.setColumns(10);
		
		pnl_tl=new JPanel();
		pnl_tl.setBackground(Color.CYAN);
		pnl_tl.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		JScrollPane spl_t = new JScrollPane(pnl_tl);
		spl_t.setBounds(97, 125, 400, 110);
		contentPanel.add(spl_t);
		
		time_cnt=1;
		pnl_tl.add(new TimePanel());
		pnl_tl.setPreferredSize(new Dimension(278,18));
		
		JButton btn_add_time = new JButton("添加");
		btn_add_time.setBounds(10, 125, 74, 23);
		btn_add_time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				time_cnt+=1;
				pnl_tl.add(new TimePanel());
				pnl_tl.setPreferredSize(new Dimension(350,18*time_cnt));
				pnl_tl.updateUI();
			}
		});
		contentPanel.add(btn_add_time);
		
		JLabel lb_local = new JLabel("上课地点");
		lb_local.setBounds(30, 49, 54, 15);
		contentPanel.add(lb_local);
		
		JTextField txt_local = new JTextField();
		txt_local.setBounds(97, 46, 193, 18);
		contentPanel.add(txt_local);
		txt_local.setColumns(50);
		
		JLabel lb_credit = new JLabel("学分");
		lb_credit.setBounds(30, 80, 54, 15);
		contentPanel.add(lb_credit);
		
		JTextField txt_credit = new JTextField();
		txt_credit.setBounds(97, 80, 193, 18);
		contentPanel.add(txt_credit);
		txt_credit.setColumns(50);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						///////////////add/////////////
						
						CourseAdd.this.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						CourseAdd.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public class TimePanel extends JPanel{
		
		private JTextField txt_begin;
		private JTextField txt_end;
		
		public TimePanel(){
			setPreferredSize(new Dimension(350, 18));
			setLayout(null);
			
			JLabel lb_from = new JLabel("从");
			lb_from.setHorizontalAlignment(SwingConstants.CENTER);
			lb_from.setBounds(0, 0, 19, 18);
			this.add(lb_from);
			
			txt_begin = new JTextField();
			txt_begin.setText("8:00");
			txt_begin.setBounds(20, 0, 45, 18);
			this.add(txt_begin);
			txt_begin.setColumns(10);
			
			JLabel lb_end = new JLabel("到");
			lb_end.setHorizontalAlignment(SwingConstants.CENTER);
			lb_end.setBounds(75, 0, 19, 18);
			this.add(lb_end);
			
			txt_end = new JTextField();
			txt_end.setText("10:00");
			txt_end.setBounds(95, 0, 45, 18);
			this.add(txt_end);
			txt_end.setColumns(10);
			
			JComboBox cb_flag = new JComboBox();
			cb_flag.setModel(new DefaultComboBoxModel(new String[] {"每", "单", "双"}));
			cb_flag.setBounds(158, 0, 45, 18);
			this.add(cb_flag);
			
			JLabel lb_week = new JLabel("周");
			lb_week.setHorizontalAlignment(SwingConstants.CENTER);
			lb_week.setBounds(202, 0, 25, 18);
			this.add(lb_week);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"一", "二", "三", "四", "五", "六", "日"}));
			comboBox.setBounds(227, 0, 45, 18);
			this.add(comboBox);
			
			JButton btn_del=new JButton("x");
			btn_del.setBounds(300, 0, 50, 18);
			btn_del.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//havent remove//
					if (time_cnt>1){
						TimePanel.this.setVisible(false);
						time_cnt-=1;
						pnl_tl.setPreferredSize(new Dimension(350,18*time_cnt));
						pnl_tl.updateUI();
					}
					/////////////////
				}
			});
			this.add(btn_del);
		}
	}
}
