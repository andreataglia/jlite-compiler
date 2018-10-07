package jnodes;

import java.util.ArrayList;
import java.util.List;

//mdBody ::= LBRACE varDeclList stmt stmtList RBRACE
public class JMdBody extends JNode{
    public List<JVarDecl> varDeclList;
    public JStmt stmt;
    public JStmtList stmtList;

    public JMdBody(ArrayList<JVarDecl> varDeclList, JStmt stmt, JStmtList stmtList) {
        this.varDeclList = varDeclList;
        this.stmt = stmt;
        this.stmtList = stmtList;
    }

    @Override
    public String toString() {
        return "{\n    " + varDeclList + stmt + stmtList + "}";
    }
}
