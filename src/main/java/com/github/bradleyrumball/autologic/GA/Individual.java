package com.github.bradleyrumball.autologic.GA;


import com.github.bradleyrumball.autologic.logging.MethodLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * This class contains a instance of an individual
 * an individual has its own gene pool which is tested for fitness.
 * Individuals form a population.
 */
public class Individual {


    /**
     * Genes represent the parameters that are going into the method
     */
    private final int[] genes;
    /**
     * The method that is under test
     */
    private final Method method;
    /**
     * The current fitness of the individual
     * <p>
     * Worst fitness is determined as Integer.Max
     * Ideal fitness is 0
     */
    private double fitness = Integer.MAX_VALUE;
    /**
     * The current branch that an individual is targeting
     */
    private int currentBranch;
    /**
     * The value that the method under test returns once the individual has been executed on it
     */
    private Object methodReturnValue;

    /**
     * Constructor, individuals are created with their initial gene pool set
     * such that all genes are equal, this is because it is time consuming for
     * the GA to create a case where multiple parameters are equal
     *
     * @param method        - the method under test
     * @param currentBranch - the current branch number the individual is to test for
     */
    public Individual(Method method, Integer currentBranch) {
        this.genes = new int[method.getParameterCount() - 1];
        this.currentBranch = currentBranch;
        this.method = method;
        int starter = new SecureRandom().nextInt();
        if (new SecureRandom().nextBoolean()) Arrays.fill(genes, starter);
        else Arrays.fill(genes, starter % 25);
    }

    /**
     * Constructor used to create new population from old population
     * @param method
     * @param currentBranch
     * @param genes
     */
    public Individual(Method method, Integer currentBranch, int[] genes) {
        this.genes = genes;
        this.currentBranch = currentBranch;
        this.method = method;
    }

    /**
     * Getter for individual gene
     *
     * @param id the if of the individual gene that we wish to return
     * @return value of individual gene/param
     */
    protected int getGene(int id) {
        return genes[id];
    }

    /**
     * Getter for all genes
     *
     * @return array of genes
     */
    public int[] getGenes() {
        return genes;
    }

    /**
     * Return the number of genes that each individual carries
     *
     * @return
     */
    protected int getGeneCount() {
        return genes.length;
    }

    /**
     * Allows a gene to be set individually
     * Can be used by crossover/mutate for example
     *
     * @param geneID    The ID of the gene that you wish to set
     * @param geneValue The value that you wish to set the gene to
     */
    protected void setGene(int geneID, int geneValue) {
        genes[geneID] = geneValue;
        // If a gene has been updated or set its fitness scores must be restarted
        fitness = Integer.MAX_VALUE;
    }

    /**
     * Call back to host to get the fitness of the current individual if it hasn't already been
     * calculated.
     *
     * @return int - fitness value of the individual
     */
    public double getFitness() {
        if (fitness == Integer.MAX_VALUE) {
            Object[] inputParams = new Object[genes.length + 1];
            for (int i = 0; i < genes.length; i++) inputParams[i] = genes[i];

            MethodLogger methodLogger = new MethodLogger(currentBranch);
            inputParams[inputParams.length - 1] = methodLogger;

            try {
                methodReturnValue = method.invoke(null, inputParams);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            fitness = methodLogger.getFitness();
        }
        return fitness;
    }

    /**
     * Get the method from the current individual
     * @return
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Calls back to the host to run the class under test and get the expected output that will be used
     * in the methodReturnValue
     *
     * @return Object (the return type of the method under test)
     */
    public Object getMethodReturnValue() {
        return methodReturnValue;
    }

    /**
     * For debugging more than anything
     *
     * @return The individual as a string
     */
    @Override
    public String toString() {
        String[] genesString = Arrays.stream(genes)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
        return ("Input Params: " + Arrays.toString(genesString) + " | Fitness: " + fitness + " | Method Out: " + getMethodReturnValue() + "\n");
    }

}
