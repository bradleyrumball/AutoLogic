package com.github.bradleyrumball.autologic.test_case_generation;

import com.github.bradleyrumball.autologic.GA.Individual;
import com.github.bradleyrumball.autologic.GA.TypeValue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JUnitOutputManager {

    /**
     * Array list of conditions
     */
    private ArrayList<Individual> conditions;
    /**
     * The class path of the class under test as a String
     */
    private final String classPath;
    /**
     * The String name of the class under test
     */
    private final String className;
    /**
     * The method name of the class under test
     */
    private final String methodName;
    /**
     * The output String for the test suite
     */
    private final String outputPath;

    /***
     * Constructor for the output manager
     *
     * @param conditions Array list of conditions
     * @param classPath Class path string
     * @param className The name of the class being tested
     * @param methodName The name of the method being tested
     * @param outputPath The output path for the test suite
     */
    public JUnitOutputManager(ArrayList<Individual> conditions, String classPath, String className, String methodName, String outputPath) {
        this.conditions = conditions;
        this.classPath = classPath;
        this.className = className;
        this.methodName = methodName;
        this.outputPath = outputPath;
    }

    /***
     * Generates the test file
     *
     * @param input Takes an output element to build the tests
     * @param outputPath Where the file is going to be placed
     * @throws IOException
     */
    public void generate(List<OutputElement> input, String outputPath) throws IOException {
        Set<String> imports = new HashSet<>();
        baseImports(imports);

        // Constructs the tests
        StringBuffer header = new StringBuffer();
        StringBuffer body = new StringBuffer();
        StringBuffer footer = new StringBuffer();

        // For each test case we build the test classes
        int testNum = 1;
        for (OutputElement output : input) {
            body.append("\t@Test\n\tpublic void test" + testNum + "() {\n");
            body.append(output.createTestString());
            body.append("\t}\n\n");
            for (String i : output.getImports()) {
                imports.add(i);
            }
            testNum++;
        }

        // Add the imports needed
        for (String i : imports) {
            header.append("import " + i + ";\n");
        }

        // Add the paths and make it compilable
        Path path = Paths.get(outputPath);
        path.toFile().mkdirs();
        path = path.resolve(className + methodName + "Test.java");
        header.append("\npublic class " + className + methodName + "Test" + " {\n");
        footer.append("}");

        // Write it all out...
        Files.write(path, (header.toString() + body.toString() + footer.toString()).getBytes());
    }

    /**
     * A set of strings that need to be put at the top of the file
     *
     * @param imports A set of strings of imports to be put at the top of the outputted test file
     */
    private static void baseImports(Set<String> imports) {
        imports.add("org.junit.Test");
        imports.add("static org.junit.Assert.*");
    }

    /**
     * Generates each unit test as a list
     */
    public void unitGenerator() {
        List<OutputElement> o = new ArrayList<>();
        for (Individual condition : conditions) {
            ArrayList<String> inputs = new ArrayList<>();
            for (TypeValue gene : condition.getGenes()) {
                String val = String.valueOf(gene.getValue());
                Type tp = gene.getType();
                if (tp == String.class) val = "\"" + val + "\"";
                if (tp == char.class) val = "\'" + val + "\'";
                inputs.add(val);
            }
            String[] imports = {classPath};
            o.add(new OutputElement(methodName, inputs, String.valueOf(condition.getMethodReturnValue()), Arrays.asList(imports)));
        }


        try {
            generate(o, Paths.get(outputPath).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
