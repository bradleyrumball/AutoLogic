package com.github.bradleyrumball.autologic.test_case_generation;

import com.github.javaparser.ast.expr.BinaryExpr;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OutputElementTest {

    @Test
    public void checkGetMethod() {
        OutputElement outputElement = new OutputElement("methodcheck", Arrays.asList("a","b","c"), "expectedcheck", Arrays.asList("import"));
        String method = outputElement.getMethod();
        assertEquals("methodcheck", method);
    }

    @Test
    public void checkGetInput() {
        OutputElement outputElement = new OutputElement("methodcheck", Arrays.asList("a","b","c"), "expectedcheck", Arrays.asList("import"));
        List<String> input = outputElement.getInput();
        assertEquals(Arrays.asList("a","b","c"), input);
    }

    @Test
    public void checkGetExpected() {
        OutputElement outputElement = new OutputElement("methodcheck", Arrays.asList("a","b","c"), "expectedcheck", Arrays.asList("import"));
        String expected = outputElement.getExpected();
        assertEquals("expectedcheck", expected);
    }

    @Test
    public void checkGetImports() {
        OutputElement outputElement = new OutputElement("methodcheck", Arrays.asList("a","b","c"), "expectedcheck", Arrays.asList("import"));
        List<String> imports = outputElement.getImports();
        assertEquals(Arrays.asList("import"), imports);
    }

    @Test
    public void checkCreateTestString() {
        OutputElement outputElement = new OutputElement("methodcheck", Arrays.asList("a","b","c"), "expectedcheck", Arrays.asList("import"));
        String testOutput = outputElement.createTestString();
        assertEquals("\t\tassertEquals(\"expectedcheck\", String.valueOf(methodcheck(a,b,c)));\n", testOutput);
    }
}
