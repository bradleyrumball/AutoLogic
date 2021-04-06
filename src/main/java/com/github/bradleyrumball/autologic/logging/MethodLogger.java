package com.github.bradleyrumball.autologic.logging;

import com.github.bradleyrumball.autologic.GA.Fitness;
import com.github.javaparser.ast.expr.BinaryExpr;

/**
 * Class to control logging of methods and fitness handlign
 */
public class MethodLogger {

    private long fitness = Integer.MAX_VALUE;
    private int currentBranch;


    public MethodLogger(int currentBranch){
        this.currentBranch = currentBranch;
    }


    public long getFitness() {
        return fitness;
    }

    public boolean log(int id, long left, long right, BinaryExpr.Operator operator) {
        Fitness f = new Fitness(left, right, operator);
        long fScore = Integer.MAX_VALUE;
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

    public boolean log(int id){
        if (id == currentBranch) fitness = 0;
        return true;
    }
}
