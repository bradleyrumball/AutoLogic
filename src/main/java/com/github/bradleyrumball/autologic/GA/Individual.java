package com.github.bradleyrumball.autologic.GA;


import java.util.Hashtable;
import java.util.Random;

public class Individual {

  /**
   * Temp?
   * Number of params for the method (For triangle this is three (Side1, Side2, Side3)
   */
  private final int NUMBER_OF_GENES = 3;

  /**
   * Temp
   * Represents the number of branches to explore
   */
  private final int NUMBER_OF_POSSIBLE_BRANCHES = 14;

  /**
   * Genes represent the parameteres that are going into the method
   * Initial capacity set to the number of params
   */
  private Hashtable<Integer, Integer> genes = new Hashtable<>(NUMBER_OF_GENES);

  /**
   * Fitness is an array where the index is the branch index and the value is the
   * fitness score of the gene input for the given branch
   * initial capacity set to the number of branches
   *
   * FITNESS MUST BE DECLARED WITH INTEGER AND NOT INT AS NULL IS USED FOR INITIAL FITNESS
   */
  private Hashtable<Integer, Integer> fitness = new Hashtable<>(NUMBER_OF_POSSIBLE_BRANCHES);

  /**
   * Constructor, creates individual with random genes (params)
   */
  public Individual() {
    for (int i = 0; i < NUMBER_OF_GENES; i++) genes.put(i, new Random().nextInt());
  }

  /**
   * Getter for individual gene
   * @param geneID
   * @return value of individual gene/param
   */
  protected Integer getSingleGene(int geneID) {
    return genes.get(geneID);
  }

  /**
   * Allows a gene to be set individually
   * Can be used by crossover for example
   * @param geneID The ID of the gene that you wish to set
   * @param geneValue The value that you wish to set the gene to
   */
  protected void setSingleGene(int geneID, Integer geneValue) {
    genes.put(geneID, geneValue);
    // If a gene has been updated or set its fitness scores must be restarted
    fitness.clear();
  }

  /**
   * Get the fitness achieved for the given branch
   * @param branchIndex
   * @return
   */
  protected Integer getBranchFitness(int branchIndex) {
    return fitness.get(branchIndex);
  }

  /**
   * Not sure if we're going to need this but put it in anyways
   *    it's possible that population might deal with this or at a higher level
   * - Gets sets the individuals fitness for a specified branch with given score
   * @param branchIndex
   * @param fitnessScore
   */
  protected void setSingleFitness(int branchIndex, Integer fitnessScore) {
    fitness.put(branchIndex, fitnessScore);
  }

  /**
   * For debugging more than anything
   * @return
   */
  @Override
  public String toString() {
    return ("Input Params: " + genes + " | Fitness: " + fitness);
  }



}
