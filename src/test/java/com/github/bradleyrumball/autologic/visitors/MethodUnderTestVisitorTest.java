package com.github.bradleyrumball.autologic.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.Visitable;
import org.junit.Test;


import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodUnderTestVisitorTest {

    @Test
    public void checkGetIdCounters() {
        MethodUnderTestVisitor methodUnderTestVisitor = new MethodUnderTestVisitor();
        assertEquals(methodUnderTestVisitor.getIdCounters(), Collections.emptyMap());
    }

    @Test
    public void checkVisit() {
        CompilationUnit instrumentedClass = new CompilationUnit();
        instrumentedClass.setPackageDeclaration("Package");
        ClassOrInterfaceDeclaration classObj = instrumentedClass.addClass("MyClass");
        MethodDeclaration methodDec = classObj.addMethod("MyMethod");

        Expression e = new NameExpr("name");
        Statement s = mock(Statement.class);

        IfStmt ifstmt = new IfStmt(new BinaryExpr(new BinaryExpr(e,e, BinaryExpr.Operator.EQUALS),new BinaryExpr(e,e, BinaryExpr.Operator.GREATER), BinaryExpr.Operator.OR),s, s);

        methodDec.setBody(new BlockStmt().addStatement(ifstmt));

        MethodUnderTestVisitor methodUnderTestVisitor = new MethodUnderTestVisitor();

        Visitable v = instrumentedClass.accept(methodUnderTestVisitor, null);

        assertNotNull(v);
    }

    @Test
    public void checkGetClassPath() {
        MethodUnderTestVisitor methodUnderTestVisitor = new MethodUnderTestVisitor();
        assertEquals(methodUnderTestVisitor.getClassPath(), "");
    }
}
