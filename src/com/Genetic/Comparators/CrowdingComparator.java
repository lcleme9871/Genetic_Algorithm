package com.Genetic.Comparators;




import java.io.Serializable;
import java.util.Comparator;

import com.Genetic.Individual;

/**
 * Sort a Collection of Individuals by Crowd
 * 
 * @author Lee Clement
 */
public class CrowdingComparator implements Comparator<Individual>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -6576898111709166470L;

    private boolean isToMaximize;

    public CrowdingComparator(boolean maximize) {
        this.isToMaximize = maximize;
    }

    @Override
    public int compare(Individual i1,Individual i2)
    {
        if (i1.getRank() == i2.getRank() && i1.getCrowdingDistance() == i2.getCrowdingDistance())
            return 0;

        if (this.isToMaximize) {
            if (i1.getRank() < i2.getRank())
                return 1;
            else if (i1.getRank() > i2.getRank())
                return -1;
            else if (i1.getRank() == i2.getRank())
                return (i1.getCrowdingDistance() > i2.getCrowdingDistance()) ? -1 : 1;
        }
        else {
            if (i1.getRank() < i2.getRank())
                return -1;
            else if (i1.getRank() > i2.getRank())
                return 1;
            else if (i1.getRank() == i2.getRank())
                return (i1.getCrowdingDistance() > i2.getCrowdingDistance()) ? -1 : 1;
        }

        return 0;
    }
}
