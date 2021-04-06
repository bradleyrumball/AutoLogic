//package com.github.bradleyrumball.autologic.GA;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//public class IndividualTest {
//
//  private Individual individual;
//
//  @Before
//  public void setUp() {
//    individual = new Individual();
//  }
//
//  /**
//   * Check genes are equal on new individuals
//   */
//  @Test
//  public void newIndividualGenesAreEqual() {
//    int numberOfGenes = individual.getGeneCount();
//    int expectedGene = individual.getGene(0);
//    boolean genesEqual = true;
//    for (int i = 0; i < numberOfGenes; i++) {
//      if (individual.getGene(i) != expectedGene) genesEqual = false;
//    }
//    assertEquals(true, genesEqual);
//  }
//
//
//
//  /**
//   * Checks that fitness has been reset
//   * should return a new fitness score
//   */
//  @Test
//  public void setGene() {
//    individual.setGene(0, 1);
//    long newFitness = individual.getFitness();
//    assertEquals(Integer.MAX_VALUE, newFitness);
//  }
//}