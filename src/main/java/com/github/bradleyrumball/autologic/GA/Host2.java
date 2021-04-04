package com.github.bradleyrumball.autologic.GA;

import com.github.bradleyrumball.autologic.Triangle;
import com.github.bradleyrumball.autologic.test_case_generation.JUnitOutputManager;
import com.github.javaparser.ast.expr.BinaryExpr;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Host2 {

    private static final int NUMBER_OF_BRANCHES = 14;

    public static final int NUMBER_OF_GENES = 3;

    private static int currentBranch;

    private static long currentFitness;

    private static final double MUTATION_RATE = 0.4;

    private static final double CROSSOVER_BIAS = 0.5;

    private static final int TOURNAMENT_SIZE = 15;

    private static final boolean ELITISM = true;

    public static void run(String[] args) {
        ArrayList<Individual> solutions = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BRANCHES; i++) {
            currentBranch = i;
            Population population = new Population(50);
            long populationFitness = population.getFittest().getFitness();
            while (populationFitness > 0) {
                population = evolvePopulation(population);
                populationFitness = population.getFittest().getFitness();
            }
            System.out.println("  for branch " + currentBranch + " has been found");
            solutions.add(i, population.getFittest());
        }
        System.out.println("Solutions found for all");
        JUnitOutputManager jUnitGenerator = new JUnitOutputManager(solutions);
        jUnitGenerator.unitGenerator();
    }

    public static Population evolvePopulation(Population population) {
        Population newPopulation = new Population();
        int elitismOffset = 0;
        if (ELITISM) {
            newPopulation.addIndividual(population.getFittest());
            elitismOffset = 1;
        }
        for (int i = elitismOffset; i < population.getIndividuals().size(); i++) {
            Individual tournamentWinner1 = tournament(population);
            Individual tournamentWinner2 = tournament(population);
            Individual crossoverIndividual = crossover(tournamentWinner1, tournamentWinner2);
            newPopulation.addIndividual(crossoverIndividual);
        }
        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }
        return newPopulation;
    }

    private static Individual tournament(Population population) {
        Population tournament = new Population(0);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomIndividual = (int) (Math.random() * population.getIndividuals().size());
            tournament.addIndividual(population.getIndividual(randomIndividual));
        }
        return tournament.getFittest();
    }

    private static Individual crossover(Individual individualA, Individual individualB) {
        Individual cross = new Individual();
        for (int i = 0; i < cross.getGeneCount(); i++) {
            if (Math.random() <= CROSSOVER_BIAS)
                cross.setGene(i, individualA.getGene(i));
            else
                cross.setGene(i, individualB.getGene(i));
        }
        return cross;
    }

    private static void mutate(Individual individual) {
        int explorationAmount = 1;
        if (individual.getFitness() > 10000)
            explorationAmount = 500;
        if (individual.getFitness() > 1000)
            explorationAmount = 50;
        if (individual.getFitness() > 10)
            explorationAmount = 5;
        for (int i = 0; i < individual.getGeneCount(); i++) {
            if (Math.random() <= MUTATION_RATE) {
                double rand = Math.random();
                if (rand <= 0.33)
                    individual.setGene(i, individual.getGene(i) + explorationAmount);
                else if (rand <= 0.66)
                    individual.setGene(i, individual.getGene(i) - explorationAmount);
                else
                    individual.setGene(i, new SecureRandom().nextInt());
            }
        }
    }

    protected static long getFitness(Individual individual) {
        currentFitness = Integer.MAX_VALUE;
        instrumentedMethod(individual.getGene(0), individual.getGene(1), individual.getGene(2));
        return currentFitness;
    }

    protected static Object getMethodReturn(Individual individual) {
        return instrumentedMethod(individual.getGene(0), individual.getGene(1), individual.getGene(2));
    }

    private static boolean log(int id, long left, long right, BinaryExpr.Operator operator) {
        Fitness f = new Fitness(left, right, operator);
        long fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id == currentBranch) {
            currentFitness = fScore;
        }
        return fScore == 0;
    }

    private static Triangle.Type instrumentedMethod(int side1, int side2, int side3) {
        Triangle.Type type;
        if (log(0, side1, side2, BinaryExpr.Operator.GREATER)) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        } else {
            log(1, side1, side2, BinaryExpr.Operator.LESS_EQUALS);
        }
        if (log(2, side1, side3, BinaryExpr.Operator.GREATER)) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        } else {
            log(3, side1, side3, BinaryExpr.Operator.LESS_EQUALS);
        }
        if (log(4, side2, side3, BinaryExpr.Operator.GREATER)) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        } else {
            log(5, side2, side3, BinaryExpr.Operator.LESS_EQUALS);
        }
        if (log(6, (long) side1 + (long) side2, side3, BinaryExpr.Operator.LESS_EQUALS)) {
            type = Triangle.Type.INVALID;
        } else {
            log(7, (long) side1 + (long) side2, side3, BinaryExpr.Operator.GREATER);
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

    private static Object Name(int side1, int side2, int side3) {
        Type type;
        if (log(0, side1, side2, BinaryExpr.Operator.GREATER)) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        } else if ((log(1, side1, side2, BinaryExpr.Operator.LESS_EQUALS)))
            return;
        if (log(2, side1, side3, BinaryExpr.Operator.GREATER)) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        } else if ((log(3, side1, side3, BinaryExpr.Operator.LESS_EQUALS)))
            return;
        if (log(4, side2, side3, BinaryExpr.Operator.GREATER)) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        } else if ((log(5, side2, side3, BinaryExpr.Operator.LESS_EQUALS)))
            return;
        if (log(6, side1 + side2, side3, BinaryExpr.Operator.LESS_EQUALS)) {
            type = Type.INVALID;
        } else if ((log(7, side1 + side2, side3, BinaryExpr.Operator.GREATER)))
            return;
        return type;
    }
}
