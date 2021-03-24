package org.sheffield;

import com.github.javaparser.ast.expr.BinaryExpr.Operator;

public class Fitness {

  private int left;
  private int right;
  private Operator operator;
  private int K;

  public Fitness (int left, int right, Operator operator, int K) {
    this.left = left;
    this.right = right;
    this.operator = operator;
    this.K = K;
  }

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
