package com.github.bradleyrumball.autologic.logging;

import com.github.bradleyrumball.autologic.GA.Fitness;
import com.github.javaparser.ast.expr.BinaryExpr;

/**
 * Class to control logging of methods and fitness handlign
 */
public class MethodLogger {

    private double fitness = Integer.MAX_VALUE;
    private int currentBranch;
    private boolean branchEvaluatesTrue;

    /**
     * Constructor for method logger
     * @param currentBranch the current branch that we are logging fitness for
     */
    public MethodLogger(int currentBranch){
        this.currentBranch = currentBranch;
        this.branchEvaluatesTrue = (currentBranch%2 == 0);
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
        // Careful with the logic here (lots of inverting going on)
        Fitness f = new Fitness(left, right, branchEvaluatesTrue ? operator : invertOperator(operator));
        double fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We only set set the fitness value if the current branch is the one we are testing
        if((id+(branchEvaluatesTrue?0:1)) == currentBranch)
            fitness = fScore;
        // to continue with execution (if the fScore is 0 and the branch is taking the path
        // that is equal to the fScore being zero then return true otherwise false.
        return branchEvaluatesTrue == (fScore == 0);
    }


    public boolean log(int id, String left, String right, BinaryExpr.Operator operator) {
        // Careful with the logic here (lots of inverting going on)
        Fitness f = new Fitness(left, right, branchEvaluatesTrue ? operator : invertOperator(operator));
        double fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We only set set the fitness value if the current branch is the one we are testing
        if((id+(branchEvaluatesTrue?0:1)) == currentBranch)
            fitness = fScore;
        // to continue with execution (if the fScore is 0 and the branch is taking the path
        // that is equal to the fScore being zero then return true otherwise false.
        return branchEvaluatesTrue == (fScore == 0);
    }

    public boolean log(int id, boolean left, boolean right, BinaryExpr.Operator operator) {
        // Careful with the logic here (lots of inverting going on)
        Fitness f = new Fitness(left, right, branchEvaluatesTrue ? operator : invertOperator(operator));
        double fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We only set set the fitness value if the current branch is the one we are testing
        if((id+(branchEvaluatesTrue?0:1)) == currentBranch)
            fitness = fScore;
        // to continue with execution (if the fScore is 0 and the branch is taking the path
        // that is equal to the fScore being zero then return true otherwise false.
        return branchEvaluatesTrue == (fScore == 0);
    }

    public boolean log(int id, char left, char right, BinaryExpr.Operator operator) {
        // Careful with the logic here (lots of inverting going on)
        Fitness f = new Fitness((int)left, (int)right, branchEvaluatesTrue ? operator : invertOperator(operator));
        double fScore = Integer.MAX_VALUE;
        try {
            fScore = f.getFitness();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We only set set the fitness value if the current branch is the one we are testing
        if((id+(branchEvaluatesTrue?0:1)) == currentBranch)
            fitness = fScore;
        // to continue with execution (if the fScore is 0 and the branch is taking the path
        // that is equal to the fScore being zero then return true otherwise false.
        return branchEvaluatesTrue == (fScore == 0);
    }



    /**
     * Logging statment for when we are logging a branch and there are no conditions to pass
     * @param id log statement id (branch id)
     * @return true - could return void
     */
    @Deprecated
    public boolean log(int id){
        if (id == currentBranch) fitness = 0;
        return true;
    }

    /**
     * Gets the opposite of the binary expression input
     * @param op a binary operator
     * @return the opposing binary operator
     */
    private BinaryExpr.Operator invertOperator(BinaryExpr.Operator op) {
        BinaryExpr.Operator inverse = null;
        switch (op) {
            case EQUALS:
                inverse = BinaryExpr.Operator.NOT_EQUALS;
                break;
            case NOT_EQUALS:
                inverse = BinaryExpr.Operator.EQUALS;
                break;
            case GREATER:
                inverse = BinaryExpr.Operator.LESS_EQUALS;
                break;
            case GREATER_EQUALS:
                inverse = BinaryExpr.Operator.LESS;
                break;
            case LESS:
                inverse = BinaryExpr.Operator.GREATER_EQUALS;
                break;
            case LESS_EQUALS:
                inverse = BinaryExpr.Operator.GREATER;
                break;
        }
        return inverse;
    }
}
