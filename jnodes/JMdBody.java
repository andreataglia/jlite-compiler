package jnodes;

//mdBody ::= LBRACE varDeclList stmt stmtList RBRACE
public class JMdBody extends JNode{
    public JVarDeclList varDeclList;
    public JStmt stmt;
    public JStmtList stmtList;

    public JMdBody(JVarDeclList varDeclList, JStmt stmt, JStmtList stmtList) {
        this.varDeclList = varDeclList;
        this.stmt = stmt;
        this.stmtList = stmtList;
    }

    @Override
    public String toString() {
        return "{\n    " + varDeclList + stmt + stmtList + "}";
    }
}
