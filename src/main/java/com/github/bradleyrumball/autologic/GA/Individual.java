package com.github.bradleyrumball.autologic.GA;


import java.util.ArrayList;
import java.util.Random;

public class Individual {

  /**
   * Temp?
   * Number of params for the method (For triangle this is three (Side1, Side2, Side3)
   */
  protected int numberOfParams = 3;

  /**
   * Temp
   * Represents the number of branches to explore
   */
//  protected int numberOfBranches = 14;

  /**
   * Genes represent the parameteres that are going into the method
   */
  private ArrayList<Integer> genes = new ArrayList<>(numberOfParams);

  /**
   * Fitness is an array where the index is the branch index and the value is the
   * fitness score of the gene input for the given branch
   */
  private ArrayList<Integer> fitness = new ArrayList<>();

  /**
   * Constructor, creates individual with random genes (params)
   */
  public Individual() {
    genes.forEach( gene ->  gene = new Random().nextInt());
  }

  /**
   * Getter for individual gene
   * @param geneID
   * @return value of individual gene/param
   */
  protected Integer getSingleGene(Integer geneID) {
    return genes.get(geneID);
  }

  /**
   * Allows a gene to be set individually
   * Can be used by crossover for example
   * @param geneID The ID of the gene that you wish to set
   * @param geneValue The value that you wish to set the gene to
   */
  protected void setSingleGene(Integer geneID, Integer geneValue) {
    genes.set(geneID, geneValue);
    // If a gene has been updated or set its fitness scores must be restarted
    fitness.forEach( f -> f = 0);
  }

  /**
   * Get the fitness achieved for the given branch
   * @param branchIndex
   * @return
   */
  protected Integer getBranchFitness(Integer branchIndex) {
    return fitness.get(branchIndex);
  }

  /**
   * Not sure if we're going to need this but put it in anyways
   *    it's possible that population might deal with this or at a higher level
   * - Gets sets the individuals fitness for a specified branch with given score
   * @param branchIndex
   * @param fitnessScore
   */
  protected void setSingleFitness(Integer branchIndex, Integer fitnessScore) {
    fitness.set(branchIndex, fitnessScore);
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
