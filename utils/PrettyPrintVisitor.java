package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

import java.util.Map;

public class PrettyPrintVisitor implements Visitor {

    private SymbolTable symbolTable;

    public PrettyPrintVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Program program) {
        newLine();
        System.out.print("Program");
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
        newLine();
        System.out.print("MainClass-" + mainClass.className.name);
        symbolTable.indentLevel++;
        printClass(mainClass, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) {
        newLine();
        System.out.print("ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        printClass(classDecl, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) {
        newLine();
        System.out.print("MethodDecl-" + methodDecl.returnType + " " + methodDecl.name + " " + methodDecl.params.keySet().toString());
        symbolTable.indentLevel++;
        printMethod(methodDecl, getIndentation());
        symbolTable.indentLevel--;
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////// Stmts ///////////////////////////////////////////////

    @Override
    public Object visit(IfStmt stmt) {
        newLine();
        System.out.print("IfStmt ");
        stmt.condition.accept(this);
        symbolTable.indentLevel++;
        for (Stmt s: stmt.trueBranch) {
            s.accept(this);
        }
        newLine();
        System.out.print("ElseBranch: ");
        for (Stmt s: stmt.trueBranch) {
            s.accept(this);
        }
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(WhileStmt stmt) {
        newLine();
        System.out.print("WhileStmt ");
        stmt.condition.accept(this);
        symbolTable.indentLevel++;
        for (Stmt s: stmt.body) {
            s.accept(this);
        }
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(ReadlnStmt stmt) {
        newLine();
        System.out.print("ReadlnStmt " + stmt.id);
        return null;
    }

    @Override
    public Object visit(PrintlnStmt stmt) {
        newLine();
        System.out.print("returnStmt ");
        if (stmt.expr != null) stmt.expr.accept(this);
        return null;
    }

    @Override
    public Object visit(AssignmentStmt stmt) {
        newLine();
        System.out.print("AssignmentStmt ");
        if (stmt.leftSideAtom != null) {
            stmt.leftSideAtom.accept(this);
            System.out.print(".");
        }
        System.out.print(stmt.leftSideId + " = ");
        stmt.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(FunctionCallStmt stmt) {
        newLine();
        System.out.print("FunctionCallStmt ");
        stmt.atom.accept(this);
        if (!stmt.paramsList.isEmpty()){
            for (Expr e: stmt.paramsList) {
                e.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visit(ReturnStmt stmt) {
        newLine();
        System.out.print("returnStmt ");
        if (stmt.expr != null) stmt.expr.accept(this);
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public Object visit(TwoFactorsArithExpr expr) {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(TwoFactorsBoolExpr expr) {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(TwoFactorsRelExpr expr) {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(ArithGrdExpr expr) {
        System.out.print(expr);
        return null;
    }

    @Override
    public Object visit(StringExpr expr) {
        System.out.print(expr);
        return null;
    }

    @Override
    public Object visit(BoolGrdExpr expr) {
        System.out.print(expr);
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////// Expressions - Atoms /////////////////////////////////

    @Override
    public Object visit(AtomClassInstantiation atom) {
        System.out.print(atom);
        return null;
    }

    @Override
    public Object visit(AtomFieldAccess atom) {
        System.out.print(atom);
        return null;
    }

    @Override
    public Object visit(AtomFunctionCall atom) {
        System.out.print(atom);
        return null;
    }

    @Override
    public Object visit(AtomGrd atom) {
        System.out.print(atom);
        return null;
    }

    @Override
    public Object visit(AtomParenthesizedExpr atom) {
        System.out.print(atom);
        return null;
    }


    ///////////////////////////////////////////////////////////////////////////////
    //////////////////////// Helper Methods ///////////////////////////////////////

    private void newLine() {
        System.out.print("\n" + getIndentation());
    }

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
