package com.Genetic.Operators.Crossover;

import com.Genetic.Individual;

import java.util.Random;

import com.Genetic.GeneticFailException;


/**
 * Cross individuals at identical point
 *
 * @author Lee Clement
 */
public class BinaryPointCrossoverFunction extends CrossoverFunction
{

	private static final long serialVersionUID = 1215946828935020651L;
	private Random rand;

	/**
	 * {@inheritDoc}
	 *
	 * The splitting point for to individuals p1, p2 is selected within
	 * min(length(p1),length(p2))
	 */
	@Override
	public void crossOver(Individual i1,Individual i2)
	        throws GeneticFailException {

		if (i1.getLength() < 2 || i2.getLength() < 2) {
			return;
		}
		// Choose a position in the middle
		int point1 = rand.nextInt(i1.getLength() - 1) + 1;
		int point2 = rand.nextInt(i2.getLength() - 1) + 1;

		Individual t1 = i1.clone();
		Individual t2 = i2.clone();

		i1.crossover(t2, point1, point2);
		i2.crossover(t1, point2, point1);
	}

}
