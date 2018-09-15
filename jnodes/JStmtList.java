package jnodes;

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
}
