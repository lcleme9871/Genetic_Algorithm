package com.Genetic.Algorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


import com.Genetic.FitnessFunction;
import com.Genetic.Individual;
import com.Genetic.Comparators.DominanceComparator;


public abstract class GeneticAlgorithm {

	/**
	 * List of fitness functions for getting fitness values from individuals
	 */
	protected List<FitnessFunction> fitnessFunctions = new ArrayList<FitnessFunction>();


	/**
	 * a comparator for testing dominance
	 */
	private DominanceComparator dc = new DominanceComparator();
	private GeneticConfiguration conf;
	private HashSet<GeneticListener> listeners;

	/**
	 * Constructor.
	 *
	 * @param conf
	 *            configuration
	 */
	public GeneticAlgorithm(GeneticConfiguration conf) {
		if (conf == null) {
			throw new IllegalArgumentException("'conf' must not be null.");
		}

		this.conf = conf;
		listeners = new HashSet<GeneticListener>();
	}

	/**
	 * Gets the NSGA-II configuration.
	 *
	 * @return NSGA-II configuration
	 */
	public GeneticConfiguration getGeneticConfiguration() {
		return conf;
	}

	/**
	 * Adds the specified NSGA-II listener.
	 *
	 * @param nsga2listener
	 *            NSGA-II listener
	 */
	public void addGeneticListener(GeneticListener nsga2listener) {
		if (nsga2listener == null) {
			throw new IllegalArgumentException("'nsga2listener' must not be null.");
		}

		listeners.add(nsga2listener);
	}

	/**
	 * Removes the specified NSGA-II listener.
	 *
	 * @param nsga2listener
	 *            NSGA-II listener
	 */
	public void removeGeneticListener(GeneticListener nsga2listener) {
		if (nsga2listener == null) {
			throw new IllegalArgumentException("'nsga2listener' must not be null.");
		}

		listeners.remove(nsga2listener);
	}

	/**
	 * Initializes and runs the NSGA-II algorithm.
	 *
	 * @param startPopulation
	 *            start population (population size as in configuration!)
	 * @return best individuals after NSGA-II run (only non-dominated ones =>
	 *         rank 1)
	 */
	public abstract LinkedList<Individual> evolve(LinkedList<Individual> startPopulation);


	/**
	 * Makes a fast non-domination sort of the specified individuals. The method
	 * returns the different domination fronts in ascending order by their rank
	 * and sets their rank value.
	 *
	 * @param individuals
	 *            individuals to sort
	 * @return domination fronts in ascending order by their rank
	 */
	protected LinkedList<LinkedList<Individual>> fastNonDominatedSort(LinkedList<Individual> union)
	{
		if (union == null) {
			throw new IllegalArgumentException("'union' must not be null.");
		}

		LinkedList<LinkedList<Individual>> fronts = new LinkedList<LinkedList<Individual>>();

		HashMap<Individual, LinkedList<Individual>> dominatedIndividuals = new HashMap<Individual, LinkedList<Individual>>();
		HashMap<Individual, Integer> noOfDominatingIndividuals = new HashMap<Individual, Integer>();

		for (Individual P : union) {
			// Initialize the map of individuals that dominates and
			// map of the individuals that contain the number of dominated
			// individuals
			dominatedIndividuals.put(P, new LinkedList<Individual>());
			noOfDominatingIndividuals.put(P, 0);

			// for each p and q, calculate if p dominates q or vice versa
			for (Individual Q : union) {
				if (dc.compare(P, Q) == -1) {
					dominatedIndividuals.get(P).add(Q);
				} else {
					if (dc.compare(Q, P) == -1) {
						noOfDominatingIndividuals.put(P, noOfDominatingIndividuals.get(P) + 1);
					}
				}
			}

			// if nobody dominates p, p belongs to the first front
			if (noOfDominatingIndividuals.get(P) == 0) {

				P.setRank(1);
				if (fronts.isEmpty()) {
					LinkedList<Individual> firstDominationFront = new LinkedList<Individual>();
					firstDominationFront.add(P);
					fronts.add(firstDominationFront);
				} else {
					LinkedList<Individual> firstDominationFront = fronts.getFirst();
					firstDominationFront.add(P);
				}
			}
		}

		// obtain the rest of fronts
		int i = 1;
		while (fronts.size() == i) {
			LinkedList<Individual> nextDominationFront = new LinkedList<Individual>();
			for (Individual P : fronts.get(i - 1)) {
				for (Individual Q : dominatedIndividuals.get(P)) {
					noOfDominatingIndividuals.put(Q, noOfDominatingIndividuals.get(Q) - 1);
					if (noOfDominatingIndividuals.get(Q) == 0) {
						Q.setRank(i + 1);
						nextDominationFront.add(Q);
					}
				}
			}
			i++;
			if (!nextDominationFront.isEmpty()) {
				fronts.add(nextDominationFront);
			}
		}

		return fronts;
	}

}

