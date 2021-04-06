package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.ArrayList;

public class IfElseInjectionVisitor extends ModifierVisitor<Void> {

    /**
     * A counter to keep track of the id of the log statement injected
     */
    private int idCounter = 0;
    /**
     * Used to collect conditions from if statements that will be
     * inverted to find the else branch execution condition
     */
    private final ArrayList<BinaryExpr> elseBuilder = new ArrayList<>();

    /**
     * Used to get the number of logging statements issued
     *
     * @return count of log IDs
     */
    public int getIdCounter() {
        return idCounter;
    }

    /***
     * Custom visitor function that recursively calls itself
     *
     * @param n A visitor
     * @param arg args due to inheritance
     * @return A visitor
     */
    @Override
    public IfStmt visit(IfStmt n, Void arg) {
        //Check if the current IF statement that we are visiting as already been injected with log statements
        //System.out.println(n);
        BinaryExpr conditionExpression = n.getCondition().asBinaryExpr().clone();
        n.setCondition(getLogStatement(n.getCondition().asBinaryExpr(), false));
        // Add the current visitor condition to the list of conditions for constructing the else branch
//        elseBuilder.add(conditionExpression.clone());
//        }
//
//
//        // For the IF's else branch...
        // For else statements ...
        n.getElseStmt().ifPresent(stmt -> {
            if (stmt.isBlockStmt()) {
                BlockStmt elseBlock = stmt.asBlockStmt();

                elseBlock.getStatements().add(0, getLogStatement());
                // If inline
            } else if (!stmt.isIfStmt()) {
                BlockStmt elseBlock = new BlockStmt();
                n.setElseStmt(elseBlock);
                elseBlock.addStatement(stmt);
                elseBlock.getStatements().add(0, getLogStatement());
            }
        });


        if (!n.hasElseBlock() && !n.hasElseBranch()) {
            BlockStmt block = new BlockStmt();
            block.getStatements().add(getLogStatement());
            n.setElseStmt(block);
        }

        // Recursion
        super.visit(n, arg);


        // Return the injected cu
        return n;
    }

    /**
     * Used to build the else condition
     * As our GA will need to track else branches for full branch coverage and full condition coverage
     * we construct a condition set for the else branch which is based on De Morgan's laws.
     * The conditions from all ifs and if-else's in a block are taken; their logical operators are
     * replaced with the logical complement. If there is a chain of conditions the && or || are again swapped
     *
     * @return Logged else expression
     */
    @Deprecated
    private Expression buildElse() {
        StringBuilder combined = new StringBuilder();
        // Combine all conditions into one singular string and join conditions with &&
        for (int i = 0; i < elseBuilder.size(); i++) {
            // Else generator returns the logging statements for each condition with De Morgans applied
            combined.append("(").append(getLogStatement(elseBuilder.get(i), true).toString()).append(")");
            if (i != elseBuilder.size() - 1) combined.append(" && ");
        }
        // Parse the string to create an Expression object that can be used as a new if condition
        Expression polishedElse = StaticJavaParser.parseExpression(combined.toString());
        // Else builder must be cleared to allow later if blocks to build else's without conflict
        elseBuilder.clear();
        return polishedElse;
    }

    /**
     * Used to transform a logical condition into a logging statement
     * Can handle chained conditions such as those combined with && or || as it is recursive
     *
     * @param e      A logical condition
     * @param invert If we are building an else condition we want to perform De Morgan's (invert -> true)
     * @return A logging expression
     */
    private Expression getLogStatement(BinaryExpr e, Boolean invert) {
        // Recursive step - whilst operator is an AND or OR we recall the function
        if (e.getOperator() == BinaryExpr.Operator.AND || e.getOperator() == BinaryExpr.Operator.OR) {
            e.setLeft(getLogStatement(e.getLeft().asBinaryExpr(), invert));
            e.setRight(getLogStatement(e.getRight().asBinaryExpr(), invert));
            return invert ? invertExpression(e) : e;
            //Base case - one there are no more ANDs or ORs we have pure logical conditions (i.e <,>,!=,==, and so on)
        } else {
            BinaryExpr condition = invert ? invertExpression(e) : e;
            String insert = "log(" + idCounter + "," + condition.getLeft().toString()
                    + "," + condition.getRight().toString() + ",BinaryExpr.Operator." + condition.getOperator().name() + ")";
            Expression expressionCondition = StaticJavaParser.parseExpression(insert);
            idCounter++;
            return expressionCondition;
        }
    }

    private Statement getLogStatement() {
        String insert = "log(" + idCounter + ");";
        Statement statementLog = StaticJavaParser.parseStatement(insert);
        idCounter++;
        return statementLog;
    }

    /***
     * Helper function to invert operators - mainly used for else statements
     *
     * @param e A binary Expression
     * @return New Binary Expression
     */
    @Deprecated
    private BinaryExpr invertExpression(BinaryExpr e) {
        BinaryExpr.Operator operator = null;
        switch (e.getOperator()) {
            case EQUALS:
                operator = BinaryExpr.Operator.NOT_EQUALS;
                break;
            case NOT_EQUALS:
                operator = BinaryExpr.Operator.EQUALS;
                break;
            case GREATER:
                operator = BinaryExpr.Operator.LESS_EQUALS;
                break;
            case GREATER_EQUALS:
                operator = BinaryExpr.Operator.LESS;
                break;
            case LESS:
                operator = BinaryExpr.Operator.GREATER_EQUALS;
                break;
            case LESS_EQUALS:
                operator = BinaryExpr.Operator.GREATER;
                break;
            case AND:
                operator = BinaryExpr.Operator.OR;
                break;
            case OR:
                operator = BinaryExpr.Operator.AND;
                break;
        }
        return new BinaryExpr(e.getLeft(), e.getRight(), operator);
    }

}
