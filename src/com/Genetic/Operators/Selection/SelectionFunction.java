package com.Genetic.Operators.Selection;



import java.util.List;
import java.util.Random;
import com.Genetic.util.*;

import com.Genetic.Individual;



	/**
	 * {@inheritDoc}
	 *
	 * Select individual by rank
	 */
public class RankSelectionFunction<T extends Individual> extends SelectionFunction<T> {

	private static final long serialVersionUID = 7849303009915557682L;
	private Random rand = new Random();
	private double RANK_BIAS=1.7;
	
	@Override
	/**
	 * Select index of next offspring
	 * 
	 * Population has to be sorted!
	 */
	public int getIndex(List<T> population) {
		double r = rand.nextDouble();
		double d = RANK_BIAS
		        - Math.sqrt((RANK_BIAS * RANK_BIAS)
		                - (4.0 * (RANK_BIAS - 1.0) * r));
		int length = population.size();

		d = d / 2.0 / (RANK_BIAS - 1.0);

		//this is not needed because population is sorted based on Maximization
		//if(maximize)
		//	d = 1.0 - d; // to do that if we want to have Maximisation

		int index = (int) (length * d);
		return index;
	}

}
