package org.sheffield;


import com.github.javaparser.ast.NodeList;
import java.util.Set;
import java.util.TreeSet;

public class InfectionTemplate {

  //Integer will be an index, Object will be a param value
  private HashMap<Integer, <Object>> covered = new TreeSet<>();
//  private NodeList params;


  private static void coveredBranch(int id, Set<Integer> coveredBranches) {
    if (!coveredBranches.contains(id)) {
      System.out.println("* covered new branch: " + id);
      coveredBranches.add(id);
    }
  }


  public static void main(String[] args) {
//    outputCoveredBranches();
  }

}
