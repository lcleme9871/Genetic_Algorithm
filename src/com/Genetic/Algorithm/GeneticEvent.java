package com.Genetic.Algorithm;

/* ===========================================================
 * JNSGA2: a free NSGA-II library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2006-2007, Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 *
 * Project Info:  http://sourceforge.net/projects/jnsga2/
 *
 * This library is free software; you can redistribute it and/or modify it  under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */




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