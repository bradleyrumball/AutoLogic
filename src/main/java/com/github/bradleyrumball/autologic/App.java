package com.github.bradleyrumball.autologic;

import com.github.bradleyrumball.autologic.GA.Host;
import com.github.bradleyrumball.autologic.visitors.IfElseInjectionVisitor;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import net.openhft.compiler.CompilerUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

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


  public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    CompilationUnit instrumentedClass = getCU("src/main/resources/classundertest/Triangle.java");

    // inject CU with log statements on ifs
    IfElseInjectionVisitor ifElseInjectionVisitor = new IfElseInjectionVisitor();
    instrumentedClass.accept(ifElseInjectionVisitor, null);

    // to be used as final static variable in Host
    int numberOfBranches = ifElseInjectionVisitor.getIdCounter();

    String classPath = ifElseInjectionVisitor.getClassPath();
    Class<?> clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(classPath, instrumentedClass.toString());
    new Host(Arrays.stream(clazz.getMethods()).filter(m -> m.getDeclaringClass() == clazz).collect(Collectors.toList()), numberOfBranches, classPath).run();
  }
}
