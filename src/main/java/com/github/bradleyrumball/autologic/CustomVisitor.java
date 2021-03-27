package com.github.bradleyrumball.autologic;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;

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
                UnaryExpr elseExpression = invertExpression(expression).asUnaryExpr();
                String insertElse = "elseLog(" + idCounter + "," + elseExpression.getExpression().toString()
                        + ",Operator." + elseExpression.getOperator().name() + ");";
                Statement statementElse = StaticJavaParser.parseStatement(insertElse);
                idCounter++;
                elseBlock.getStatements().add(0, statementElse);
            } else {
                stmt.accept(this, null);
            }
        });
        // Recursion
        super.visit(n, arg);
        //System.out.println(n);
        return n;
    }

    /***
     *  Helper function to invert operators - mainly used for else statements
     *
     * @param e Operator
     * @return Inverted operator
     */
    public Expression invertExpression(Expression e) {
        return new UnaryExpr(new EnclosedExpr(e), UnaryExpr.Operator.LOGICAL_COMPLEMENT);
    }
}
