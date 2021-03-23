package org.sheffield;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr.Operator;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Decomposer {

  private CompilationUnit cu;
  private ArrayList<LinkedList<Expression>> branches = new ArrayList<>();
  private NodeList methodParams;

  public Decomposer(CompilationUnit cu) {
    this.cu = cu;
  }

  public NodeList getParams() {
    cu.findAll(MethodDeclaration.class).forEach(m -> {
      methodParams = m.getParameters();
    });
    return methodParams;
  }

  public void getStatement() {
    cu.findAll(Node.class).forEach(m -> {
      if(m.getClass() == VariableDeclarator.class || m.getClass() == AssignExpr.class) {
//        System.out.println(m);
      }
    });

    cu.findAll(IfStmt.class).forEach(i -> {
      System.out.println(i.getCondition());
      i.findAll(Node.class).forEach(n -> {
        if(n.getClass() == VariableDeclarator.class || n.getClass() == AssignExpr.class) {
          System.out.println("--"+n);
        }
      });
    });
  }

  public ArrayList<LinkedList<Expression>> getIfPredicates() {
    LinkedList<Expression> expressionList = new LinkedList<>();

    cu.accept(new VoidVisitorAdapter<Void>() {
      public Expression invertExpression(Expression e) {
        return new UnaryExpr(new EnclosedExpr(e), Operator.LOGICAL_COMPLEMENT);
      }

      @Override
      public void visit(IfStmt n, Void arg) {
        // Base Case
        expressionList.addLast(new EnclosedExpr(n.getCondition()));
        branches.add(expressionList.stream().collect(Collectors.toCollection(LinkedList::new)));

        n.getThenStmt().accept(this, arg);
        expressionList.removeLast();
        expressionList.addLast(invertExpression(n.getCondition()));
        n.getElseStmt().ifPresent(l -> l.accept(this, arg));
        expressionList.removeLast();

      }
    }, null);
    return branches;
  }

}
