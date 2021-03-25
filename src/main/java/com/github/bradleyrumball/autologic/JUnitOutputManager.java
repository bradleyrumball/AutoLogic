package com.github.bradleyrumball.autologic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JUnitOutputManager {

    //input schema = List<Element>
    //Element schema =

    public static void generate(List<OutputElement> input, String outputPath) throws IOException {
        Set<String> imports = new HashSet<>();
        baseImports(imports);

        StringBuffer header = new StringBuffer();
        StringBuffer body = new StringBuffer();
        StringBuffer footer = new StringBuffer();

        int testNum = 1;
        for (OutputElement output : input) {
            body.append("\t@Test\n\tpublic void test" + testNum + "() {\n");
            body.append("\t\tassertTrue(" + output.method + "(" + String.join(",", output.input) + ") == " + output.expected + ");\n");
            body.append("\t}\n\n");
            for(String i : output.imports) {
                imports.add(i);
            }
            testNum ++;
        }

        for (String i : imports) {
            header.append("import " + i + ";\n");
        }

        Path path = Paths.get(outputPath);
        header.append("\npublic class " + path.toFile().getName().replace(".java", "") + " {\n");
        footer.append("}");

        Files.write(path, (header.toString() + body.toString() + footer.toString()).getBytes());
    }

    private static void baseImports(Set<String> imports) {
        imports.add("org.junit.Test");
        imports.add("static org.junit.Assert.*");
    }

    public static void main(String[] args) throws IOException {
        List<OutputElement> o = new ArrayList<>();
        String[] s = {"5", "5", "5"};
        String[] i = {"com.github.bradleyrumball.autologic.Triangle"};
        o.add(new OutputElement("Triangle.classify", Arrays.asList(s), "Triangle.Type.EQUILATERAL", Arrays.asList(i)));
        generate(o, "src\\main\\resources\\testOutput\\test.java");
    }
}
