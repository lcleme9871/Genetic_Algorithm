package com.Genetic.Operators.Selection;




import java.util.List;
import java.util.Random;
import com.Genetic.util.*;
import com.Genetic.Individual;



/**
 * Select an individual from a population as winner of a number of tournaments
 *
 * @author Lee Clement
 */
public class TournamentSelectionFunction<T extends Individual> extends SelectionFunction<T>
{

	private static final long serialVersionUID = -7465418404056357932L;
	private Random rand;
	/**
	 * {@inheritDoc}
	 *
	 * Perform the tournament on the population, return one index
	 */
	@Override
	public int getIndex(List<T> population) {
		int new_num = rand.nextInt(population.size());
		int winner = new_num;


		

		return winner;
	}

}
