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
    individuals = new ArrayList<>();
    for (int i = 0; i < populationSize; i++) individuals.add(i, new Individual());
  }

  /**
   * Constructor for when we cant to create an empty population
   */
  public Population() {
    this(0);
  }


  protected ArrayList<Individual> getIndividuals() {
    return individuals;
  }

  protected void addIndividual(Individual individual){
    individuals.add(individual);
  }

  /**
   * Given the id of an individual in the population it will be returned
   * @param id the id of the individual we wish to return
   * @return The individual associated with the provided id
   */
  protected Individual getIndividual(int id) {
    return individuals.get(id);
  }

  /**
   * Get the fittest individuals for each branch
   * @return the individual with the best (lowest) fitness
   */
  protected Individual getFittest() {
    Individual fittest = individuals.get(0);
    for (Individual individual : individuals) {
      if (individual.getFitness() < fittest.getFitness()) fittest = individual;
    }
    return fittest;
  }

  /**
   * Nicely print out the population (Debug purposes)
   * @return The population as a string
   */
  public String toString() {
    final StringBuilder s = new StringBuilder();
    individuals.forEach(individual -> s.append(individual.toString()).append("\n"));
    return s.toString();
  }

}
