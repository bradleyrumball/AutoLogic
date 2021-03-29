package com.github.bradleyrumball.autologic.GA;

import com.github.bradleyrumball.autologic.Triangle;
import com.github.javaparser.ast.expr.BinaryExpr;

import java.util.ArrayList;
import java.util.Random;

public class Host {
    private static final int NUMBER_OF_BRANCHES = 14;

    private static int currentBranch;
    private static int currentFitness;

    private static final double mutationRate = 0.025;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;


    public static void main (String[] args) {
        // Init coverage to max int
//        for (int i = 0; i < NUMBER_OF_BRANCHES; i++) coveredBranches[NUMBER_OF_BRANCHES] = Integer.MAX_VALUE;

        ArrayList<int[]> solutions = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BRANCHES; i++) {
            currentBranch = i;

            Population population = new Population(10); //50
            int generation = 1;
            int populationFitness = population.getFittest().getFitness();
            while (populationFitness > 0) {
                System.out.println("Generation: " + generation + " Current fitness: " + populationFitness);
                population = evolvePopulation(population);
                generation++;
                populationFitness = population.getFittest().getFitness();
            }
            System.out.println("Solution for branch " + currentBranch + " has been found");
            solutions.add(i, population.getFittest().getGenes());
        }
        System.out.println("Solutions found for all");
        System.out.println(solutions.toString());
    }

    public static Population evolvePopulation(Population population) {
        Population newPopulation = new Population();

        int elitismOffset = 0;
        if (elitism) {
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
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndividual = (int) (Math.random() * population.getIndividuals().size());
            tournament.addIndividual(population.getIndividual(randomIndividual));
        }
        return tournament.getFittest();

    }

    private static Individual crossover(Individual individualA, Individual individualB) {
        final double crossoverBias = 0.5;
        Individual cross = new Individual();
        for (int i = 0; i < cross.getGeneCount(); i++) {
            if(Math.random() <= crossoverBias) cross.setGene(i, individualA.getGene(i));
            else cross.setGene(i, individualB.getGene(i));
        }
        return cross;
    }

    private static void mutate(Individual individual) {
        for (int i = 0; i < individual.getGeneCount(); i++) {
            if(Math.random() <= mutationRate) individual.setGene(i, new Random().nextInt());
        }
    }

    protected static int getFitness(Individual individual) {
        currentFitness = Integer.MAX_VALUE;
        instrumentedClassify(individual.getGene(0), individual.getGene(1), individual.getGene(2));
        return currentFitness;
    }



    //-------------------------------------------------------------------

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
//        System.out.println("id: "+id+", left: "+left+", right: "+right+", OP: "+operator+", Fit:"+fScore);

        if(id==currentBranch) currentFitness = fScore;

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
//      coveredBranch(1, coveredBranches);
            int temp = side1;
            side1 = side2;
            side2 = temp;
        } else {
            log(1, side1, side2, BinaryExpr.Operator.LESS_EQUALS);
//      coveredBranch(2, coveredBranches);
        }

        if (log(2,side1, side3, BinaryExpr.Operator.GREATER)) {
//      coveredBranch(3, coveredBranches);
            int temp = side1;
            side1 = side3;
            side3 = temp;
        } else {
            log(3,side1, side3, BinaryExpr.Operator.LESS_EQUALS);
//      coveredBranch(4, coveredBranches);
        }

        if (log(4, side2, side3, BinaryExpr.Operator.GREATER)) {
//      coveredBranch(5, coveredBranches);
            int temp = side2;
            side2 = side3;
            side3 = temp;
        } else {
            log(5, side2, side3, BinaryExpr.Operator.LESS_EQUALS);
//      coveredBranch(6, coveredBranches);
        }

        if (log(6, side1 + side2, side3, BinaryExpr.Operator.LESS_EQUALS)) {
//      coveredBranch(7, coveredBranches);
            type = Triangle.Type.INVALID;
        } else {
            log(7, side1 + side2, side3, BinaryExpr.Operator.GREATER);
//      coveredBranch(8, coveredBranches);
            type = Triangle.Type.SCALENE;
            if (log(8, side1, side2, BinaryExpr.Operator.EQUALS)) {
//        coveredBranch(9, coveredBranches);
                if (log(12, side2, side3, BinaryExpr.Operator.EQUALS)) {
//          coveredBranch(10, coveredBranches);
                    type = Triangle.Type.EQUILATERAL;
                } else {
                    log(13, side2, side3, BinaryExpr.Operator.NOT_EQUALS);
//          coveredBranch(11, coveredBranches);
                }
            } else {
                log(9, side1, side2, BinaryExpr.Operator.NOT_EQUALS);
//        coveredBranch(12, coveredBranches);
                if (log(10, side2, side3, BinaryExpr.Operator.EQUALS)) {
//          coveredBranch(13, coveredBranches);
                    type = Triangle.Type.ISOSCELES;
                } else {
                    log(11, side2, side3, BinaryExpr.Operator.NOT_EQUALS);
//          coveredBranch(14, coveredBranches);
                }
            }
        }
        return type;
    }
}
