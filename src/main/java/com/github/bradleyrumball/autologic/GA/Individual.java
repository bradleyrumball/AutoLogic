package com.github.bradleyrumball.autologic.GA;


import java.security.SecureRandom;
import java.util.Arrays;

public class Individual {

  /**
   * Temp?
   * Number of params for the method (For triangle this is three (Side1, Side2, Side3)
   */
  private final int NUMBER_OF_GENES = 3;


  /**
   * Genes represent the parameters that are going into the method
   * Initial capacity set to the number of params
   */
  private final int[] genes = new int[NUMBER_OF_GENES];

  /**
   * The current fitness of the individual
   *
   * Worst fitness is determined as Integer.Max
   */
  private int fitness = Integer.MAX_VALUE;

  /**
   * Constructor, creates individual with random genes (params)
   */
  public Individual() {
    for (int i = 0; i < genes.length; i++) genes[i] = new SecureRandom().nextInt();
  }

  /**
   * Getter for individual gene
   * @param id the if of the individual gene that we wish to return
   * @return value of individual gene/param
   */
  protected int getGene(int id) {
    return genes[id];
  }

  protected int[] getGenes() {
    return genes;
  }

  protected int getGeneCount() {
    return NUMBER_OF_GENES;
  }

  /**
   * Allows a gene to be set individually
   * Can be used by crossover for example
   * @param geneID The ID of the gene that you wish to set
   * @param geneValue The value that you wish to set the gene to
   */
  protected void setGene(int geneID, int geneValue) {
    genes[geneID] = geneValue;
    // If a gene has been updated or set its fitness scores must be restarted
    fitness = Integer.MAX_VALUE;
  }

  public int getFitness() {
    if (fitness == Integer.MAX_VALUE) fitness = Host.getFitness(this);
    return fitness;
  }

  /**
   * For debugging more than anything
   * @return The individual as a string
   */
  @Override
  public String toString() {
    String[] genesString = Arrays.stream(genes)
            .mapToObj(String::valueOf)
            .toArray(String[]::new);
    return ("Input Params: " + Arrays.toString(genesString) + " | Fitness: " + fitness);
  }



}
