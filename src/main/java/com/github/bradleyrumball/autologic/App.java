package com.github.bradleyrumball.autologic;

import com.github.bradleyrumball.autologic.visitors.IfElseInjectionVisitor;
import com.github.bradleyrumball.autologic.visitors.ParameterVisitor;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    ParserConfiguration parserConfiguration = new ParserConfiguration()
            .setAttributeComments(false);
    StaticJavaParser.setConfiguration(parserConfiguration);
    return StaticJavaParser.parse(f);
  }


  public static void main(String[] args) throws IOException {
    CompilationUnit classUnderTest = getCU("src/main/resources/classundertest/BMICalculator.java");

    // inject CU with log statements on ifs
    IfElseInjectionVisitor ifElseInjectionVisitor = new IfElseInjectionVisitor();
    classUnderTest.accept(ifElseInjectionVisitor, null);
    System.out.println(classUnderTest);

    // to be used as final static variable in Host
    int numberOfBranches = ifElseInjectionVisitor.getIdCounter();

    ParameterVisitor parameterVisitor = new ParameterVisitor(classUnderTest);
    // to be used as final static variable in Host
    int numberOfParameters = parameterVisitor.getNumberOfParameters();

    // Compilation unit of host
    CompilationUnit host = getCU("src/main/java/com/github/bradleyrumball/autologic/GA/Host.java");

    /*
    TODO: - inject classUnderTest into host
          - when injected method to be tested should be called "instrumentedMethod"
          - other methods will also need to be injected that the methodUnderTest relies on (e.g. Triangle needs Type)
          - "numberOfBranches" also needs to be injected into host as "private static final int NUMBER_OF_BRANCHES = _;"
          - "numerOfParameters" also needs to be injected into host as "public static final int NUMBER_OF_GENES = _;"
          - once host is injected it will need to be saved in the same class file "Host.java"
          - 🤞 it should run!!!
     */


  }
}
