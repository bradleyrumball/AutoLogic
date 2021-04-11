package com.github.bradleyrumball.autologic.GA;


import com.github.bradleyrumball.autologic.test_case_generation.JUnitOutputManager;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    /**
     * The maximum number of generations that are permitted per branch
     * after this a branch is determined intractable
     */
    private static final int MAX_GENERATIONS_PER_BRANCH = 1000;

    /**
     * list of all methods in a class that we are going to test
     */
    private final List<Method> methods;
    /**
     * map of number of branches against a method
     */
    private final Map<String, Integer> numberOfBranches;
    /**
     * the path of the class under test
     */
    private final String classPath;
    /**
     * the path of the returned test suite classes
     */
    private final String outputPath;

    /**
     * Constructor for host
     *
     * @param methods          list of all methods in a class that we are going to test
     * @param numberOfBranches map of number of branches against a method
     * @param classPath        the path of the class under test
     * @param outputPath       the output path for the test suite
     */
    public Host(List<Method> methods, Map<String, Integer> numberOfBranches, String classPath, String outputPath) {
        this.methods = methods;
        this.numberOfBranches = numberOfBranches;
        this.classPath = classPath;
        this.outputPath = outputPath;
    }

    /**
     * Runs the GA - probably should be abstracted to another class
     */
    public void run() {
        methods.parallelStream().forEach(method -> {
            int branchID = 0;
            ArrayList<Individual> solutions = new ArrayList<>();
            Population population = new Population(400, method, branchID); //50
            for (int i = 0; i < numberOfBranches.getOrDefault(method.getName(), 0); i += 2) {
                for (int j = 0; j <= 1; j++) {
                    branchID = i + j;
                    population = population.setBranchID(branchID);
                    double populationFitness = population.getFittest().getFitness();
                    int generation = 0;
                    while (populationFitness > 0 && generation <= MAX_GENERATIONS_PER_BRANCH) {
                        System.out.println("Current Branch: " + (i+j) + " Current fitness: " + populationFitness);
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

            JUnitOutputManager jUnitGenerator = new JUnitOutputManager(solutions, classPath, method.getDeclaringClass().getSimpleName(), method.getName(), outputPath);
            jUnitGenerator.unitGenerator();
        });
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
        int offset = 0;
        if (ELITISM) {
            newPopulation.addIndividual(population.getFittest());
            offset++;
        }
        newPopulation.addIndividual(new Individual(method, currentBranch));
        offset++;


        // Crossover - get best x individuals and cross them
        IntStream.range(offset, population.getIndividuals().size()).parallel().mapToObj(i -> {
            Individual tournamentWinner1 = tournament(population, method, currentBranch);
            Individual tournamentWinner2 = tournament(population, method, currentBranch);
            Individual crossed = crossover(tournamentWinner1, tournamentWinner2, method, currentBranch);
            // Mutation - adjust genetics by manipulating their values
            mutate(crossed);
            return crossed;

        }).collect(Collectors.toList()).forEach(newPopulation::addIndividual);

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

        IntStream.range(0, TOURNAMENT_SIZE).parallel().mapToObj(i -> {
            int randomIndividual = (int) (Math.random() * population.getIndividuals().size());
            return population.getIndividual(randomIndividual);
        }).collect(Collectors.toList()).forEach(tournament::addIndividual);
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
            if (Math.random() <= CROSSOVER_BIAS) cross.setGene(i, individualA.getGene(i).getValue());
            else cross.setGene(i, individualB.getGene(i).getValue());
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
        double explorationAmount = individual.getFitness();
        for (int i = 0; i < individual.getGeneCount(); i++) {
            Type indvType = individual.getGene(i).getType();
            Object indvValue = individual.getGene(i).getValue();
            if (Math.random() <= MUTATION_RATE) {
                double rand = Math.random();
                if (indvType == double.class) {
                    if (rand <= 0.33) individual.setGene(i, (double)indvValue + explorationAmount);
                    else if (rand <= 0.66) individual.setGene(i, (double)indvValue - explorationAmount);
                    else individual.setGene(i, (double)new SecureRandom().nextInt());
                }
                if (indvType == float.class) {
                    if (rand <= 0.33) individual.setGene(i, (float)((float)indvValue + explorationAmount));
                    else if (rand <= 0.66) individual.setGene(i, (float)((float)indvValue - explorationAmount));
                    else individual.setGene(i, (float)new SecureRandom().nextInt(Math.round(Float.MAX_VALUE)));
                }
                if (indvType == int.class) {
                    if (rand <= 0.33) individual.setGene(i, (int)((int)indvValue + Math.round(explorationAmount)));
                    else if (rand <= 0.66) individual.setGene(i, (int)((int)indvValue - Math.round(explorationAmount)));
                    else individual.setGene(i, new SecureRandom().nextInt());
                }
                if (indvType == short.class) {
                    if (rand <= 0.33) individual.setGene(i, (short)((short)indvValue + Math.round(explorationAmount)));
                    else if (rand <= 0.66) individual.setGene(i, (short)((short)indvValue - Math.round(explorationAmount)));
                    else individual.setGene(i, (short)new SecureRandom().nextInt(Short.MAX_VALUE));
                }
                if (indvType == byte.class) {
                    if (rand <= 0.33) individual.setGene(i, (byte)((byte)indvValue + Math.round(explorationAmount)));
                    else if (rand <= 0.66) individual.setGene(i, (byte)((byte)indvValue - Math.round(explorationAmount)));
                    else individual.setGene(i, (byte)(new SecureRandom().nextInt(255)-128));
                }
                if (indvType == boolean.class) {
                    individual.setGene(i, new SecureRandom().nextBoolean());
                }
                if (indvType == char.class) {
                    if (rand <= 0.33) individual.setGene(i, (char)((char)indvValue + Math.round(explorationAmount)));
                    else if (rand <= 0.66) individual.setGene(i, (char)((char)indvValue - Math.round(explorationAmount)));
                    else individual.setGene(i, individual.getRandomChar());
                }
                if (indvType == String.class) {
                    if (rand <= 0.01) {
                        String fixLength = (String)indvValue;
                        for (int j = 0; j < Math.floor(explorationAmount/1000); j++) fixLength+=individual.getRandomChar();
                        individual.setGene(i, fixLength);
                    }
                    else {
                        StringBuilder current = new StringBuilder((String)indvValue);
                        for (int i1 = 0; i1 < current.length(); i1++) {
                            double rand2 = Math.random();
                            if (rand2 <= 0.15) {
                                current.setCharAt(i1, (char) (current.charAt(i1)+explorationAmount%1000));
                            } else if (rand2 <= 0.3){
                                current.setCharAt(i1, (char) (current.charAt(i1)-explorationAmount%1000));
                            } else if (rand2 <= 0.55) {
                                current.setCharAt(i1, individual.getRandomChar());
                            }
                        }
                        individual.setGene(i, current.toString());
                    }
                }
            }
        }
    }

}
