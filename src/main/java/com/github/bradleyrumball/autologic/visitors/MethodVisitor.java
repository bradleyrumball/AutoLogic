package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class MethodVisitor extends ModifierVisitor<Void> {

    protected BlockStmt body;

    @Override
    public Visitable visit(MethodDeclaration n, Void arg) {
        n.getBody().ifPresent(blockStmt -> {
            body = blockStmt;
        });

        return n;
    }
}
