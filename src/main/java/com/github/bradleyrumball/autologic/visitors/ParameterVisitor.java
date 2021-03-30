package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;

public class ParameterVisitor {

    /**
     * The compilation unit is the source that we wish to analyse parameters for
     */
    private CompilationUnit cu;

    /**
     * NodeList containing all parameters
     */
    private NodeList params;

    /**
     * Constructor takes a compilation unit and then finds all parameters of all methods withn a class
     * @param cu
     */
    public ParameterVisitor(CompilationUnit cu) {
        this.cu = cu;

        this.cu.findAll(MethodDeclaration.class).forEach(m -> {
            // This will be overwritten, only the last method will be retained
            params = m.getParameters();
        });
    }

    /**
     * gets the total number of parameters
     * @return total number of parameters
     */
    public int getNumberOfParameters() {
        return params.size();
    }

    /**
     * Returns an individual parameter as a node
     * @param id id of the parameter we wish to get
     * @return the parameter as a node
     */
    public Node getParameter(int id) {
        return params.get(id);
    }

    /**
     * Returns all parameters
     * @return all parameters as a nodeList
     */
    public NodeList getParameters() {
        return params;
    }

}
