package jnodes;


import utils.ClassNameType;

// CLASS OBJECT LBRACE VOID MAIN LPAREN fmlList RPAREN mdBody RBRACE
public class JMainClass extends JNode {
    public JClassNameType cname;
    public JFmlList fmlList;
    public JMdBody mdBody;

    public JMainClass(JClassNameType cname, JFmlList fmlList, JMdBody mdBody) {
        this.cname = cname;
        this.fmlList = fmlList;
        this.mdBody = mdBody;
    }

    @Override
    public String toString() {
        return "class " + cname + "{\n    Void main(" + fmlList + ")" + mdBody + "\n}\n";
    }
}
