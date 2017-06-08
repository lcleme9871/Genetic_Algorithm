package com.Optimize.Plot;

/**
 * Exception used when an data date is entered into a chart
 * @author Lee Clement
 */
public class NoChartDataException extends Exception {




	/**
	 * <p>Constructor for NoChartFailedException.</p>
	 *
	 * @param reason 
	 */
	public NoChartDataException(String reason) 
	{
		super(reason);
	}

}