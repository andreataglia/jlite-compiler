package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

public interface Visitor {

    Object visit(Program program) throws Exception;

    Object visit(MainClass mainClass) throws Exception;

    Object visit(ClassDecl classDecl) throws Exception;

    Object visit(MethodDecl methodDecl) throws Exception;

    Object visit(IfStmt stmt) throws Exception;

    Object visit(WhileStmt stmt) throws Exception;

    Object visit(ReadlnStmt stmt) throws Exception;

    Object visit(PrintlnStmt stmt) throws Exception;

    Object visit(AssignmentStmt stmt) throws Exception;

    Object visit(FunctionCallStmt stmt) throws Exception;

    Object visit(ReturnStmt stmt) throws Exception;

    Object visit(TwoFactorsArithExpr expr) throws Exception;

    Object visit(TwoFactorsBoolExpr expr) throws Exception;

    Object visit(TwoFactorsRelExpr expr) throws Exception;

    Object visit(ArithGrdExpr expr) throws Exception;

    Object visit(StringExpr expr) throws Exception;

    Object visit(BoolGrdExpr expr) throws Exception;

    Object visit(VarDecl varDecl) throws Exception;

    Object visit(AtomClassInstantiation atom) throws Exception;

    Object visit(AtomFieldAccess atom) throws Exception;

    Object visit(AtomFunctionCall atom) throws Exception;

    Object visit(AtomGrd atom) throws Exception;

    Object visit(AtomParenthesizedExpr atom) throws Exception;
}
