package jnodes;

import concrete_nodes.ClassDecl;
import utils.BasicType;
import utils.ClassNameType;

import java.util.HashMap;

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
        HashMap<String, BasicType> varDeclList = classDeclBody.getVarDeclList();
        return new ClassDecl((ClassNameType) cname.basicType, varDeclList, classDeclBody.getMethodDeclList());
    }
}
