package com.Genetic.Operators.Crossover;




import java.io.Serializable;

import com.Genetic.GeneticFailException;
import com.Genetic.Individual;


/**
 * Cross over two individuals
 * 
 * @author Lee Clement
 */
public abstract class CrossoverFunction implements Serializable
{

	private static final long serialVersionUID = -4765602400132319324L;



	/**
	 * Replace parents with crossed over individuals
	 * 
	 * @param parent1
	 *            a {@link org.evosuite.ga.Chromosome} object.
	 * @param parent2
	 *            a {@link org.evosuite.ga.Chromosome} object.
	 * @throws org.evosuite.ga.ConstructionFailedException
	 *             if any.
	 */
	public abstract void crossOver(Individual i1,Individual i2)
	        throws GeneticFailException;

}
