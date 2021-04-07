package com.github.bradleyrumball.autologic.GA;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * A class to manage a population of individuals
 */
public class Population {

    /**
     * A list containing all individuals in this population
     */
    private ArrayList<Individual> individuals;


    /**
     * Constructor that will create population of individuals
     *
     * @param populationSize the size of the population to create
     * @param method         current method under test
     * @param currentBranch  the branch that the population is targeting
     */
    public Population(int populationSize, Method method, int currentBranch) {
        individuals = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) individuals.add(i, new Individual(method, currentBranch));
    }

    public Population(ArrayList<Individual> individuals) {
        this.individuals = individuals;
    }

    public Population setBranchID(int newCurrentBranch) {
        ArrayList<Individual> individualsList = new ArrayList<>();
        individuals.forEach(i -> {
            individualsList.add(new Individual(i.getMethod(), newCurrentBranch, i.getGenes()));
        });
        return new Population(individualsList);
    }

    /**
     * Getter for all individuals in the population
     *
     * @return a list of all individuals in the populaiton
     */
    protected ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    /**
     * Add an individual to the population
     * used by the crossover and mutate methods
     *
     * @param individual the individual to add to the population
     */
    protected void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    /**
     * Given the id of an individual in the population it will be returned
     *
     * @param id the id of the individual we wish to return
     * @return The individual associated with the provided id
     */
    protected Individual getIndividual(int id) {
        return individuals.get(id);
    }

    /**
     * Get the fittest individual in the population
     *
     * @return the individual with the best (lowest) fitness
     */
    protected Individual getFittest() {
        return individuals.stream().sorted(Comparator.comparingDouble(Individual::getFitness)).limit(1).collect(Collectors.toList()).get(0);
    }

    /**
     * Nicely print out the population (Debug purposes)
     *
     * @return The population as a string
     */
    public String toString() {
        final StringBuilder s = new StringBuilder();
        individuals.forEach(individual -> s.append(individual.toString()).append("\n"));
        return s.toString();
    }

}
