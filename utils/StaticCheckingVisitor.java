package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticCheckingVisitor implements Visitor {

    private SymbolTable symbolTable;
    private BasicType localType;

    public StaticCheckingVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Program program) {
        newLine();
        System.out.println("------------- Static Checking Output------------------");
        symbolTable.indentLevel++;

        //generate class descriptors
        List<ClassDescriptor> classDescriptors = new ArrayList<>();
        classDescriptors.add(genClassDescriptor(program.mainClass));
        for (ClassDecl c : program.classDeclList) {
            classDescriptors.add(genClassDescriptor(c));
        }

        //start exploring the tree
        //program.mainClass.accept(this);
        for (ClassDecl c : program.classDeclList) {
            //c.accept(this);
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
        printClass(mainClass);
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) {
        newLine();
        System.out.print("ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        printClass(classDecl);
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) {
        newLine();
        System.out.print("MethodDecl-" + methodDecl.returnType + " " + methodDecl.name + " [");
        boolean fistParam = true;
        for (Map.Entry<String, BasicType> entry : methodDecl.params.entrySet()) {
            if (!fistParam) System.out.print(", ");
            System.out.print(entry.getValue() + " " + entry.getKey());
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
    public Object visit(IfStmt stmt) {
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
    public Object visit(WhileStmt stmt) {
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
    public Object visit(PrintlnStmt stmt) {
        newLine();
        System.out.print("PrintlnStmt ");
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
    public Object visit(ReturnStmt stmt) {
        newLine();
        System.out.print("ReturnStmt ");
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
        localType = new BasicType(BasicType.DataType.STRING);
        printObjType(expr);
        return localType;
    }

    @Override
    public BasicType visit(BoolGrdExpr expr) {
        localType = new BasicType(BasicType.DataType.BOOL);
        if (expr.isNegatedGround()) localType = (BasicType) expr.grdExpr.accept(this);
        else if (expr.isAtomGround()) localType = (BasicType) expr.atom.accept(this);
        printObjType(expr);
        return localType;
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
    public BasicType visit(AtomGrd atom) {
        if (atom.isNullGround()) localType = new BasicType(BasicType.DataType.NULL);
        else if (atom.isThisGround()) localType = new ClassNameType("lookup class name"); //TODO lookup class name
        printObjType(atom);
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

    private void printClass(ClassDecl classDecl) {
        if (!classDecl.varDeclList.isEmpty()) {
            for (Map.Entry<String, BasicType> entry : classDecl.varDeclList.entrySet()) {
                newLine();
                System.out.print("FieldDecl-" + entry.getValue() + " " + entry.getKey());
            }
        }
        if (!classDecl.methodDeclList.isEmpty()) {
            for (MethodDecl m : classDecl.methodDeclList) {
                m.accept(this);
            }
        }
    }

    private void printMethod(MethodDecl methodDecl) {
        if (!methodDecl.varDeclList.isEmpty()) {
            for (Map.Entry<String, BasicType> entry : methodDecl.varDeclList.entrySet()) {
                newLine();
                System.out.print("LocalVarDecl-" + entry.getValue() + " " + entry.getKey());
            }
        }
        if (!methodDecl.stmtList.isEmpty()) {
            for (Stmt s : methodDecl.stmtList) {
                s.accept(this);
            }
        }
    }

    private void printObjType(Object object) {
        System.out.print("[" + object + "->" + localType + "]");
    }

    private ClassDescriptor genClassDescriptor(ClassDecl classDecl) {
        List<VarDecl> classFields = new ArrayList<>();
        List<MethodSignature> classMethodsSignatures = new ArrayList<>();
        newLine();
        System.out.print("Class - " + classDecl.className);
        symbolTable.indentLevel++;
        if (!classDecl.varDeclList.isEmpty()) {
            for (Map.Entry<String, BasicType> entry : classDecl.varDeclList.entrySet()) {
                classFields.add(new VarDecl(entry.getKey(), entry.getValue()));
                localType = entry.getValue();
                newLine();
                System.out.print("field:");
                printObjType(entry.getKey());
            }
        }
        if (!classDecl.methodDeclList.isEmpty()) {
            for (MethodDecl m : classDecl.methodDeclList) {
                classMethodsSignatures.add(MethodSignature.fromMethodDecl(m));
                newLine();
                System.out.print("method:["+ m.name + "->" + m.returnType + " [");
                for (VarDecl varDecl: classMethodsSignatures.get(classMethodsSignatures.size()-1).params) {
                    localType = varDecl.type;
                    printObjType(varDecl.id);
                }
                System.out.print("]]");
            }
        }
        symbolTable.indentLevel--;
        return new ClassDescriptor(classDecl.className.toString(), classFields, classMethodsSignatures);
    }
}
