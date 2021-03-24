package org.sheffield;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class App {

  /**
   * Takes valid path of class for which we wish to produce a test suite
   *
   * @param path Valid path to class
   * @return Compilation unit
   * @throws FileNotFoundException Thrown when the JP can't parse
   */
  public static CompilationUnit getCU(String path) throws FileNotFoundException {
    File f = new File(path);
    return StaticJavaParser.parse(f);
  }

  /**
   * Could be used to generate code coverage stats -based on using the coveredBranch method in
   * RandomlyTestTriangle code from phil
   *
   * @param cu cu to be injected
   * @return an updated Complication Unit containing the injected method statements
   */
  @Deprecated
  public static CompilationUnit injectMethodAllBranches(CompilationUnit cu) {
    String methodName = "coveredBranch";
    String param = "coveredBranches";
    AtomicReference<Integer> branchId = new AtomicReference<>(0);

    // For each line in the class (cu) provided
    cu.findAll(Statement.class).forEach(statement -> {
      // If the current statement is an IF statement
      if (statement.isIfStmt()) {
        // increase the branchId by 1
        branchId.getAndSet(branchId.get() + 1);
        // declare the expression to inject (method with updated branchId
        Expression methodToInject = StaticJavaParser
            .parseExpression(methodName + "(" + branchId + ", " + param + ")");
        // inject method into the true branch of the if statement
        ((BlockStmt) statement.getChildNodes().get(1)).addStatement(methodToInject);

        // same as above but for false (else) branch on if statement
        branchId.getAndSet(branchId.get() + 1);
        methodToInject = StaticJavaParser
            .parseExpression(methodName + "(" + branchId + ", " + param + ")");
        try {
          ((BlockStmt) statement.getChildNodes().get(2)).addStatement(methodToInject);
        } catch (Exception e) {
          // occurs when there is no else on an if
          // decrement the branchId as it was not used
          branchId.getAndSet(branchId.get() - 1);
        }

      }
    });
    return cu;
  }

  @Deprecated
  public static ArrayList<Expression> getBranchConditions(CompilationUnit cu) {
    ArrayList<Expression> classConditions = new ArrayList<>();
    cu.findAll(IfStmt.class).forEach(expression -> classConditions.add(expression.getCondition()));
    return classConditions;
  }

  public static void main(String[] args) throws FileNotFoundException {
    CompilationUnit cu = getCU("src/main/resources/classundertest/Triangle.java");
    Decomposer d = new Decomposer(cu);
//    System.out.println(d.getMethod().get(0).getParameters());
//    System.out.println(d.getParams().get(0));
//    InfectionTemplate template = new InfectionTemplate(d.getParams());


    Expression e = d.getIfPredicates().get(0).get(0);
    BinaryExpr ue = e.asBinaryExpr();
    System.out.println(ue.getOperator());


  }
}
