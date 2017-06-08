package com.Genetic.Comparators;



import java.io.Serializable;
import java.util.Comparator;

import com.Genetic.FitnessFunction;
import com.Genetic.Individual;

/**
 * Sort a Collection of Chromosomes by their Dominance
 * 
 * @author Lee Clement
 */
public class DominanceComparator
    implements Comparator<Individual>, Serializable
{


    /**
     * Is c2 dominated by c1?
     * 
     * http://en.wikipedia.org/wiki/Multi-objective_optimization#Introduction
     * 
     * @param c1
     * @param c2
     * @return -1, or 0, or 1 if solution1 dominates solution2, both are non-dominated, or solution1 is dominated by
     *         solution2, respectively.
     */
    @Override
    public int compare(Individual i1,Individual i2)
    {
        int dominate1 = 0;
        int dominate2 = 0;

        int flag; // stores the result of the comparison

        for (FitnessFunction ff : i1.getFitnessList())
        {
            double value1 = i1.getFitnessByFunc(ff);
            double value2 = i2.getFitnessByFunc(ff);

            if (value1 < value2)
                flag = -1;
            else if (value1 > value2)
                flag = 1;
            else
                flag = 0;

            if (flag == -1)
                dominate1 = 1;
            if (flag == 1)
                dominate2 = 1;
        }

        if (dominate1 == dominate2)
            return 0; // no one dominate the other
        if (dominate1 == 1)
            return -1; // chromosome1 dominate

        return 1; // chromosome2 dominate
    }
}
