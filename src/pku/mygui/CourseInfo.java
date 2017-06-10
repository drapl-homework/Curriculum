package mygui;

import java.util.*;

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

import mygui.Cheif.CoursePanel.TaskPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CourseInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnl_tl;
	private JPanel pnl_pl;
	private JPanel pnl_kl;
	private int time_cnt;
	private int pro_cnt;
	private int tsk_cnt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CourseInfo dialog = new CourseInfo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CourseInfo() {
		setBounds(100, 100, 537, 511);
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
		txt_credit.setColumns(10);
		
		JLabel lb_score = new JLabel("成绩");
		lb_score.setBounds(30, 120, 54, 15);
		contentPanel.add(lb_score);
		
		JTextField txt_score = new JTextField();
		txt_score.setBounds(97, 120, 193, 18);
		contentPanel.add(txt_score);
		txt_score.setColumns(10);
		
		
		
		pnl_tl=new JPanel();
		pnl_tl.setBackground(Color.CYAN);
		pnl_tl.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		JScrollPane spl_t = new JScrollPane(pnl_tl);
		spl_t.setBounds(97, 164, 400, 80);
		contentPanel.add(spl_t);
		
		time_cnt=1;
		pnl_tl.add(new TimePanel());
		pnl_tl.setPreferredSize(new Dimension(278,18));
		
		JButton btn_add_time = new JButton("添加时段");
		btn_add_time.setBounds(10, 159, 74, 23);
		btn_add_time.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				time_cnt+=1;
				pnl_tl.add(new TimePanel());
				pnl_tl.setPreferredSize(new Dimension(350,18*time_cnt));
				pnl_tl.updateUI();
			}
		});
		contentPanel.add(btn_add_time);
		
		
		pnl_pl=new JPanel();
		pnl_pl.setBackground(Color.YELLOW);
		pnl_pl.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		JScrollPane spl_p = new JScrollPane(pnl_pl);
		spl_p.setBounds(97, 250, 400, 80);
		contentPanel.add(spl_p);
		
		pro_cnt=0;
		
		JButton btn_add_pro = new JButton("添加老师");
		btn_add_pro.setBounds(10, 250, 74, 23);
		btn_add_pro.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				ProfInfo info_pro=new ProfInfo();
				info_pro.setModal(true);
				info_pro.setAlwaysOnTop(true);
				info_pro.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				info_pro.setVisible(true);
				
				if (info_pro.showtxt!=null){
					pro_cnt+=1;
					pnl_pl.add(new ProPanel(info_pro.showtxt));
					pnl_pl.setPreferredSize(new Dimension(350,18*pro_cnt));
					pnl_pl.updateUI();
				}
			}
		});
		contentPanel.add(btn_add_pro);
		
		pnl_kl=new JPanel();
		pnl_kl.setBackground(Color.ORANGE);
		pnl_kl.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		JScrollPane spl_k = new JScrollPane(pnl_kl);
		spl_k.setBounds(97, 340, 400, 80);
		contentPanel.add(spl_k);
		
		tsk_cnt=0;
		
		JButton btn_add_tsk = new JButton("添加任务");
		btn_add_tsk.setBounds(10, 340, 74, 23);
		btn_add_tsk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				TaskInfo info_tsk=new TaskInfo();
				info_tsk.setModal(true);
				info_tsk.setAlwaysOnTop(true);
				info_tsk.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				info_tsk.setVisible(true);
				
				if (info_tsk.showtxt!=null){
					tsk_cnt+=1;
					pnl_kl.add(new ProPanel(info_tsk.showtxt));
					pnl_kl.setPreferredSize(new Dimension(350,18*pro_cnt));
					pnl_kl.updateUI();
				}
			}
		});
		contentPanel.add(btn_add_tsk);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////////revise//////////////
						
						CourseInfo.this.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton delButton = new JButton("Delete");
				delButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////////del//////////////
						
						CourseInfo.this.dispose();
					}
				});
				buttonPane.add(delButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						CourseInfo.this.dispose();
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
					TimePanel.this.setVisible(false);
					time_cnt-=1;
					pnl_tl.setPreferredSize(new Dimension(350,18*time_cnt));
					pnl_tl.updateUI();
					/////////////////
				}
			});
			this.add(btn_del);
		}
	}
	
	public class ProPanel extends JPanel{
		
		private JButton btn_item;
		
		public ProPanel(String pinfo){
			setPreferredSize(new Dimension(350, 18));
			setLayout(null);
			
			btn_item=new JButton(pinfo);
			btn_item.setHorizontalAlignment(SwingConstants.LEFT);
			btn_item.setBounds(0, 0, 300, 18);
			btn_item.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					ProfInfo info_pro=new ProfInfo();
					info_pro.setModal(true);
					info_pro.setAlwaysOnTop(true);
					info_pro.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					info_pro.setVisible(true);
					
					/////更新
					ProPanel.this.updateUI();
				}
			});
			this.add(btn_item);
			
			JButton btn_del=new JButton("x");
			btn_del.setBounds(300, 0, 50, 18);
			btn_del.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//havent remove//
					if (pro_cnt>1){
						ProPanel.this.setVisible(false);
						pro_cnt-=1;
						pnl_pl.setPreferredSize(new Dimension(350,18*pro_cnt));
						pnl_pl.updateUI();
					}
					/////////////////
				}
			});
			this.add(btn_del);
		}
	}
	
	public class TaskPanel extends JPanel{
		
		private JButton btn_item;
		
		public TaskPanel(String pinfo){
			setPreferredSize(new Dimension(350, 18));
			setLayout(null);
			
			btn_item=new JButton(pinfo);
			btn_item.setHorizontalAlignment(SwingConstants.LEFT);
			btn_item.setBounds(0, 0, 300, 18);
			btn_item.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					TaskInfo info_tsk=new TaskInfo();
					info_tsk.setModal(true);
					info_tsk.setAlwaysOnTop(true);
					info_tsk.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					info_tsk.setVisible(true);
					
					/////更新
					TaskPanel.this.updateUI();
				}
			});
			this.add(btn_item);
			
			JButton btn_del=new JButton("x");
			btn_del.setBounds(300, 0, 50, 18);
			btn_del.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					//havent remove//
					TaskPanel.this.setVisible(false);
					tsk_cnt-=1;
					pnl_kl.setPreferredSize(new Dimension(350,18*tsk_cnt));
					pnl_kl.updateUI();
					/////////////////
				}
			});
			this.add(btn_del);
		}
	}
}
