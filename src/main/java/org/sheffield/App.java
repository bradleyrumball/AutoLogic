package org.sheffield;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class App {

  static void coveredBranch(int id, Set<Integer> coveredBranches) {
    if (!coveredBranches.contains(id)) {
      System.out.println("* covered new branch: " + id);
      coveredBranches.add(id);
    }
  }

  /**
   * Takes valid path of class for which we wish to produce a test suite
   * @param path Valid path to class
   * @return  Compliation unit
   * @throws FileNotFoundException
   */
  public static CompilationUnit getCU(String path) throws FileNotFoundException {
    File f = new File(path);
    return StaticJavaParser.parse(f);
  }

  /**
   * Can be
   * @param cu
   * @return
   */
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


  public static void main(String[] args) throws FileNotFoundException {
    CompilationUnit cu = getCU("src/main/resources/classundertest/Triangle.java");
    cu = injectMethodAllBranches(cu);
    System.out.println(cu);

  }
}
