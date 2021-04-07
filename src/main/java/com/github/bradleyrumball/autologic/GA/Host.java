package com.github.bradleyrumball.autologic.GA;


import com.github.bradleyrumball.autologic.test_case_generation.JUnitOutputManager;

import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controls the main execution of the GA
 */
public class Host {

    /**
     * The rate (0->1) that we which to mutate individuals in the population at
     */
    private static final double MUTATION_RATE = 0.4;
    /**
     * The crossover bias between geners of an individual A and an individual B
     */
    private static final double CROSSOVER_BIAS = 0.5;
    /**
     * The size of the tournament (number of fittest individuals to obtain from the previous population)
     */
    private static final int TOURNAMENT_SIZE = 15;
    /**
     * Enables Elitism, without this we might not be able to achieve optimum fitness although it can put
     * us in local minima
     */
    private static final boolean ELITISM = true;

    private static final int MAX_GENERATIONS_PER_BRANCH = 5000;


    private final List<Method> methods;
    private final Map<String, Integer> numberOfBranches;
    private final String classPath;

    /**
     * Constructor for host
     *
     * @param methods          list of all methods in a class that we are going to test
     * @param numberOfBranches map of number of branches against a method
     * @param classPath        the path of the class under test
     */
    public Host(List<Method> methods, Map<String, Integer> numberOfBranches, String classPath) {
        this.methods = methods;
        this.numberOfBranches = numberOfBranches;
        this.classPath = classPath;
    }

    /**
     * Runs the GA - probably should be abstracted to another class
     */
    public void run() {
        for (Method method : methods) {
            int branchID = 0;
            ArrayList<Individual> solutions = new ArrayList<>();
            Population population = new Population(50, method, branchID); //50
            for (int i = 0; i < numberOfBranches.getOrDefault(method.getName(), 0); i += 2) {
                for (int j = 0; j <= 1; j++) {
                    branchID = i + j;
                    population = population.setBranchID(branchID);
                    double populationFitness = population.getFittest().getFitness();
                    int generation = 0;
                    while (populationFitness > 0 && generation <= MAX_GENERATIONS_PER_BRANCH) {
//                        System.out.println("Current Branch: " + (i+j) + " Current fitness: " + populationFitness);
                        population = evolvePopulation(population, method, branchID);
                        populationFitness = population.getFittest().getFitness();
                        generation++;
                    }
                    if (populationFitness == 0) {
                        System.out.println("Solution for branch " + branchID + " has been found");
                        solutions.add(population.getFittest());
                    } else System.out.println("Branch " + branchID + " is intractable");

                }
            }
            System.out.println(solutions.size() + " test conditions found with solutions");
            System.out.println("Coverage for " + method.getName() + " = " + ((solutions.size() / (numberOfBranches.getOrDefault(method.getName(), 0).doubleValue()+1)) * 100) + "%");

            JUnitOutputManager jUnitGenerator = new JUnitOutputManager(solutions, classPath, method.getDeclaringClass().getSimpleName(), method.getName());
            jUnitGenerator.unitGenerator();
        }
    }

    /**
     * Once a population has been evaluated we evolve it by crossover and mutation
     *
     * @param population    the population that has just been tested for fitness
     * @param method        the method under test
     * @param currentBranch the current branch that we are testing
     * @return population - a new population that contains hopefully better individuals
     */
    public Population evolvePopulation(Population population, Method method, int currentBranch) {
        Population newPopulation = new Population(0, method, currentBranch);

        // Elitism - Get best individual
        int elitismOffset = 0;
        if (ELITISM) {
            newPopulation.addIndividual(population.getFittest());
            elitismOffset = 1;
        }

        // Crossover - get best x individuals and cross them
        for (int i = elitismOffset; i < population.getIndividuals().size(); i++) {
            Individual tournamentWinner1 = tournament(population, method, currentBranch);
            Individual tournamentWinner2 = tournament(population, method, currentBranch);
            Individual crossoverIndividual = crossover(tournamentWinner1, tournamentWinner2, method, currentBranch);
            newPopulation.addIndividual(crossoverIndividual);
        }

        // Mutation - adjust genetics by manipulating their values
        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }
        return newPopulation;
    }

    /**
     * Returns the fittest individual from a random portion of the population
     *
     * @param population the population that had just been run
     * @return Individual - the best individual from a random proportion of the population
     */
    private Individual tournament(Population population, Method method, int currentBranch) {
        Population tournament = new Population(0, method, currentBranch);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomIndividual = (int) (Math.random() * population.getIndividuals().size());
            tournament.addIndividual(population.getIndividual(randomIndividual));
        }
        return tournament.getFittest();
    }

    /**
     * Crosses over individuals by producing an individual from a mixture of genes
     * from two parent individuals. This is done on a gene by gene (parameter by parameter basis)
     * Crossover bias can manipulate the breeding process
     *
     * @param individualA   parent A of the new individual
     * @param individualB   parent B of the new individual
     * @param method        current method under test
     * @param currentBranch the current branch that we are getting fitness for
     * @return a child of A and B
     */
    private Individual crossover(Individual individualA, Individual individualB, Method method, int currentBranch) {
        Individual cross = new Individual(method, currentBranch);
        for (int i = 0; i < cross.getGeneCount(); i++) {
            if (Math.random() <= CROSSOVER_BIAS) cross.setGene(i, individualA.getGene(i));
            else cross.setGene(i, individualB.getGene(i));
        }
        return cross;
    }

    /**
     * This is where most success of the GA comes in
     * There is a 33% chance that an individual will be
     * -incremented by a dynamic amount
     * -decremented by a dyncmic amount
     * -replaced by a new random value
     * <p>
     * When incrementing and decrementing, the amount to manipulate the value by is dependant on
     * how far the value is from its optimum fitness
     *
     * @param individual the individual to mutate
     */
    private void mutate(Individual individual) {
        int explorationAmount = 1;
        if (individual.getFitness() > 10000) explorationAmount = 500;
        if (individual.getFitness() > 1000) explorationAmount = 50;
        if (individual.getFitness() > 10) explorationAmount = 5;
        for (int i = 0; i < individual.getGeneCount(); i++) {
            if (Math.random() <= MUTATION_RATE) {
                double rand = Math.random();
                if (rand <= 0.33) individual.setGene(i, individual.getGene(i) + explorationAmount);
                else if (rand <= 0.66) individual.setGene(i, individual.getGene(i) - explorationAmount);
                else individual.setGene(i, new SecureRandom().nextInt());
            }

        }
    }

}
