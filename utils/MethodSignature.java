package utils;

import concrete_nodes.MethodDecl;

import java.util.Map;

public class MethodSignature {
    String name;
    BasicType returnType;
    VarsList params;

    public MethodSignature(String name, BasicType returnType, VarsList params) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    public static MethodSignature fromMethodDecl(MethodDecl methodDecl) {
        VarsList varDecls = new VarsList();
        if (!methodDecl.params.isEmpty()) {
            for (Map.Entry<String, BasicType> entry : methodDecl.params.entrySet()) {
                varDecls.add(new VarDecl(entry.getKey(), entry.getValue()));
            }
        }
        return new MethodSignature(methodDecl.name, methodDecl.returnType, varDecls);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodSignature) {
            MethodSignature element = (MethodSignature) obj;
            return this.name.equals(element.name) && returnType.equals(returnType) && this.params.equals(element.params);
        }
        return false;
    }
}
