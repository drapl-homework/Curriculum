package mygui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

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
import java.sql.Date;
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
import java.util.UUID;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;

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

public class func {
	static private final Color[] clr={
			Color.decode("#f08080"),
			Color.decode("#87ceeb"),
			Color.decode("#ffa07a"),
			Color.decode("#f0e68c"),
			Color.decode("#dda0dd"),
			Color.decode("#7fffd4"),
			Color.decode("#c0c0c0"),
			Color.decode("#b0c4de")};
	static private int cpoint = 0;
	
	
	func(){}
	
	static public void setButtonIcon(JButton button,String default_icon,String Rollover_icon,String Pressed_icon){
		button.setIcon(new ImageIcon("./ico/"+default_icon));
		button.setRolloverIcon(new ImageIcon("./ico/"+Rollover_icon));
		button.setPressedIcon(new ImageIcon("./ico/"+Pressed_icon));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setIconTextGap(0);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);
	}
	
	
	
	static public JPanel getImgPanel(String background){
		JPanel pnl=new JPanel(){
			public void paintComponent(Graphics g){
				ImageIcon icon=new ImageIcon("./ico/"+background);
				g.drawImage(icon.getImage(), 0, 0, null,null);

			}
		};
		pnl.setOpaque(false);
		return pnl;
	}
	
	static public Color getColor(){
		return clr[cpoint++%clr.length];
	}
}
