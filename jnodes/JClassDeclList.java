package jnodes;

//classDeclList ::=
//                | classDecl classDeclList
public class JClassDeclList extends JNode {
    public JClassDecl classDecl;
    public JClassDeclList classDeclList;

    public JClassDeclList(JClassDecl classDecl, JClassDeclList classDeclList) {
        this.classDecl = classDecl;
        this.classDeclList = classDeclList;
    }

    public JClassDeclList() {
    }

    @Override
    public String toString() {
        return classDecl != null ? classDecl + " " + classDeclList : "";
    }
}