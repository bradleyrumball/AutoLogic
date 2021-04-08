package com.github.bradleyrumball.autologic.GA;

import com.github.javaparser.ast.expr.BinaryExpr;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FitnessTest {

    // TEST SUITE BELOW IS SIMPLE CHECK TO ENSURE INPUTS ARE ACCEPTED
    @Test
    public void checkPrimitiveByte() throws Exception {
        byte input = Byte.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperByte() throws Exception {
        Byte input = Byte.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveShort() throws Exception {
        short input = Short.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperShort() throws Exception {
        Short input = Short.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveInt() throws Exception {
        int input = Integer.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperInteger() throws Exception {
        Integer input = Integer.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveLong() throws Exception {
        long input = Long.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperLong() throws Exception {
        Long input = Long.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveFloat() throws Exception {
        float input = Float.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperFloat() throws Exception {
        Float input = Float.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveDouble() throws Exception {
        double input = Double.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperDouble() throws Exception {
        Double input = Double.MAX_VALUE;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveBoolean() throws Exception {
        boolean input = true;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperBoolean() throws Exception {
        Boolean input = true;
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkPrimitiveChar() throws Exception {
        char input = 'B';
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkWrapperCharacter() throws Exception {
        Character input = 'B';
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkString() throws Exception {
        String input = "Beans";
        Fitness fitness = new Fitness(input,input, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }

    @Test
    public void checkStringEqualHalfWrong() throws Exception {
        String a = "bird";
        String b = "herd";
        Fitness fitness = new Fitness(a,b, BinaryExpr.Operator.EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(3, fitnessScore, 0);
    }

    @Test
    public void checkStringNotEqual() throws Exception {
        String a = "fly";
        String b = "ant";
        Fitness fitness = new Fitness(a,b, BinaryExpr.Operator.NOT_EQUALS);
        double fitnessScore = fitness.getFitness();
        assertEquals(0, fitnessScore, 0);
    }



    // COMPLETE SUITE BELOW FOR INTEGER INPUTS

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
        assertEquals(1, fitnessScore, 0);
    }

    @Test
    public void checkLessThanWhenGreater() throws Exception {
        Fitness fitness = new Fitness(6,5, BinaryExpr.Operator.LESS);
        double fitnessScore = fitness.getFitness();
        assertEquals(2, fitnessScore, 0);
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
        assertEquals(2, fitnessScore, 0);
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
        assertEquals(1, fitnessScore, 0);
    }

    @Test
    public void checkGreaterThanWhenLess() throws Exception {
        Fitness fitness = new Fitness(5,6, BinaryExpr.Operator.GREATER);
        double fitnessScore = fitness.getFitness();
        assertEquals(2, fitnessScore, 0);
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
        assertEquals(2, fitnessScore, 0);
    }


}