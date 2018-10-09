package utils;

import java.util.List;

class ClassDescriptor {
    String className;
    VarsList classFields;
    List<MethodSignature> methodSignatures;

    public ClassDescriptor(String className, VarsList classFields, List<MethodSignature> methodSignatures) {
        this.className = className;
        this.classFields = classFields;
        this.methodSignatures = methodSignatures;
    }
}
