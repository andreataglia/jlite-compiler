package jnodes;

//classDeclBody ::=
//                | type ident SEMICOLON classDeclBody
//                | type ident LPAREN fmlList RPAREN mdBody classDeclBody
//                ;
public class JClassDeclBody extends JNode{
    public JType type;
    public JId id;
    public JClassDeclBody classDeclBody;

    public JFmlList fmlList;
    public JMdBody mdBody;

    String print;

    public JClassDeclBody(JType type, JId id, JClassDeclBody classDeclBody) {
        this.type = type;
        this.id = id;
        this.classDeclBody = classDeclBody;
        print = type + " " + id +";\n" + classDeclBody;
    }

    public JClassDeclBody(JType type, JId id, JFmlList fmlList, JMdBody mdBody, JClassDeclBody classDeclBody) {
        this.type = type;
        this.id = id;
        this.fmlList = fmlList;
        this.mdBody = mdBody;
        this.classDeclBody = classDeclBody;
        print = type + " " + id + "(" + fmlList + ")" + mdBody + " " + classDeclBody;
    }

    public JClassDeclBody() {
        print = "";
    }

    @Override
    public String toString() {
        return print;
    }
}
