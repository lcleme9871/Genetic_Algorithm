package com.Genetic.Operators.Crossover;

import com.Genetic.Individual;

import java.util.Random;

import com.Genetic.GeneticFailException;


/**
 * Cross individuals at identical point
 *
 * @author Lee Clement
 */
public class SinglePointCrossoverFunction extends CrossoverFunction
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

		if (i1.getLength() < 2 || i2.getLength() < 2)
		{
			return;
		}

		int point = rand.nextInt(Math.min(i1.getLength(), i2.getLength()) - 1) + 1;

		Individual t1 = i1.clone();
		Individual t2 = i2.clone();

		i1.crossover(t2, point);
		i2.crossover(t1, point);
	}

}
