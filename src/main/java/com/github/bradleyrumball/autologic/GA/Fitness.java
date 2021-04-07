package com.github.bradleyrumball.autologic.GA;

import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import org.apache.commons.text.similarity.JaroWinklerDistance;

/**
 * Class to calculate the fitness of a logical condition
 * Fitness guides to how far/close a condition is to being satisfied
 */
public class Fitness {

  /**
   * Left argument to a condition
   */
  private final Object left;
  /**
   * Right argument to a condition
   */
  private final Object right;
  /**
   * The operator of a conditional expression
   */
  private final Operator operator;
  /**
   * A constant to give fitness a nudge in the right direction
   */
  private final int K;

  private final Class objectType;

  /**
   * Main constructor allows all inputs plus a custom K value (not used by this application)
   * @param left left side of condition
   * @param right right side of condition
   * @param operator operator of condition
   * @param K A small positive value
   */
  public Fitness (Object left, Object right, Operator operator, int K) {
    this.left = left;
    this.right = right;
    this.operator = operator;
    this.K = K;
    this.objectType = left.getClass();
  }

  /**
   * Primary constructor, sets k as 1.
   * @param left left side of condition
   * @param right right side of condition
   * @param operator operator of condition
   */
  public Fitness (Object left, Object right , Operator operator) {
    this(left, right, operator, 1);
  }

  /**
   * Condition type a == b
   * @return fitness score 0 if condition met else |a-b|+K
   */
  private double equal() {
    if (objectType.getSuperclass() == Number.class) {
      double score = Math.abs((double)left - (double)right);
      return (score == 0) ? 0 : score + K;
    }
    if (objectType.getSuperclass() == CharSequence.class) {
      JaroWinklerDistance jw = new JaroWinklerDistance();
      return jw.apply((CharSequence)left, (CharSequence)right)*Integer.MAX_VALUE;
    }
    return Integer.MAX_VALUE;
  }

  /**
   * Condition type a != b
   * @return fitness score 0 if condition met else K
   */
  private double notEqual() {
    if (objectType.getSuperclass() == Number.class) {
      double score = Math.abs((double)left - (double)right);
      return (score != 0) ? 0 : K;
    }
    if (objectType.getSuperclass() == CharSequence.class) {
      JaroWinklerDistance jw = new JaroWinklerDistance();
      double dist = jw.apply((CharSequence)left, (CharSequence)right)*Integer.MAX_VALUE;
      return (dist == 0) ? 0 : K;
    }
    return Integer.MAX_VALUE;

  }

  /**
   * Condition type a < b
   * @return fitness score 0 if condition met else a-b+k
   */
  private double lessThan() {
    double score = (double)left - (double)right;
    return (score < 0) ? 0 : (score + K);
  }

  /**
   * Condition type a <= b
   * @return fitness score 0 if condition met else a-b+k
   */
  private double lessThanEqualTo() {
    double score = (double)left - (double)right;
    return (score <= 0) ? 0 : (score + K);
  }

  /**
   * Condition type a > b
   * @return fitness score 0 if condition met else b-a+k
   */
  private double greaterThan() {
    double score = (double)right - (double)left;
    return (score < 0) ? 0 : (score + K);
  }

  /**
   * Condition type a >= b
   * @return fitness score 0 if condition met else b-a+k
   */
  private double greaterThanEqualTo() {
    double score = (double)right - (double)left;
    return (score <= 0) ? 0 : (score + K);
  }

  /**
   * Public method to get the fitness of an instance
   * @return int - the fitness of the condition provided to the instance
   * @throws Exception Annoying exception because I used a switch statement
   */
  public double getFitness() throws Exception {
    switch(operator) {
      case EQUALS: return equal();
      case NOT_EQUALS: return notEqual();
      case LESS: return lessThan();
      case LESS_EQUALS: return lessThanEqualTo();
      case GREATER: return greaterThan();
      case GREATER_EQUALS: return greaterThanEqualTo();
    }
    throw new Exception("Fitness invalid");
  }

}
