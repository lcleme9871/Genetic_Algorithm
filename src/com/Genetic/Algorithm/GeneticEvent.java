package com.Genetic.Algorithm;



import java.util.*;

import com.Genetic.Individual;

/**
 * This class implements an event object for storing all necessary information about this event.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class GeneticEvent extends EventObject
{
   
   private static final long serialVersionUID = 1L;

   private LinkedList<Individual> bestIndividuals;
   private int numberGeneration;
   
   /**
    * Constructor.
    * 
    * @param source source of this event (NSGA-II instance)
    * @param bestIndividuals best individuals in this generation (only non-dominated ones)
    * @param numberGeneration number of generation
    */
   public GeneticEvent(Object source, LinkedList<Individual> bestIndividuals, int numberGeneration) {
      super(source);
      
      if (source == null) {
         throw new IllegalArgumentException("'source' must not be null.");
      }
      if (bestIndividuals == null) {
         throw new IllegalArgumentException("'bestIndividuals' must not be null.");
      }
      if (numberGeneration < 0) {
         throw new IllegalArgumentException("'numberGeneration' must not be negative.");
      }
      
      this.bestIndividuals = bestIndividuals;
      this.numberGeneration = numberGeneration;
   }
   
   /**
    * Gets the best individuals in this generation (only non dominated ones). A cloned list is
    * returned so that no changes to the intern data can be made.
    * 
    * @return best individuals in this generation
    */
   public LinkedList<Individual> getBestIndividuals() {
      return (LinkedList<Individual>)bestIndividuals.clone();
   }
   
   /**
    * Gets the number of generation.
    * 
    * @return number of generation
    */
   public int getNumberGeneration() {
      return numberGeneration;
   }
}
