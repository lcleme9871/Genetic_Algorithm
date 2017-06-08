package com.Genetic.Algorithm.NSGA2;

import java.util.ArrayList;
import com.Genetic.Algorithm.GeneticConfiguration;

import com.Genetic.FitnessFunction;

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
