package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;
import nodes3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IRGeneratorVisitor implements Visitor {

    private SymbolTable symbolTable;
    private int labelCount;
    private int tempCount;

    public IRGeneratorVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        labelCount = 0;
        tempCount = 0;
        symbolTable.indentLevel = 0;
    }

    @Override
    public Program3 visit(Program program) throws Exception {
        System.out.println("\n\n========== CData3 ==========");
        HashMap<CName3, List<VarDecl3>> cData3 = new HashMap<>();
        cData3.put(new CName3(program.mainClass.className), convertList(program.mainClass.varDeclList));
        for (ClassDecl c : program.classDeclList) {
            cData3.put(new CName3(c.className), convertList(c.varDeclList));
        }
        for (Map.Entry<CName3, List<VarDecl3>> entry : cData3.entrySet()) {
            printClass(entry.getKey(), entry.getValue());
        }

        //generate code for each method
        System.out.println("\n\n========== CMtd3 ==========");
        List<CMtd3> methods = new ArrayList<>();
        symbolTable.increaseIndentLevel(program.mainClass.className);
        for (MethodDecl m : program.mainClass.methodDeclList) {
            List<Stmt3> stmts = (List<Stmt3>) m.accept(this);
            methods.add(new CMtd3(new Type3(m.returnType),new Id3(m.name),convertList(m.params), convertList(m.varDeclList), stmts));
        }
        symbolTable.decreaseIndentLevel();

        for (ClassDecl c : program.classDeclList) {
            symbolTable.increaseIndentLevel(c.className);
            for (MethodDecl m : c.methodDeclList) {
                List<Stmt3> stmts = (List<Stmt3>) m.accept(this);
                methods.add(new CMtd3(new Type3(m.returnType),new Id3(m.name),convertList(m.params), convertList(m.varDeclList), stmts));

            }
            symbolTable.decreaseIndentLevel();
        }

        System.out.println("\n\n=====fx== End of IR3 Program =======");
        return new Program3(cData3, methods);
    }

    @Override
    public Object visit(MainClass mainClass) {
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) {
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) throws Exception {
        newLine();
        System.out.print(methodDecl.returnType + " " + methodDecl.name + "(" + symbolTable.currentClass + " this");
        //TODO parama is allowed to be NULL, but in IR3 specs doesn't allow a type to be null
        for (VarDecl entry : methodDecl.params.list) {
            System.out.print(", " + entry.type + " " + entry.id);
        }
        System.out.print("){");
        symbolTable.increaseIndentLevel(methodDecl);
        if (!methodDecl.varDeclList.list.isEmpty()) {
            for (VarDecl entry : methodDecl.varDeclList.list) {
                newLine();
                System.out.print(entry.type + " " + entry.id + ";");
            }
        }
        if (!methodDecl.stmtList.isEmpty()) {
            for (Stmt s : methodDecl.stmtList) {
                s.accept(this);
            }
        }
        symbolTable.decreaseIndentLevel();
        newLine();
        System.out.print("}");
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////// Stmts ///////////////////////////////////////////////

    @Override
    public Object visit(IfStmt stmt) throws Exception {
        String trueBranchLabel = newLabel();
        String exitLabel = newLabel();

        String condition = (String) stmt.condition.accept(this);
        newLine();
        System.out.print("if (" + condition + ") goto " + trueBranchLabel + ";");
        for (Stmt s : stmt.falseBranch) {
            s.accept(this);
        }
        newLine();
        System.out.print("goto " + exitLabel + ";");
        printLabel(trueBranchLabel);
        for (Stmt s : stmt.trueBranch) {
            s.accept(this);
        }
        printLabel(exitLabel);
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
        if (!stmt.atom.paramsList.isEmpty()) {
            for (Expr e : stmt.atom.paramsList) {
                if (!firstParam) System.out.print(", ");
                e.accept(this);
                firstParam = false;
            }
        }
        System.out.print(")");
        return null;
    }

    @Override
    public String visit(ReturnStmt stmt) throws Exception {
        String ret = "";
        if (stmt.expr != null) ret = " " + exprDownToId3(stmt.expr);
        newLine();
        System.out.print("return" + ret + ";");
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public String visit(TwoFactorsArithExpr expr) throws Exception {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return "<Exp3>";
    }

    @Override
    public String visit(TwoFactorsBoolExpr expr) throws Exception {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return "<Exp3>";
    }

    @Override
    public String visit(TwoFactorsRelExpr expr) throws Exception {
        return (exprDownToId3(expr.leftSide) + " " + expr.operator + " " + exprDownToId3(expr.rightSide));
    }

    @Override
    public String visit(ArithGrdExpr expr) throws Exception {
        String ret = null;
        if (expr.isIntLiteral()) ret = expr.toString();
        else if (expr.isNegateArithGrd()) ret = "-" + expr.negateFactor.accept(this);
        else if (expr.isAtomGrd()) ret = expr.atom.accept(this).toString();
        return ret;
    }

    @Override
    public String visit(StringExpr expr) {
        return expr.toString();
    }

    @Override
    public String visit(BoolGrdExpr expr) throws Exception {
        String ret = null;
        if (expr.isGround()) ret = expr.boolGrd.toString();
        else if (expr.isAtomGround()) ret = expr.atom.accept(this).toString();
        else if (expr.isNegatedGround()) ret = "!" + expr.grdExpr.accept(this);
        return ret;
    }

    @Override
    public String visit(VarDecl varDecl) {
        return varDecl.toString();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////// Expressions - Atoms /////////////////////////////////

    @Override
    public String visit(AtomClassInstantiation atom) {
        return "@";
    }

    @Override
    public String visit(AtomFieldAccess atom) {
        return "@";
    }

    @Override
    public String visit(AtomFunctionCall atom) {
        return "@";
    }

    @Override
    public String visit(AtomGrd atom) {
        return atom.toString();
    }

    @Override
    public String visit(AtomParenthesizedExpr atom) {
        return "@";
    }


    ///////////////////////////////////////////////////////////////////////////////
    //////////////////////// Helper Methods ///////////////////////////////////////

    private void printLabel(String label) {
        System.out.print("\n" + getIndentation(true) + "Label " + label + ":");
    }

    private void newLine() {
        System.out.print("\n" + getIndentation(false));
    }

    private String getIndentation(boolean labelPrint) {
        String ret = "";
        if (symbolTable.indentLevel > 1) {
            ret += "    ";
        }
        if (labelPrint && !ret.isEmpty()) ret = ret.substring(1);
        return ret;
    }

    private void printClass(CName3 name, List<VarDecl3> vars) {
        System.out.print("\nData3 " + name.name + " {");
        if (!vars.isEmpty()) {
            for (VarDecl3 entry : vars) {
                System.out.print("\n    " + entry + ";");
            }
        }
        System.out.print("\n}");
    }

    private String newLabel() {
        this.labelCount++;
        return String.valueOf(labelCount);
    }

    private String exprDownToId3(Expr expr) throws Exception {
        //check if it's already id3
        if (expr instanceof ArithGrdExpr) {
            if (((ArithGrdExpr) expr).isIntLiteral()) return expr.accept(this).toString();
        }
        String temp = newTemp();
        newLine();
        System.out.print(expr.type + " " + temp + ";");
        newLine();
        System.out.print(temp + " = " + expr.accept(this) + ";");
        //temp is the returned <id3>
        return temp;
    }

    private String newTemp() {
        this.tempCount++;
        return "_t" + tempCount;
    }

    private List<VarDecl3> convertList(List<VarDecl> vars) {
        List<VarDecl3> list = new ArrayList<>();
        for (VarDecl v : vars) {
            list.add(new VarDecl3(v));
        }
        return list;
    }

    private List<VarDecl3> convertList(VarsList vars) {
        List<VarDecl3> list = new ArrayList<>();
        for (VarDecl v : vars.list) {
            list.add(new VarDecl3(v));
        }
        return list;
    }


}
