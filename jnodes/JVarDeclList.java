package jnodes;

import java.util.ArrayList;

//  varDeclList ::=
//              | varDecl varDeclList
public class JVarDeclList extends JNode {

    public JVarDecl varDecl;
    public JVarDeclList varDeclList;

    public JVarDeclList(JVarDecl varDecl, JVarDeclList varDeclList) {
        this.varDecl = varDecl;
        this.varDeclList = varDeclList;
    }

    public JVarDeclList() {
    }

    @Override
    public String toString() {
        return varDecl == null ? "" : varDecl + " " + varDeclList;
    }

    ArrayList<JVarDecl> getVarsList(){
        ArrayList<JVarDecl> list = new ArrayList<>();
        if (varDecl != null) {
            list.add(varDecl);
            list.addAll(varDeclList.getVarsList());
        }
        return list;
    }
}