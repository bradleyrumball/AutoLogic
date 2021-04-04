package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
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
    public Visitable visit(ClassOrInterfaceDeclaration n, Void arg) {
        MethodDeclaration method = n.addMethod("Name");
        //Sets the type
        method.setPrivate(true);
        method.setStatic(true);
        method.setType(Object.class);
        // Adds parameters to the newly injected class
        method.addParameter("int", "side1");
        method.addParameter("int", "side2");
        method.addParameter("int", "side3");

        //Seraches for method body within original cu
        cu.accept(mv, null);
        method.setBody(mv.body);

        super.visit(n,arg);

        return n;
    }
}
