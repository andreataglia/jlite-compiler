package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

public class PrettyPrintVisitor implements Visitor {

    private SymbolTable symbolTable;

    public PrettyPrintVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Program program) throws Exception {
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
    public Object visit(MainClass mainClass) throws Exception {
        newLine();
        System.out.print("MainClass-" + mainClass.className.name);
        symbolTable.indentLevel++;
        printClass(mainClass);
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) throws Exception {
        newLine();
        System.out.print("ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        printClass(classDecl);
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) throws Exception {
        newLine();
        System.out.print("MethodDecl-" + methodDecl.returnType + " " + methodDecl.name + " [");
        boolean fistParam = true;
        for (VarDecl entry : methodDecl.params.list) {
            if (!fistParam) System.out.print(", ");
            System.out.print(entry.type + " " + entry.id);
            fistParam = false;
        }
        System.out.print("]");
        symbolTable.indentLevel++;
        printMethod(methodDecl);
        symbolTable.indentLevel--;
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////// Stmts ///////////////////////////////////////////////

    @Override
    public Object visit(IfStmt stmt) throws Exception {
        newLine();
        System.out.print("IfStmt ");
        stmt.condition.accept(this);
        symbolTable.indentLevel++;
        for (Stmt s : stmt.trueBranch) {
            s.accept(this);
        }
        symbolTable.indentLevel--;
        newLine();
        System.out.print("ElseBranch: ");
        symbolTable.indentLevel++;
        for (Stmt s : stmt.falseBranch) {
            s.accept(this);
        }
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(WhileStmt stmt) throws Exception {
        newLine();
        System.out.print("WhileStmt ");
        stmt.condition.accept(this);
        symbolTable.indentLevel++;
        for (Stmt s : stmt.body) {
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
    public Object visit(PrintlnStmt stmt) throws Exception {
        newLine();
        System.out.print("PrintlnStmt ");
        if (stmt.expr != null) stmt.expr.accept(this);
        return null;
    }

    @Override
    public Object visit(AssignmentStmt stmt) throws Exception {
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
    public Object visit(FunctionCallStmt stmt) throws Exception {
        newLine();
        System.out.print("FunctionCallStmt ");
        stmt.atom.accept(this);
        System.out.print("(");
        boolean firstParam = true;
        if (!stmt.paramsList.isEmpty()) {
            for (Expr e : stmt.paramsList) {
                if (!firstParam) System.out.print(", ");
                e.accept(this);
                firstParam = false;
            }
        }
        System.out.print(")");
        return null;
    }

    @Override
    public Object visit(ReturnStmt stmt) throws Exception {
        newLine();
        System.out.print("ReturnStmt ");
        if (stmt.expr != null) stmt.expr.accept(this);
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public Object visit(TwoFactorsArithExpr expr) throws Exception {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(TwoFactorsBoolExpr expr) throws Exception {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Object visit(TwoFactorsRelExpr expr) throws Exception {
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

    @Override
    public Object visit(VarDecl varDecl) {
        System.out.print(varDecl.type + " " + varDecl.id);
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

    private void printClass(ClassDecl classDecl) throws Exception {
        if (!classDecl.varDeclList.isEmpty()) {
            for (VarDecl entry : classDecl.varDeclList) {
                newLine();
                System.out.print("FieldDecl-" + entry.type + " " + entry.id);
            }
        }
        if (!classDecl.methodDeclList.isEmpty()) {
            for (MethodDecl m : classDecl.methodDeclList) {
                m.accept(this);
            }
        }
    }

    private void printMethod(MethodDecl methodDecl) throws Exception {
        if (!methodDecl.varDeclList.list.isEmpty()) {
            for (VarDecl entry : methodDecl.varDeclList.list) {
                newLine();
                System.out.print("LocalVarDecl-" + entry.type + " " + entry.id);
            }
        }
        if (!methodDecl.stmtList.isEmpty()) {
            for (Stmt s : methodDecl.stmtList) {
                s.accept(this);
            }
        }
    }
}
