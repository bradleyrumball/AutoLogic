package com.github.bradleyrumball.autologic.GA;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndividualTest {

  private Individual i;

  @Before
  public void setUp() throws Exception {
    i = new Individual();
  }

  /**
   * Checks that fitness has been set back to 0
   */
  @Test
  public void setGene() {
    i.setGene(0, 1);
    int newFitness = i.getFitness();
    assertEquals(Integer.MAX_VALUE, newFitness);
  }
}