package com.github.bradleyrumball.autologic;

import com.github.bradleyrumball.autologic.GA.Host;
import com.github.bradleyrumball.autologic.visitors.MethodUnderTestVisitor;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import net.openhft.compiler.CompilerUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MAIN APPLICATION ENTRY POINT
 */
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


  /**
   * Entry point
   * @param args
   * @throws IOException bad things
   * @throws ClassNotFoundException bad things
   */
  public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
    // Command line args for file paths
    String inputPath = args[0];
    String outputPath = args[1];

    System.out.println("Welcome to AutoLogic :D!");
    System.out.println("You are currently testing " + inputPath + " file");
    System.out.println("With output to " + outputPath);

    // get the compilation unit of the class under test
    CompilationUnit instrumentedClass = getCU(Paths.get(inputPath).toString());

    // inject CU with log statements on ifs
    MethodUnderTestVisitor methodUnderTestVisitor = new MethodUnderTestVisitor();
    instrumentedClass.accept(methodUnderTestVisitor, null);
    System.out.println(instrumentedClass);

    // to be used as final static variable in Host
    Map<String, Integer> numberOfBranches = methodUnderTestVisitor.getIdCounters();

    String classPath = methodUnderTestVisitor.getClassPath();
    Class<?> clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(classPath, instrumentedClass.toString());

    long start = System.currentTimeMillis();
    new Host(Arrays.stream(clazz.getMethods()).filter(m -> m.getDeclaringClass() == clazz).collect(Collectors.toList()), numberOfBranches, classPath, outputPath).run();
    System.out.println("\n Woah, that only took " + (System.currentTimeMillis() - start) + "ms");
  }
}
