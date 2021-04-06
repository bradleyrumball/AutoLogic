package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class HostVisitor extends ModifierVisitor<Void> {
    private CompilationUnit cu;
    private MethodVisitor mv;

    public HostVisitor(CompilationUnit cu) {
        this.cu = cu;
        this.mv = new MethodVisitor();
    }

    @Override
    public Visitable visit(CompilationUnit n, Void arg) {
        n.addImport("com.github.bradleyrumball.autologic.Triangle.Type");
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(MethodDeclaration n, Void arg) {
        if (n.getNameAsString().equals("instrumentedMethod")) {

            NodeList arguments = new NodeList();


            // Adds parameters to the newly injected class
            arguments.add(StaticJavaParser.parseParameter("int side1"));
            arguments.add(StaticJavaParser.parseParameter("int side2"));
            arguments.add(StaticJavaParser.parseParameter("int side3"));
            n.setParameters(arguments);

            //Seraches for method body within original cu
            cu.accept(mv, null);
            n.setBody(mv.body);

            super.visit(n, arg);

        }
        return n;
    }
}
