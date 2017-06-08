package com.Optimize.Plot;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;

import com.Optimize.TestCaseIndividual;


/**
 * A graph class to display a line graph
 *  * @author Lee Clement
 * @author www.codejava.net
 *
 */
public class TwoDPlot extends JFrame 
 {
	private LinkedHashMap<String,LinkedList<TestCaseIndividual>> dataMap;
	XYSeriesCollection dataset;
    private String folder ="";
    private String yName="";
    private String chartTitle="";
    private String xName="";
    private String filename="";
    public TwoDPlot(String xName, String yName,String title, String folder, String filename)
    {
    	dataMap= new LinkedHashMap<String,LinkedList<TestCaseIndividual>>();
    	dataset = new XYSeriesCollection();
        this.folder=folder;
        this.xName=xName;
        this.filename=filename;
        this.yName=yName;
        this.chartTitle=title;
               
    }
 
    /**
     * Builds JPanel
     * @param runTime
     * @return
     * @throws NoChartDataException 
     */
    public void makeChart() throws NoChartDataException
    {
    	if(dataMap.isEmpty())
    		throw new NoChartDataException("Enter chart data");
    	
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	    this.setVisible(true);
    }
    private JPanel createChartPanel()
    {
    	for (Entry<String, LinkedList<TestCaseIndividual>> entry : dataMap.entrySet())
    	{
    		addSeries(entry.getKey(),entry.getValue()); 
    	}

    	    JFreeChart chart = ChartFactory.createScatterPlot(chartTitle,xName,yName, dataset, PlotOrientation.HORIZONTAL, false, false, false);
    	    LegendTitle legend = new LegendTitle(chart.getPlot());
    	    legend.setItemFont(new Font("Times New Roman", Font.ROMAN_BASELINE,12));
    	    legend.setBorder(0, 0, 0, 0);
    	    legend.setBackgroundPaint(Color.WHITE);
    	    legend.setPosition(RectangleEdge.BOTTOM);

    	    RectangleInsets padding = new RectangleInsets(5, 5, 5, 5);
    	    legend.setItemLabelPadding(padding);

    	    chart.addLegend(legend);
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setDataset(dataset);
    	    Font timesNewRoman = new Font("Times New Roman", Font.ROMAN_BASELINE,12);
    	    
            chart.setBackgroundPaint(Color.white);
            
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(Color.black);
            plot.setRangeGridlinePaint(Color.black);
            plot.setAxisOffset(new RectangleInsets(4.0, 4.0, 4.0, 4.0));
            plot.setDomainCrosshairVisible(true);
            plot.setRangeCrosshairVisible(true);
            
            Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
            //NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
            plot.getDomainAxis().setRange(0.00, 1.00);
            //plot.getRangeAxis().setRange(0.00, 1);
            //plot.getDomainAxis().setCategoryMargin(0.0);
            plot.getDomainAxis().setTickMarksVisible(true);
            plot.getRangeAxis().setTickMarksVisible(true);
            NumberAxis dAxis = (NumberAxis) plot.getDomainAxis();
             NumberAxis rAxis = (NumberAxis) plot.getRangeAxis();
            dAxis.setTickUnit(new NumberTickUnit(0.1));
            rAxis.setTickUnit(new NumberTickUnit(0.1));
            //Set up some Font stuff
            chart.getTitle().setFont(timesNewRoman);
            plot.getDomainAxis().setAxisLineStroke(new BasicStroke(1.5f));
            plot.getRangeAxis().setAxisLineStroke(new BasicStroke(1.5f));
            plot.getDomainAxis().setTickMarkStroke(new BasicStroke(1.5f));
            plot.getRangeAxis().setTickMarkStroke(new BasicStroke(1.5f));
            plot.getDomainAxis().setLabelFont(timesNewRoman);
            plot.getRangeAxis().setLabelFont(timesNewRoman);
            plot.getDomainAxis().setTickLabelFont(timesNewRoman);
            plot.getRangeAxis().setTickLabelFont(timesNewRoman);
            // set data set colour
            Color[] colorArray ={Color.red,Color.GREEN,Color.BLACK};
            for(int i=0; i<dataMap.size(); i++)
            {
              plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(i,colorArray[i]);
            }


            XYItemRenderer r = plot.getRenderer();
            if (r instanceof XYLineAndShapeRenderer)
            {
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
                renderer.setBaseShapesVisible(true);
                renderer.setBaseShapesFilled(true);
                renderer.setSeriesShape(0, cross);
                renderer.setDrawSeriesLineAsPath(true);
               // renderer.setPaint(Color.RED);
                renderer.setBaseItemLabelFont(timesNewRoman);
                //renderer.setBaseShape(new Ellipse2D.Double());
            }
            
            //save chart
            saveChart(chart,500,500,xName,yName);
            
   	    plot.setRenderer(r);
   	  ChartPanel panel = new ChartPanel(chart);
      panel.setFillZoomRectangle(true);
      panel.setMouseWheelEnabled(true);
      
    	    return panel;
    
    }
    
    /**
     * Generates dataset for individual list
     * @param clist
     * @return
     */
    private XYDataset createDataset(List<TestCaseIndividual>list) 
    {
        DefaultXYDataset data = new DefaultXYDataset();
        double[][] values = new double[2][2];
        double[] Xs = new double[list.size()];
        double[] Ys = new double[list.size()];
        for (int i = 0; i < Xs.length; i++)
        {
            Ys[i] = list.get(i).getFitnessValue(0);
            Xs[i] = list.get(i).getFitnessValue(1);
            
        }
        values[0] = Xs;
        values[1] = Ys;

        data.addSeries("Test", values);

 	    return dataset;
    }
    /**
     * Generates a series for a list
     * * @param title
     * * @param list
     */
    private void addSeries(String title,List<TestCaseIndividual>list) 
    {
 	    XYSeries series = new XYSeries(title);
         for(int i=0; i<list.size(); i++)
            series.add(list.get(i).getFitnessValue(0),list.get(i).getFitnessValue(1));
         dataset.addSeries(series);
    }

    public void saveChart(JFreeChart chart, int height, int width, String obj1, String obj2)
    {
    	String dir ="results/plot/TwoD/";
        File chartToSave = new File(dir+filename); 
        try {
			ChartUtilities.saveChartAsJPEG(chartToSave , chart , width , height );
			System.out.println("Saved Chart successfully, in:"+dir);
		} 
        catch (IOException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    }

	/**
	 * @param str
	 * @param list
	 */
	public void addList(String str, LinkedList<TestCaseIndividual>list)
	{
		dataMap.put(str,list);
	}



}
