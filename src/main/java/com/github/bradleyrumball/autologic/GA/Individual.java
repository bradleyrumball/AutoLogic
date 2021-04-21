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
    private final TypeValue[] genes;
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
        this.genes = new TypeValue[method.getParameterCount() - 1];
        this.currentBranch = currentBranch;
        this.method = method;

        int rndByte = new SecureRandom().nextInt(255)-128;
        int rnd = new SecureRandom().nextInt(1 << 16);
        int starter = new SecureRandom().nextBoolean() ? rnd : rnd%25;
        char randChar = getRandomChar();
        Class<?>[] paramTypes = method.getParameterTypes();
        for (int i = 0; i < paramTypes.length-1; i++) {
            if (paramTypes[i] == int.class)
                genes[i] = new TypeValue(paramTypes[i], starter);
            if (paramTypes[i] == long.class)
                genes[i] = new TypeValue(paramTypes[i], (long)starter);
            if (paramTypes[i] == short.class)
                genes[i] = new TypeValue(paramTypes[i], (short)starter);
            if (paramTypes[i] == byte.class)
                genes[i] = new TypeValue(paramTypes[i], (byte)rndByte);
            if (paramTypes[i] == float.class)
                genes[i] = new TypeValue(paramTypes[i], (float)starter);
            if (paramTypes[i] == double.class)
                genes[i] = new TypeValue(paramTypes[i], (double)starter);
            if (paramTypes[i] == boolean.class)
                genes[i] = new TypeValue(paramTypes[i], new SecureRandom().nextBoolean());
            if (paramTypes[i] == char.class)
                genes[i] = new TypeValue(paramTypes[i], randChar);
            if (paramTypes[i] == String.class)
                genes[i] = new TypeValue(paramTypes[i], Character.toString(randChar));
        }
    }

    public char getRandomChar() {
        StringBuilder AlphaNumericString = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 abcdefghijklmnopqrstuvxyz");
        return AlphaNumericString.charAt((int) (Math.random() * AlphaNumericString.length()));

    }

    /**
     * Constructor used to create new population from old population
     * @param method
     * @param currentBranch
     * @param genes
     */
    public Individual(Method method, Integer currentBranch, TypeValue[] genes) {
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
    protected TypeValue getGene(int id) {
        return genes[id];
    }

    /**
     * Getter for all genes
     *
     * @return array of genes
     */
    public TypeValue[] getGenes() {
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
    protected void setGene(int geneID, Object geneValue) {
        genes[geneID].setValue(geneValue);
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
            for (int i = 0; i < genes.length; i++) inputParams[i] = genes[i].getValue();

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
                .map(String::valueOf)
                .toArray(String[]::new);
        return ("Input Params: " + Arrays.toString(genesString) + " | Fitness: " + fitness + " | Method Out: " + getMethodReturnValue() + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;

        if (Double.compare(that.fitness, fitness) != 0) return false;
        if (currentBranch != that.currentBranch) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(genes, that.genes)) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return methodReturnValue != null ? methodReturnValue.equals(that.methodReturnValue) : that.methodReturnValue == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Arrays.hashCode(genes);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        temp = Double.doubleToLongBits(fitness);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + currentBranch;
        result = 31 * result + (methodReturnValue != null ? methodReturnValue.hashCode() : 0);
        return result;
    }
}
