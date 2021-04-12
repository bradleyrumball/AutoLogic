package com.github.bradleyrumball.autologic.logging;

import com.github.bradleyrumball.autologic.test_case_generation.OutputElement;
import com.github.javaparser.ast.expr.BinaryExpr;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MethodLoggerTest {

    @Test
    public void checkingGetFitness() {
        MethodLogger methodLogger = new MethodLogger(5);
        double fitness = methodLogger.getFitness();
        assertEquals(Integer.MAX_VALUE, fitness, 0);
    }

    // Checking the logs return statement for each operator for doubles
    @Test
    public void checkLogDouble_EQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,1, BinaryExpr.Operator.EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_EQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,4, BinaryExpr.Operator.EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogDouble_NOTEQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 4,1, BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_NOTEQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,1, BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogDouble_LESSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,10, BinaryExpr.Operator.LESS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_LESSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 10,1, BinaryExpr.Operator.LESS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogDouble_GREATERTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 10,1, BinaryExpr.Operator.GREATER);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_GREATERFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,10, BinaryExpr.Operator.GREATER);
        assertEquals(false, b);
    }

    @Test
    public void checkLogDouble_GREATEREQTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 10,1, BinaryExpr.Operator.GREATER_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_GREATEREQTrueEquals() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 10,10, BinaryExpr.Operator.GREATER_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_GREATEREQFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,10, BinaryExpr.Operator.GREATER_EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogDouble_LESSEQTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 1,10, BinaryExpr.Operator.LESS_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_LESSEQTrueEquals() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 10,10, BinaryExpr.Operator.LESS_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogDouble_LESSEQFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 10,1, BinaryExpr.Operator.LESS_EQUALS);
        assertEquals(false, b);
    }

    // Testing log return values for Strings

    @Test
    public void checkLogString_EQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, "Hi","Hi", BinaryExpr.Operator.EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogString_EQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, "Hi","Bye", BinaryExpr.Operator.EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogString_NOTEQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, "Hi","Bye", BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogString_NOTEQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, "Hi","Hi", BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(false, b);
    }

    // Testing log return values for Boolean

    @Test
    public void checkLogBoolean_EQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, true,true, BinaryExpr.Operator.EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogBoolean_EQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, true,false, BinaryExpr.Operator.EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogBoolean_NOTEQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, true,false, BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogBoolean_NOTEQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, true,true, BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(false, b);
    }

    // Testing log return values for chars

    @Test
    public void checkLogChar_EQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','a', BinaryExpr.Operator.EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_EQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','b', BinaryExpr.Operator.EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogChar_NOTEQUALSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','b', BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_NOTEQUALSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','a', BinaryExpr.Operator.NOT_EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogChar_LESSTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','b', BinaryExpr.Operator.LESS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_LESSFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'b','a', BinaryExpr.Operator.LESS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogChar_GREATERTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'z','a', BinaryExpr.Operator.GREATER);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_GREATERFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','z', BinaryExpr.Operator.GREATER);
        assertEquals(false, b);
    }

    @Test
    public void checkLogChar_GREATEREQTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'z','a', BinaryExpr.Operator.GREATER_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_GREATEREQTrueEquals() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','a', BinaryExpr.Operator.GREATER_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_GREATEREQFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','z', BinaryExpr.Operator.GREATER_EQUALS);
        assertEquals(false, b);
    }

    @Test
    public void checkLogChar_LESSEQTrue() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','z', BinaryExpr.Operator.LESS_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_LESSEQTrueEquals() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'a','a', BinaryExpr.Operator.LESS_EQUALS);
        assertEquals(true, b);
    }

    @Test
    public void checkLogChar_LESSEQFalse() {
        // True branchEval
        MethodLogger methodLogger = new MethodLogger(0);
        boolean b =  methodLogger.log(0, 'z','a', BinaryExpr.Operator.LESS_EQUALS);
        assertEquals(false, b);
    }
}
