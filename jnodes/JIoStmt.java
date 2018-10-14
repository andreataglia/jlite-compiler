package jnodes;

import concrete_nodes.PrintlnStmt;
import concrete_nodes.ReadlnStmt;
import concrete_nodes.Stmt;
import concrete_nodes.expressions.AtomGrd;

//      READLN LPAREN ident RPAREN SEMICOLON
//      | PRINTLN LPAREN exp RPAREN SEMICOLON
public class JIoStmt extends JStmt {
    public String ioOperation;
    public JId id;
    public JExp exp;

    Stmt stmt;

    public JIoStmt(String ioOperation, JId id) {
        this.ioOperation = ioOperation;
        this.id = id;

        stmt = new ReadlnStmt(new AtomGrd(id.s));
    }

    public JIoStmt(String ioOperation, JExp exp) {
        this.ioOperation = ioOperation;
        this.exp = exp;

        stmt = new PrintlnStmt(exp.getConcreteExpr());
    }

    @Override
    public String toString() {
        return ioOperation + "(" + (id == null ? exp : id) + ");\n";
    }

    @Override
    Stmt getConcreteStmt() {
        return stmt;
    }
}
