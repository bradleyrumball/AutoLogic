package com.github.bradleyrumball.autologic.test_case_generation;

import java.util.List;

/***
 * Construction helper class for making outputs to files
 */
public class OutputElement {

    private String method;
    private List<String>  input;
    private String expected;
    private List<String> imports;

    /***
     * Constructor for output element
     *
     * @param method Method
     * @param input What the user inputs are to be compared to the expected
     * @param expected What is expected
     * @param imports Imports
     */
    public OutputElement(String method, List<String> input, String expected, List<String> imports) {
        this.method  = method;
        this.input = input;
        this.expected = expected;
        this.imports = imports;
    }

    /***
     * Getter for method string
     *
     * @return method String
     */
    protected String getMethod() {
        return method;
    }

    /***
     * Getter for input list
     *
     * @return A list of inputs
     */
    protected List<String>  getInput() {
        return input;
    }

    /***
     * Getter for expected result
     *
     * @return expected result String
     */
    protected String getExpected() {
        return expected;
    }

    /***
     * Getter for import list
     *
     * @return List of imports
     */
    protected List<String> getImports() {
        return imports;
    }

    protected String createTestString() {
        String testOutput = "\t\tassertEquals(" + "\""+this.getExpected()+"\""+ ", " + "String.valueOf("+this.getMethod() + "(" + String.join(",", this.getInput()) + "))" + ");\n";
        return testOutput;
    }
}
