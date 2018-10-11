package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;
import jdk.internal.dynalink.support.TypeUtilities;

import java.util.ArrayList;
import java.util.List;

public class StaticCheckingVisitor implements Visitor {

    private SymbolTable symbolTable;
    private BasicType localType;

    public StaticCheckingVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Program program) throws Exception {
        newLine();
        System.out.println("------------- Static Checking Output------------------");
        symbolTable.indentLevel++;

        //generate and check class descriptors
        List<ClassDescriptor> classDescriptors = new ArrayList<>();
        classDescriptors.add(genClassDescriptor(program.mainClass));
        for (ClassDecl c : program.classDeclList) {
            classDescriptors.add(genClassDescriptor(c));
            checkClassDescriptor(classDescriptors.get(classDescriptors.size() - 1));
        }

        //check classes have different names
        for (int i = 0; i < classDescriptors.size(); i++) {
            for (int j = i + 1; j < classDescriptors.size(); j++) {
                if (classDescriptors.get(i).className.equalsIgnoreCase(classDescriptors.get(j).className))
                    throw new TypeExecption("Two classes have the same name");
            }
        }
        symbolTable.indentLevel--;

        //start exploring the tree
        symbolTable.increaseIndentLevel(program.mainClass.className);
        program.mainClass.accept(this);
        symbolTable.decreaseIndentLevel();
        for (ClassDecl c : program.classDeclList) {
            symbolTable.increaseIndentLevel(c.className);
            c.accept(this);
            symbolTable.decreaseIndentLevel();
        }
        newLine();
        System.out.println("------------- End Static Checking Output------------------");
        return null;
    }

    @Override
    public Object visit(MainClass mainClass) throws Exception {
        newLine();
        System.out.print("MainClass-" + mainClass.className.name);
        symbolTable.setCurrentClass(mainClass);
        for (MethodDecl m : mainClass.methodDeclList) {
            symbolTable.increaseIndentLevel(m.name);
            if (!m.returnType.equals(m.accept(this)))
                throw new TypeExecption("method body type doesn't match return type", mainClass.className.name, m.name);
            symbolTable.decreaseIndentLevel();
        }
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) throws Exception {
        newLine();
        System.out.print("ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        symbolTable.setCurrentClass(classDecl);
        for (MethodDecl m : classDecl.methodDeclList) {
            symbolTable.increaseIndentLevel(m.name);
            if (!m.returnType.equals(m.accept(this)))
                throw new TypeExecption("method body type doesn't match return type", classDecl.className.name, m.name);
            symbolTable.decreaseIndentLevel();
        }
        symbolTable.indentLevel--;
        return null;
    }

    @Override
    public Object visit(MethodDecl methodDecl) throws Exception {
        //TODO check if it's fine having more local vars with the same name and only get the last one
        newLine();
        System.out.print("MethodDecl-" + methodDecl.name);

        //enrich the environment with local vars
        symbolTable.increaseIndentLevel(methodDecl.name);
        for (VarDecl entry : methodDecl.params) {
            localType = entry.type;
            newLine();
            printObjType(entry);
            symbolTable.pushLocalVar(entry);
        }

        //check if the statements are correct
        for (Stmt s : methodDecl.stmtList) {
            localType = (BasicType) s.accept(this);
            printObjType(s);
        }

        symbolTable.decreaseIndentLevel();
        return localType;
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
        BasicType rightSideType = (BasicType) stmt.rightSide.accept(this);

        //different calculation of left side type
        if (stmt.leftSideAtom != null) {
            //check leftSideAtom is a class
            localType = (BasicType) stmt.leftSideAtom.accept(this);
            printObjType(stmt.leftSideAtom);
            if (!(localType instanceof ClassNameType))
                throw new TypeExecption("FdAss violated: not a class", symbolTable.currentClass.name, symbolTable.currentMethod);
            System.out.print(".");
            //check leftSideId is a field of a leftSideAtom class, and get its type
            if (symbolTable.fieldIsInClass(stmt.leftSideId, ((ClassNameType) localType).name))
                throw new TypeExecption("FdAss violated: class hasn't that field", symbolTable.currentClass.name, symbolTable.currentMethod);
            localType = symbolTable.getClassFieldType(stmt.leftSideId);
            printObjType(stmt.leftSideId);
            //check right side to match left side type
            System.out.print(" = ");
            printObjType(stmt.rightSide);
            if (!rightSideType.equals(localType))
                throw new TypeExecption("FdAss violated: assignment types mismatch", symbolTable.currentClass.name, symbolTable.currentMethod);
        } else {

        }
        return new BasicType(BasicType.DataType.VOID);
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
        localType = new BasicType(BasicType.DataType.STRING);
        printObjType(expr);
        return localType;
    }

    @Override
    public BasicType visit(BoolGrdExpr expr) throws Exception {
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

    @Override
    public Object visit(VarDecl varDecl) {
        System.out.print(varDecl.type + " " + varDecl.id);
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
        if (!methodDecl.varDeclList.isEmpty()) {
            for (VarDecl entry : methodDecl.varDeclList) {
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
            for (VarDecl entry : classDecl.varDeclList) {
                classFields.add(new VarDecl(entry.id, entry.type));
                localType = entry.type;
                newLine();
                System.out.print("field:");
                printObjType(entry.id);
            }
        }
        if (!classDecl.methodDeclList.isEmpty()) {
            for (MethodDecl m : classDecl.methodDeclList) {
                classMethodsSignatures.add(MethodSignature.fromMethodDecl(m));
                newLine();
                System.out.print("method:[" + m.name + "->" + m.returnType + " [");
                for (VarDecl varDecl : classMethodsSignatures.get(classMethodsSignatures.size() - 1).params.list) {
                    localType = varDecl.type;
                    printObjType(varDecl.id);
                }
                System.out.print("]]");
            }
        }
        symbolTable.indentLevel--;
        return new ClassDescriptor(classDecl.className.toString(), new VarsList(classFields), classMethodsSignatures);
    }

    private void checkClassDescriptor(ClassDescriptor classDescriptor) throws Exception {
        //different fields name
        if (!classDescriptor.classFields.allVarsHaveDifferentIds())
            throw new TypeExecption("Class " + classDescriptor.className + " has two vars with the same name");
        //different params names in methods
        for (MethodSignature m : classDescriptor.methodSignatures) {
            if (!m.params.allVarsHaveDifferentIds()) {
                throw new TypeExecption("Two params with same name", m.name, classDescriptor.className);
            }
        }
        //different methods
        for (int i = 0; i < classDescriptor.methodSignatures.size(); i++) {
            for (int j = i + 1; j < classDescriptor.methodSignatures.size(); j++) {
                if (classDescriptor.methodSignatures.get(i).equals(classDescriptor.methodSignatures.get(j)))
                    throw new TypeExecption("Two methods with the same signature", classDescriptor.className);
            }
        }
    }


}
