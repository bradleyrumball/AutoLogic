package com.github.bradleyrumball.autologic.logging;

import com.github.bradleyrumball.autologic.GA.Fitness;
import com.github.javaparser.ast.expr.BinaryExpr;

/**
 * Class to control logging of methods and fitness handlign
 */
public class MethodLogger {

    private double fitness = Integer.MAX_VALUE;
    private int currentBranch;

    /**
     * Constructor for method logger
     * @param currentBranch the current branch that we are logging fitness for
     */
    public MethodLogger(int currentBranch){
        this.currentBranch = currentBranch;
    }


    /**
     * Return the current fitness for the current branch
     * @return
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Logging statement called by instrumented method when conditions are involved
     * @param id log statement id (branch id)
     * @param left left hand condition
     * @param right right hand condition
     * @param operator operator of the condition
     * @return the evaluation of the condition
     */
    public boolean log(int id, double left, double right, BinaryExpr.Operator operator) {
        Fitness f = new Fitness(left, right, operator);
        double fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We only set set the fitness value if the current branch is the one we are testing
        if(id==currentBranch) fitness = fScore;
        // to continue with execution
        return fScore == 0;
    }

    /**
     * Logging statment for when we are logging a branch and there are no conditions to pass
     * @param id log statement id (branch id)
     * @return true - could return void
     */
    public boolean log(int id){
        if (id == currentBranch) fitness = 0;
        return true;
    }
}
