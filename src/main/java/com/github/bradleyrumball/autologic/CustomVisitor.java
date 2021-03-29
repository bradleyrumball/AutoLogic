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

        n.setCondition(ifLogStatement(expression));

        // For else statements ...
        n.getElseStmt().ifPresent(stmt -> {
            if(stmt.isBlockStmt()) {
                BlockStmt elseBlock = stmt.asBlockStmt();

                elseBlock.getStatements().add(0, elseLogStatement(expression));
            // If inline
            } else if(!stmt.isIfStmt()) {
                BlockStmt elseBlock = new BlockStmt();
                n.setElseStmt(elseBlock);
                elseBlock.addStatement(stmt);
                elseBlock.getStatements().add(0, elseLogStatement(expression));
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

    public Expression ifLogStatement (BinaryExpr e) {
        if (e.getOperator() == BinaryExpr.Operator.AND || e.getOperator() == BinaryExpr.Operator.OR) {
            e.setLeft(ifLogStatement(e.getLeft().asBinaryExpr()));
            e.setRight(ifLogStatement(e.getRight().asBinaryExpr()));
            return e;
        } else {
            String insert = "log(" + idCounter + "," + e.getLeft().toString()
                    + "," + e.getRight().toString() + ",Operator." + e.getOperator().name() + ")";
            Expression expressionIfStatement = StaticJavaParser.parseExpression(insert);
            idCounter++;
            return expressionIfStatement;
        }
    }

    public Statement elseLogStatement (BinaryExpr e) {
            BinaryExpr elseExpression = invertExpression(e);
            String insertElse = "log(" + idCounter + "," + elseExpression.getLeft().toString()
                    + "," + elseExpression.getRight().toString() + ",Operator." + elseExpression.getOperator().name() + ");";
            Statement statementElse = StaticJavaParser.parseStatement(insertElse);
            idCounter++;
            return statementElse;
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
            case AND:
            case OR:
                return deMorgan(e);
        }
        return new BinaryExpr(e.getLeft(), e.getRight(), operator);
    }

    public BinaryExpr deMorgan(BinaryExpr expression) {
        switch (expression.getOperator()){
            case AND: expression.setOperator(BinaryExpr.Operator.OR); break;
            case OR: expression.setOperator(BinaryExpr.Operator.AND); break;
        }

        if (expression.getLeft().isBinaryExpr()) {
            expression.setLeft(deMorgan(expression.getLeft().asBinaryExpr()));
        } else {
            expression.setLeft(new UnaryExpr(expression.getLeft(), UnaryExpr.Operator.LOGICAL_COMPLEMENT));
        }

        if (expression.getRight().isBinaryExpr()) {
            expression.setRight(deMorgan(expression.getRight().asBinaryExpr()));
        } else {
            expression.setRight(new UnaryExpr(expression.getRight(), UnaryExpr.Operator.LOGICAL_COMPLEMENT));
        }

        return expression;
    }
}
