package com.github.bradleyrumball.autologic.GA;


import java.security.SecureRandom;
import java.util.Arrays;

/**
 * This class contains a instance of an individual
 * an individual has its own gene pool which is tested for fitness.
 * Individuals form a population.
 */
public class Individual {




  /**
   * Genes represent the parameters that are going into the method
   */
  private final int[] genes = new int[Host.NUMBER_OF_GENES];

  /**
   * The current fitness of the individual
   *
   * Worst fitness is determined as Integer.Max
   * Ideal fitness is 0
   */
  private int fitness = Integer.MAX_VALUE;

  private Object methodReturnValue;

  /**
   * Constructor, individuals are created with their initial gene pool set
   * such that all genes are equal, this is because it is time consuming for
   * the GA to create a case where multiple parameters are equal
   */
  public Individual() {
    int starter = new SecureRandom().nextInt();
    for (int i = 0; i < genes.length; i++) genes[i] = starter;
  }

  /**
   * Getter for individual gene
   * @param id the if of the individual gene that we wish to return
   * @return value of individual gene/param
   */
  protected int getGene(int id) {
    return genes[id];
  }

  /**
   * Return the number of genes that each individual carries
   * @return
   */
  protected int getGeneCount() {
    return Host.NUMBER_OF_GENES;
  }

  /**
   * Allows a gene to be set individually
   * Can be used by crossover/mutate for example
   * @param geneID The ID of the gene that you wish to set
   * @param geneValue The value that you wish to set the gene to
   */
  protected void setGene(int geneID, int geneValue) {
    genes[geneID] = geneValue;
    // If a gene has been updated or set its fitness scores must be restarted
    fitness = Integer.MAX_VALUE;
  }

  /**
   * Call back to host to get the fitness of the current individual if it hasn't already been
   * calculated.
   * @return int - fitness value of the individual
   */
  public int getFitness() {
    if (fitness == Integer.MAX_VALUE) fitness = Host.getFitness(this);
    return fitness;
  }

  public Object getMethodReturnValue() {
    if (methodReturnValue == null) methodReturnValue= Host.getMethodReturn(this);
    return methodReturnValue;
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
    return ("Input Params: " + Arrays.toString(genesString) + " | Fitness: " + fitness+ " | Method Out: "+ getMethodReturnValue() +"\n");
  }



}
