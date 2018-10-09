package jnodes;

import concrete_nodes.Stmt;
import concrete_nodes.WhileStmt;

//WHILE LPAREN exp RPAREN LBRACE stmt stmtList RBRACE
public class JWhileStmt extends JStmt {
    public JExp exp;
    public JStmtList stmtList;

    public JWhileStmt(JExp exp, JStmtList stmtList) {
        this.exp = exp;
        this.stmtList = stmtList;
    }

    @Override
    public String toString() {
        return "while(" + exp + "){\n" + stmtList + "\n}\n";
    }

    @Override
    Stmt getConcreteStmt() {
        return new WhileStmt(exp.getConcreteExpr(), stmtList.getStmtList());
    }
}
