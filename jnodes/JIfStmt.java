package jnodes;

import concrete_nodes.IfStmt;
import concrete_nodes.Stmt;

import java.util.ArrayList;

//IF LPAREN exp RPAREN LBRACE stmt stmtList RBRACE ELSE LBRACE stmt stmtList RBRACE
public class JIfStmt extends JStmt {
    public JExp exp;
    public JStmt stmt1;
    public JStmtList stmtList1;
    public JStmt stmt2;
    public JStmtList stmtList2;

    public JIfStmt(JExp exp, JStmt stmt1, JStmtList stmtList1, JStmt stmt2, JStmtList stmtList2) {
        this.exp = exp;
        this.stmt1 = stmt1;
        this.stmtList1 = stmtList1;
        this.stmt2 = stmt2;
        this.stmtList2 = stmtList2;
    }

    @Override
    public String toString() {
        return "if(" + exp + "){\n" + stmt1 + " " + stmtList1 + "}else{\n" + stmt2 + " " + stmtList2 + "}";
    }

    @Override
    Stmt getConcreteStmt() {
        return new IfStmt(exp.getConcreteExpr(), getStmtList1(), getStmtList2());
    }

    private ArrayList<Stmt> getStmtList1() {
        ArrayList<Stmt> list = new ArrayList<>();
        list.add(stmt1.getConcreteStmt());
        list.addAll(stmtList1.getStmtList());
        return list;
    }

    private ArrayList<Stmt> getStmtList2() {
        ArrayList<Stmt> list = new ArrayList<>();
        list.add(stmt2.getConcreteStmt());
        list.addAll(stmtList2.getStmtList());
        return list;
    }
}
