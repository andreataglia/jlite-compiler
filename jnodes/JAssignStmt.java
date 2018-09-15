package jnodes;

// ident EQUAL exp SEMICOLON
// | atom DOT ident EQUAL exp SEMICOLON
public class JAssignStmt extends JStmt {
    public JId id;
    public JExp exp;
    public JAtom atom;

    public JAssignStmt(JId id, JExp exp) {
        this.id = id;
        this.exp = exp;
    }

    public JAssignStmt(JId id, JExp exp, JAtom atom) {
        this.id = id;
        this.exp = exp;
        this.atom = atom;
    }

    @Override
    public String toString() {
        return (atom != null ? atom + "." : "") + id + "=" + exp + ";";
    }
}
