package mygui;
import java.awt.Font;  
import java.text.SimpleDateFormat;  
  
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class GPAchart extends ApplicationFrame{
	   public GPAchart( String applicationTitle , String chartTitle )
	   {
	      super(applicationTitle);
	      JFreeChart lineChart = ChartFactory.createLineChart(
	         chartTitle,
	         "GPA","Semester",
	         createDataset(),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	   }

	   private DefaultCategoryDataset createDataset( )
	   {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      dataset.addValue( 15 , "GPA" , "1970" );
	      dataset.addValue( 30 , "GPA" , "1980" );
	      dataset.addValue( 60 , "GPA" ,  "1990" );
	      return dataset;
	   }
	   public void main( String[ ] args ) 
	   {
	      GPAchart chart = new GPAchart("GPA" , "Semester");

	      chart.pack( );
	      RefineryUtilities.centerFrameOnScreen( chart );
	      chart.setVisible( true );
	   }
}
