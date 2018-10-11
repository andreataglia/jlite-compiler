package utils;

import concrete_nodes.VarDecl;

import java.util.List;

class ClassDescriptor {
    ClassNameType className;
    VarsList classFields;
    List<MethodSignature> methodSignatures;

    ClassDescriptor(ClassNameType className, VarsList classFields, List<MethodSignature> methodSignatures) {
        this.className = className;
        this.classFields = classFields;
        this.methodSignatures = methodSignatures;
    }

    BasicType getFieldType(String varId){
        for (VarDecl v: classFields.list) {
            if (v.id == varId) return v.type;
        }
        return null;
    }
}
