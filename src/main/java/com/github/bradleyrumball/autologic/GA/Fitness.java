package com.github.bradleyrumball.autologic.GA;

import com.github.javaparser.ast.expr.BinaryExpr.Operator;

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

  private final Class superClass;

  /**
   * Main constructor allows all inputs plus a custom K value (not used by this application)
   * @param left left side of condition
   * @param right right side of condition
   * @param operator operator of condition
   * @param K A small positive value
   */
  public Fitness (Object left, Object right, Operator operator, int K) {
    this.operator = operator;
    this.K = K;
    this.left = left;
    this.right = right;
    this.superClass = left.getClass().getSuperclass()!=Object.class ? left.getClass().getSuperclass() : (left.getClass()==String.class ? CharSequence.class : left.getClass());
  }

  public Fitness (Number left, Number right , Operator operator) {
    this(((Number) left).doubleValue(), ((Number) right).doubleValue(), operator, 1);
  }

  public Fitness (CharSequence left, CharSequence right , Operator operator) {
    this((CharSequence)left, (CharSequence)right, operator, 1);
  }

  public Fitness (char left, char right , Operator operator) {
    this(String.valueOf(left), String.valueOf(right), operator);
  }

  public Fitness (Boolean left, Boolean right , Operator operator) {
    this(left, right, operator, 1);
  }

  /**
   * Condition type a == b
   * @return fitness score 0 if condition met else |a-b|+K
   */
  private double equal() {
    double score = Integer.MAX_VALUE-K;
    if (superClass == Number.class) {
      score = Math.abs((double)left - (double)right);
    }
    if (superClass == CharSequence.class) {
      score = stringEqualsDistance((String) left, (String)right);
    }
    if (superClass == Boolean.class) {
      score = left == right ? 0 : 1;
    }
    return (score == 0) ? 0 : score + K;
  }

  /**
   * Condition type a != b
   * @return fitness score 0 if condition met else K
   */
  private double notEqual() {
    double score = Integer.MAX_VALUE-K;
    if (superClass == Number.class) {
      score = Math.abs((double)left - (double)right);
    }
    if (superClass == CharSequence.class) {
      score = stringEqualsDistance((String) left, (String)right);
    }
    if (superClass == Boolean.class) {
      score = left == right ? 0 : 1;
    }
    return (score != 0) ? 0 : K;
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

  private double stringEqualsDistance(String str, String target) {
    double distance = 0;
    for (int i = 0; i < Math.min(str.length(), target.length()); i++) {
      distance += charEqualsDistance(str.charAt(i), target.charAt(i));
    }
    int diffPenalty = Math.abs(str.length() - target.length());
    return distance + diffPenalty;
  }

  private double charEqualsDistance(char chr, char target) {
    return 1.0 - 1.0 / (1.0 + Math.abs(chr - target));
  }


}
