package com.Genetic.Operators.Selection;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.Genetic.Individual;


/**
 * Abstract base class of selection functions
 * 
 * @author Lee Clement
 */
public abstract class SelectionFunction<T extends Individual> implements Serializable {

	private static final long serialVersionUID = -2514933149542277609L;



	/**
	 * Do we want to minimize or maximize fitness?
	 */
	protected boolean maximize = true;

	/**
	 * Return index of next offspring
	 * 
	 * @param population
	 *            a {@link java.util.List} object.
	 * @return a int.
	 */
	public abstract int getIndex(List<T> population);

	/**
	 * Return two parents
	 * 
	 * @param population
	 *            a {@link java.util.List} object.
	 * @return a {@link org.evosuite.ga.Chromosome} object.
	 */
	public T select(List<T> population) {
		return select(population, 1).get(0);
	}

	/**
	 * Return n parents
	 * 
	 * @param population
	 *            a {@link java.util.List} object.
	 * @param number
	 *            n
	 * @return a {@link java.util.List} object.
	 */
	public List<T> select(List<T> population, int number) {
		List<T> offspring = new ArrayList<T>();
		for (int i = 0; i < number; i++) {
			offspring.add(population.get(getIndex(population)));
		}
		return offspring;
	}

	/**
	 * Are we maximizing or minimizing fitness?
	 * 
	 * @param max
	 *            a boolean.
	 */
	public void setMaximize(boolean max) {
		maximize = max;
	}

	/**
	 * <p>
	 * isMaximize
	 * </p>
	 * 
	 * @return true is we have to maximize
	 */
	public boolean isMaximize() {
		return maximize;
	}

}
