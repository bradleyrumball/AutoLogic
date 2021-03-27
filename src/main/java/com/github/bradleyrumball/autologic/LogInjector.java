package com.github.bradleyrumball.autologic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class LogInjector {

    /***
     * Injects a compilation unit with branch log statements by using a custom visitor
     *
     * @param cu Compilation unit of a class
     * @return Modified compilation unit with injected log statements
     */
    public static CompilationUnit injectMethodAllBranches(CompilationUnit cu) {
        cu.accept(new CustomVisitor(), null);
        return cu;
    }

}
