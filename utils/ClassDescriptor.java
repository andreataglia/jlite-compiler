package utils;

import java.util.List;

class ClassDescriptor {
    String className;
    List<VarDecl> classFields;
    List<MethodSignature> classMethodsSignatures;

    public ClassDescriptor(String className, List<VarDecl> classFields, List<MethodSignature> classMethodsSignatures) {
        this.className = className;
        this.classFields = classFields;
        this.classMethodsSignatures = classMethodsSignatures;
    }
}
