package com.github.bradleyrumball.autologic.GA;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Population now requires method reflection object hence deprecation
 */

@Deprecated
@Ignore
public class PopulationTest {

    private static final int POPULATION_SIZE = 10;
    private Population population;

    @Ignore
    @Before
    public void setUp() throws Exception {
//        population = new Population(POPULATION_SIZE);
    }

    @Ignore
    @Test
    public void correctPopulationSize() {
        int popSize = population.getIndividuals().size();
        assertEquals(POPULATION_SIZE, popSize);
    }

    @Ignore
    @Test
    public void populationIncreasesWhenNewIndividualAdded() {
//        Individual newIndividual = new Individual();
//        population.addIndividual(newIndividual);
        int popSize = population.getIndividuals().size();
        assertEquals(POPULATION_SIZE+1, popSize);
    }

    @Ignore
    @Test
    public void correctIndividualReturnedAsFittest() {
        ArrayList<Individual> allIndividuals = population.getIndividuals();
        double fittest = Integer.MAX_VALUE;
        for (Individual i: allIndividuals) {
            if (i.getFitness() < fittest) fittest = i.getFitness();
        }

        assertEquals(fittest,population.getFittest().getFitness());
    }


}