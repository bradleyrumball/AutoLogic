package com.github.bradleyrumball.autologic.test_case_generation;

import java.util.List;

/***
 * Construction helper class for making outputs to files
 */
public class OutputElement {

    private String method;
    private List<String> input;
    private String expected;
    private List<String> imports;

    /***
     * Constructor for output element
     *
     * @param m Method
     * @param l What the user inputs are to be compared to the expected
     * @param e What is expected
     * @param i Imports
     */
    public OutputElement(String m, List<String> l, String e, List<String> i) {
        method = m;
        input = l;
        expected = e;
        imports = i;
    }

    /***
     * Setter for method name
     *
     * @param method the method name
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /***
     * Setter for inputs for the test
     *
     * @param input List of inputs
     */
    public void setInput(List<String> input) {
        this.input = input;
    }

    /***
     * Setter for expected output
     *
     * @param expected Output string
     */
    public void setExpected(String expected) {
        this.expected = expected;
    }

    /***
     * Setter for imports
     *
     * @param imports List of imports as strings
     */
    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    /***
     * Getter for method string
     *
     * @return method String
     */
    public String getMethod() {
        return method;
    }

    /***
     * Getter for input list
     *
     * @return A list of inputs
     */
    public List<String> getInput() {
        return input;
    }

    /***
     * Getter for expected result
     *
     * @return expected result String
     */
    public String getExpected() {
        return expected;
    }

    /***
     * Getter for import list
     *
     * @return List of imports
     */
    public List<String> getImports() {
        return imports;
    }
}
