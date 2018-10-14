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
    private List<Stmt3> currentStmts;
    private List<VarDecl3> currentVars; //current method local vars

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
            currentStmts = new ArrayList<>();
            currentVars = new ArrayList<>();
            currentVars.addAll(convertList(m.varDeclList));
            List<VarDecl3> params = new ArrayList<>();
            params.add(new VarDecl3(new CName3(program.mainClass.className), new Id3("this")));
            params.addAll(convertList(m.params));
            m.accept(this);
            methods.add(new CMtd3(new Type3(m.returnType), new Id3(m.name), params, currentVars, currentStmts));
        }
        symbolTable.decreaseIndentLevel();

        for (ClassDecl c : program.classDeclList) {
            symbolTable.increaseIndentLevel(c.className);
            for (MethodDecl m : c.methodDeclList) {
                currentStmts = new ArrayList<>();
                currentVars = new ArrayList<>();
                currentVars.addAll(convertList(m.varDeclList));
                List<VarDecl3> params = new ArrayList<>();
                params.add(new VarDecl3(new CName3(c.className), new Id3("this")));
                params.addAll(convertList(m.params));
                methods.add(new CMtd3(new Type3(m.returnType), new Id3(m.name), params, currentVars, currentStmts));
                m.accept(this);
            }
            symbolTable.decreaseIndentLevel();
        }

        //once done creating the new tree, print every method just by calling the toString()
        for (CMtd3 m : methods) {
            System.out.print("\n" + m.returnType + " " + m.name + " (");
            boolean fistParam = true;
            for (VarDecl3 entry : m.params) {
                if (!fistParam) System.out.print(", ");
                System.out.print(entry);
                fistParam = false;
            }
            System.out.print("){");
            for (VarDecl3 v : m.varDeclList) {
                System.out.print("\n    " + v + ";");
            }
            for (Stmt3 s : m.stmtList) {
                System.out.print("\n   " + s + ";");
            }
            System.out.println("\n}");
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
        if (!methodDecl.stmtList.isEmpty()) {
            for (Stmt s : methodDecl.stmtList) {
                s.accept(this);
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////// Stmts ///////////////////////////////////////////////

    @Override
    public Object visit(IfStmt stmt) throws Exception {
        String trueBranchLabel = newLabel();
        String exitLabel = newLabel();

        RelExp3Impl condition = (RelExp3Impl) stmt.condition.accept(this);
        currentStmts.add(new Stmt3(Stmt3.Stmt3Type.IF, trueBranchLabel, condition));
        for (Stmt s : stmt.falseBranch) {
            s.accept(this);
        }
        currentStmts.add(new Stmt3(Stmt3.Stmt3Type.GOTO, exitLabel));
        currentStmts.add(new Stmt3(Stmt3.Stmt3Type.LABEL, trueBranchLabel));
        for (Stmt s : stmt.trueBranch) {
            s.accept(this);
        }
        currentStmts.add(new Stmt3(Stmt3.Stmt3Type.LABEL, exitLabel));
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
    public Object visit(ReturnStmt stmt) throws Exception {
        if (stmt.expr != null) currentStmts.add(new Stmt3(Stmt3.Stmt3Type.RETURN_VAR, exprDownToId3(stmt.expr)));
        else currentStmts.add(new Stmt3(Stmt3.Stmt3Type.RETURN));
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public Exp3 visit(TwoFactorsArithExpr expr) throws Exception {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public Exp3 visit(TwoFactorsBoolExpr expr) throws Exception {
        expr.leftSide.accept(this);
        System.out.print(" " + expr.operator + " ");
        expr.rightSide.accept(this);
        return null;
    }

    @Override
    public RelExp3Impl visit(TwoFactorsRelExpr expr) throws Exception {
        return new RelExp3Impl(downToIdc3(expr.leftSide), expr.operator, downToIdc3(expr.rightSide));
    }

    Idc3 downToIdc3(ArithExpr expr) throws Exception {
        Idc3 idc3 = new Id3("WARNING: empty id3 - expr");
        if (expr instanceof ArithGrdExpr) {
            if (((ArithGrdExpr) expr).isIntLiteral()) idc3 = new Const(((ArithGrdExpr) expr).intLiteral);
            else if (((ArithGrdExpr) expr).isNegateArithGrd()) idc3 = exprDownToId3(expr);
            else if (((ArithGrdExpr) expr).isAtomGrd()) idc3 = downToIdc3(((ArithGrdExpr) expr).atom);
            else System.out.println("..TODO.."); //TODO impl
        } else if (expr instanceof TwoFactorsArithExpr) {
            idc3 = exprDownToId3(expr);
        }
        return idc3;
    }

    Idc3 downToIdc3(Atom atom) throws Exception {
        Idc3 idc3 = new Id3("WARNING: empty id3 - atom");
        if (atom instanceof AtomParenthesizedExpr) idc3 = exprDownToId3(((AtomParenthesizedExpr) atom).expr);
        else if (atom instanceof AtomClassInstantiation)
            idc3 = exprDownToId3(new Exp3Impl(new CName3(((AtomClassInstantiation) atom).cname)), new Type3(new ClassNameType(((AtomClassInstantiation) atom).cname.name)));
        else System.out.println("..TODO.."); //TODO impl
        return idc3;
    }

    @Override
    public Exp3 visit(ArithGrdExpr expr) throws Exception {
        Exp3 ret = null;
        if (expr.isIntLiteral()) ret = new Const(expr.intLiteral);
        else if (expr.isNegateArithGrd()) ret = new Exp3Impl(exprDownToId3(expr.negateFactor));
        else if (expr.isAtomGrd()) ret = (Exp3) expr.atom.accept(this);
        return ret;
    }

    @Override
    public Exp3 visit(StringExpr expr) {
        return new Const(expr.s);
    }

    @Override
    public Exp3 visit(BoolGrdExpr expr) throws Exception {
        Exp3 ret = null;
        if (expr.isGround()) ret = new Const(expr.boolGrd);
        else if (expr.isAtomGround()) ret = (Exp3) expr.atom.accept(this);
        else if (expr.isNegatedGround()) ret = new Exp3Impl(exprDownToId3((Expr) expr.grdExpr.accept(this)));
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

    //it just breaks the expr into:
    //Type temp;
    //temp = Exp3Impl;
    //return temp;
    private Id3 exprDownToId3(Expr expr) throws Exception {
        Id3 newTemp = new Id3(newTemp());
        currentVars.add(new VarDecl3(new Type3(expr.type), newTemp));
        currentStmts.add(new Stmt3(Stmt3.Stmt3Type.ASS_VAR, newTemp, (Exp3) expr.accept(this)));
        //temp is the returned <id3>
        return newTemp;
    }

    private Id3 exprDownToId3(Exp3Impl expr, Type3 type) {
        Id3 temp = new Id3(newTemp());
        currentStmts.add(new Stmt3(Stmt3.Stmt3Type.ASS_VARDECL, type, temp, expr));
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
