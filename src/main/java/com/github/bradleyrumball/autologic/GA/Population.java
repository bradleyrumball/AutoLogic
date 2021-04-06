package com.github.bradleyrumball.autologic.GA;

import java.lang.reflect.Method;
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
  public Population(int populationSize, Method method, int currentBranch) {
    individuals = new ArrayList<>();
    for (int i = 0; i < populationSize; i++) individuals.add(i, new Individual(method, currentBranch));
  }

//  /**
//   * Constructor for when we want to create an empty population
//   * This is used for new populations that are constructed manually
//   * from mutation and crossover etc
//   */
//  public Population(int currentBranch) {
//    this(0, currentBranch);
//  }

  /**
   * Getter for all individuals in the population
   * @return a list of all individuals in the populaiton
   */
  protected ArrayList<Individual> getIndividuals() {
    return individuals;
  }

  /**
   * Add an individual to the population
   * used by the crossover and mutate methods
   * @param individual the individual to add to the population
   */
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
   * Get the fittest individual in the population
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
