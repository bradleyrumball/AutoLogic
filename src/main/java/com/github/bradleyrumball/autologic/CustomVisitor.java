package com.github.bradleyrumball.autologic;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.NoSuchElementException;

public class CustomVisitor extends ModifierVisitor<Void> {

    private int idCounter = 1;

    /***
     * Injects CU with log statements.
     *
     * @param n A visitor
     * @param arg args due to inheritance
     * @return A visitor
     */
    @Override
    public IfStmt visit(IfStmt n, Void arg) {
        BinaryExpr expression = n.getCondition().asBinaryExpr();
        // logging injection for if statement
        String insert = "log(" + idCounter + "," + expression.getLeft().toString()
                + "," + expression.getRight().toString() + ",Operator." + expression.getOperator().name() + ");";
        Statement statement = StaticJavaParser.parseStatement(insert);
        idCounter++;
        // If has brackets, just add new line

        if(n.getThenStmt().isBlockStmt()) {
            BlockStmt then = n.getThenStmt().asBlockStmt();
            then.getStatements().add(0, statement);
            // Adds to end but not end of if block
            //then.getStatements().addLast(statement);
            // Need to add else statement AFTER the entire block statement
            //then.getStatements().addAfter();
        } else {
            //If inline or no brackets, make block statement
            ExpressionStmt then = n.getThenStmt().asExpressionStmt();
            BlockStmt block = new BlockStmt();
            block.addStatement(statement);
            block.addStatement(then);
            n.setThenStmt(block);
        }

        // For else statements ...
        n.getElseStmt().ifPresent(stmt -> {
            if(stmt.isBlockStmt()) {
                BlockStmt elseBlock = stmt.asBlockStmt();
                BinaryExpr elseExpression = invertExpression(expression);
                String insertElse = "log(" + idCounter + "," + elseExpression.getLeft().toString()
                        + "," + elseExpression.getRight().toString() + ",Operator." + elseExpression.getOperator().name() + ");";
                Statement statementElse = StaticJavaParser.parseStatement(insertElse);
                idCounter++;
                elseBlock.getStatements().add(0, statementElse);
            // If inline
            } else if(!stmt.isIfStmt()) {
                BlockStmt elseBlock = new BlockStmt();
                n.setElseStmt(elseBlock);
                elseBlock.addStatement(stmt);
                BinaryExpr elseExpression = invertExpression(expression);
                String insertElse = "log(" + idCounter + "," + elseExpression.getLeft().toString()
                        + "," + elseExpression.getRight().toString() + ",Operator." + elseExpression.getOperator().name() + ");";
                Statement statementElse = StaticJavaParser.parseStatement(insertElse);
                idCounter++;
                elseBlock.getStatements().add(0, statementElse);
            }
        });

        // Adds else statement after a single if for alternate branch
        if(!n.hasElseBranch() && !n.hasElseBlock()) {
            BlockStmt block = new BlockStmt();
            BinaryExpr elseExpression = invertExpression(expression);
            String insertElse = "log(" + idCounter + "," + elseExpression.getLeft().toString()
                    + "," + elseExpression.getRight().toString() + ",Operator." + elseExpression.getOperator().name() + ");";
            Statement statementElse = StaticJavaParser.parseStatement(insertElse);
            idCounter++;
            block.getStatements().add(statementElse);
            n.setElseStmt(block);
        }

        // Recursion
        super.visit(n, arg);
        //System.out.println(n);
        return n;
    }

    /***
     * Helper function to invert operators - mainly used for else statements
     *
     * @param e A binary Expression
     * @return New Binary Expression
     */
    public BinaryExpr invertExpression(BinaryExpr e) {
        BinaryExpr.Operator operator = null;
        switch (e.getOperator()) {
            case EQUALS: operator = BinaryExpr.Operator.NOT_EQUALS; break;
            case NOT_EQUALS: operator = BinaryExpr.Operator.EQUALS; break;
            case GREATER: operator = BinaryExpr.Operator.LESS_EQUALS; break;
            case GREATER_EQUALS: operator = BinaryExpr.Operator.LESS; break;
            case LESS: operator = BinaryExpr.Operator.GREATER_EQUALS; break;
            case LESS_EQUALS: operator = BinaryExpr.Operator.GREATER; break;
        }
        return new BinaryExpr(e.getLeft(), e.getRight(), operator);
    }
}
