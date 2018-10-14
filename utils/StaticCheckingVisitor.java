package utils;

import concrete_nodes.*;
import concrete_nodes.expressions.*;

import java.util.ArrayList;
import java.util.List;

public class StaticCheckingVisitor implements Visitor {

    private SymbolTable symbolTable;
    private BasicType localType;

    public StaticCheckingVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public Object visit(Program program) throws Exception {
        newLine();
        System.out.println("------------- Static Checking Output------------------");
        symbolTable.indentLevel++;

        //generate and check class descriptors
        symbolTable.classDescriptors.add(genClassDescriptor(program.mainClass));
        for (ClassDecl c : program.classDeclList) {
            symbolTable.classDescriptors.add(genClassDescriptor(c));
        }
        for (ClassDescriptor c : symbolTable.classDescriptors) {
            checkClassDescriptor(c);
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
        program.mainClass.accept(this);
        for (ClassDecl c : program.classDeclList) {
            c.accept(this);
        }
        newLine();
        System.out.println("------------- End Static Checking Output------------------");
        return null;
    }

    @Override
    public Object visit(MainClass mainClass) throws Exception {
        newLine();
        System.out.print("\nMainClass-" + mainClass.className.name);
        symbolTable.increaseIndentLevel(mainClass.className);
        enrichEnvironmentWithVars(new VarsList(mainClass.varDeclList), false);
        typeCheckClass(mainClass);
        symbolTable.decreaseIndentLevel();
        return null;
    }

    @Override
    public Object visit(ClassDecl classDecl) throws Exception {
        newLine();
        System.out.print("\nClassDecl-" + classDecl.className.name);
        symbolTable.increaseIndentLevel(classDecl.className);
        enrichEnvironmentWithVars(new VarsList(classDecl.varDeclList), false);
        typeCheckClass(classDecl);
        symbolTable.decreaseIndentLevel();
        return null;
    }

    private void typeCheckClass(ClassDecl classDecl) throws Exception {
        for (MethodDecl m : classDecl.methodDeclList) {
            if (!m.returnType.equals(m.accept(this))) {
                throwTypeException("method body type doesn't match return type", 2);
            }
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
            symbolTable.pushMethodVar(entry);
        }
        enrichEnvironmentWithVars(methodDecl.varDeclList, true);

        //check if the statements are correct
        for (Stmt s : methodDecl.stmtList) {
            localType = (BasicType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        return localType;
    }

    private void enrichEnvironmentWithVars(VarsList list, boolean addToMethod) throws TypeException {
        for (VarDecl entry : list.list) {
            localType = entry.type;
            newLine();
            printObjType(entry);
            if (localType instanceof ClassNameType && symbolTable.lookUpClass(((ClassNameType) localType).name) == null)
                throwTypeException("Class " + ((ClassNameType) localType).name + " doesn't exist", 2);
            if (addToMethod) {
                symbolTable.pushMethodVar(entry);
            } else {
                symbolTable.pushClassVar(entry);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////// Stmts ///////////////////////////////////////////////

    @Override
    public BasicType visit(IfStmt stmt) throws Exception {
        newLine();
        System.out.print("IfStmt ");
        if (!((BasicType) stmt.condition.accept(this)).equals(BasicType.DataType.BOOL))
            throwTypeException("If condition violated: not Bool ", 2);
        printObjType(stmt.condition);
        symbolTable.increaseIndentLevel();
        for (Stmt s : stmt.trueBranch) {
            localType = (BasicType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        newLine();
        System.out.print("ElseBranch: ");
        DataType trueBranchType = localType;
        symbolTable.increaseIndentLevel();
        for (Stmt s : stmt.falseBranch) {
            localType = (BasicType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        if (!localType.equals(trueBranchType)) throwTypeException("IfStmt branches type mismatch", 2);
        printObjType("Stmt");
        return localType;
    }

    @Override
    public BasicType visit(WhileStmt stmt) throws Exception {
        newLine();
        System.out.print("WhileStmt ");
        if (!((BasicType) stmt.condition.accept(this)).equals(BasicType.DataType.BOOL))
            throwTypeException("While condition violated: not Bool ", 2);
        symbolTable.increaseIndentLevel();
        localType = new BasicType(BasicType.DataType.NULL);
        for (Stmt s : stmt.body) {
            localType = (BasicType) s.accept(this);
        }
        symbolTable.decreaseIndentLevel();
        printObjType("Stmt");
        return localType;
    }

    @Override
    public BasicType visit(ReadlnStmt stmt) throws TypeException {
        newLine();
        System.out.print("ReadlnStmt ");
        BasicType idType = symbolTable.lookupVarType(stmt.id);
        if (!(idType.equals(BasicType.DataType.BOOL) || idType.equals(BasicType.DataType.STRING) || idType.equals(BasicType.DataType.INT)))
            throwTypeException("ReadlnStmt can't be applied to this identifier", 2);
        localType = new BasicType(BasicType.DataType.VOID);
        printObjType("Stmt");
        return localType;
    }

    @Override
    public Object visit(PrintlnStmt stmt) throws Exception {
        newLine();
        System.out.print("PrintlnStmt ");
        BasicType idType = (BasicType) stmt.expr.accept(this);
        if (!(idType.equals(BasicType.DataType.BOOL) || idType.equals(BasicType.DataType.STRING) || idType.equals(BasicType.DataType.INT)))
            throwTypeException("PrintStmt can't be applied to this expression", 2);
        localType = new BasicType(BasicType.DataType.VOID);
        printObjType("Stmt");
        return localType;
    }

    @Override
    public BasicType visit(AssignmentStmt stmt) throws Exception {
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
        localType = new BasicType(BasicType.DataType.VOID);
        printObjType("Stmt");
        return localType;
    }

    @Override
    public BasicType visit(FunctionCallStmt stmt) throws Exception {
        newLine();
        System.out.print("FunctionCallStmt ");
        localType = (BasicType) stmt.atom.accept(this);
        printObjType(stmt.atom);
        return localType;
    }

    @Override
    public BasicType visit(ReturnStmt stmt) throws Exception {
        newLine();
        System.out.print("ReturnStmt ");
        localType = new BasicType(BasicType.DataType.VOID);
        if (stmt.expr != null) localType = (BasicType) stmt.expr.accept(this);
        if (!symbolTable.currentMethod.returnType.equals(localType))
            throwTypeException("ReturnStmt doesn't match return type", 2);
        printObjType("Stmt");
        return localType;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// Expressions /////////////////////////////////

    @Override
    public BasicType visit(TwoFactorsArithExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(localType.equals(BasicType.DataType.INT) && localType.equals(expr.rightSide.accept(this))))
            throwTypeException("Arith violated: non int member", 2);
        expr.type = localType;
        return localType;
    }

    @Override
    public BasicType visit(TwoFactorsBoolExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(localType.equals(BasicType.DataType.BOOL) && localType.equals(expr.rightSide.accept(this))))
            throwTypeException("Bool violated: non bool member", 2);
        expr.type = localType;
        return localType;
    }

    @Override
    public BasicType visit(TwoFactorsRelExpr expr) throws Exception {
        localType = (BasicType) (expr.leftSide.accept(this));
        if (!(localType.equals(BasicType.DataType.INT) && localType.equals(expr.rightSide.accept(this))))
            throwTypeException("Rel violated: non int member", 2);
        localType = new BasicType(BasicType.DataType.BOOL);
        expr.type = localType;
        return localType;
    }

    @Override
    public BasicType visit(ArithGrdExpr expr) throws Exception {
        if (expr.isIntLiteral()) localType = new BasicType(BasicType.DataType.INT);
        else if (expr.isAtomGrd()) localType = (BasicType) expr.atom.accept(this);
        else if (expr.isNegateArithGrd()) localType = (BasicType) expr.negateFactor.accept(this);
        else throw new Exception("ArithGrdExpr " + expr + " shouldn't be null");
        expr.type = localType;
        return localType;
    }

    @Override
    public BasicType visit(StringExpr expr) {
        localType = new BasicType(BasicType.DataType.STRING);
        expr.type = localType;
        return localType;
    }

    @Override
    public BasicType visit(BoolGrdExpr expr) throws Exception {
        localType = new BasicType(BasicType.DataType.BOOL);
        if (expr.isNegatedGround()) localType = (BasicType) expr.grdExpr.accept(this);
        else if (expr.isAtomGround()) localType = (BasicType) expr.atom.accept(this);
        expr.type = localType;
        return localType;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////// Expressions - Atoms /////////////////////////////////

    @Override
    public BasicType visit(AtomClassInstantiation atom) {
        return symbolTable.lookUpClass(atom.cname.toString());
    }

    @Override
    public BasicType visit(AtomFieldAccess atom) throws Exception {
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
    public BasicType visit(AtomFunctionCall atom) throws Exception {
        List<FunctionType> matchingFunctions;
        String functionId = "";
        ClassNameType functionClass = null;
        if (atom.functionId instanceof AtomFieldAccess) {
            localType = (BasicType) ((AtomFieldAccess) atom.functionId).atom.accept(this);
            if (!(localType instanceof ClassNameType))
                throwTypeException("FunctionCall violated: not a function identifier ", 2);
            functionId = ((AtomFieldAccess) atom.functionId).field;
            functionClass = (ClassNameType) localType;
        }

        //atom.functionId must indeed be a function id. Get that function type
        else if (atom.functionId instanceof AtomGrd) {
            //it's a local call
            functionId = ((AtomGrd) atom.functionId).id;
            functionClass = symbolTable.currentClass;

        } else {
            throwTypeException("FunctionCall violated: not a function identifier ", 2);
        }

        matchingFunctions = symbolTable.lookupFunctionTypeInClass(functionClass, functionId);
        if (matchingFunctions.isEmpty()) throwTypeException("FunctionCall violated: not a function identifier ", 2);

        ArrayList<BasicType> params = new ArrayList<>();
        for (Expr expr : atom.paramsList) {
            params.add((BasicType) expr.accept(this));
        }
        FunctionType matchingFunction = null;
        for (FunctionType f : matchingFunctions) {
            if (f.paramsMatch(params)) matchingFunction = f;
        }
        if (matchingFunction == null) throwTypeException("FunctionCall violated: params mismatch", 2);
        localType = matchingFunction.returnType;

        return localType;
    }

    @Override
    public BasicType visit(AtomGrd atom) throws TypeException {
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
    public BasicType visit(AtomParenthesizedExpr atom) throws Exception {
        return (BasicType) atom.expr.accept(this);
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
        //no fake Cnames in fields
        for (VarDecl v : classDescriptor.classFields.list) {
            if ((v.type instanceof ClassNameType) && symbolTable.lookUpClass(((ClassNameType) v.type).name) == null)
                throwTypeException("Class " + v.type + " doesn't exist", 0);
        }
        //different params names in methods
        for (MethodSignature m : classDescriptor.methodSignatures) {
            if (!m.params.allVarsHaveDifferentIds()) {
                throwTypeException("Two params with same name in method " + m.name + " of Class " + classDescriptor.className.toString(), 0);
            }
            //check return type is no fake Cname
            if ((m.returnType instanceof ClassNameType) && symbolTable.lookUpClass(((ClassNameType) m.returnType).name) == null)
                throwTypeException("Class " + m.returnType + " doesn't exist in Method:" + m.name + " of Class " + classDescriptor.className.toString(), 0);

            //check there is no fake Cname
            for (VarDecl v : m.params.list) {
                if ((v.type instanceof ClassNameType) && symbolTable.lookUpClass(((ClassNameType) v.type).name) == null)
                    throwTypeException("Class " + v.type + " doesn't exist in Method:" + m.name + " of Class " + classDescriptor.className.toString(), 0);
            }
        }
        //different methods
        for (int i = 0; i < classDescriptor.methodSignatures.size(); i++) {
            for (int j = i + 1; j < classDescriptor.methodSignatures.size(); j++) {
                if (classDescriptor.methodSignatures.get(i).equals(classDescriptor.methodSignatures.get(j)))
                    throwTypeException("Two methods with the same signature in class" + classDescriptor.className.toString(), 0);
            }
        }
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
