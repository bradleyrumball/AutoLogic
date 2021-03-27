package com.github.bradleyrumball.autologic;

import java.util.List;

/***
 * Construction helper class for making outputs to files
 */
public class OutputElement {

    public String method;
    public List<String> input;
    public String expected;
    public List<String> imports;

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
}
