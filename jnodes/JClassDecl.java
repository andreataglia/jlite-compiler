package jnodes;


//classDecl ::= CLASS cname LBRACE classDeclBody RBRACE
public class JClassDecl extends JNode{
    public JCname cname;
    public JClassDeclBody classDeclBody;

    public JClassDecl(JCname cname, JClassDeclBody classDeclBody) {
        this.cname = cname;
        this.classDeclBody = classDeclBody;
    }

    @Override
    public String toString() {
        return "class " + cname + "{\n" + classDeclBody + "\n}";
    }
}
