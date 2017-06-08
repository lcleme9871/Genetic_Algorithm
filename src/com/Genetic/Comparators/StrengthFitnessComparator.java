package com.Genetic.Comparators;



import com.Genetic.Individual;

import java.io.Serializable;
import java.util.Comparator;

/**
 * StrengthFitnessComparator class.
 * 
 * @author Lee Clement
 */
public class StrengthFitnessComparator implements Comparator<Individual>, Serializable {

  private static final long serialVersionUID = 1365198556267160032L;

  @Override
  public int compare(Individual i1,Individual i2)
  {
    if (i1 == null && i2 == null) {
      return 0;
    } else if (i1 == null) {
      return 1;
    } else if (i2 == null) {
      return -1;
    }

    double strengthC1 = i1.getCrowdingDistance(); // TODO: should we change name of the function?
    double strengthC2 = i2.getCrowdingDistance();

    if (strengthC1 < strengthC2) {
      return -1;
    } else if (strengthC1 > strengthC2) {
      return 1;
    } else {
      return 0;
    }
  }
}
