package com.Optimize;

import com.Genetic.FitnessFunction;
import com.Genetic.Individual;

import com.Genetic.Algorithm.NSGA2.NSGA2;
import com.Genetic.Algorithm.NSGA2.NSGA2Configuration;

import java.io.File;
import java.util.*;


/**
 *
 * Represents an individual Solution to the test suite optimisation problem
 *
 * @author Lee Clement
 */
public class TestCaseIndividual extends Individual {

	private double[] fitnessValues;
	private ArrayList<JavaClass> tests; // list of tests
	private ArrayList<JavaClass> sequence; // map of tests
	private NSGA2Configuration conf;

	private File results;


	/**
	 * Constructor.
	 * 
	 * @param nsga2
	 *            NSGA-II instance this individual is used for
	 */
	public TestCaseIndividual(NSGA2 nsga2) {
		super(nsga2);
	}

	public TestCaseIndividual(NSGA2 nsga2,ArrayList<JavaClass> sequence,ArrayList<JavaClass> tests) {
		super(nsga2);
		this.tests = tests;
		this.sequence=sequence;
		this.conf = (NSGA2Configuration) nsga2.getGeneticConfiguration();
		fitnessValues = new double[Constants.OBJECTIVES];
		for (int i = 0; i < fitnessValues.length; i++) {
			fitnessValues[i] =  conf.getFitnessFunction(i).evaluate(this);
		}

	}

	/**
	 * @return the tests
	 */
	public ArrayList<JavaClass> getTests() {
		return tests;
	}

	/**
	 * @param tests
	 *            the tests to set
	 */
	public void setTests(ArrayList<JavaClass> tests) {
		this.tests = tests;
	}
	
	/**
	 * @return the sequence
	 */
	public ArrayList<JavaClass> getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(ArrayList<JavaClass> sequence) {
		this.sequence = sequence;
	}

	private JavaClass generateRandomTest() 
	{
		Random rand = new Random();
		return this.tests.get(rand.nextInt(this.tests.size()));
	}


	@Override
	protected Individual createClonedIndividual()
	{
		Individual clone = new TestCaseIndividual(nsga2,sequence,tests);
		return clone;
	}

	@Override
	public
	/**
	 *
	 * <p>
	 * Mutation function randomly changes single genes in the chromosome. Each
	 * gene is an option for a given activity. The probably of a new option
	 * being selected for any gene is given by the defined MUTATION_PROBABILITY
	 * in the NSGAConfiguration object.
	 * </p>
	 */ void mutate()
	{
		boolean mutated = false;
		Random rand = new Random();

/*
		for (JavaClass entry : sequence)
		{
			if (rand.nextDouble() <= nsga2.getNSGA2Configuration().getMutationProbability()) 
			{
				JavaClass toReplace = entry;
				if (sequence.size() <= 1)
					break;
				JavaClass replacement = tests.get(rand.nextInt(tests.size()));
				while (toReplace.equals(replacement))
					replacement = tests.get(rand.nextInt(tests.size()));
				sequence.add(replacement);
				sequence.remove(toReplace);
				
				
				// System.out.printf("Name: %s - Category: %s\n",
				// replacement.getName(), replacement.getCategory());

				mutated = true;
			}
		}
		
*/
		if (rand.nextDouble() <= conf.getMutationProbability()) 
		{
			//A mutation is the removal of a duplicate(s) gene
			//ie. a test case from the chromosome
		
			//check the previous size
			int oldsize=sequence.size();
			//convert to a hashset to remove duplicates
			Set<JavaClass> set = new HashSet<JavaClass>(sequence);
			//make a new list
			LinkedList<JavaClass> newList = new LinkedList<JavaClass>();
			newList.addAll(set);
		
			//get the difference in sizes.
			// this is need to find out how many random test cases need to be inserted
			// as to preserve the previous size of the collection
		
			int diff =oldsize - newList.size();

			sequence.clear();
			sequence.addAll(newList);

			//add new genes to the chromosome

			for(int i=0; i<diff; i++)
			{
				JavaClass replacement =tests.get(rand.nextInt(tests.size()));
				
				while (sequence.contains(replacement))
					replacement = tests.get(rand.nextInt(tests.size()));
				
				sequence.add(replacement);
			}
			mutated = true;
		}

		if (mutated)
		{
			// update fitness values
			for (int i = 0; i < fitnessValues.length; i++) 
			{
				fitnessValues[i] = conf.getFitnessFunction(i).evaluate(this);
			}
		}
	}

	@Override
	/**
	 * <p>
	 * Crossover function does One-Point Crossover at randomly selected gene.
	 * Each gene is an option for a given activity. The probability of crossover
	 * occurring is given by the defined CROSSOVER_PROBABILITY in the
	 * NSGAConfiguration object.
	 * </p>
	 */
	public void crossover(Individual otherIndividual,int point1) 
	{
		if (otherIndividual == null)
		{
			throw new IllegalArgumentException("'otherIndividual' must not be null.");
		}
		if (!(otherIndividual instanceof TestCaseIndividual))
		{
			throw new IllegalArgumentException("Must be IndividualProject.");
		}

		TestCaseIndividual otherInd = (TestCaseIndividual) otherIndividual;

		if (nsga2 != otherInd.nsga2)
		{
			throw new IllegalArgumentException("Both individuals must belong to the same NSGA-II instance.");
		}

		Random rand = new Random();
		if (rand.nextDouble() < conf.getCrossoverProbability()) {
			// crossover in front of 'randomIndex'
			int currIndex = 0;
			for (JavaClass entry : sequence)
			{
				if (currIndex >=point1)
					break;

				// Swap
				JavaClass toSwap = entry;
				sequence.add(currIndex,otherInd.getSequence().get(currIndex));

				otherInd.getSequence().add(currIndex, toSwap);
				currIndex++;
			}

			// update fitness values
			for (int i = 0; i < fitnessValues.length; i++)
			{
				FitnessFunction fitnessFunction = (FitnessFunction) conf.getFitnessFunction(i);
				fitnessValues[i] = fitnessFunction.evaluate(this);
				otherInd.fitnessValues[i] = fitnessFunction.evaluate(otherInd);
			}
		}
	}



	private void printIndividual() {
		System.out.printf("Individual Length: %s\n\n",this.getLength());
		for (JavaClass entry : sequence)
			System.out.println(entry.getClassName());

		System.out.println("\nBranch Coverage: " + this.fitnessValues[0]);
		System.out.println("Statement Coverage: " + this.fitnessValues[1]);
		System.out.println("Estimated Execution Time: " + this.fitnessValues[2]);

		System.out.println();
	}

	@Override
	public double getFitnessValue(int index) throws IndexOutOfBoundsException
	{
		return fitnessValues[index];
	}

	@Override
	public void crossover(Individual otherIndividual, int point1, int point2) {
		// TODO Auto-generated method stub
		
	}
}