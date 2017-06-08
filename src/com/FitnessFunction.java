package com.Genetic;

/**
 * This interface describes a fitness function of a genetic algorithm.
 * 
 * @author Lee Clement
 * @version 1.0
 */
public interface FitnessFunction 
{
   
   /**
    * Evaluates the fitness value of the specified individual.
    * 
    * @param individual individual
    * @return fitness value
    */
   double evaluate(Individual individual);
}