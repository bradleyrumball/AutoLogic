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
public class AutoLogic {

  //Input path String

  private final String inputPath;

  //Output path String

  private final String outputPath;

  /**
   * Constructor for AutoLogic, takes input and output paths
   *
   * @param inputPath input path String
   * @param outputPath output path String
   */
  public AutoLogic(String inputPath, String outputPath) {
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  /**
   * Generates Junit code
   *
   * @param printInstrumentedClass Booklean for printing the CU
   */
  public void generateJunit(boolean printInstrumentedClass) {
    // get the compilation unit of the class under test
    CompilationUnit instrumentedClass = null;
    try {
      instrumentedClass = getCU(Paths.get(inputPath).toString());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // inject CU with log statements on ifs
    MethodUnderTestVisitor methodUnderTestVisitor = new MethodUnderTestVisitor();
    instrumentedClass.accept(methodUnderTestVisitor, null);

    if (printInstrumentedClass) System.out.println(instrumentedClass);

    // to be used as final static variable in Host
    Map<String, Integer> numberOfBranches = methodUnderTestVisitor.getIdCounters();

    String classPath = methodUnderTestVisitor.getClassPath();


    long start = System.currentTimeMillis();
    Class<?> finalClazz = getClazz(instrumentedClass, classPath);
    new Host(Arrays.stream(finalClazz.getMethods()).filter(m -> m.getDeclaringClass() == finalClazz).collect(Collectors.toList()), numberOfBranches, classPath, outputPath).run();
    System.out.println("\n Woah, that only took " + (System.currentTimeMillis() - start) + "ms");
  }

  /**
   * Gets a compiled version of the class - modifies JVM in real time :o
   *
   * @param cu Compliation unit
   * @param classPath String of class path
   * @return Returns a class
   */
  private Class<?> getClazz(CompilationUnit cu, String classPath) {
    Class<?> clazz = null;
    try {
      clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(classPath, cu.toString());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return clazz;
  }

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
    String in = args[0];
    String out = args[1];

    // Print statements
    System.out.println("Welcome to AutoLogic :D!");
    System.out.println("You are currently testing " + in + " file");
    System.out.println("With output to " + out);

    // Constructs autologic
    AutoLogic autologic = new AutoLogic(in, out);
    autologic.generateJunit(true);

  }
}
