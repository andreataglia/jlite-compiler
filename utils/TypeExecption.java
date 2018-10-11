package utils;

import concrete_nodes.ClassDecl;
import concrete_nodes.MethodDecl;

public class TypeExecption extends Exception {

    public TypeExecption(String issue) {
        super(issue);
    }

    public TypeExecption(String issue, String className) {
        this(issue + " in Class:" + className);
    }

    public TypeExecption(String issue, String inClass, String inMethod) {
        this(issue + " in Method:" + inMethod , inClass);
    }
}
