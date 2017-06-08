package com.Genetic.Comparators;



import java.util.Comparator;

import com.Genetic.FitnessFunction;
import com.Genetic.Individual;

/**
 * Sort a Collection of Individuals by their fitness value
 * 
 * @author Lee Clement
 */
public class ArrangeByFitness implements Comparator<Individual>
{
    private FitnessFunction ff;

    private boolean order;

    /**
     * 
     * @param ff
     * @param des descending order
     */
    public ArrangeByFitness(FitnessFunction ff, boolean desc)
    {
        this.ff = ff;
        this.order = desc;
    }

    @Override
    public int compare(Individual i1, Individual i2)
    {
        if (i1 == null)
            return 1;
        else if (i2 == null)
            return -1;

        double objetive1 = i1.getFitnessByFunc(this.ff);
        double objetive2 = i2.getFitnessByFunc(this.ff);

        if (this.order)
        {
            if (objetive1 < objetive2)
                return 1;
            else if (objetive1 > objetive2)
                return -1;
            else
                return 0;
        }
        else
        {
            if (objetive1 < objetive2)
                return -1;
            else if (objetive1 > objetive2)
                return 1;
            else
                return 0;
        }
    }
}
