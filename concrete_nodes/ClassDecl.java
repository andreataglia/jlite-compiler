package concrete_nodes;

import utils.ClassNameType;
import utils.Visitor;

import java.util.List;

public class ClassDecl extends Node {
    public ClassNameType className;
    public List<VarDecl> varDeclList;
    public List<MethodDecl> methodDeclList;

    public ClassDecl(ClassNameType className, List<VarDecl> varDeclList, List<MethodDecl> methodDeclList) {
        this.className = className;
        this.varDeclList = varDeclList;
        this.methodDeclList = methodDeclList;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
