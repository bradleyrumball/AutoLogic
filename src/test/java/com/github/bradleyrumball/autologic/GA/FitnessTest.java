package com.github.bradleyrumball.autologic.GA;

import com.github.javaparser.ast.expr.BinaryExpr;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FitnessTest {

    @Test
    public void checkEqualWhenEqual() throws Exception {
        Fitness fitness = new Fitness(1,1, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkEqualWhenNotEqual() throws Exception {
        Fitness fitness = new Fitness(1,2, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }

    @Test
    public void checkNotEqualWhenNotEqual() throws Exception {
        Fitness fitness = new Fitness(10,1, BinaryExpr.Operator.NOT_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore,0);
    }

    @Test
    public void checkNotEqualWhenEqual() throws Exception {
        Fitness fitness = new Fitness(1,1, BinaryExpr.Operator.NOT_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }

    @Test
    public void checkLessThanWhenLessThan() throws Exception {
        Fitness fitness = new Fitness(4,5, BinaryExpr.Operator.LESS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkLessThanWhenEqual() throws Exception {
        Fitness fitness = new Fitness(5,5, BinaryExpr.Operator.LESS);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }

    @Test
    public void checkLessThanWhenGreater() throws Exception {
        Fitness fitness = new Fitness(6,5, BinaryExpr.Operator.LESS);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }

    @Test
    public void checkLessThanEqualWhenLessThan() throws Exception {
        Fitness fitness = new Fitness(4,5, BinaryExpr.Operator.LESS_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkLessThanEqualWhenEqual() throws Exception {
        Fitness fitness = new Fitness(5,5, BinaryExpr.Operator.LESS_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkLessThanEqualWhenGreater() throws Exception {
        Fitness fitness = new Fitness(6,5, BinaryExpr.Operator.LESS_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }
//
    @Test
    public void checkGreaterThanWhenGreaterThan() throws Exception {
        Fitness fitness = new Fitness(5,4, BinaryExpr.Operator.GREATER);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkGreaterThanWhenEqual() throws Exception {
        Fitness fitness = new Fitness(5,5, BinaryExpr.Operator.GREATER);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }

    @Test
    public void checkGreaterThanWhenLess() throws Exception {
        Fitness fitness = new Fitness(5,6, BinaryExpr.Operator.GREATER);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore);
    }

    @Test
    public void checkGreaterThanEqualWhenGreaterThan() throws Exception {
        Fitness fitness = new Fitness(5,4, BinaryExpr.Operator.GREATER_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkGreaterThanEqualWhenEqual() throws Exception {
        Fitness fitness = new Fitness(5,5, BinaryExpr.Operator.GREATER_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkGreaterThanEqualWhenLess() throws Exception {
        Fitness fitness = new Fitness(5,6, BinaryExpr.Operator.GREATER_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertNotEquals(0, fitnessScore, 0);
    }


}