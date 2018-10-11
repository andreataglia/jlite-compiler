package utils;

import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;
import concrete_nodes.expressions.Atom;
import concrete_nodes.expressions.AtomGrd;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class SymbolTable {
    int indentLevel;
    List<ClassDescriptor> classDescriptors;
    private Stack<List<VarDecl>> enviromentVars;
    ClassNameType currentClass;
    MethodDecl currentMethod;

    SymbolTable() {
        this.indentLevel = 0;
        enviromentVars = new Stack<>();
        classDescriptors = new ArrayList<>();
    }

    void increaseIndentLevel() {
        indentLevel++;
        enviromentVars.push(new ArrayList<>());
    }

    void decreaseIndentLevel() {
        enviromentVars.pop();
        indentLevel--;
    }

    void increaseIndentLevel(MethodDecl methodDecl) {
        increaseIndentLevel();
        currentMethod = methodDecl;
    }

    void increaseIndentLevel(ClassNameType classNameType) {
        increaseIndentLevel();
        currentClass = classNameType;
    }

    void pushLocalVar(VarDecl varDecl) {
        enviromentVars.peek().add(varDecl);
    }

    boolean isFieldOfClass(String field, String className) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.equals(className)) {
                if (c.getFieldType(field) != null) return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////////LookUps///////////////////////////////////////////


    BasicType lookupClassFieldType(ClassNameType classNameType, String id) {
        for (ClassDescriptor c : classDescriptors) {
            if (c.className.equals(classNameType)) {
                return c.getFieldType(id);
            }
        }
        return null;
    }

    BasicType lookupVarType(String id) {
        for (int i = enviromentVars.size() - 1; i > 0; i--) {
            if (enviromentVars.get(i) != null) {
                for (VarDecl var : enviromentVars.get(i)) {
                    if (var.id.equals(id)) return var.type;
                }
            }
        }
        return null;
    }

    FunctionType lookupFunctionType(Atom atom) {
        FunctionType ret = null;
        if (atom instanceof AtomGrd) {
            if (((AtomGrd) atom).isIdentifierGround()) ret = null;
        }
        return ret;
    }

    FunctionType lookupFunctionType(String id) {
        for (ClassDescriptor c : classDescriptors) {
            for (MethodSignature m : c.methodSignatures) {
                if (m.name.equals(id)) return m.getFunctionType();
            }
        }
        return null;
    }

    ClassNameType lookUpClass(String className) {
        for (ClassDescriptor c : classDescriptors) {
            System.out.println(c.className);
            if (c.className.name.equals(className)) return c.className;
        }
        return null;
    }
}
