package utils;

import concrete_nodes.*;

public interface Visitor {
    Object visit(Node node);

    Object visit(Program program);

    Object visit(MainClass mainClass);

    Object visit(ClassDecl classDecl);

    Object visit(MethodDecl methodDecl);

    Object visit(IfStmt ifStmt);
}
