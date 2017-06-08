package com.Genetic;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


import com.Genetic.FitnessFunction;

import com.Genetic.Algorithm.NSGA2.NSGA2;



/**
 * @author Lee Clement
 *
 */
public abstract class Individual
{

	/** NSGA2 instance this individual belongs to */
	 protected NSGA2 nsga2;
	
	/** Constant <code>fitnessScores</code> */
	private ArrayList<FitnessFunction>fitnessFunctions = new ArrayList<FitnessFunction>();
	
	/**Domination rank */
	protected int rank;

	/**Crowding distance */
	protected double crowding_distance;
	
	/**Length */
	protected int length;

	
	
	   /**
	    * Constructor.
	    * 
	    * @param nsga2 NSGA-II instance this individual is used for
	    */
	   public Individual(NSGA2 nsga22) {
	      if (nsga22 == null) {
	         throw new IllegalArgumentException("'NSGA2 Instance' must not be null.");
	      }
	      
	      this.nsga2 = nsga22;
	   }
	   


	   /**
	    * Mutates this individual.
	    * 
	    * Classes implementing this abstract method must ensure that the fitness values for the
	    * different objectives are updated right after mutation.
	    */
	   public abstract void mutate() throws GeneticFailException;

	   /**
	    * Does a crossover between the two individuals. Afterwards, both individuals are altered. If
	    * the original individuals are still needed, use the {@link #clone()} method to get clones and
	    * use them instead.
	    * 
	    * Classes implementing this abstract method must ensure that the fitness values for the
	    * different objectives (for both individuals!) are updated right after crossover.
	    * 
	    * @param otherIndividual other individual
	    */
	   public abstract void crossover(Individual otherIndividual, int point);
	
	   /**
	    * Does a crossover between the two individuals. Afterwards, both individuals are altered. If
	    * the original individuals are still needed, use the {@link #clone()} method to get clones and
	    * use them instead.
	    * 
	    * Classes implementing this abstract method must ensure that the fitness values for the
	    * different objectives (for both individuals!) are updated right after crossover.
	    * 
	    * @param otherIndividual other individual
	    */
	   public abstract void crossover(Individual otherIndividual, int point1, int point2);
		
	   
	   /**
	    * Gets this individual's fitness value for the index-th objective.
	    * 
	    * Classes implementing this abstract method must ensure that the fitness values for the
	    * different objectives are updated right after creation, crossover or mutation.
	    * 
	    * @param index index
	    * @return fitness value for the index-th objective
	    * @throws IndexOutOfBoundsException if the index is out of bounds
	    */
	   public abstract double getFitnessValue(int index) throws IndexOutOfBoundsException;
	   
	   /**
	    * Creates a copy of this object, so that changes on the clone do not change the intern data of
	    * the original.
	    * 
	    * @return cloned object
	    */
	   public Individual clone()
	   {
	      Individual clone = createClonedIndividual();
	      
	      // clone rank
	      int rank = getRank();
	      if (rank != 0) {
	         clone.setRank(rank);
	      }
	      
	      // clone crowding distance
	      clone.setCrowdingDistance(getCrowdingDistance());
		  clone.setFitnessList(this.getFitnessList());
		  clone.setLength(this.getLength());
	      
	      return clone;
	   }
	   
	   /**
	    * Creates a clone of this individual, so that changes on the clone do not change the intern data
	    * of the original. The rank and crowding distance are not copied by this method. The NSGA-II
	    * instance is only copied.
	    * 
	    * @return cloned individual (without correct rank and crowding distance)
	    */
	   protected abstract Individual createClonedIndividual();



	/**
	 * @return the rank
	 */
	public int getRank()
	{
		return rank;
	}

	/**
	 * @param rank 
	 */
	public void setRank(int rank)
	{
	       this.rank = rank;
	}


	/**
	 * @return the distance
	 */
	public double getCrowdingDistance()
	{
		return crowding_distance;
	}
	
	
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}


	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	
	
	/**
	 * @param cdistance
	 */
	public void setCrowdingDistance(double cdistance)
	{
	      if (cdistance < 0) {
	          throw new IllegalArgumentException("'crowdingDistance' must not be negative.");
	       }
	       
	       this.crowding_distance = cdistance;
	}

	
	/**
	 * @param funclist
	 */
	public void setFitnessList(ArrayList<FitnessFunction>funclist)
	{
		this.fitnessFunctions=funclist;
	}

	/**
	 * @return fitnessfunctions
	 */
	public ArrayList<FitnessFunction> getFitnessList()
	{
		return this.fitnessFunctions;
	}
	
	/**
	 * @param ff
	 */
	public void addFitnessFunction(FitnessFunction ff)
	{

		this.fitnessFunctions.add(ff);
	}
	/**
	 * @param  ff
	 * @return
	 */
	public Double getFitnessByFunc(FitnessFunction ff)
	{
		return ff.evaluate(this);
	}

	/**
	 * @return nsga2
	 */
	public NSGA2 getNSGA2Instance()
	{
		return this.nsga2;
	}

}