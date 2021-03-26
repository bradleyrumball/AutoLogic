package com.github.bradleyrumball.autologic.GA;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
  public void setSingleGene() {
    i.setSingleFitness(0, 3);
    i.setSingleGene(0, 1);
    Integer newFitness = i.getBranchFitness(0);
    assertEquals(null, newFitness);
  }
}