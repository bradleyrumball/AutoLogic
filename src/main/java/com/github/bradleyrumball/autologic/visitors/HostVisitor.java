package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.lang.instrument.ClassDefinition;

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
        method.setType(Object.class);
        method.addParameter("int", "side1");
        method.addParameter("int", "side2");
        method.addParameter("int", "side3");

        //Seraches for method body within original cu
        cu.accept(mv, null);
        method.setBody(mv.body);

        return n;
    }
}
