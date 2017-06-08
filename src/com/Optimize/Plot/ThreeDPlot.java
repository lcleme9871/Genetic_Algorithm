package com.Optimize.Plot;

import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;

import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.JFrame;


import org.math.plot.Plot3DPanel;

import com.Optimize.TestCaseIndividual;



public class ThreeDPlot
{

	// private static Random rand = new Random();
	/**
	 * Displays results for a 3d scatter plot
	 * 
	 * @param list
	 * @param runTime
	 * @return
	 */
	String xName="";
	String yName="";
	String zName="";
	String title="";
	String folder="";
	String filename="";
	public ThreeDPlot(String xName, String yName, String zName, String title, String folder, String filename)
	{
		this.xName=xName;
		this.yName=yName;
		this.zName=zName;
		this.title=title;
		this.folder=folder;
		this.filename=filename;
		
	}
	public JFrame plot3DScatter(List<TestCaseIndividual> list)
	{
		double[] x = new double[list.size()];
		double[] y = new double[list.size()];
		double[] z = new double[list.size()];

		for (int i = 0; i < x.length; i++) {
			x[i] = list.get(i).getFitnessValue(0);
			y[i] = list.get(i).getFitnessValue(1);
			z[i] = list.get(i).getFitnessValue(2);
		}
		// create your PlotPanel (you can use it as a JPanel)
		Plot3DPanel plot = new Plot3DPanel();
		plot.setBackground(Color.black);
		// plot.setBackground(Color.lightGray);

		// add a line plot to the PlotPanel
		plot.addScatterPlot("ComplexScS", Color.RED, x, y, z);
		plot.setFixedBounds(0,0,1);// x data range is [0,1]
		plot.setFixedBounds(1,0,1);// y data range is [0,1]
		//plot.setFixedBounds(2,0,1);// y data range is [0,1]
		//plot.setFixedBounds(2,0,Constants.getTotalMilliseconds());// z data range is [0,100000]
		// plot.getAxis(0).setLabelPosition(0.1);
		Font timesNewRoman = new Font("Times New Roman", Font.CENTER_BASELINE, 12);
		for (int i = 0; i < 3; i++) {
			plot.getAxis(i).setLabelFont(timesNewRoman);
			//plot.getAxis(i).setLightLabelFont(timesNewRoman);
		}
		plot.setAxisLabels(xName,yName,zName);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setContentPane(plot);
		frame.setVisible(true);
		saveChart(frame);
		return frame;
	}

	public void saveChart(JFrame f) 
	{
	 	String dir ="results/plot/ThreeD/"+filename;

		   try
	        {
			   
	            BufferedImage image = new BufferedImage(500,500, BufferedImage.TYPE_INT_RGB);
	            Graphics2D graphics2D = image.createGraphics();
	            f.paint(graphics2D);
	            ImageIO.write(image,"jpeg", new File(dir));
	        }
	        catch(Exception exception)
	        {
	            //code
	        }

	}
}
