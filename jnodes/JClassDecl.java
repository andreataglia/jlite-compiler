package jnodes;

import concrete_nodes.ClassDecl;

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
        return new ClassDecl();
    }
}
