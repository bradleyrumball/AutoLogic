package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.ArrayList;

public class IfElseInjectionVisitor extends ModifierVisitor<Void> {

    private int idCounter = 0;
    private ArrayList<BinaryExpr> elseBuilder = new ArrayList<>();

    /***
     * Injects CU with log statements.
     *
     * @param n A visitor
     * @param arg args due to inheritance
     * @return A visitor
     */
    @Override
    public IfStmt visit(IfStmt n, Void arg) {
        Expression conditionExpression = n.getCondition();
        if (conditionExpression.isBinaryExpr()) {
            n.setCondition(ifLogStatement(conditionExpression.asBinaryExpr()));
            elseBuilder.add(conditionExpression.asBinaryExpr().clone());


            // For else statements ...
            n.getElseStmt().ifPresent(stmt -> {
                if (stmt.isBlockStmt()) {
                    IfStmt elif = new IfStmt().setCondition(buildElse());
                    n.setElseStmt(elif);
                    // If inline
                } else if (!stmt.isIfStmt()) {
                    IfStmt elif = new IfStmt().setCondition(buildElse());
                    n.setElseStmt(elif);
                }
            });


            // Adds else statement after a single if for alternate branch
            if (!n.hasElseBranch() && !n.hasElseBlock()) {
                IfStmt elif = new IfStmt().setCondition(buildElse());
                n.setElseStmt(elif);
            }


        }
        // Recursion
        super.visit(n, arg);
//        System.out.println(n);
        return n;
    }

    protected Expression buildElse() {
//        System.out.println("e:"+elseBuilder);
        String combined = "";
        for (int i = 0; i < elseBuilder.size(); i++) {
            combined += "(" + elseGenerator(elseBuilder.get(i)).toString() + ")";
            if (i != elseBuilder.size() - 1) combined += " && ";
        }
        Expression polishedElse = StaticJavaParser.parseExpression(combined);
//        System.out.println(polishedElse);
        elseBuilder.clear();
        return polishedElse;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public Expression ifLogStatement (BinaryExpr e) {
        if ((e.getOperator() == BinaryExpr.Operator.AND || e.getOperator() == BinaryExpr.Operator.OR) && (e.getLeft().isBinaryExpr() && e.getRight().isBinaryExpr())) {
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

    public Expression elseGenerator (BinaryExpr e) {
        if ((e.getOperator() == BinaryExpr.Operator.AND || e.getOperator() == BinaryExpr.Operator.OR) && (e.getLeft().isBinaryExpr() && e.getRight().isBinaryExpr())) {
            e.setLeft(elseGenerator(e.getLeft().asBinaryExpr()));
            e.setRight(elseGenerator(e.getRight().asBinaryExpr()));
            return invertExpression(e);
        } else {
            BinaryExpr condition = invertExpression(e);
            String insert = "log(" + idCounter + "," + condition.getLeft().toString()
                    + "," + condition.getRight().toString() + ",Operator." + condition.getOperator().name() + ")";
            Expression expressionCondition = StaticJavaParser.parseExpression(insert);
            idCounter++;
            return expressionCondition;
        }
    }

    @Deprecated
    public Statement elseLogStatement (BinaryExpr e) {
            BinaryExpr elseExpression = invertExpression(e);
            String insertElse = "log(" + idCounter + "," + elseExpression.getLeft().toString()
                    + "," + elseExpression.getRight().toString() + ",Operator." + elseExpression.getOperator().name() + ")";
            Statement statementElse = StaticJavaParser.parseStatement(insertElse + ";");
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
            case AND: operator = BinaryExpr.Operator.OR; break;
            case OR: operator = BinaryExpr.Operator.AND; break;
//                return deMorgan(e);
        }
        return new BinaryExpr(e.getLeft(), e.getRight(), operator);
    }

    @Deprecated
    public BinaryExpr deMorgan(BinaryExpr expression) {
        Expression left = expression.getLeft().clone();
        Expression right = expression.getRight().clone();
        BinaryExpr.Operator op = null;
        switch (expression.getOperator()){
            case AND: op = BinaryExpr.Operator.OR; break;
            case OR: op = BinaryExpr.Operator.AND; break;
        }

        if(left.isBinaryExpr()) {
            left = invertExpression(left.asBinaryExpr());
        } else {
            left = new UnaryExpr(left, UnaryExpr.Operator.LOGICAL_COMPLEMENT);
        }

        if(right.isBinaryExpr()) {
            right = invertExpression(right.asBinaryExpr());
        } else {
            right = new UnaryExpr(right, UnaryExpr.Operator.LOGICAL_COMPLEMENT);
        }

        return new BinaryExpr(left, right, op);
    }

        /*
    TODO: - Fix injection with else branches
     */
}
