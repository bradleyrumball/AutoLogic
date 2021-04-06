//package com.github.bradleyrumball.autologic.GA;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//
//public class PopulationTest {
//
//    private static final int POPULATION_SIZE = 10;
//    private Population population;
//
//
//    @Before
//    public void setUp() throws Exception {
//        population = new Population(POPULATION_SIZE);
//    }
//
//    @Test
//    public void correctPopulationSize() {
//        int popSize = population.getIndividuals().size();
//        assertEquals(POPULATION_SIZE, popSize);
//    }
//
//    @Test
//    public void populationIncreasesWhenNewIndividualAdded() {
//        Individual newIndividual = new Individual();
//        population.addIndividual(newIndividual);
//        int popSize = population.getIndividuals().size();
//        assertEquals(POPULATION_SIZE+1, popSize);
//    }
//
//    @Test
//    public void correctIndividualReturnedAsFittest() {
//        ArrayList<Individual> allIndividuals = population.getIndividuals();
//        long fittest = Integer.MAX_VALUE;
//        for (Individual i: allIndividuals) {
//            if (i.getFitness() < fittest) fittest = i.getFitness();
//        }
//
//        assertEquals(fittest,population.getFittest().getFitness());
//    }
//
//
//}