package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

public interface Visitor {

    Object visit(Program program);

    Object visit(MainClass mainClass);

    Object visit(ClassDecl classDecl);

    Object visit(MethodDecl methodDecl);

    Object visit(IfStmt stmt);

    Object visit(WhileStmt stmt);

    Object visit(ReadlnStmt stmt);

    Object visit(PrintlnStmt stmt);

    Object visit(AssignmentStmt stmt);

    Object visit(FunctionCallStmt stmt);

    Object visit(ReturnStmt stmt);

    Object visit(TwoFactorsArithExpr expr);

    Object visit(TwoFactorsBoolExpr expr);

    Object visit(TwoFactorsRelExpr expr);

    Object visit(ArithGrdExpr expr);

    Object visit(StringExpr expr);

    Object visit(BoolGrdExpr expr);

    Object visit(AtomClassInstantiation atom);

    Object visit(AtomFieldAccess atom);

    Object visit(AtomFunctionCall atom);

    Object visit(AtomGrd atom);

    Object visit(AtomParenthesizedExpr atom);
}
