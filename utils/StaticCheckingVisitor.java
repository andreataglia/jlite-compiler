package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

import java.util.ArrayList;
import java.util.List;

public class StaticCheckingVisitor implements Visitor {

    private SymbolTable symbolTable;
    private DataType localType;

    public StaticCheckingVisitor() {
        this.symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(Program program) throws Exception {
        newLine();
        System.out.println("------------- Static Checking Output------------------");
        symbolTable.indentLevel++;

        //generate and check class descriptors
        symbolTable.classDescriptors.add(genClassDescriptor(program.mainClass));
        checkClassDescriptor(symbolTable.classDescriptors.get(symbolTable.classDescriptors.size() - 1));
        for (ClassDecl c : program.classDeclList) {
            symbolTable.classDescriptors.add(genClassDescriptor(c));
            checkClassDescriptor(symbolTable.classDescriptors.get(symbolTable.classDescriptors.size() - 1));
        }

        //check classes have different names
        for (int i = 0; i < symbolTable.classDescriptors.size(); i++) {
            for (int j = i + 1; j < symbolTable.classDescriptors.size(); j++) {
                if (symbolTable.classDescriptors.get(i).className.equals(symbolTable.classDescriptors.get(j).className)) {
                    throw new TypeExecption("Two classes have the same name");
                }
            }
        }
        symbolTable.indentLevel--;

        newLine();
        System.out.print("\n------------- Type Checking ------------------");
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
        typeCheckClass(mainClass);
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) throws Exception {
        newLine();
        System.out.print("ClassDecl-" + classDecl.className.name);
        symbolTable.indentLevel++;
        symbolTable.setCurrentClass(classDecl);
        typeCheckClass(classDecl);
        symbolTable.indentLevel--;
        return null;
    }

    private void typeCheckClass(ClassDecl classDecl) throws Exception {
        for (MethodDecl m : classDecl.methodDeclList) {
            symbolTable.increaseIndentLevel(m);
            if (!m.returnType.equals((BasicType) m.accept(this))) {
                throw new TypeExecption("method body type doesn't match return type", classDecl.className.name, m.name);
            }
            symbolTable.decreaseIndentLevel();
        }
    }

    @Override
    public Object visit(MethodDecl methodDecl) throws Exception {
        //TODO check if it's fine having more local vars with the same name and only get the last one
        newLine();
        System.out.print("MethodDecl-" + methodDecl.name);

        //enrich the environment with local vars
        symbolTable.increaseIndentLevel(methodDecl);
        for (VarDecl entry : methodDecl.params.list) {
            localType = entry.type;
            newLine();
            printObjType(entry);
            symbolTable.pushLocalVar(entry);
        }
        for (VarDecl entry : methodDecl.varDeclList.list) {
            localType = entry.type;
            newLine();
            printObjType(entry);
            symbolTable.pushLocalVar(entry);
        }

        //check if the statements are correct
        for (Stmt s : methodDecl.stmtList) {
            localType = (BasicType) s.accept(this);
            printObjType(" --> " + s);
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

        //differentiate calculation of left side type
        if (stmt.leftSideAtom != null) {
            //check leftSideAtom is a class
            localType = (BasicType) stmt.leftSideAtom.accept(this);
            printObjType(stmt.leftSideAtom);
            if (!(localType instanceof ClassNameType))
                throw new TypeExecption("FdAss violated: not a class", symbolTable.currentClass.name, symbolTable.currentMethod);
            System.out.print(".");
            //check leftSideId is a field of a leftSideAtom class, and get its type
            if (!symbolTable.fieldIsInClass(stmt.leftSideId, ((ClassNameType) localType).name))
                throw new TypeExecption("FdAss violated: class hasn't that field", symbolTable.currentClass.name, symbolTable.currentMethod);
            localType = symbolTable.lookupClassFieldType((ClassNameType) localType, stmt.leftSideId);
            printObjType(stmt.leftSideId);
        } else {
            localType = symbolTable.lookupVarType(stmt.leftSideId);
            printObjType(stmt.leftSideId);
            if (localType == null) {
                throw new TypeExecption("VarAss violated: left side type undefined", symbolTable.currentClass.name, symbolTable.currentMethod);
            }
        }
        if (!rightSideType.equals(localType)) {
            throw new TypeExecption("Ass violated: assignment types mismatch", symbolTable.currentClass.name, symbolTable.currentMethod);
        }
        System.out.print(" = ");
        localType = rightSideType;
        printObjType(stmt.rightSide);

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
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(localType.equals(BasicType.DataType.INT) && localType.equals(expr.rightSide.accept(this))))
            throw new TypeExecption("Arith violated: non int member", symbolTable.currentClass.name, symbolTable.currentMethod);
        return localType;
    }

    @Override
    public Object visit(TwoFactorsBoolExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(localType.equals(BasicType.DataType.BOOL) && localType.equals(expr.rightSide.accept(this))))
            throw new TypeExecption("Bool violated: non bool member", symbolTable.currentClass.name, symbolTable.currentMethod);
        return localType;
    }

    @Override
    public Object visit(TwoFactorsRelExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(localType.equals(BasicType.DataType.INT) && localType.equals(expr.rightSide.accept(this))))
            throw new TypeExecption("Rel violated: non int member", symbolTable.currentClass.name, symbolTable.currentMethod);
        return new BasicType(BasicType.DataType.BOOL);
    }

    @Override
    public Object visit(ArithGrdExpr expr) throws Exception {
        if (expr.isIntLiteral()) localType = new BasicType(BasicType.DataType.INT);
        else if (expr.isAtomGrd()) localType = (BasicType) expr.atom.accept(this);
        else if (expr.isNegateArithGrd()) localType = (BasicType) expr.negateFactor.accept(this);
        else throw new Exception("ArithGrdExpr " + expr + " shouldn't be null");
        return localType;
    }

    @Override
    public Object visit(StringExpr expr) {
        return new BasicType(BasicType.DataType.STRING);
    }

    @Override
    public BasicType visit(BoolGrdExpr expr) throws Exception {
        localType = new BasicType(BasicType.DataType.BOOL);
        if (expr.isNegatedGround()) localType = (BasicType) expr.grdExpr.accept(this);
        else if (expr.isAtomGround()) localType = (BasicType) expr.atom.accept(this);
        return (BasicType) localType;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////// Expressions - Atoms /////////////////////////////////

    @Override
    public Object visit(AtomClassInstantiation atom) {
        return symbolTable.lookUpClass(atom.cname.toString());
    }

    @Override
    public Object visit(AtomFieldAccess atom) throws Exception {
        //check leftSideAtom is a class
        localType = (BasicType) atom.atom.accept(this);
        if (!(localType instanceof ClassNameType))
            throw new TypeExecption("Field violated: not a class", symbolTable.currentClass.name, symbolTable.currentMethod);
        //check id is a field of a leftSideAtom class, and get its type
        if (!symbolTable.fieldIsInClass(atom.field, ((ClassNameType) localType).name))
            throw new TypeExecption("Field violated: class hasn't that field", symbolTable.currentClass.name, symbolTable.currentMethod);
        localType = symbolTable.lookupClassFieldType((ClassNameType) localType, atom.field);
        return localType;
    }

    @Override
    public FunctionType visit(AtomFunctionCall atom) throws Exception {
        //atom is the function name
        localType = (DataType) atom.atom.accept(this);
        if (!(localType instanceof FunctionType)) throw new TypeExecption("LocalCall violated: not a function name", symbolTable.currentClass.name, symbolTable.currentMethod);
        //TODO impl
        return (FunctionType) localType;
    }

    @Override
    public DataType visit(AtomGrd atom) throws TypeExecption {
        localType = null;
        if (atom.isNullGround()) localType = new BasicType(BasicType.DataType.NULL); //TODO check how to deal with it
        else if (atom.isThisGround()) localType = symbolTable.currentClass;
        else if (atom.isIdentifierGround()) {
            localType = symbolTable.lookupVarType(atom.id);
            if (localType == null)
                throw new TypeExecption("Id violated", symbolTable.currentClass.name, symbolTable.currentMethod);
        }
        return localType;
    }

    @Override
    public DataType visit(AtomParenthesizedExpr atom) throws Exception {
        return (DataType) atom.expr.accept(this);
    }

    @Override
    public BasicType visit(VarDecl varDecl) {
        return varDecl.type;
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
        return new ClassDescriptor(classDecl.className, new VarsList(classFields), classMethodsSignatures);
    }

    private void checkClassDescriptor(ClassDescriptor classDescriptor) throws Exception {
        //different fields name
        if (!classDescriptor.classFields.allVarsHaveDifferentIds())
            throw new TypeExecption("Class " + classDescriptor.className + " has two vars with the same name");
        //different params names in methods
        for (MethodSignature m : classDescriptor.methodSignatures) {
            if (!m.params.allVarsHaveDifferentIds()) {
                throw new TypeExecption("Two params with same name", m.name, classDescriptor.className.toString());
            }
        }
        //different methods
        for (int i = 0; i < classDescriptor.methodSignatures.size(); i++) {
            for (int j = i + 1; j < classDescriptor.methodSignatures.size(); j++) {
                if (classDescriptor.methodSignatures.get(i).equals(classDescriptor.methodSignatures.get(j)))
                    throw new TypeExecption("Two methods with the same signature", classDescriptor.className.toString());
            }
        }
    }


}
