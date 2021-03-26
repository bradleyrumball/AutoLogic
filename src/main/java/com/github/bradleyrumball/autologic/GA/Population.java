package com.github.bradleyrumball.autologic.GA;

import java.util.ArrayList;

public class Population {

  /**
   * A list containing all individuals in this population
   */
  private ArrayList<Individual> individuals;

  /**
   * Constructor that will create population of individuals
   * @param populationSize the size of the population to create
   */
  public Population(int populationSize) {
    individuals = new ArrayList<>(populationSize);
    for (int i = 0; i < populationSize; i++) individuals.add(i, new Individual());
  }

  /**
   * Constructor for when we cant to create an empty population
   */
  public Population() {
    this(0);
  }

  /**
   * Given the id of an individual in the population it will be returned
   * @param individualID
   * @return The individual associated with the provided id
   */
  protected Individual getIndividual(int individualID) {
    return individuals.get(individualID);
  }

  //TODO: Get fittest individual
  protected Individual getFittest() {
    //Get fittest for each branch?
    return null;
  }

  /**
   * Nicely print out the population (Debug purposes)
   * @return
   */
  public String toString() {
    final StringBuilder s = new StringBuilder();
    individuals.forEach(individual -> s.append(individual.toString()+"\n"));
    return s.toString();
  }

}
