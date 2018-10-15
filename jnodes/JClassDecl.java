package jnodes;

import concrete_nodes.ClassDecl;
import concrete_nodes.VarDecl;
import utils.BasicType;
import utils.ClassNameType;

import java.util.HashMap;
import java.util.List;

//classDecl ::= CLASS cname LBRACE classDeclBody RBRACE
public class JClassDecl extends JNode{
    public JClassNameType cname;
    public JClassDeclBody classDeclBody;

    public JClassDecl(JClassNameType cname, JClassDeclBody classDeclBody) {
        this.cname = cname;
        this.classDeclBody = classDeclBody;
    }

    @Override
    public String toString() {
        return "class " + cname + "{\n" + classDeclBody + "\n}";
    }

    ClassDecl genConcreteClass(){
        List<VarDecl> varDeclList = classDeclBody.getVarDeclList();
        return new ClassDecl((ClassNameType) cname.basicType, varDeclList, classDeclBody.getMethodDeclList());
    }
}
