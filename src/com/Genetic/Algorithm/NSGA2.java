package com.Genetic.Algorithm.NSGA2;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.Genetic.GeneticFailException;
import com.Genetic.FitnessFunction;
import com.Genetic.Individual;
import com.Genetic.Comparators.ArrangeByFitness;
import com.Genetic.Comparators.CrowdingComparator;
import com.Genetic.Comparators.DominanceComparator;
import com.Genetic.Operators.Crossover.*;
import com.Genetic.Operators.Selection.*;
import com.Genetic.Algorithm.*;
import java.util.Random;
import com.Genetic.util.*;

/**
 * * @article{Deb:2002, author = {Deb, K. and Pratap, A. and Agarwal, S. and
 * Meyarivan, T.}, title = {{A Fast and Elitist Multiobjective Genetic
 * Algorithm: NSGA-II}}, journal = {Trans. Evol. Comp}, issue_date = {April
 * 2002}, volume = {6}, number = {2}, month = apr, year = {2002}, issn =
 * {1089-778X}, pages = {182--197}, numpages = {16}, url =
 * {http://dx.doi.org/10.1109/4235.996017}, doi = {10.1109/4235.996017}, acmid =
 * {2221582}, publisher = {IEEE Press}, address = {Piscataway, NJ, USA}}
 * 
 * @author lc
 * @author jose campos
 */

public class NSGA2 extends GeneticAlgorithm {



	/**
	 * SelectionFunction for either Rank or Tournament Selection
	 */
	private SelectionFunction selectionFunction = new RankSelectionFunction();

	/**
	 * Single point fixed crossover
	 */
	// private SinglePointCrossOver crossoverFunction = new
	// SinglePointCrossOver();
	/**
	 * Single point crossover
	 */
	private CrossoverFunction crossoverFunction = new SinglePointCrossoverFunction();


	private Random rand;

	private NSGA2Configuration conf;
	private HashSet<GeneticListener> listeners;

	/**
	 * Constructor.
	 *
	 * @param conf
	 *            configuration
	 */
	public NSGA2(NSGA2Configuration conf) {
		super(conf);
		listeners = new HashSet<GeneticListener>();
	}


	/**
	 * Initializes and runs the NSGA-II algorithm.
	 *
	 * @param startPopulation
	 *            start population (population size as in configuration!)
	 * @return best individuals after NSGA-II run (only non-dominated ones =>
	 *         rank 1)
	 */
	public LinkedList<Individual> evolve(LinkedList<Individual> startPopulation) {
		if (startPopulation == null) {
			throw new IllegalArgumentException("'startPopulation' must not be null.");
		}

		LinkedList<Individual> parentPopulation = new LinkedList<Individual>();
		parentPopulation.addAll(startPopulation);
		LinkedList<Individual> offspringPopulation = new LinkedList<Individual>();
		// Create the offSpring population

		for (int currentGen = 0; currentGen < this.getGeneticConfiguration().getNumberOfGenerations(); currentGen++) {

			// create a offspring population Qt of size N
			// call NSGA-II listeners
			// check whether all individuals in start population have this
			// NSGA-II instance
			for (Individual individual : startPopulation) {
				if (individual.getNSGA2Instance() != this) {
					throw new IllegalArgumentException(
							"All individuals in start population must have this NSGA-II instance.");
				}
			}

			if (!listeners.isEmpty()) {
				// create NSGA-II event
				GeneticEvent event = new GeneticEvent(this, parentPopulation, currentGen);
				for (GeneticListener listener : listeners) {
					listener.performEvent(event);
				}
			}

			for (int i = 0; i < (parentPopulation.size() / 2); i++) {
				// Selection
				Individual parent1 = selectionFunction.select(parentPopulation);
				Individual parent2 = selectionFunction.select(parentPopulation);

				Individual offspring1 = parent1.clone();
				Individual offspring2 = parent2.clone();

				// Crossover
				try {

						crossoverFunction.crossOver(offspring1, offspring2);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Mutation


					try {
						offspring1.mutate();
						offspring2.mutate();
					} catch (GeneticFailException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				

				// evaluate
				// for(FitnessFunction ff:offspring1.getFitnessMap())
				// {
				// offspring1.update(ff);
				// offspring2.update(ff);
				// }

				offspringPopulation.add(offspring1);
				offspringPopulation.add(offspring2);
			}

			// Create the population union of Population and offSpring
			LinkedList<Individual> union = union(parentPopulation, offspringPopulation);

			// Ranking the union
			List<LinkedList<Individual>> ranking = fastNonDominatedSort(union);

			int remain = parentPopulation.size();
			int index = 0;
			List<Individual> front = null;
			parentPopulation.clear();

			// Obtain the next front
			front = ranking.get(index);

			while ((remain > 0) && (remain >= front.size())) {

				// Assign crowding distance to individuals
				crowingDistanceAssignment(front);

				// Add the individuals of this front
				for (int k = 0; k < front.size(); k++)
					parentPopulation.add(front.get(k));

				// Decrement remain
				remain = remain - front.size();

				// Obtain the next front
				index++;
				if (remain > 0)
					front = ranking.get(index);
			}

			// Remain is less than front(index).size, insert only the best one
			if (remain > 0) {
				// front contains individuals to insert
				crowingDistanceAssignment(front);

				Collections.sort(front, new CrowdingComparator(true));

				for (int k = 0; k < remain; k++)
					parentPopulation.add(front.get(k));

				remain = 0;
			}

			currentGen++;

		}
		LinkedList<Individual> bestIndividuals = new LinkedList<Individual>(parentPopulation);
		// call NSGA-II listeners
		if (!listeners.isEmpty()) {
			// create NSGA-II event
			GeneticEvent event = new GeneticEvent(this, bestIndividuals, conf.getNumberOfGenerations());
			for (GeneticListener listener : listeners)
			{
				listener.performEvent(event);
			}
		}

		return bestIndividuals;
	}

	/**
	 * Assigns crowding distance to chromosomes
	 * 
	 * @param f
	 */
	protected void crowingDistanceAssignment(List<Individual> f) {
		int size = f.size();

		if (size == 0)
			return;
		if (size == 1) {
			f.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}
		if (size == 2) {
			f.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			f.get(1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}

		// use a new Population List to avoid altering the original Population
		LinkedList<Individual> front = new LinkedList<Individual>();
		front.addAll(f);

		for (int i = 0; i < size; i++)
			front.get(i).setCrowdingDistance(0.0);

		double objetiveMaxn;
		double objetiveMinn;
		double distance;

		for (final FitnessFunction ff : this.getGeneticConfiguration().getFitnessFunctions())
				{
			// Sort the population by Fit n
			Collections.sort(front, new ArrangeByFitness(ff, false));

			objetiveMinn = front.get(0).getFitnessByFunc(ff);
			objetiveMaxn = front.get(front.size() - 1).getFitnessByFunc(ff);

			// set crowding distance
			front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			front.get(size - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			if (objetiveMinn != objetiveMaxn) {
				for (int j = 1; j < size - 1; j++) {
					distance = front.get(j + 1).getFitnessByFunc(ff) - front.get(j - 1).getFitnessByFunc(ff);
					distance = distance / (objetiveMaxn - objetiveMinn);
					distance += front.get(j).getCrowdingDistance();
					// System.out.println(distance);
					front.get(j).setCrowdingDistance(distance);

				}
			}
		}
	}


	/**
	 * Combines both original population and children to create a union of both
	 * 
	 * @param population
	 * @param offspringPopulation
	 * @return result
	 */
	protected LinkedList<Individual> union(LinkedList<Individual> population,
			LinkedList<Individual> offspringPopulation) {
		if (population == null) {
			throw new IllegalArgumentException("'population' must not be null.");
		}
		if (offspringPopulation == null) {
			throw new IllegalArgumentException("'offspringPopulation' must not be null.");
		}

		LinkedList<Individual> result = new LinkedList<Individual>(population);
		result.addAll(offspringPopulation);

		return result;
	}

}
