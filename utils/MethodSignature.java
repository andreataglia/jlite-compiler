package utils;

import concrete_nodes.MethodDecl;
import concrete_nodes.VarDecl;

import java.util.ArrayList;
import java.util.List;

public class MethodSignature {
    String name;
    BasicType returnType;
    VarsList params;

    private MethodSignature(String name, BasicType returnType, VarsList params) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    static MethodSignature fromMethodDecl(MethodDecl methodDecl) {
        VarsList varDecls = new VarsList();
        if (!methodDecl.params.list.isEmpty()) {
            for (VarDecl entry : methodDecl.params.list) {
                varDecls.add(entry);
            }
        }
        return new MethodSignature(methodDecl.name, methodDecl.returnType, varDecls);
    }

    FunctionType getFunctionType() {
        ArrayList<BasicType> params = new ArrayList<>();
        for (VarDecl v : this.params.list) {
            params.add(v.type);
        }
        return new FunctionType(params, returnType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodSignature) {
            MethodSignature element = (MethodSignature) obj;
            return this.name.equals(element.name) && this.params.equals(element.params);
        }
        return false;
    }
}
