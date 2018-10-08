package concrete_nodes;

import utils.BasicType;
import utils.ClassNameType;
import utils.Visitor;

import java.util.HashMap;
import java.util.List;

public class ClassDecl extends Node {
    public ClassNameType className;
    public HashMap<String, BasicType> varDeclList;
    public List<MethodDecl> methodDeclList;

    public ClassDecl(ClassNameType className, HashMap<String, BasicType> varDeclList, List<MethodDecl> methodDeclList) {
        this.className = className;
        this.varDeclList = varDeclList;
        this.methodDeclList = methodDeclList;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
