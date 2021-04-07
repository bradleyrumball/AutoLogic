package com.github.bradleyrumball.autologic.visitors;

import com.github.bradleyrumball.autologic.logging.MethodLogger;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
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

        BinaryExpr conditionExpression = n.getCondition().asBinaryExpr().clone();
        n.setCondition(getLogStatement(arg, n.getCondition().asBinaryExpr()));

        // Recursion
        super.visit(n, arg);


        // Return the injected cu
        return n;
    }

    /**
     * Used to transform a logical condition into a logging statement
     * Can handle chained conditions such as those combined with && or || as it is recursive
     *
     * @param e      A logical condition
     * @return A logging expression
     */
    private Expression getLogStatement(MethodDeclaration method, BinaryExpr e) {
        // Recursive step - whilst operator is an AND or OR we recall the function
        if (e.getOperator() == BinaryExpr.Operator.AND || e.getOperator() == BinaryExpr.Operator.OR) {
            e.setLeft(getLogStatement(method, e.getLeft().asBinaryExpr()));
            e.setRight(getLogStatement(method, e.getRight().asBinaryExpr()));
            return e;
            //Base case - one there are no more ANDs or ORs we have pure logical conditions (i.e <,>,!=,==, and so on)
        } else {
            idCounter.compute(method.getNameAsString(), (k, v) -> {
                if (v == null) return 1;
                else return v+2;
            });
            String insert = "methodLogger.log(" + (idCounter.get(method.getNameAsString())-1) + "," + e.getLeft().toString()
                    + "," + e.getRight().toString() + ",BinaryExpr.Operator." + e.getOperator().name() + ")";
            Expression expressionCondition = StaticJavaParser.parseExpression(insert);
            return expressionCondition;
        }
    }

}
