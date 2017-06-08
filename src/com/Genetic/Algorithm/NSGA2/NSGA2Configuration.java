package com.Genetic.Algorithm.NSGA2;

import java.util.ArrayList;
import com.Genetic.Algorithm.GeneticConfiguration;

import com.Genetic.FitnessFunction;

/* ===========================================================
 * JNSGA2: a free NSGA-II library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2006-2007, Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 *
 * Project Info:  http://sourceforge.net/projects/jnsga2/
 *
 * This library is free software; you can redistribute it and/or modify it  under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

/**
 * This class stores the configuration information for a run of the
 * multi-objective genetic algorithm NSGA-II.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class NSGA2Configuration extends GeneticConfiguration {



	private double mutationProbability;
	private double crossoverProbability;


	/**
	 * Constructor.
	 * 
	 * @param fitnessFunctions
	 *            fitness function for the different objectives
	 * @param mutationProbability
	 *            mutation probability (0 <= . <= 1)
	 * @param crossoverProbability
	 *            crossover probability (0 <= . <= 1)
	 * @param populationSize
	 *            population size (must be divisible by four)
	 * @param numberOfGenerations
	 *            number of generations of the genetic algorithm
	 */
	public NSGA2Configuration(ArrayList<FitnessFunction> fitnessFunctions, int populationSize, int numberOfGenerations, double mutationProbability,
			double crossoverProbability)
	{
		super(fitnessFunctions, numberOfGenerations, numberOfGenerations);
		
		this.mutationProbability = mutationProbability;
		this.crossoverProbability = crossoverProbability;

	}


	/**
	 * Gets the mutation probability.
	 * 
	 * @return mutation probability
	 */
	public double getMutationProbability() {
		return this.mutationProbability;
	}

	/**
	 * Gets the crossover probability.
	 * 
	 * @return crossover probability
	 */
	public double getCrossoverProbability() {
		return this.crossoverProbability;
	}

}