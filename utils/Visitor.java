package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.ArithExpr;
import concrete_nodes.expressions.Expr;
import concrete_nodes.expressions.OneFactorArithExpr;
import concrete_nodes.expressions.TwoFactorsArithExpr;

public interface Visitor {
    Object visit(Node node);

    Object visit(Program program);

    Object visit(MainClass mainClass);

    Object visit(ClassDecl classDecl);

    Object visit(MethodDecl methodDecl);

    Object visit(Stmt stmt);

    Object visit(IfStmt stmt);

    Object visit(ReturnStmt stmt);

    Object visit(Expr expr);

    Object visit(ArithExpr expr);

    Object visit(TwoFactorsArithExpr expr);

    Object visit(OneFactorArithExpr expr);
}
