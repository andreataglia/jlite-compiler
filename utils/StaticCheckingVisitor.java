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
                    throwTypeException("Two classes have the same name", 0);
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
        typeCheckClass(mainClass);
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) throws Exception {
        newLine();
        System.out.print("ClassDecl-" + classDecl.className.name);
        typeCheckClass(classDecl);
        return null;
    }

    private void typeCheckClass(ClassDecl classDecl) throws Exception {
        symbolTable.increaseIndentLevel(classDecl.className);
        for (MethodDecl m : classDecl.methodDeclList) {
            if (!m.returnType.equals((BasicType) m.accept(this))) {
                throwTypeException("method body type doesn't match return type", 2);
            }
        }
        symbolTable.decreaseIndentLevel();
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
            printObjType("Stmt");
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
        if (!((BasicType) stmt.condition.accept(this)).equals(BasicType.DataType.BOOL))
            throwTypeException("If condition violated: not Bool ", 2);
        printObjType(stmt.condition);
        symbolTable.increaseIndentLevel();
        for (Stmt s : stmt.trueBranch) {
            localType = (DataType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        newLine();
        System.out.print("ElseBranch: ");
        DataType trueBranchType = localType;
        symbolTable.increaseIndentLevel();
        for (Stmt s : stmt.falseBranch) {
            localType = (DataType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        if (!localType.equals(trueBranchType)) throwTypeException("IfStmt branches type mismatch", 2);
        return localType;
    }

    @Override
    public Object visit(WhileStmt stmt) throws Exception {
        newLine();
        System.out.print("WhileStmt ");
        if (!stmt.condition.accept(this).equals(BasicType.DataType.BOOL))
            throwTypeException("While condition violated: not Bool ", 2);
        symbolTable.increaseIndentLevel();
        localType = new BasicType(BasicType.DataType.NULL);
        for (Stmt s : stmt.body) {
            localType = (DataType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        return localType;
    }

    @Override
    public Object visit(ReadlnStmt stmt) throws TypeException {
        newLine();
        System.out.print("ReadlnStmt ");
        BasicType idType = symbolTable.lookupVarType(stmt.id);
        if (!(idType.equals(BasicType.DataType.BOOL) || idType.equals(BasicType.DataType.STRING) || idType.equals(BasicType.DataType.INT)))
            throwTypeException("ReadlnStmt can't be applied to this identifier", 2);
        return new BasicType(BasicType.DataType.VOID);
    }

    @Override
    public Object visit(PrintlnStmt stmt) throws Exception {
        newLine();
        System.out.print("PrintlnStmt ");
        BasicType idType = (BasicType) stmt.expr.accept(this);
        if (!(idType.equals(BasicType.DataType.BOOL) || idType.equals(BasicType.DataType.STRING) || idType.equals(BasicType.DataType.INT)))
            throwTypeException("PrintStmt can't be applied to this expression", 2);
        return new BasicType(BasicType.DataType.VOID);
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
                throwTypeException("FdAss violated: not a class", 2);
            System.out.print(".");
            //check leftSideId is a field of a leftSideAtom class, and get its type
            if (!symbolTable.isFieldOfClass(stmt.leftSideId, ((ClassNameType) localType).name))
                throwTypeException("FdAss violated: class hasn't that field", 2);
            localType = symbolTable.lookupClassFieldType((ClassNameType) localType, stmt.leftSideId);
            printObjType(stmt.leftSideId);
        } else {
            localType = symbolTable.lookupVarType(stmt.leftSideId);
            printObjType(stmt.leftSideId);
            if (localType == null) {
                throwTypeException("VarAss violated: left side type undefined", 2);
            }
        }
        if (!rightSideType.equals(localType)) {
            throwTypeException("Ass violated: assignment types mismatch", 2);
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
        localType = new BasicType(BasicType.DataType.VOID);
        if (stmt.expr != null) localType = (DataType) stmt.expr.accept(this);
        if (!symbolTable.currentMethod.returnType.equals(localType))
            throwTypeException("ReturnStmt doesn't match return type", 2);
        return localType;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public Object visit(TwoFactorsArithExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(((BasicType) localType).equals(BasicType.DataType.INT) && localType.equals(expr.rightSide.accept(this))))
            throwTypeException("Arith violated: non int member", 2);
        return localType;
    }

    @Override
    public Object visit(TwoFactorsBoolExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(((BasicType) localType).equals(BasicType.DataType.BOOL) && localType.equals(expr.rightSide.accept(this))))
            throwTypeException("Bool violated: non bool member", 2);
        return localType;
    }

    @Override
    public Object visit(TwoFactorsRelExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(((BasicType) localType).equals(BasicType.DataType.INT) && localType.equals(expr.rightSide.accept(this))))
            throwTypeException("Rel violated: non int member", 2);
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
            throwTypeException("Field violated: not a class", 2);
        //check id is a field or method of a leftSideAtom class, and get its type
        if (!symbolTable.isFieldOfClass(atom.field, ((ClassNameType) localType).name))
            throwTypeException("Field violated: class hasn't field " + atom.field, 2);
        localType = symbolTable.lookupClassFieldType((ClassNameType) localType, atom.field);
        return localType;
    }

    @Override
    public FunctionType visit(AtomFunctionCall atom) throws Exception { //TODO impl
        //lookUp function name after having understood the type of atom
        if ((atom.atom instanceof AtomGrd)) {

        }
        if (!(localType instanceof FunctionType))
            throwTypeException("LocalCall violated: not a function name", 2);

        return (FunctionType) localType;
    }

    @Override
    public DataType visit(AtomGrd atom) throws TypeException {
        localType = null;
        if (atom.isNullGround()) localType = new BasicType(BasicType.DataType.NULL); //TODO check how to deal with it
        else if (atom.isThisGround()) localType = symbolTable.currentClass;
        else if (atom.isIdentifierGround()) {
            localType = symbolTable.lookupVarType(atom.id);
            if (localType == null) {
                throwTypeException("Id violated: there is not such an identifier as " + atom.id, 2);
            }
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
            throwTypeException("Class " + classDescriptor.className + " has two vars with the same name", 0);
        //different params names in methods
        for (MethodSignature m : classDescriptor.methodSignatures) {
            if (!m.params.allVarsHaveDifferentIds()) {
                throwTypeException("Two params with same name in method " + m.name + " of Class " + classDescriptor.className.toString(), 0);
            }
        }
        //different methods
        for (int i = 0; i < classDescriptor.methodSignatures.size(); i++) {
            for (int j = i + 1; j < classDescriptor.methodSignatures.size(); j++) {
                if (classDescriptor.methodSignatures.get(i).equals(classDescriptor.methodSignatures.get(j)))
                    throwTypeException("Two methods with the same signature in class" + classDescriptor.className.toString(), 0);
            }
        }
        //TODO whatch out for params which don't exist
    }

    private void throwTypeException(String issue, int depth) throws TypeException {
        switch (depth) {
            case 1:
                throw new TypeException(issue, symbolTable.currentClass.name);
            case 2:
                throw new TypeException(issue, symbolTable.currentClass.name, symbolTable.currentMethod.name);
            case 3:
                throw new TypeException(issue + " in Stmt ", symbolTable.currentClass.name, symbolTable.currentMethod.name);
            default:
                throw new TypeException(issue);
        }
    }


}
