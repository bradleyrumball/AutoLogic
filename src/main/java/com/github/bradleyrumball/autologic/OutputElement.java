package com.github.bradleyrumball.autologic;

import java.util.List;

public class OutputElement {

    public String method;
    public List<String> input;
    public String expected;
    public List<String> imports;

    public OutputElement(String m, List<String> l, String e, List<String> i) {
        method = m;
        input = l;
        expected = e;
        imports = i;
    }
}
