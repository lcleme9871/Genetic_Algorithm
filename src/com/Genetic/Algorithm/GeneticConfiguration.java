package com.Genetic.Algorithm;

import java.util.ArrayList;

import com.Genetic.FitnessFunction;

public class GeneticConfiguration {

	private ArrayList<FitnessFunction> fitnessFunctions;
	private int populationSize;
	private int numberOfGenerations;

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
	public GeneticConfiguration(ArrayList<FitnessFunction> fitnessFunctions,int populationSize, int numberOfGenerations)
	{
		if (fitnessFunctions == null) {
			throw new IllegalArgumentException("'fitnessFunctions' must not be null.");
		}
		if (populationSize <= 0) {
			throw new IllegalArgumentException("'populationSize' must be a positive number.");
		}
		//if (populationSize % 4 != 0)
	//	{
	//		throw new IllegalArgumentException("'populationSize' must be divisible by four.");
		//}
		if (numberOfGenerations <= 0) {
			throw new IllegalArgumentException("'numberOfGenerations' must be a positive number.");
		}

		this.fitnessFunctions = fitnessFunctions;
		this.populationSize = populationSize;
		this.numberOfGenerations = numberOfGenerations;
	}

	/**
	 * Gets the fitness function for the index-th objective (0 ... n-1).
	 * 
	 * @param index
	 *            index
	 * @return fitness function for the index-th objective
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds
	 */
	public FitnessFunction getFitnessFunction(int index) throws IndexOutOfBoundsException {
		return this.fitnessFunctions.get(index);
	}

	/**
	 * @return the fitnessFunctions
	 */
	public ArrayList<FitnessFunction> getFitnessFunctions() {
		return fitnessFunctions;
	}

	/**
	 * Gets the number of objectives.
	 * 
	 * @return number of objectives
	 */
	public int getNumberOfObjectives() {
		return this.fitnessFunctions.size();
	}


	/**
	 * Gets the population size of one generation of the genetic algorithm.
	 * 
	 * @return population size
	 */
	public int getPopulationSize() {
		return this.populationSize;
	}

	/**
	 * Gets the number of generations of the genetic algorithm.
	 * 
	 * @return number of generations
	 */
	public int getNumberOfGenerations() {
		return this.numberOfGenerations;
	}
}
