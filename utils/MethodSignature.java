package utils;

import concrete_nodes.MethodDecl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodSignature {
    String name;
    BasicType returnType;
    List<VarDecl> params;

    public MethodSignature(String name, BasicType returnType, List<VarDecl> params) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    FunctionType getMethodSignature() {
        ArrayList<BasicType> par = new ArrayList<>();
        for (VarDecl v: params) {
            par.add(v.type);
        }
        return new FunctionType(par, returnType);
    }

    public static MethodSignature fromMethodDecl(MethodDecl methodDecl){
        List<VarDecl> varDecls = new ArrayList<>();
        for (Map.Entry<String, BasicType> entry: methodDecl.params.entrySet()) {
            varDecls.add(new VarDecl(entry.getKey(), entry.getValue()));
        }
        return new MethodSignature(methodDecl.name, methodDecl.returnType, varDecls);
    }
}
