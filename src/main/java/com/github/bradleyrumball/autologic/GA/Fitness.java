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
  private final int left;
  /**
   * Right argument to a condition
   */
  private final int right;
  /**
   * The operator of a conditional expression
   */
  private final Operator operator;
  /**
   * A constant to give fitness a nudge in the right direction
   */
  private final int K;

  /**
   * Main constructor allows all inputs plus a custom K value (not used by this application)
   * @param left left side of condition
   * @param right right side of condition
   * @param operator operator of condition
   * @param K A small positive value
   */
  public Fitness (int left, int right, Operator operator, int K) {
    this.left = left;
    this.right = right;
    this.operator = operator;
    this.K = K;
  }

  /**
   * Primary constructor, sets k as 1.
   * @param left left side of condition
   * @param right right side of condition
   * @param operator operator of condition
   */
  public Fitness (int left, int right , Operator operator) {
    this(left, right, operator, 1);
  }

  /**
   * Condition type a == b
   * @return fitness score 0 if condition met else |a-b|+K
   */
  private int equal() {
    int score = Math.abs(left - right);
    if (score == 0) return 0;
    else return score+K;
  }

  /**
   * Condition type a != b
   * @return fitness score 0 if condition met else K
   */
  private int notEqual() {
    int score = Math.abs(left - right);
    if (score != 0) return 0;
    else return K;
  }

  /**
   * Condition type a < b
   * @return fitness score 0 if condition met else a-b+k
   */
  private int lessThan() {
    int score = left - right;
    if (score < 0) return 0;
    else return score + K;
  }

  /**
   * Condition type a <= b
   * @return fitness score 0 if condition met else a-b+k
   */
  private int lessThanEqualTo() {
    int score = left - right;
    if (score <= 0) return 0;
    else return score + K;
  }

  /**
   * Condition type a > b
   * @return fitness score 0 if condition met else b-a+k
   */
  private int greaterThan() {
    int score = right - left;
    if (score < 0) return 0;
    else return score + K;
  }

  /**
   * Condition type a >= b
   * @return fitness score 0 if condition met else b-a+k
   */
  private int greaterThanEqualTo() {
    int score = right - left;
    if (score <= 0) return 0;
    else return score + K;
  }

  /**
   * Public method to get the fitness of an instance
   * @return int - the fitness of the condition provided to the instance
   * @throws Exception Annoying exception because I used a switch statement
   */
  public int getFitness() throws Exception {
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
