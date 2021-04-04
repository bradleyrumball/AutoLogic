package com.github.bradleyrumball.autologic.test_case_generation;

import com.github.bradleyrumball.autologic.GA.Individual;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JUnitOutputManager {

    //input schema = List<Element>
    //Element schema =

    private ArrayList<Individual> conditions;

    /**
     * Constructor for Junit Manager
     * @param conditions array list of individuals
     */
    public JUnitOutputManager(ArrayList<Individual> conditions) {
        this.conditions = conditions;
    }

    /***
     * Generates the test file
     *
     * @param input Takes an output element to build the tests
     * @param outputPath Where the file is going to be placed
     * @throws IOException
     */
    public static void generate(List<OutputElement> input, String outputPath) throws IOException {
        Set<String> imports = new HashSet<>();
        baseImports(imports);

        StringBuffer header = new StringBuffer();
        StringBuffer body = new StringBuffer();
        StringBuffer footer = new StringBuffer();

        // For each test case we build the test classes
        int testNum = 1;
        for (OutputElement output : input) {
            body.append("\t@Test\n\tpublic void test" + testNum + "() {\n");
            body.append(output.createTestString());
            body.append("\t}\n\n");
            for(String i : output.getImports()) {
                imports.add(i);
            }
            testNum ++;
        }

        // Add the imports needed
        for (String i : imports) {
            header.append("import " + i + ";\n");
        }

        // Add the paths and make it compilable
        Path path = Paths.get(outputPath);
        header.append("\npublic class " + path.toFile().getName().replace(".java", "") + " {\n");
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

    // Shows how files can be built. Will need to use o.add for multiple test cases.
    public void unitGenerator() {
        List<OutputElement> o = new ArrayList<>();
        for (Individual condition : conditions) {
            ArrayList<String> inputs = new ArrayList<>();// = {"5", "5", "5"};
            for (int gene : condition.getGenes()) {
                System.out.println(gene);
                inputs.add(String.valueOf(gene));
            }
            String[] imports = {"com.github.bradleyrumball.autologic.Triangle"};
            o.add(new OutputElement("Triangle.classify",inputs, String.valueOf(condition.getMethodReturnValue()), Arrays.asList(imports)));
        }


        try {
            generate(o, "src"+ File.separator + "test"+ File.separator + "java"+ File.separator + "com"+ File.separator + "github"+ File.separator + "bradleyrumball"+ File.separator + "autologic"+ File.separator + "test.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
