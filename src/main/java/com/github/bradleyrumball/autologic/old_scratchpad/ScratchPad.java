//package com.github.bradleyrumball.autologic.old_scratchpad;
//
//import com.github.bradleyrumball.autologic.GA.Fitness;
//import com.github.bradleyrumball.autologic.GA.Population;
//import com.github.bradleyrumball.autologic.Triangle.Type;
//import com.github.javaparser.ast.expr.BinaryExpr.Operator;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// *
// * THIS CLASS IS A SCRATCH PAD ONLY
// * GA class is based in com.github.bradleyrumball.autologic.GA.Host
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// */
//@Deprecated
//public class ScratchPad {
//
//  static final int ITERATIONS = 10000;
//
//   static final int MIN_INT = Integer.MIN_VALUE;
//   static final int MAX_INT = Integer.MAX_VALUE;
////  static final int MIN_INT = -10;
////  static final int MAX_INT = 10;
//
//
//  private static HashMap<Integer, Integer> coveredBranches = new HashMap<>();
//  private static ArrayList<ArrayList> testVals = new ArrayList();
//
//  @Deprecated
//  public static void main(String[] args) throws Exception {
////    Three lines below are to test out population generation
//    Population p = new Population(10);
////    Individual i = new Individual();
//    System.out.println(p.toString());
////    randomlyTestClassify();
//  }
//
//  @Deprecated
//  static void randomlyTestClassify() throws Exception {
//    Random r = new Random();
//
//
//    for (int i=0; i < ITERATIONS; i ++) {
//      int side1 = randomInt(r);
//      int side2 = randomInt(r);
//      int side3 = randomInt(r);
//      System.out.println((i+1) + ": [" + side1 + ", " + side2 + ", " + side3 + "]");
//      Type result = instrumentedClassify(side1, side2, side3);
//      System.out.println("-> " + result);
//
//    }
//
//    AtomicInteger cover= new AtomicInteger();
//    coveredBranches.forEach((k,v) -> {
//      if(v==0) cover.getAndIncrement();
//    });
//    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//    System.out.println("Branch Coverage: " + cover + "/14");
//    System.out.println("Branch with Best Fitness: " + coveredBranches);
//    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//  }
//
//  @Deprecated
//  static int randomInt(Random r) {
//    if (MIN_INT == Integer.MIN_VALUE && MAX_INT == Integer.MAX_VALUE) {
//      return r.nextInt();
//    } else {
//      return r.nextInt((MAX_INT - MIN_INT + 1)) + MIN_INT;
//    }
//  }
//
//  @Deprecated
//  static void coveredBranch(int id) {
//    if (!coveredBranches.containsKey(id)) {
//      System.out.println("* covered new branch: " + id);
////      coveredBranches.add(id);
//    }
//  }
//
//  @Deprecated
//  static boolean log(int id, int left, int right, Operator op) throws Exception {
//      Fitness f = new Fitness(left, right, op);
//      long fScore = f.getFitness();
//      System.out.println("id: "+id+", left: "+left+", right: "+right+", OP: "+op+", Fit:"+fScore);
//
//      // Hit on each branch, not only when branch is covered
//      if (!coveredBranches.containsKey(id)) coveredBranches.put(id, fScore);
//      else if(coveredBranches.get(id) > fScore) coveredBranches.replace(id, fScore);
//
////      if (fScore == 0) //        testVals.add();
//      // to continue with execution
//      return (fScore == 0) ? true : false;
//  }
//
//  @Deprecated
//  static Type instrumentedClassify (int side1, int side2, int side3) throws Exception {
//    Type type;
//
//
//    if (log(1, side1, side2, Operator.GREATER)) {
////      coveredBranch(1, coveredBranches);
//      int temp = side1;
//      side1 = side2;
//      side2 = temp;
//    } else {
//      log(2, side1, side2, Operator.LESS_EQUALS);
////      coveredBranch(2, coveredBranches);
//    }
//
//    if (log(3,side1, side3, Operator.GREATER)) {
////      coveredBranch(3, coveredBranches);
//      int temp = side1;
//      side1 = side3;
//      side3 = temp;
//    } else {
//      log(4,side1, side3, Operator.LESS_EQUALS);
////      coveredBranch(4, coveredBranches);
//    }
//
//    if (log(5, side2, side3, Operator.GREATER)) {
////      coveredBranch(5, coveredBranches);
//      int temp = side2;
//      side2 = side3;
//      side3 = temp;
//    } else {
//      log(6, side2, side3, Operator.LESS_EQUALS);
////      coveredBranch(6, coveredBranches);
//    }
//
//    if (log(7, side1 + side2, side3, Operator.LESS_EQUALS)) {
////      coveredBranch(7, coveredBranches);
//      type = Type.INVALID;
//    } else {
//      log(8, side1 + side2, side3, Operator.GREATER);
////      coveredBranch(8, coveredBranches);
//      type = Type.SCALENE;
//      if (log(9, side1, side2, Operator.EQUALS)) {
////        coveredBranch(9, coveredBranches);
//        if (log(13, side2, side3, Operator.EQUALS)) {
////          coveredBranch(10, coveredBranches);
//          type = Type.EQUILATERAL;
//        } else {
//          log(14, side2, side3, Operator.NOT_EQUALS);
////          coveredBranch(11, coveredBranches);
//        }
//      } else {
//        log(10, side1, side2, Operator.NOT_EQUALS);
////        coveredBranch(12, coveredBranches);
//        if (log(11, side2, side3, Operator.EQUALS)) {
////          coveredBranch(13, coveredBranches);
//          type = Type.ISOSCELES;
//        } else {
//          log(12, side2, side3, Operator.NOT_EQUALS);
////          coveredBranch(14, coveredBranches);
//        }
//      }
//    }
//    return type;
//  }
//}
