package com.github.bradleyrumball.autologic.GA;

import com.github.bradleyrumball.autologic.Triangle;
import com.github.javaparser.ast.expr.BinaryExpr;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Controls the main execution of the GA
 */
public class Host {
    /**
     * Temp
     * This is the number of branches that can possibly execute in the method under test
     * This should be calculated when the method is parsed and instrumented
     */
    private static final int NUMBER_OF_BRANCHES = 14;

    /**
     * The current branch that the GA is attempting to obtain solutions for
     */
    private static int currentBranch;
    /**
     * The fitness of an individual that has been run
     * This value is used as a handler between log, getFitness and handing back to the individual class
     */
    private static int currentFitness;

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
     * Runs the GA - probably should be abstracted to another class
     * @param args program args
     */
    public static void main (String[] args) {
        ArrayList<Individual> solutions = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BRANCHES; i++) {
            currentBranch = i;

            Population population = new Population(50); //50
            int generation = 1;
            int populationFitness = population.getFittest().getFitness();
            while (populationFitness > 0) {
//                System.out.println("Generation: " + generation + " Current fitness: " + populationFitness);
                population = evolvePopulation(population);
                generation++;
                populationFitness = population.getFittest().getFitness();
            }
            System.out.println("Solution for branch " + currentBranch + " has been found");
            solutions.add(i, population.getFittest());
        }
        System.out.println("Solutions found for all");
        System.out.println(solutions.toString());
    }

    /**
     * Once a population has been evaluated we evolve it by crossover and mutation
     * @param population the population that has just been tested for fitness
     * @return population - a new population that contains hopefully better individuals
     */
    public static Population evolvePopulation(Population population) {
        Population newPopulation = new Population();

        // Elitism - Get best individual
        int elitismOffset = 0;
        if (ELITISM) {
            newPopulation.addIndividual(population.getFittest());
            elitismOffset = 1;
        }

        // Crossover - get best x individuals and cross them
        for (int i = elitismOffset; i < population.getIndividuals().size(); i++) {
            Individual tournamentWinner1 = tournament(population);
            Individual tournamentWinner2 = tournament(population);
            Individual crossoverIndividual = crossover(tournamentWinner1, tournamentWinner2);
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
     * @param population the population that had just been run
     * @return Individual - the best individual from a random proportion of the population
     */
    private static Individual tournament(Population population) {
        Population tournament = new Population(0);
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
     * @param individualA parent A of the new individual
     * @param individualB parent B of the new individual
     * @return a child of A and B
     */
    private static Individual crossover(Individual individualA, Individual individualB) {
        Individual cross = new Individual();
        for (int i = 0; i < cross.getGeneCount(); i++) {
            if(Math.random() <= CROSSOVER_BIAS) cross.setGene(i, individualA.getGene(i));
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
     *
     * When incrementing and decrementing, the amount to manipulate the value by is dependant on
     * how far the value is from its optimum fitness
     * @param individual the individual to mutate
     */
    private static void mutate(Individual individual) {
        int explorationAmount = 1;
        if (individual.getFitness() > 10000) explorationAmount = 500;
        if (individual.getFitness() > 1000) explorationAmount = 50;
        if (individual.getFitness() > 10) explorationAmount = 5;
        for (int i = 0; i < individual.getGeneCount(); i++) {
            if(Math.random() <= MUTATION_RATE) {
                double rand = Math.random();
                if(rand <= 0.33) individual.setGene(i, individual.getGene(i)+explorationAmount);
                else if(rand <= 0.66) individual.setGene(i, individual.getGene(i)-explorationAmount);
                else individual.setGene(i, new SecureRandom().nextInt());
            }

        }
    }

    /**
     * Called by the Individual class the run its gene pool on the instrumented class
     * returns the fitness produced by the individual
     * @param individual the individual to test the class with
     * @return fitness of individual
     */
    protected static int getFitness(Individual individual) {
        currentFitness = Integer.MAX_VALUE;
        instrumentedClassify(individual.getGene(0), individual.getGene(1), individual.getGene(2));

        return currentFitness;
    }

    /**
     * Logging method from instrumented class
     * @param id the current branch
     * @param left the parameter on the left side of the operator
     * @param right the parameter on the right side of the operator
     * @param operator the operator in the condition between left and right
     * @return the boolean output of the condition once evaluated
     */
    private static boolean log(int id, int left, int right, BinaryExpr.Operator operator) {
        Fitness f = new Fitness(left, right, operator);
        int fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We only set set the fitness value if the current branch is the one we are testing
        if(id==currentBranch) {
            currentFitness = fScore;
//            System.out.println("id: "+id+", left: "+left+", right: "+right+", OP: "+operator+", Fit:"+currentFitness);
        }
        // to continue with execution
        return fScore == 0;
    }

    /**
     * Instrumented class under test
     * @param side1 side length 1
     * @param side2 side length 2
     * @param side3 side length 3
     * @return triangle type

     */
    private static Triangle.Type instrumentedClassify (int side1, int side2, int side3) {
        Triangle.Type type;

        if (log(0, side1, side2, BinaryExpr.Operator.GREATER)) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        } else {
            log(1, side1, side2, BinaryExpr.Operator.LESS_EQUALS);
        }

        if (log(2,side1, side3, BinaryExpr.Operator.GREATER)) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        } else {
            log(3,side1, side3, BinaryExpr.Operator.LESS_EQUALS);
        }

        if (log(4, side2, side3, BinaryExpr.Operator.GREATER)) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        } else {
            log(5, side2, side3, BinaryExpr.Operator.LESS_EQUALS);
        }

        if (log(6, side1 + side2, side3, BinaryExpr.Operator.LESS_EQUALS)) {
            type = Triangle.Type.INVALID;
        } else {
            log(7, side1 + side2, side3, BinaryExpr.Operator.GREATER);
            type = Triangle.Type.SCALENE;
            if (log(8, side1, side2, BinaryExpr.Operator.EQUALS)) {
                if (log(12, side2, side3, BinaryExpr.Operator.EQUALS)) {
                    type = Triangle.Type.EQUILATERAL;
                } else {
                    log(13, side2, side3, BinaryExpr.Operator.NOT_EQUALS);
                }
            } else {
                log(9, side1, side2, BinaryExpr.Operator.NOT_EQUALS);
                if (log(10, side2, side3, BinaryExpr.Operator.EQUALS)) {
                    type = Triangle.Type.ISOSCELES;
                } else {
                    log(11, side2, side3, BinaryExpr.Operator.NOT_EQUALS);
                }
            }
        }
        return type;
    }
}
