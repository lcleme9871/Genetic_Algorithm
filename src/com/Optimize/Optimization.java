package com.Optimize;



import com.Genetic.FitnessFunction;
import com.Genetic.Individual;
import com.Genetic.Algorithm.NSGA2.*;
import com.Optimize.Plot.NoChartDataException;
import com.Optimize.Plot.ThreeDPlot;
import com.Optimize.Plot.TwoDPlot;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 *
 * @author Lee Clement
 */
public class Optimization
{



	   


    /**
     * Main method
     *
     * @param args arguments (not used)
     */
    public static void main(String[] args)
    {
        FitnessFunction fitnessFunction1;
        FitnessFunction fitnessFunction2;
        FitnessFunction fitnessFunction3;


        fitnessFunction1 = (FitnessFunction) new BranchCoverageFitness();
        fitnessFunction2 = (FitnessFunction) new StatementCoverageFitness();
        fitnessFunction3 = (FitnessFunction) new EstimatedExecutionTimeFitness();

        ArrayList<FitnessFunction> fitnessFunctions = new ArrayList<FitnessFunction>();
        fitnessFunctions.add(fitnessFunction1);
        fitnessFunctions.add(fitnessFunction2);
        fitnessFunctions.add(fitnessFunction3);


        NSGA2Configuration conf = new NSGA2Configuration(fitnessFunctions,
                Constants.POPULATION_SIZE,
                Constants.NUMBER_OF_GENERATIONS,
                Constants.MUTATION_PROBABILITY,
                Constants.CROSSOVER_PROBABILITY);
        NSGA2 nsga2 = new NSGA2(conf);
        nsga2.addGeneticListener(new TestCaseNSGA2Listener());

        // create start population
        LinkedList<Individual> startPopulation = new LinkedList<Individual>();
        LinkedHashMap<String,JavaClass> pool= parseReport();
        Collection<JavaClass>tcpool = pool.values();
        ArrayList<JavaClass> newpool = new ArrayList<JavaClass>(tcpool);

    	for(int i=0; i<Constants.POPULATION_SIZE; i++)
    	{
    		startPopulation.add(createIndividual(nsga2,newpool));
    		System.out.println(newpool.get(i).getNbPartiallyCoveredLines());

    	}


        System.out.println("Simulation Info");
        System.out.println("===============");
        //System.out.println(eResults.getPath());
        System.out.printf("Population Siz :%d\n"
                + "# of Generations: %d\n"
                + "Mutation probability: %.2f\n"
                + "Crossover probability: %.2f\n", Constants.POPULATION_SIZE, Constants.NUMBER_OF_GENERATIONS,
                Constants.MUTATION_PROBABILITY, Constants.CROSSOVER_PROBABILITY);
        // start evolution
        LinkedList<Individual> bestIndividuals = nsga2.evolve(startPopulation);

        LinkedList<TestCaseIndividual> bestTestCaseIndividuals = new LinkedList<TestCaseIndividual>();
        for (Individual individual : bestIndividuals)
        {
            bestTestCaseIndividuals.add((TestCaseIndividual) individual);
        }
        Collections.sort(bestTestCaseIndividuals, new TestCaseIndividualComparator());

        System.out.println("\n========= BEST PROJECTS=========");
        printBestIndividuals(bestTestCaseIndividuals);

        LinkedList<TestCaseIndividual> uniques = getUniqueTestCases(bestTestCaseIndividuals);
        System.out.println("\nUNIQUE PROJECTS:");
        printBestIndividuals(uniques);

        // show graphs
    	String graphName=System.nanoTime()+".jpeg";
    	
    	if(Constants.OBJECTIVES==2)
    	{
    		TwoDPlot chart =new TwoDPlot(Constants.OBJ1,Constants.OBJ2,"Branch Cov vs Statement Cov","Coverage",graphName);
    		chart.addList("Coverage",bestTestCaseIndividuals);
    		try 
    		{
    			chart.makeChart();
    		}
    		catch (NoChartDataException e)
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	else if(Constants.OBJECTIVES==3)
    	{
			ThreeDPlot chart =new ThreeDPlot(Constants.OBJ1,Constants.OBJ2,Constants.OBJ3,"Branch Coverage vs Statement Coverage vs Execution Time","Coverage",graphName);
			chart.plot3DScatter(bestTestCaseIndividuals);
    	}
    }
    
    private static LinkedHashMap<String, JavaClass> parseReport()
    {
 	   String FNAME =Constants.FOLDER+Constants.UNIX_SEPERATOR+Constants.FILENAME;
    	LinkedHashMap<String, JavaClass> classMap = new LinkedHashMap<String,JavaClass>();
    	File report = new File(FNAME);
    		try 
    		{
    			classMap= (LinkedHashMap<String, JavaClass>) CoverageReportParser.getCoverageData(report);

    		}
    		catch (ParserConfigurationException | SAXException | IOException e)
    		{
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    		}
    		
    		
    		return classMap;
    }
    

    private static ArrayList<TestCaseIndividual> createPopulation(NSGA2 nsga2,ArrayList<JavaClass>pool)
    {
    	ArrayList<TestCaseIndividual>startPopulation = new ArrayList<TestCaseIndividual>() ;
    	for(int i=0; i<Constants.POPULATION_SIZE; i++)
    	{
    		startPopulation.add(createIndividual(nsga2,pool));

    	}
    	return startPopulation;
    }
    
    private static TestCaseIndividual createIndividual(NSGA2 nsga2,ArrayList<JavaClass>pool)
    {
    	Random rand = new Random();
    	ArrayList<JavaClass>tests = new ArrayList<JavaClass>();
    	
    	for(int i=0; i<Constants.INDIVIDUAL_LENGTH; i++)
    	{
    		tests.add(pool.get(rand.nextInt(pool.size())));

    	}
    	return new TestCaseIndividual(nsga2,tests,pool);
    }
    



    /**
     * Prints the specified schedule individuals.
     *
     * @param bestSchedules schedule individuals
     */
    private static void printBestIndividuals(LinkedList<TestCaseIndividual> bestIndividuals)
    {
        NumberFormat precision = new DecimalFormat("#.00");
        if (bestIndividuals == null)
        {
            throw new IllegalArgumentException("'bestSchedules' must not be null.");
        }

        // sort best schedules
        TestCaseIndividual[] array =
                bestIndividuals.toArray(new TestCaseIndividual[bestIndividuals.size()]);
        Arrays.sort(array, new TestCaseIndividualComparator());

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

    private static LinkedList<TestCaseIndividual> getUniqueTestCases(List<TestCaseIndividual> tests)
    {
        TestCaseIndividualComparator comparator = new TestCaseIndividualComparator();
        LinkedList<TestCaseIndividual> uniqueTestCases = new LinkedList<TestCaseIndividual>();

        for (TestCaseIndividual tc : tests)
        {
            boolean found = false;
            for (int i = 0; i < uniqueTestCases.size(); i++)
            {
                if (comparator.compare(tc, uniqueTestCases.get(i)) == 0)
                {
                    found = true;
                    break;
                }
            }
            if (!found)
                uniqueTestCases.add(tc);
        }
        return uniqueTestCases;
    }

    /**
     * This inner class implements a comparator for two schedule individuals.
     */
    private static class TestCaseIndividualComparator implements Comparator<TestCaseIndividual>
    {

        /**
         * Compares the two specified schedule individuals.
         *
         * @param individual1 first individual
         * @param individual2 second individual
         * @return -1, 0 or 1 as the first individual is less than, equal to, or
         * greater than the second one
         */
        public int compare(TestCaseIndividual individual1, TestCaseIndividual individual2)
        {
            if (individual1 == null)
            {
                throw new IllegalArgumentException("'individual1' must not be null.");
            }
            if (individual2 == null)
            {
                throw new IllegalArgumentException("'individual2' must not be null.");
            }


            if (individual1.getFitnessValue(0) < individual2.getFitnessValue(0))
            {
                return -1;
            }

            if (individual1.getFitnessValue(0) > individual2.getFitnessValue(0))
            {
                return 1;
            }

            if (individual1.getFitnessValue(1) < individual2.getFitnessValue(1))
            {
                return -1;
            }

            if (individual1.getFitnessValue(1) > individual2.getFitnessValue(1))
            {
                return 1;
            }
            if (individual1.getFitnessValue(2) < individual2.getFitnessValue(2))
            {
                return 1;
            }

            if (individual1.getFitnessValue(2) > individual2.getFitnessValue(2))
            {
                return -1;
            }

            // both individuals are equal
            return 0;
        }
    }
}
