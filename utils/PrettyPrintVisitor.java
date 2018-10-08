package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.ArithExpr;
import concrete_nodes.expressions.Expr;
import concrete_nodes.expressions.OneFactorArithExpr;
import concrete_nodes.expressions.TwoFactorsArithExpr;

import java.util.Map;

public class PrettyPrintVisitor implements Visitor {

    private SymbolTable symbolTable;

    public PrettyPrintVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Node node) {
        return null;
    }

    @Override
    public Object visit(Program program) {
        System.out.print("\n" + getIndentation() + "Program");
        symbolTable.indentLevel++;
        program.mainClass.accept(this);
        for (ClassDecl c : program.classDeclList) {
            c.accept(this);
        }
        System.out.println("\n");
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MainClass mainClass) {
        System.out.print("\n" + getIndentation() + "MainClass-" + mainClass.className.name);
        symbolTable.indentLevel++;
        printClass(mainClass, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) {
        System.out.print("\n" + getIndentation() + "ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        printClass(classDecl, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) {
        System.out.print("\n" + getIndentation() + "MethodDecl-" + methodDecl.returnType + " " + methodDecl.name + " " + methodDecl.params.keySet().toString());
        symbolTable.indentLevel++;
        printMethod(methodDecl, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////// Stmts ///////////////////////////////////////////////

    @Override
    public Object visit(Stmt stmt) {
        System.out.print("\n" + getIndentation() );
        return null;
    }

    @Override
    public Object visit(IfStmt stmt) {
        return null;
    }

    @Override
    public Object visit(ReturnStmt stmt) {
        System.out.print("returnStmt ");
        if (stmt.expr != null) stmt.expr.accept(this);
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public Object visit(Expr expr) {
        return null;
    }

    @Override
    public Object visit(ArithExpr expr) {
        System.out.print(" ");
        return null;
    }

    @Override
    public Object visit(TwoFactorsArithExpr expr) {
        expr.leftSide.accept(this);
        System.out.print(expr.operand);
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(OneFactorArithExpr expr) {
        System.out.print(expr);
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //////////////////////// Helper Methods ///////////////////////////////////////

    private String getIndentation() {
        String ret = "";
        for (int i = 0; i < symbolTable.indentLevel; i++) {
            ret += "    ";
        }
        return ret;
    }

    private void printClass(ClassDecl classDecl, String spaces) {
        if (!classDecl.varDeclList.isEmpty()) {
            for (Map.Entry<String, BasicType> entry : classDecl.varDeclList.entrySet()) {
                System.out.print("\n" + spaces + "FieldDecl-" + entry.getValue() + " " + entry.getKey());
            }
        }
        if (!classDecl.methodDeclList.isEmpty()) {
            for (MethodDecl m : classDecl.methodDeclList) {
                m.accept(this);
            }
        }
    }

    private void printMethod(MethodDecl methodDecl, String spaces) {
        if (!methodDecl.varDeclList.isEmpty()) {
            for (Map.Entry<String, BasicType> entry : methodDecl.varDeclList.entrySet()) {
                //TODO add types
                System.out.print("\n" + spaces + "LocalVarDecl-" + entry.getValue() + " " + entry.getKey());
            }
        }
        if (!methodDecl.stmtList.isEmpty()) {
            for (Stmt s : methodDecl.stmtList) {
                s.accept(this);
            }
        }
    }
}
