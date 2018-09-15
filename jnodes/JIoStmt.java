package jnodes;

//      READLN LPAREN ident RPAREN SEMICOLON
//      | PRINTLN LPAREN exp RPAREN SEMICOLON
public class JIoStmt extends JStmt {
    public String ioOperation;
    public JId id;
    public JExp exp;

    public JIoStmt(String ioOperation, JId id) {
        this.ioOperation = ioOperation;
        this.id = id;
    }

    public JIoStmt(String ioOperation, JExp exp) {
        this.ioOperation = ioOperation;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return ioOperation + "(" + (id == null ? exp : id) + ");\n";
    }
}
