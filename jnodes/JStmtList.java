package jnodes;

import concrete_nodes.Stmt;

import java.util.ArrayList;

//stmtList ::=
//           | stmt stmtList
public class JStmtList extends JNode{
    public JStmt stmt;
    public JStmtList stmtList;

    String print;

    public JStmtList(JStmt stmt, JStmtList stmtList) {
        this.stmt = stmt;
        this.stmtList = stmtList;

        print = stmt + " " + stmtList;
    }

    public JStmtList() {
        print = "";
    }

    @Override
    public String toString() {
        return print;
    }

    ArrayList<Stmt> getStmtList(){
        ArrayList<Stmt> list = new ArrayList<>();
        if (stmt != null){
            list.add(stmt.getConcreteStmt());
            list.addAll(stmtList.getStmtList());
        }
        return list;
    }
}
