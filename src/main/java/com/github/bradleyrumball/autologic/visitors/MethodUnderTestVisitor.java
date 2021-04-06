package com.github.bradleyrumball.autologic.visitors;

import com.github.bradleyrumball.autologic.logging.MethodLogger;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MethodUnderTestVisitor extends ModifierVisitor<MethodDeclaration> {

    /**
     * A counter to keep track of the id of the log statement injected
     */
    private Map<String, Integer> idCounter = new HashMap<>();
    /**
     * Used to collect conditions from if statements that will be
     * inverted to find the else branch execution condition
     */
    private final ArrayList<BinaryExpr> elseBuilder = new ArrayList<>();

    private String classPath = "";

    /**
     * Used to get the number of logging statements issued
     *
     * @return count of log IDs
     */
    public Map<String, Integer> getIdCounters() {
        return idCounter;
    }

    @Override
    public Visitable visit(MethodDeclaration n, MethodDeclaration arg) {
        n.addParameter(MethodLogger.class, "methodLogger");
        return super.visit(n, n);
    }

    @Override
    public Visitable visit(CompilationUnit n, MethodDeclaration arg) {
        n.addImport(BinaryExpr.class.getCanonicalName());
        n.getPackageDeclaration().ifPresent(pkg -> classPath = pkg.getNameAsString()+".");
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(ClassOrInterfaceDeclaration n, MethodDeclaration arg) {
        classPath +=n.getNameAsString();
        return super.visit(n, arg);
    }

    public String getClassPath() {
        return classPath;
    }

    /***
     * Custom visitor function that recursively calls itself
     *
     * @param n A visitor
     * @param arg args due to inheritance
     * @return A visitor
     */
    @Override
    public IfStmt visit(IfStmt n, MethodDeclaration arg) {
        //Check if the current IF statement that we are visiting as already been injected with log statements
        //System.out.println(n);
        BinaryExpr conditionExpression = n.getCondition().asBinaryExpr().clone();
        n.setCondition(getLogStatement(arg, n.getCondition().asBinaryExpr(), false));
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

                elseBlock.getStatements().add(0, getLogStatement(arg));
                // If inline
            } else if (!stmt.isIfStmt()) {
                BlockStmt elseBlock = new BlockStmt();
                n.setElseStmt(elseBlock);
                elseBlock.addStatement(stmt);
                elseBlock.getStatements().add(0, getLogStatement(arg));
            }
        });


        if (!n.hasElseBlock() && !n.hasElseBranch()) {
            BlockStmt block = new BlockStmt();
            block.getStatements().add(getLogStatement(arg));
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
//    @Deprecated
//    private Expression buildElse() {
//        StringBuilder combined = new StringBuilder();
//        // Combine all conditions into one singular string and join conditions with &&
//        for (int i = 0; i < elseBuilder.size(); i++) {
//            // Else generator returns the logging statements for each condition with De Morgans applied
//            combined.append("(").append(getLogStatement(elseBuilder.get(i), true).toString()).append(")");
//            if (i != elseBuilder.size() - 1) combined.append(" && ");
//        }
//        // Parse the string to create an Expression object that can be used as a new if condition
//        Expression polishedElse = StaticJavaParser.parseExpression(combined.toString());
//        // Else builder must be cleared to allow later if blocks to build else's without conflict
//        elseBuilder.clear();
//        return polishedElse;
//    }

    /**
     * Used to transform a logical condition into a logging statement
     * Can handle chained conditions such as those combined with && or || as it is recursive
     *
     * @param e      A logical condition
     * @param invert If we are building an else condition we want to perform De Morgan's (invert -> true)
     * @return A logging expression
     */
    private Expression getLogStatement(MethodDeclaration method, BinaryExpr e, Boolean invert) {
        // Recursive step - whilst operator is an AND or OR we recall the function
        if (e.getOperator() == BinaryExpr.Operator.AND || e.getOperator() == BinaryExpr.Operator.OR) {
            e.setLeft(getLogStatement(method, e.getLeft().asBinaryExpr(), invert));
            e.setRight(getLogStatement(method, e.getRight().asBinaryExpr(), invert));
            return invert ? invertExpression(e) : e;
            //Base case - one there are no more ANDs or ORs we have pure logical conditions (i.e <,>,!=,==, and so on)
        } else {
            idCounter.compute(method.getNameAsString(), (k, v) -> {
                if (v == null) return 1;
                else return v+1;
            });
            BinaryExpr condition = invert ? invertExpression(e) : e;
            String insert = "methodLogger.log(" + (idCounter.get(method.getNameAsString())-1) + "," + condition.getLeft().toString()
                    + "," + condition.getRight().toString() + ",BinaryExpr.Operator." + condition.getOperator().name() + ")";
            Expression expressionCondition = StaticJavaParser.parseExpression(insert);
            return expressionCondition;
        }
    }

    private Statement getLogStatement(MethodDeclaration method) {
        idCounter.compute(method.getNameAsString(), (k, v) -> {
            if (v == null) return 1;
            else return v+1;
        });
        String insert = "methodLogger.log(" + (idCounter.get(method.getNameAsString())-1) + ");";
        Statement statementLog = StaticJavaParser.parseStatement(insert);
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
