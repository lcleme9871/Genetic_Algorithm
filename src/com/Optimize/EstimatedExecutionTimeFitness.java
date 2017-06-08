package com.Optimize;

import com.Genetic.FitnessFunction;
import com.Genetic.Individual;
import java.util.Map;



/**
 *
 * @author Lee Clement
 */
public class EstimatedExecutionTimeFitness implements FitnessFunction
{

    @Override
    public double evaluate(Individual indv)
    {
        if (indv == null)
        {
            throw new IllegalArgumentException("Individual must not be null.");
        }
        if (!(indv instanceof TestCaseIndividual))
        {
            throw new IllegalArgumentException("Individual must be of type TestCaseIndividual.");
        }

        TestCaseIndividual testIndv = (TestCaseIndividual) indv;

        double milliseconds=0.0;
        
        for (JavaClass entry : testIndv.getSequence())
        {
        	milliseconds += entry.getExecutionTime();
        }
        return Math.round(milliseconds/1000);
    }
}
