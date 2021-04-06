package com.github.bradleyrumball.autologic.GA;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Individual now requires a Method reflection object hence deprecation
 */

@Ignore
@Deprecated
public class IndividualTest {

  private Individual individual;

  @Ignore
  @Before
  public void setUp() {
//    individual = new Individual();
  }

  /**
   * Check genes are equal on new individuals
   */
  @Ignore
  @Test
  public void newIndividualGenesAreEqual() {
    int numberOfGenes = individual.getGeneCount();
    int expectedGene = individual.getGene(0);
    boolean genesEqual = true;
    for (int i = 0; i < numberOfGenes; i++) {
      if (individual.getGene(i) != expectedGene) genesEqual = false;
    }
    assertEquals(true, genesEqual);
  }



  /**
   * Checks that fitness has been reset
   * should return a new fitness score
   */
  @Ignore
  @Test
  public void setGene() {
    individual.setGene(0, 1);
    double newFitness = individual.getFitness();
    assertEquals(Integer.MAX_VALUE, newFitness, 0);
  }
}