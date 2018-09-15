package jnodes;

//WHILE LPAREN exp RPAREN LBRACE stmt stmtList RBRACE
public class JWhileStmt extends JStmt {
    public JExp exp;
    public JStmt stmt;
    public JStmtList stmtList;

    public JWhileStmt(JExp exp, JStmt stmt, JStmtList stmtList) {
        this.exp = exp;
        this.stmt = stmt;
        this.stmtList = stmtList;
    }

    @Override
    public String toString() {
        return "while(" + exp + "){\n" + stmt + " " + stmtList + "\n}\n";
    }
}
