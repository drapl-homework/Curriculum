package mygui;

import curriculum.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import mygui.CourseInfo.TaskPanel;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;


import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class Cheif {

	private JFrame frame;
	private CardLayout card;
	private JPanel pnl_main;
	
	// 获取学期和课程
	String databaseFile = "data.sqlite";
    Storage db = new Storage("jdbc:sqlite:" + databaseFile, !new File(databaseFile).exists());
    List<Semester> semesters = db.getAllSementers();
    Semester semester0 = semesters.get(0);
	List<Course> courseList=semester0.getAllCourses();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cheif window = new Cheif();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cheif() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel pnl_info = new JPanel();
		pnl_info.setBackground(new Color(127, 255, 212));
		pnl_info.setBounds(0, 0, 180, 561);
		pnl_info.setLayout(null);
		JLabel lb_welcome = new JLabel("Welcome to SyllabusPro!");
		lb_welcome.setBounds(10,80,160,40);
		pnl_info.add(lb_welcome);
		JLabel lb_name = new JLabel("张三"); //test
		lb_name.setBounds(70,160,40,40);
		pnl_info.add(lb_name);
		JLabel lb_num = new JLabel("1300013000"); //test
		lb_num.setBounds(40,200,80,40);
		pnl_info.add(lb_num);
		frame.getContentPane().add(pnl_info);
		
		JPanel pnl_btn = new JPanel();
		pnl_btn.setBackground(new Color(192, 192, 192));
		pnl_btn.setBounds(180, 0, 804, 70);
		frame.getContentPane().add(pnl_btn);
		
		card=new CardLayout(0, 0);
		pnl_main = new JPanel();
		pnl_main.setBounds(180, 70, 804, 490);
		frame.getContentPane().add(pnl_main);
		pnl_main.setLayout(card);
		
		JPanel pnl_crs = new CoursePanel();
		pnl_crs.setBackground(Color.WHITE);
		pnl_main.add(pnl_crs, "crs");
		
		JPanel pnl_sms = new SemesterPanel();
		pnl_sms.setBackground(Color.YELLOW);
		pnl_main.add(pnl_sms, "sms");
		
		JPanel pnl_dat = new DataPanel();
		pnl_main.add(pnl_dat, "dat");
		pnl_dat.setBackground(Color.GREEN);
		
		
		JButton btn_crs = new JButton("课表");
		btn_crs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				card.show(pnl_main,"crs");
				}
			});
		pnl_btn.add(btn_crs);
		
		JButton btn_sms = new JButton("学期");
		btn_sms.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				card.show(pnl_main,"sms");
				}
			});
		pnl_btn.add(btn_sms);
		
		JButton btn_dat = new JButton("学况");
		btn_dat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				card.show(pnl_main,"dat");
				}
			});
		pnl_btn.add(btn_dat);
			
	}
	
	
	////////////////
	/////Course/////
	////////////////	
	public class CoursePanel extends JPanel{
		
		private final int width=37;
		private JPanel pnl_kl;
		private JPanel[] pnl_days;
		private int tsk_cnt;

		public CoursePanel() {
			setLayout(null);
			
			JPanel pnl_m=new JPanel();
			pnl_m.setLayout(null);
			pnl_m.setBounds(10, 10, width*15, 440);
			add(pnl_m);
			
			JPanel pnl_label=new JPanel();
			pnl_label.setLayout(null);
			pnl_label.setBounds(width, 0, width*14, 40);
			pnl_label.setBackground(Color.YELLOW);
			pnl_m.add(pnl_label);
			String[] days={"一","二","三","四","五","六","日"};
			JLabel[] lb_days = new JLabel[7];
			for(int i=0; i<7; i++){
				lb_days[i] = new JLabel("周"+days[i]);
				lb_days[i].setBounds(width*2*i+18,0,width*2,40);
				pnl_label.add(lb_days[i]);
			}
			
			JPanel pnl_time=new JPanel();
			pnl_time.setLayout(null);
			pnl_time.setBounds(0, 40, width, 400);
			pnl_time.setBackground(Color.PINK);
			JLabel[] lb_hours = new JLabel[14];
			String[] hours={"9:00  -","10:00-","11:00-","12:00-","13:00-","14:00-","15:00-","16:00-",
					"17:00-","18:00-","19:00-","20:00-","21:00-","22:00-"};
			for(int i=0; i<14; i++){
				lb_hours[i]=new JLabel(hours[i]);
				lb_hours[i].setBounds(0,30*i+15,width,30);
				pnl_time.add(lb_hours[i]);
			}
			
			
			pnl_m.add(pnl_time);
			
			pnl_days=new JPanel[7];
			
			for(int i=0;i<7;i++){
				pnl_days[i]=new JPanel();
				pnl_days[i].setLayout(null);
				pnl_days[i].setBounds(width+width*2*i, 40, width*2, 400);
				pnl_days[i].setBackground(new Color(250-10*i, 255, 250-10*i));

				pnl_m.add(pnl_days[i]);
			}
			
			JPanel pnl_btmbtn = new JPanel();
			pnl_btmbtn.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
			pnl_btmbtn.setBounds(10, 440, width*15, 40);
			add(pnl_btmbtn);
			
			JButton btn_new=new JButton("新增课程");
			btn_new.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					CourseAdd new_crs=new CourseAdd(semester0);
					new_crs.setModal(true);
					new_crs.setAlwaysOnTop(true);
					new_crs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					new_crs.setVisible(true);
					
					///更新课表//
					pnl_main.updateUI();
				}
			});
			pnl_btmbtn.add(btn_new);
			
			
			//test//
			addClassBar("物理",1,2.0/12,4.0/12);
			addClassBar("数学",3,9.0/12,12.0/12);
			//
			

			pnl_kl=new JPanel();
			pnl_kl.setBackground(Color.ORANGE);
			pnl_kl.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			JScrollPane spl_k = new JScrollPane(pnl_kl);
			spl_k.setBounds(width*15+20, 10, 220, 440);
			add(spl_k);
			
			JPanel pnl_taskbtn=new JPanel();
			pnl_taskbtn.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
			pnl_taskbtn.setBounds(width*15+20, 440, 220, 40);
			add(pnl_taskbtn);
			
			tsk_cnt=0;
			
			JButton btn_new_tsk = new JButton("添加任务");
			btn_new_tsk.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					TaskInfo info_tsk=new TaskInfo();
					info_tsk.setModal(true);
					info_tsk.setAlwaysOnTop(true);
					info_tsk.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					info_tsk.setVisible(true);
					
					if (info_tsk.showtxt!=null){
						tsk_cnt+=1;
						pnl_kl.add(new TaskPanel(info_tsk.showtxt));
						pnl_kl.setPreferredSize(new Dimension(180,18*tsk_cnt));
						pnl_kl.updateUI();
					}
				}
			});
			pnl_taskbtn.add(btn_new_tsk);
		}
		
		//参数待议
		void addClassBar(String name, int day, double begin_rate, double end_rate){
			JButton cls=new JButton(name);
			cls.setBounds(2, (int)(begin_rate*400), width*2-4, (int)((end_rate-begin_rate)*400));
			cls.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					CourseInfo info_crs=new CourseInfo();
					info_crs.setModal(true);
					info_crs.setAlwaysOnTop(true);
					info_crs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					info_crs.setVisible(true);
					
					/////更新
					pnl_main.updateUI();
				}
			});
			pnl_days[day].add(cls);
		}
		
		public class TaskPanel extends JPanel{
			
			private JButton btn_item;
			
			public TaskPanel(String ptime){
				setPreferredSize(new Dimension(180, 18));
				setLayout(null);
				
				btn_item=new JButton(ptime);
				btn_item.setHorizontalAlignment(SwingConstants.LEFT);
				btn_item.setBounds(0, 0, 130, 18);
				btn_item.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						TaskInfo info_tsk=new TaskInfo();
						info_tsk.setModal(true);
						info_tsk.setAlwaysOnTop(true);
						info_tsk.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						info_tsk.setVisible(true);

						//修改任务
						pnl_kl.updateUI();
					}
				});
				this.add(btn_item);
				
				JButton btn_del=new JButton("x");
				btn_del.setBounds(130, 0, 50, 18);
				btn_del.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//havent remove//
						TaskPanel.this.setVisible(false);
						tsk_cnt-=1;
						pnl_kl.setPreferredSize(new Dimension(180,18*tsk_cnt));
						pnl_kl.updateUI();
						/////////////////
					}
				});
				this.add(btn_del);
			}
		}
			
	}
	
	
	////////////////
	////Semester////
	////////////////
	public class SemesterPanel extends JPanel {
		
		//private Semester[] sms_l;
		private JPanel pnl_sms_l;
		private JTextField txt_sms;
		private Vector<Semester> sms_l;

		public SemesterPanel() {
			setLayout(null);
			txt_sms=new JTextField("新学期");
			pnl_sms_l=new JPanel();
			pnl_sms_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			pnl_sms_l.setBackground(Color.CYAN);
			
			//test//
			sms_l=new Vector<Semester>();
			sms_l.addElement(new Semester("2013"));
			sms_l.addElement(new Semester("2014"));
			sms_l.addElement(new Semester("2015"));
			sms_l.addElement(new Semester("2016"));
			sms_l.addElement(new Semester("2017"));
			sms_l.addElement(new Semester("2018"));
			//
			
			int h=0;
			for (int i=0;i<sms_l.size();i++){
				sms_l.elementAt(i).setPreferredSize(new Dimension(750,sms_l.elementAt(i).height));
				pnl_sms_l.add(sms_l.elementAt(i));
				h+=sms_l.elementAt(i).height;
			}
			pnl_sms_l.setPreferredSize(new Dimension(750, h));
			
			JScrollPane spnl_sms = new JScrollPane(pnl_sms_l);
			spnl_sms.setBounds(10, 10, 784, 440);
			add(spnl_sms);
			
			JPanel pnl_btmbtn = new JPanel();
			pnl_btmbtn.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
			pnl_btmbtn.setBounds(10, 440, 784, 40);
			add(pnl_btmbtn);
			
			pnl_btmbtn.add(txt_sms);
			JButton btn_new=new JButton("新增学期");
			btn_new.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					////add///
					
					Semester new_sms=new Semester(txt_sms.getText());
					sms_l.addElement(new_sms);
					pnl_sms_l.add(new_sms);
					paintSmsList();
				}
			});
			pnl_btmbtn.add(btn_new);
		}
		
		public void paintSmsList(){
			int height=0;
			for (int i=0;i<sms_l.size();i++){
				if (sms_l.elementAt(i).isVisible()){
					sms_l.elementAt(i).setPreferredSize(new Dimension(750,sms_l.elementAt(i).height));
					height+=sms_l.elementAt(i).height;
				}
			}
			pnl_sms_l.setPreferredSize(new Dimension(750, height));
			pnl_sms_l.updateUI();
		}
		
		private class Semester extends JPanel{
			
			private JPanel pnl_crs_l; 
			private JPanel pnl_title; 
			private JTextField txt_title;
			
			public int height;
			
			public Semester() {
				super();
			}
			
			public Semester(String tl){
				height=30;
				setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				
				pnl_title=new JPanel();
				pnl_title.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_title.setPreferredSize(new Dimension(750, 30));
				pnl_title.setBackground(Color.WHITE);
				pnl_crs_l=new JPanel();
				pnl_crs_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_crs_l.setBackground(Color.GREEN);
				
				txt_title=new JTextField(tl);
				txt_title.setPreferredSize(new Dimension(100, 30));
				txt_title.setEditable(false);
				txt_title.addMouseListener(new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent arg0) {
						txt_title.setEditable(true);
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {}

					@Override
					public void mouseExited(MouseEvent arg0) {}

					@Override
					public void mousePressed(MouseEvent arg0) {}

					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
				txt_title.addKeyListener(new KeyListener(){

					@Override
					public void keyPressed(KeyEvent arg0) {
						if (arg0.getKeyChar()==KeyEvent.VK_ENTER)
							txt_title.setEditable(false);
						
						//更新数据库
					}

					@Override
					public void keyReleased(KeyEvent arg0) {}

					@Override
					public void keyTyped(KeyEvent arg0) {}
					
				});
				txt_title.addFocusListener(new FocusListener(){

					@Override
					public void focusGained(FocusEvent arg0) {}

					@Override
					public void focusLost(FocusEvent arg0) {
						txt_title.setEditable(false);
						
						//更新数据库
					}
					
				});
				pnl_title.add(txt_title);
				
				JButton title=new JButton("课表");
				title.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						//////need update//////
						card.show(pnl_main,"crs");
						pnl_main.updateUI();
					}
				});
				pnl_title.add(title);
				this.add(pnl_title);
				this.add(pnl_crs_l);
				
				/////////test//////////
				String[] crs={"物理","数学","英语","面向对象"};
				///////////////////////
				for(int i=0;i<crs.length;i++){
					JPanel pnl_cls=new JPanel();
					pnl_cls.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
					pnl_cls.setPreferredSize(new Dimension(750, 20));
					pnl_cls.setBackground(Color.GREEN);
					JButton btn_cls=new JButton(crs[i]);
					btn_cls.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							CourseInfo info_crs=new CourseInfo();
							info_crs.setModal(true);
							info_crs.setAlwaysOnTop(true);
							info_crs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							info_crs.setVisible(true);
							
							/////更新
							pnl_main.updateUI();
						}
					});
					pnl_cls.add(btn_cls);
					pnl_crs_l.add(pnl_cls);
				}
				pnl_crs_l.setPreferredSize(new Dimension(750,20*crs.length));
				pnl_crs_l.setVisible(false);
				
				JButton btn_fold=new JButton("+");
				btn_fold.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(pnl_crs_l.isVisible()){
							pnl_crs_l.setVisible(false);
							height=30;
							paintSmsList();
							btn_fold.setText("+");
						}
						else{
							pnl_crs_l.setVisible(true);
							height=30+20*crs.length;
							paintSmsList();
							btn_fold.setText("-");
						}
					}
				});
				pnl_title.add(btn_fold);
				
				JButton btn_del=new JButton("x");
				btn_del.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//havent remove//
						Semester.this.setVisible(false);
						paintSmsList();
						/////////////////
					}
				});
				pnl_title.add(btn_del);
				setVisible(true);
			}
		}
	}

	
	
	////////////////
	//////Data//////
	////////////////
	public class DataPanel extends JPanel {

		private Data[] dat_l;
		private JPanel pnl_dat_l;
		private JPanel pnl_chart;
		/**
		 * Create the panel.
		 */
		public DataPanel() {
			setLayout(null);
			
			pnl_dat_l=new JPanel();
			pnl_dat_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			
			//test//
			JLabel lb_l=new JLabel("平均绩点3.99");
			lb_l.setPreferredSize(new Dimension(360,30));
			pnl_dat_l.add(lb_l);
			////////
			dat_l=new Data[6];
			dat_l[0]=new Data("2016");
			dat_l[1]=new Data("2017");
			dat_l[2]=new Data("2016");
			dat_l[3]=new Data("2017");
			dat_l[4]=new Data("2016");
			dat_l[5]=new Data("2017");
			int h=0;
			//test
			int a=30;
			//
			
			for (int i=0;i<dat_l.length;i++){
				dat_l[i].setPreferredSize(new Dimension(360,dat_l[i].height));
				pnl_dat_l.add(dat_l[i]);
				h+=dat_l[i].height;
			}
			pnl_dat_l.setPreferredSize(new Dimension(360, h+a));
			
			JScrollPane spnl_dat = new JScrollPane(pnl_dat_l);
			spnl_dat.setBounds(10, 10, 390, 470);
			add(spnl_dat);
			
			pnl_chart=new JPanel();
			pnl_chart.setBounds(450,100,300,300);
			pnl_chart.setBackground(Color.WHITE);
			pnl_chart.add(new JLabel("走势图"));
			add(pnl_chart);
		}
		
		public void paintSmsList(){
			int height=0;
			//test
			int a=30;
			//
			for (int i=0;i<dat_l.length;i++){
				if (dat_l[i].isVisible()){
					dat_l[i].setPreferredSize(new Dimension(360,dat_l[i].height));
					height+=dat_l[i].height;
				}
			}
			pnl_dat_l.setPreferredSize(new Dimension(360, height+a));
		}
	
		
		private class Data extends JPanel{
			
			private JPanel pnl_crs_l; 
			private JPanel pnl_title; 
			
			public int height;
			
			public Data() {
				super();
			}
			
			public Data(String tl){
				height=30;
				setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_title=new JPanel();
				pnl_title.setPreferredSize(new Dimension(360,30));
				pnl_title.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_title.setBackground(Color.WHITE);
				pnl_crs_l=new JPanel();
				pnl_crs_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_crs_l.setBackground(Color.GREEN);
				
				JLabel title=new JLabel(tl);
				pnl_title.add(title);
				this.add(pnl_title);
				this.add(pnl_crs_l);
				
				/////////test//////////
				String[] crs={"物理 3.8","数学 3.9","英语 3.6"};
				///////////////////////
				for(String c:crs){
					JPanel pnl_clsd=new JPanel();
					pnl_clsd.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
					pnl_clsd.setPreferredSize(new Dimension(360, 20));
					pnl_clsd.setBackground(Color.GREEN);
					pnl_clsd.add(new JLabel(c));
					pnl_crs_l.add(pnl_clsd);
				}
				pnl_crs_l.setPreferredSize(new Dimension(360,20*crs.length));
				pnl_crs_l.setVisible(false);
				
				JButton btn_fold=new JButton("+");
				btn_fold.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(pnl_crs_l.isVisible()){
							pnl_crs_l.setVisible(false);
							height=30;
							paintSmsList();
							btn_fold.setText("+");
						}
						else{
							pnl_crs_l.setVisible(true);
							height=30+20*crs.length;
							paintSmsList();
							btn_fold.setText("-");
						}
					}
				});
				pnl_title.add(btn_fold);
				setVisible(true);
			}
		}
	}

}
