package com.Genetic.Algorithm;



import java.util.EventListener;

/**
 * This interface describes an NSGA-II listener.
 */
public interface GeneticListener extends EventListener {
   
   /**
    * Performs the specified NSGA-II event.
    * 
    * @param nsga2Event NSGA-II event
    */
   public void performEvent(GeneticEvent nsga2Event);
}
