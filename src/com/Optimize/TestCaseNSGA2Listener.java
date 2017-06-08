package com.Optimize;

import com.Genetic.Individual;

import com.Genetic.Algorithm.GeneticEvent;
import com.Genetic.Algorithm.GeneticListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 *
 * @author Joseph Rivera
 */
public class TestCaseNSGA2Listener implements GeneticListener
{
    
    private final int NUM_GEN = 25;
     /**
     * Performs the specified NSGA-II event.
     * <p>
     * Every 100 generations, the best individuals found so far are printed.
     * 
     * @param nsga2event NSGA-II event
     */
    public void performEvent(GeneticEvent nsga2event)
    {
        if (nsga2event.getNumberGeneration() % NUM_GEN == 0)
        {
            System.out.println();
            System.out.println("Generation: " + nsga2event.getNumberGeneration());

            LinkedList<Individual> bestIndividuals = nsga2event.getBestIndividuals();

            LinkedList<TestCaseIndividual> bestProjects = new LinkedList<TestCaseIndividual>();
            for (Individual individual : bestIndividuals)
            {
                bestProjects.add((TestCaseIndividual) individual);
            }

            printBestProjects(bestProjects);
        }
    }

    /**
     * Prints the specified individuals.
     */
    private static void printBestProjects(LinkedList<TestCaseIndividual> bestIndividuals)
    {
       // NumberFormat fmt = NumberFormat.getPercentInstance();
        NumberFormat precision = new DecimalFormat("#.00");
        if (bestIndividuals == null)
        {
            throw new IllegalArgumentException("'bestProjects' must not be null.");
        }

        TestCaseIndividual[] array =
                bestIndividuals.toArray(new TestCaseIndividual[bestIndividuals.size()]);


        System.out.println();
        System.out.println("Number of offered solutions: " + bestIndividuals.size());

        for (int i = 0; i < array.length; i++)
        {
            System.out.print(" Branch Coverage: " + precision.format(array[i].getFitnessValue(0)));
            System.out.print(" / Statement Coverage: " + precision.format(array[i].getFitnessValue(1)));
            System.out.print(" / Estimated Execution Time: " + array[i].getFitnessValue(2));
            System.out.println(" / Crowding Distance " + array[i].getCrowdingDistance());
        }
        
    }



}