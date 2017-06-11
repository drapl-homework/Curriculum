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
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import java.util.Vector;
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
import java.util.UUID;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Insets;

import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;  
  
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.ImageIcon;

public class Cheif {

	private JFrame frame;
	private CardLayout card;
	private JPanel pnl_main;
	
	// 获取学期和课程
	String databaseFile = "data.sqlite";
    Storage db = new Storage("jdbc:sqlite:" + databaseFile, !new File(databaseFile).exists());
   
    
    //test//
    //Semester semester = db.addSemester(UUID.randomUUID().toString(), "xxx",
    //        Date.valueOf("2000-01-01"), Date.valueOf("2000-01-02"));
    //test//
    
    List<Semester> semesters = db.getAllSementers();
    
    
    
    Semester current_sms = semesters.get(0);
	List<Course> courseList=current_sms.getAllCourses();
	
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
		pnl_btn.setBackground(new Color(112, 128, 144));
		pnl_btn.setBounds(180, 0, 804, 70);
		frame.getContentPane().add(pnl_btn);
		
		card=new CardLayout(0, 0);
		pnl_main = new JPanel();
		pnl_main.setBounds(180, 70, 804, 490);
		frame.getContentPane().add(pnl_main);
		pnl_main.setLayout(card);
		
		JPanel pnl_crs = new CoursePanel();
		pnl_crs.setBackground(new Color(47, 79, 79));
		pnl_main.add(pnl_crs, "crs");
		
		JPanel pnl_sms = new SemesterPanel();
		pnl_sms.setBackground(Color.decode("#f08080"));
		pnl_main.add(pnl_sms, "sms");
		
		JPanel pnl_dat = new DataPanel();
		pnl_main.add(pnl_dat, "dat");
		pnl_dat.setBackground(Color.GREEN);
		
		
		JButton btn_crs = new JButton("");
		btn_crs.setBackground(new Color(192, 192, 192));
		btn_crs.setPreferredSize(new Dimension(70,70));
		func.setButtonIcon(btn_crs, "crs1.png", "crs2.png", "crs1.png");
		btn_crs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				card.show(pnl_main,"crs");
				}
			});
		pnl_btn.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		pnl_btn.add(btn_crs);
		
		JButton btn_sms = new JButton("");
		btn_sms.setPreferredSize(new Dimension(70,70));
		func.setButtonIcon(btn_sms, "sms1.png", "sms2.png", "sms1.png");
		btn_sms.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				card.show(pnl_main,"sms");
				}
			});
		pnl_btn.add(btn_sms);
		
		JButton btn_dat = new JButton("");
		btn_dat.setPreferredSize(new Dimension(70,70));
		func.setButtonIcon(btn_dat, "dat1.png", "dat2.png", "dat1.png");
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
			pnl_m.setOpaque(false);
			add(pnl_m);
			
			JPanel pnl_label=func.getImgPanel("weekbar.png");
			pnl_label.setBounds(width, 0, width*14, 40);
			pnl_m.add(pnl_label);
			
			JPanel pnl_time=func.getImgPanel("timebar.png");
			pnl_time.setBounds(0, 0, width, 440);
			pnl_m.add(pnl_time);
			
			pnl_days=new JPanel[7];
			
			for(int i=0;i<7;i++){	
				if(i%2==0)
					pnl_days[i]=func.getImgPanel("line1.png");
				else
					pnl_days[i]=func.getImgPanel("line2.png");
				pnl_days[i].setLayout(null);
				pnl_days[i].setBounds(width+width*2*i, 40, width*2, 400);

				pnl_m.add(pnl_days[i]);
			}
			
			JPanel pnl_btmbtn = new JPanel();
			pnl_btmbtn.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
			pnl_btmbtn.setBounds(10, 450, width*15, 30);
			pnl_btmbtn.setOpaque(false);
			add(pnl_btmbtn);
			
			paintCourse();
			
			JButton btn_new=new JButton("+ 课程");
			btn_new.setPreferredSize(new Dimension(80,25));
			func.setButtonIcon(btn_new, "btn1.png", "btn2.png", "btn3.png");
			btn_new.setFont(new Font("黑体",Font.BOLD,12));
			btn_new.setForeground(Color.WHITE);
			btn_new.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					CourseAdd new_crs=new CourseAdd(current_sms);
					new_crs.setModal(true);
					new_crs.setAlwaysOnTop(true);
					new_crs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					new_crs.setVisible(true);
					
					///更新课表//
					new_crs.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent windowEvent) {
							super.windowClosed(windowEvent);
							updateCourseList();
							pnl_main.updateUI();
						}
					});
//					courseList=current_sms.getAllCourses();
//					System.out.println(courseList.size());
//					pnl_main.updateUI();
				}
			});
			pnl_btmbtn.add(btn_new);
			
			
			//test//
			//addClassBar("物理",1,2.0/12,4.0/12);
			//addClassBar("数学",3,9.0/12,12.0/12);
			//
			
			pnl_kl=new JPanel();
			pnl_kl.setLayout(new FlowLayout(FlowLayout.LEFT,0,2));
			pnl_kl.setOpaque(false);
			
			JScrollPane spl_k = new JScrollPane(pnl_kl);
			spl_k.setBounds(width*15+20, 10, 220, 440);
			spl_k.setOpaque(false);
			spl_k.getViewport().setOpaque(false);
			spl_k.setBorder(null);
			add(spl_k);
			
			JPanel pnl_taskbtn=new JPanel();
			pnl_taskbtn.setLayout(new FlowLayout(FlowLayout.RIGHT,0,2));
			pnl_taskbtn.setBounds(width*15+20, 450, 220, 40);
			pnl_taskbtn.setOpaque(false);
			add(pnl_taskbtn);
			
			JPanel pnl_bk=func.getImgPanel("tskbk.png");
			pnl_bk.setBounds(width*15+20, 10, 220, 440);
			add(pnl_bk);
			
			paintTask();
			
			JButton btn_new_tsk = new JButton("+ 任务");
			btn_new_tsk.setPreferredSize(new Dimension(80,25));
			func.setButtonIcon(btn_new_tsk, "btn1.png", "btn2.png", "btn3.png");
			btn_new_tsk.setFont(new Font("黑体",Font.BOLD,12));
			btn_new_tsk.setForeground(Color.WHITE);
			btn_new_tsk.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					TaskInfo info_tsk=new TaskInfo(null, null, current_sms);
					info_tsk.setModal(true);
					info_tsk.setAlwaysOnTop(true);
					info_tsk.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					info_tsk.setVisible(true);
					
		/*			if (info_tsk.showtxt!=null){
						tsk_cnt+=1;
						pnl_kl.add(new TaskPanel(info_tsk.showtxt));
						pnl_kl.setPreferredSize(new Dimension(180,18*tsk_cnt));
						pnl_kl.updateUI();
					} */
					paintTask();
					pnl_kl.updateUI();
				}
			});
			pnl_taskbtn.add(btn_new_tsk);
		}

		private void updateCourseList() {
			courseList=current_sms.getAllCourses();
			System.out.println("Course added " + courseList.size());
		    for(int i=0; i<7; i++) {
		    	pnl_days[i].removeAll();
			}
			for (Course course:courseList){
				addClassBar(course);
			}
		}
		
		private void paintTask(){
			pnl_kl.removeAll();
			tsk_cnt=0;
			for(Course course : courseList) {
				List<Task> tasks = course.getTasks();
				for (Task task : tasks) {
					pnl_kl.add(new TaskPanel(task));
					pnl_kl.setPreferredSize(new Dimension(180,18*tsk_cnt));
					tsk_cnt++;
				}
			}
		}
		
		void addClassBar(Course course){ 
			String name = course.getName();
			String[] alternative = {"每周", "单周", "双周"};
			List<CourseTime> courseTimes = course.getTimes();
			Color c=func.getColor();
			for (CourseTime courseTime : courseTimes)
			{
				int day=courseTime.getDayOfWeek();
				String alterWeek = alternative[courseTime.getAlternateWeek()];
				JButton cls=new JButton("["+alterWeek+"]"+name);
				cls.setMargin(new Insets(0, 0, 0, 0));
				cls.setBackground(c);
				cls.setBorder(null);
				double begin_rate = 30*(courseTime.getStartHour()-8+
						courseTime.getStartMinute()/60.0);
				double end_rate = 30*(courseTime.getEndHour()-8+
						courseTime.getEndMinute()/60.0);
				
				cls.setBounds(2, (int)(begin_rate), width*2-4, (int)(end_rate-begin_rate));
				cls.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						CourseInfo info_crs=new CourseInfo(course);
						info_crs.setModal(true);
						info_crs.setAlwaysOnTop(true);
						info_crs.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						info_crs.setVisible(true);
						info_crs.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent windowEvent) {
								super.windowClosed(windowEvent);
								updateCourseList();
							}
						});
						
						/////更新    更新不了？？？
						paintTask();
						pnl_main.updateUI();
					}
				});
				pnl_days[day].add(cls);
			}	
		}
		
		public class TaskPanel extends JPanel{
			
			private JButton btn_item;
			
			public TaskPanel(Task task){
				setPreferredSize(new Dimension(180, 18));
				setLayout(null);
				if(task.getStatus()==0)
					setBackground(new Color(242,165,165));
				else
					setBackground(new Color(165,239,165));
				
				btn_item=new JButton(task.getName()+"  "+task.getEndTime());
				btn_item.setBounds(0, 0, 162, 18);
				btn_item.setContentAreaFilled(false);
				btn_item.setBorderPainted(false);
				btn_item.setHorizontalAlignment(SwingConstants.LEFT); 
				btn_item.setFont(new Font("黑体",Font.BOLD,13));
				btn_item.setForeground(Color.DARK_GRAY);
				btn_item.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						TaskInfo info_tsk=new TaskInfo(task, task.getCourse(), current_sms);
						info_tsk.setModal(true);
						info_tsk.setAlwaysOnTop(true);
						info_tsk.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						info_tsk.setVisible(true);

						//修改任务
						paintTask();
						pnl_kl.updateUI();
					}
				});
				this.add(btn_item);
				
				JButton btn_del=new JButton("");
				func.setButtonIcon(btn_del, "x1.png", "x2.png", "x3.png");
				btn_del.setBounds(162, 0, 18, 18);
				btn_del.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//havent remove//
						task.deleteFromDatabase();
						TaskPanel.this.setVisible(false);
						tsk_cnt-=1;
						pnl_kl.setPreferredSize(new Dimension(180,18*tsk_cnt));
						paintTask();
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
		private JTextField txt_start;
		private JTextField txt_end;
		private Vector<Semester0> sms_l;

		public SemesterPanel() {
			setLayout(null);
			
			pnl_sms_l=new JPanel();
			pnl_sms_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			pnl_sms_l.setBackground(Color.CYAN);
			
			sms_l = new Vector<Semester0>();
			for(int i=0; i<semesters.size(); i++){
				sms_l.add(new Semester0(semesters.get(i)));
			}
			
			int h=0;
			for (int i=0;i<sms_l.size();i++){
				sms_l.get(i).setPreferredSize(new Dimension(750,sms_l.get(i).height));
				pnl_sms_l.add(sms_l.get(i));
				h+=sms_l.get(i).height;
			}
			pnl_sms_l.setPreferredSize(new Dimension(750, h));
			
			JScrollPane spnl_sms = new JScrollPane(pnl_sms_l);
			spnl_sms.setBounds(10, 10, 784, 440);
			add(spnl_sms);
			
			JPanel pnl_btmbtn = new JPanel();
			pnl_btmbtn.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
			pnl_btmbtn.setBounds(10, 440, 784, 40);
			add(pnl_btmbtn);
			
			JLabel lb_name = new JLabel("名称：");
			pnl_btmbtn.add(lb_name);
			txt_sms=new JTextField("新学期");
			pnl_btmbtn.add(txt_sms);
			JLabel lb_start = new JLabel("从");
			pnl_btmbtn.add(lb_start);
			txt_start=new JTextField("yyyy-mm-dd");
			pnl_btmbtn.add(txt_start);
			JLabel lb_end = new JLabel("到");
			pnl_btmbtn.add(lb_end);
			txt_end=new JTextField("yyyy-mm-dd");
			pnl_btmbtn.add(txt_end);
			
			JButton btn_new=new JButton("新增学期");
			btn_new.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Semester semester = db.addSemester(UUID.randomUUID().toString(), txt_sms.getText(),
			                Date.valueOf(txt_start.getText()), Date.valueOf(txt_end.getText()));
					
					Semester0 new_sms=new Semester0(semester);
					sms_l.add(new_sms);
					pnl_sms_l.add(new_sms);
					paintSmsList();
				}
			});
			pnl_btmbtn.add(btn_new);
		}
		
		public void paintSmsList(){
			int height=0;
			for (int i=0;i<sms_l.size();i++){
				if (sms_l.get(i).isVisible()){
					sms_l.get(i).setPreferredSize(new Dimension(750,sms_l.get(i).height));
					height+=sms_l.get(i).height;
				}
			}
			pnl_sms_l.setPreferredSize(new Dimension(750, height));
			pnl_sms_l.updateUI();
		}
		
		private class Semester0 extends JPanel{
			
			private JPanel pnl_crs_l; 
			private JPanel pnl_title; 
			private JTextField txt_title;
			
			public int height;
			
			public Semester0() {
				super();
			}
			
			public Semester0(Semester semester){
				height=30;
				setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				
				pnl_title=new JPanel();
				pnl_title.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_title.setPreferredSize(new Dimension(750, 30));
				pnl_title.setBackground(Color.WHITE);
				pnl_crs_l=new JPanel();
				pnl_crs_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_crs_l.setBackground(Color.GREEN);
				
				String title_str;
				title_str = semester.getStartDate().toString()+"  到   "+
						semester.getEndDate().toString() + "  " + semester.getName();
				txt_title=new JTextField(title_str);
				txt_title.setPreferredSize(new Dimension(300, 30));
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
						current_sms = semester;
						card.show(pnl_main,"crs");
						pnl_main.updateUI();
					}
				});
				pnl_title.add(title);
				this.add(pnl_title);
				this.add(pnl_crs_l);
				
				/////////test//////////
				/*
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
				setVisible(true); */
			}
		}
	}

	
	
	////////////////
	//////Data//////
	////////////////
	public class DataPanel extends JPanel {

		private List<Data> dat_l = new ArrayList();
		private JPanel pnl_dat_l;
		private JPanel pnl_chart;
		private int sms_cnt=0;
		
		
		public class GPA {
			private int sms_num; //从1-n
			private double course_gpa;
			private double credit;
			private String coursename;
			public GPA(int sms_num, double score, double credit, String name) {
				this.sms_num = sms_num;
				if (score>=60) {
					this.course_gpa = 4 - 3*(100-score)*(100-score)/1600;
				}
				else {
					this.course_gpa = 0;
				}
				this.credit = credit;
				this.coursename = name;
			}
		}
		
		
		private List<Cheif.DataPanel.GPA> gpaList = new ArrayList();  
		
		{
			for(int i=0; i<semesters.size(); i++) {
				Semester semester = semesters.get(i);
				List<Course> courseList = semester.getAllCourses();
				for(Course course: courseList) {
					gpaList.add(new GPA(i+1, course.getScore(),course.getCredit(),course.getName()));
				}
				sms_cnt++;
			}
		}
		
		public double cal_gpa(int sms) //0表示总绩
		{
			double total = 0;
			double credits = 0;
			for(GPA gpa : gpaList) {
				if ((sms == 0) || (sms==gpa.sms_num)) {
					total += gpa.course_gpa * gpa.credit;
					credits += gpa.credit;
				}
			}
			return total/credits;
		}
		
		/**
		 * Create the panel.
		 */
		public DataPanel() {
			setLayout(null);
			
			pnl_dat_l=new JPanel();
			pnl_dat_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			
			
			
			//test//
			JLabel lb_l=new JLabel("平均绩点："+Double.toString(cal_gpa(0)));
			lb_l.setPreferredSize(new Dimension(360,30));
			pnl_dat_l.add(lb_l);
			////////
			for (int i=0; i<sms_cnt; i++){
				dat_l.add(new Data(i+1));
			}
			
			int h=0;
			//test
			int a=30;
			//
			
			for (int i=0;i<dat_l.size();i++){
				dat_l.get(i).setPreferredSize(new Dimension(360,dat_l.get(i).height));
				pnl_dat_l.add(dat_l.get(i));
				h+=dat_l.get(i).height;
			}
			pnl_dat_l.setPreferredSize(new Dimension(360, h+a));
			
			JScrollPane spnl_dat = new JScrollPane(pnl_dat_l);
			spnl_dat.setBounds(10, 10, 390, 470);
			add(spnl_dat);
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		    for (int i=0; i<sms_cnt; i++)
		    {
		    	dataset.addValue(cal_gpa(i+1), "GPA", Integer.toString(i+1));
		    }
		    JFreeChart gpachart = ChartFactory.createLineChart("GPA vs Semester","Semester","GPA",
		    		dataset, PlotOrientation.VERTICAL, true, true, false);

			ChartPanel chartPanel = new ChartPanel(gpachart);
			chartPanel.setBounds(450,100,300,300);
			chartPanel.setBackground(Color.WHITE);

			add(chartPanel);
		}
		

		
		
		
		public void paintSmsList(){
			int height=0;
			//test
			int a=30;
			//
			for (int i=0;i<dat_l.size();i++){
				if (dat_l.get(i).isVisible()){
					dat_l.get(i).setPreferredSize(new Dimension(360,dat_l.get(i).height));
					height+=dat_l.get(i).height;
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
			
			public Data(int tl){
				height=30;
				setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_title=new JPanel();
				pnl_title.setPreferredSize(new Dimension(360,30));
				pnl_title.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_title.setBackground(Color.WHITE);
				pnl_crs_l=new JPanel();
				pnl_crs_l.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				pnl_crs_l.setBackground(Color.GREEN);
				
				JLabel title=new JLabel(Integer.toString(tl));
				pnl_title.add(title);
				this.add(pnl_title);
				this.add(pnl_crs_l);
				
				List<String> crs = new ArrayList();
				for (GPA gpa : gpaList){
					if(gpa.sms_num == tl) {
						crs.add(gpa.coursename + "    " + String.format("%.1f", gpa.credit) + "  " + 
								String.format("%.2f", gpa.course_gpa));
					}
				}

				for(String c:crs){
					JPanel pnl_clsd=new JPanel();
					pnl_clsd.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
					pnl_clsd.setPreferredSize(new Dimension(360, 20));
					pnl_clsd.setBackground(Color.GREEN);
					pnl_clsd.add(new JLabel(c));
					pnl_crs_l.add(pnl_clsd);
				}
				pnl_crs_l.setPreferredSize(new Dimension(360,20*crs.size()));
				pnl_crs_l.setVisible(false);
				
				JButton btn_fold=new JButton("第"+Integer.toString(tl)+"学期  "+
							String.format("%.2f\t", cal_gpa(tl)) + "+");
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
							height=30+20*crs.size();
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
